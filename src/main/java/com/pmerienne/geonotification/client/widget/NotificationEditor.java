package com.pmerienne.geonotification.client.widget;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.ScaleControl;
import com.google.gwt.maps.client.control.SmallZoomControl3D;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.pmerienne.geonotification.client.view.HomeView.Presenter;

public class NotificationEditor extends Composite {

	private static NotificationEditorUiBinder uiBinder = GWT.create(NotificationEditorUiBinder.class);

	interface NotificationEditorUiBinder extends UiBinder<Widget, NotificationEditor> {
	}

	private final static String GOOGLE_API_KEY = "AIzaSyBI-TYk_olIac52DKOjq1T2x_dpdTEU5to";
	private final static String GOOGLE_API_VERSION = "2";

	private final static Integer DEFAULT_ZOOM = 17;

	@UiField
	HTMLPanel mapContainer;

	private MapWidget map;

	@UiField
	NotificationTypeListBox notificationType;

	@UiField
	MTextBox name;

	@UiField
	MTextArea description;

	private double[] currentPosition;

	// Position to init when the map is ready
	private double[] desiredPosition;

	private Presenter presenter;

	public NotificationEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		// Load UI async
		if (Maps.isLoaded()) {
			buildMap();
		} else {
			Maps.loadMapsApi(GOOGLE_API_KEY, GOOGLE_API_VERSION, true, new Runnable() {
				@Override
				public void run() {
					buildMap();
				}
			});
		}
	}

	@UiHandler("save")
	protected void onSaveTaped(TapEvent e) {
		// Check values
		if (this.name.getValue().isEmpty()) {
			Window.alert("Name is required");
			return;
		}

		this.presenter.create(this.notificationType.getValue(), this.name.getValue(), this.description.getValue(), this.currentPosition);
	}

	private void buildMap() {
		this.mapContainer.clear();
		this.mapContainer.setHeight((Document.get().getClientHeight() / 2) + "px");
		this.mapContainer.setWidth((Document.get().getClientHeight() * 2 / 3) + "px");

		// Build map widget
		this.map = new MapWidget();
		this.map.setSize("100%", "100%");
		this.map.addControl(new ScaleControl());
		this.map.addControl(new SmallZoomControl3D());
		this.map.setZoomLevel(DEFAULT_ZOOM);
		this.mapContainer.add(this.map);

		// Magic trick
		this.map.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getTabBarCss().tabPanel());

		// Center to desired position
		if (this.desiredPosition != null) {
			this.setPosition(this.desiredPosition);
		} else {
			this.centerToCurrentPosition();
		}

		// Set notification position on click
		this.map.addMapClickHandler(new MapClickHandler() {
			@Override
			public void onClick(MapClickEvent event) {
				setPosition(new double[] { event.getLatLng().getLatitude(), event.getLatLng().getLongitude() });
			}
		});
	}

	public void setPosition(Position position) {
		double latitude = position.getCoordinates().getLatitude();
		double longitude = position.getCoordinates().getLongitude();
		this.setPosition(new double[] { latitude, longitude });
	}

	public void setPosition(double[] position) {
		double latitude = position[0];
		double longitude = position[1];
		this.currentPosition = position;

		// Center map and add marker if API loaded
		if (this.map == null) {
			this.desiredPosition = new double[] { latitude, longitude };

		} else {
			// Center
			this.map.setCenter(LatLng.newInstance(latitude, longitude));

			// Add unique marker
			LatLng latLng = LatLng.newInstance(latitude, longitude);
			this.map.clearOverlays();
			this.map.addOverlay(new Marker(latLng));
		}
	}

	public void centerToCurrentPosition() {
		// Center to current position
		if (Geolocation.isSupported()) {
			Geolocation geolocation = Geolocation.getIfSupported();
			geolocation.getCurrentPosition(new Callback<Position, PositionError>() {
				@Override
				public void onSuccess(Position position) {
					setPosition(position);
				}

				@Override
				public void onFailure(PositionError reason) {
					Window.alert("Unable to get your current location : " + reason.getMessage());
				}
			});
		} else {
			Window.alert("Unable to get your current location");
		}
	}

	public void clear() {
		this.notificationType.setSelectedIndex(0);
		this.name.setText("");
		this.description.setText("");
		this.map.clearOverlays();
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
}
