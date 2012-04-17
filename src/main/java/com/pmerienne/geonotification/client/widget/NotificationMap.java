package com.pmerienne.geonotification.client.widget;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.Callback;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.ScaleControl;
import com.google.gwt.maps.client.control.SmallZoomControl3D;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapMoveEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;
import com.pmerienne.geonotification.shared.model.Bounds;
import com.pmerienne.geonotification.shared.model.Notification;

public class NotificationMap extends Composite {

	private final static String GOOGLE_API_KEY = "AIzaSyBI-TYk_olIac52DKOjq1T2x_dpdTEU5to";
	private final static String GOOGLE_API_VERSION = "2";

	private final static Integer DEFAULT_ZOOM = 15;

	private ProgressIndicator progressIndicator;

	// Position to init when the map is ready
	private double[] desiredPosition;

	// Handlers waiting for map to be registered
	private Set<MapMoveEndHandler> mapMoveEndHandlers = new HashSet<MapMoveEndHandler>();
	private Set<MapClickHandler> mapClickHandlers = new HashSet<MapClickHandler>();

	private Set<Notification> displayedMarker = new HashSet<Notification>();

	private MapWidget map;

	private HTMLPanel mainContent;

	public NotificationMap() {
		this.mainContent = new HTMLPanel("");
		initWidget(this.mainContent);

		// Magic trick
		this.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getTabBarCss().tabPanel());

		// Add loading indicator
		this.progressIndicator = new ProgressIndicator();
		this.progressIndicator.getElement().getStyle().setProperty("margin", "auto");
		this.progressIndicator.getElement().getStyle().setProperty("marginTop", "50px");
		this.mainContent.add(this.progressIndicator);

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

	private void buildMap() {
		this.mainContent.clear();

		// Build map widget
		this.map = new MapWidget();
		this.map.setSize("100%", "100%");
		this.map.addControl(new ScaleControl());
		this.map.addControl(new SmallZoomControl3D());
		this.map.setZoomLevel(DEFAULT_ZOOM);
		this.mainContent.add(this.map);
		// Magic trick
		this.map.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getTabBarCss().tabPanel());

		// Add waiting handlers
		for (MapMoveEndHandler handler : this.mapMoveEndHandlers) {
			this.map.addMapMoveEndHandler(handler);
		}

		for (MapClickHandler handler : this.mapClickHandlers) {
			this.map.addMapClickHandler(handler);
		}
		
		// Center to desired position
		if(this.desiredPosition != null) {
			this.setPosition(this.desiredPosition);
		} else {
			this.centerToCurrentPosition();
		}
		
		this.mapMoveEndHandlers.clear();
		this.mapClickHandlers.clear();
	}

	public void addNotifications(List<Notification> notifications) {
		for (Notification notification : notifications) {
			this.addNotification(notification);
		}
	}

	public void addNotification(Notification notification) {
		if (!this.displayedMarker.contains(notification) && this.isInBounds(notification)) {
			this.displayedMarker.add(notification);
			final NotificationMarker marker = new NotificationMarker(notification);
			// Add on the map
			this.map.addOverlay(marker);
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

	public void setPosition(Position position) {
		double latitude = position.getCoordinates().getLatitude();
		double longitude = position.getCoordinates().getLongitude();
		this.setPosition(new double[] { latitude, longitude });
	}

	public void setPosition(double[] position) {
		double latitude = position[0];
		double longitude = position[1];
		if (this.map == null) {
			this.desiredPosition = new double[] { latitude, longitude };
		} else {
			this.map.setCenter(LatLng.newInstance(latitude, longitude));
		}
	}

	public Bounds getBounds() {
		LatLngBounds bounds = this.map.getBounds();
		return new Bounds(bounds);
	}

	public void clear() {
		this.displayedMarker.clear();
		this.map.clearOverlays();
	}

	/**
	 * Add a click handler for mouse click events. Note: this event fires even
	 * for double click events (twice!).
	 * 
	 * @param handler
	 *            handler to invoke on mouse click events.
	 */
	public void addMapClickHandler(final MapClickHandler handler) {
		if (this.map != null) {
			this.map.addMapClickHandler(handler);
		} else {
			this.mapClickHandlers.add(handler);
		}
	}

	/**
	 * This event is fired when the change of the map view ends.
	 * 
	 * @param handler
	 *            the handler to call when this event fires.
	 */
	public void addMapMoveEndHandler(final MapMoveEndHandler handler) {
		if (this.map != null) {
			this.map.addMapMoveEndHandler(handler);
		} else {
			this.mapMoveEndHandlers.add(handler);
		}
	}

	private boolean isInBounds(Notification notification) {
		Bounds bounds = new Bounds(this.map.getBounds());
		double[] position = notification.getPosition();
		return bounds.contains(position);
	}
}
