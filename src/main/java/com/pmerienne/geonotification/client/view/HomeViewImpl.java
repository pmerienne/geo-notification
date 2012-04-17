package com.pmerienne.geonotification.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapMoveEndHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabPanel;
import com.pmerienne.geonotification.client.widget.NotificationDetail;
import com.pmerienne.geonotification.client.widget.NotificationEditor;
import com.pmerienne.geonotification.client.widget.NotificationList;
import com.pmerienne.geonotification.client.widget.NotificationMap;
import com.pmerienne.geonotification.client.widget.NotificationMarker;
import com.pmerienne.geonotification.client.widget.NotificationTypeCheckList;
import com.pmerienne.geonotification.shared.model.Bounds;
import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;

public class HomeViewImpl extends Composite implements HomeView {

	private static HomeViewImplUiBinder uiBinder = GWT.create(HomeViewImplUiBinder.class);

	interface HomeViewImplUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}

	@UiField
	TabPanel tabPanel;

	@UiField
	NotificationMap map;

	@UiField
	NotificationList notificationList;

	@UiField
	NotificationEditor notificationEditor;

	@UiField
	NotificationDetail notificationDetail;

	@UiField
	NotificationTypeCheckList notificationTypeCheckList;

	private Presenter presenter;

	public HomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		// Load map notifications on drag
		this.map.addMapMoveEndHandler(new MapMoveEndHandler() {
			@Override
			public void onMoveEnd(MapMoveEndEvent event) {
				Bounds bounds = map.getBounds();
				if (bounds.isValid()) {
					presenter.loadMapNotifications(bounds);
				}
			}
		});

		// Map click handlers
		this.map.addMapClickHandler(new MapClickHandler() {
			@Override
			public void onClick(MapClickEvent event) {
				if (event.getOverlay() == null) {
					// Si on click sur la carte alors on ajoute un nouveau
					// marker
					double[] position = new double[] { event.getLatLng().getLatitude(), event.getLatLng().getLongitude() };
					notificationEditor.clear();
					showNewNotificationForm(position);
				} else if (event.getOverlay() instanceof NotificationMarker) {
					// Si on clique sur un marker alors on affiche son detail
					Notification notification = ((NotificationMarker) event.getOverlay()).getNotification();
					presenter.loadNotificationDetail(notification.getId());
				}
			}
		});

		// Change filtered types
		this.notificationTypeCheckList.addValueChangeHandler(new ValueChangeHandler<List<NotificationType>>() {
			@Override
			public void onValueChange(ValueChangeEvent<List<NotificationType>> event) {
				presenter.updateAcceptedTypes(event.getValue());
			}
		});
		
		// Update notification create date in the list
		this.tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if(event.getSelectedItem() == 1) {
					notificationList.render();
				}
			}
		});

	}

	@UiHandler("notificationList")
	protected void onNotificationSelected(CellSelectedEvent event) {
		int index = event.getIndex();
		Notification notification = this.notificationList.getNotification(index);
		this.presenter.loadNotificationDetail(notification.getId());
	}

	@Override
	public Bounds getDisplayedBounds() {
		return this.map.getBounds();
	}

	@Override
	public void setMapPosition(Position position) {
		this.map.setPosition(position);
	}

	@Override
	public void setMapNotifications(List<Notification> notifications) {
		this.map.addNotifications(notifications);
	}

	@Override
	public void setLastNotifications(List<Notification> notifications) {
		this.notificationList.setNotifications(notifications);
	}

	@Override
	public void addNewNotification(Notification notification) {
		this.map.addNotification(notification);
		this.notificationList.addNotification(notification);
	}

	@Override
	public void showMap() {
		this.tabPanel.setSelectedChild(0);
	}

	@Override
	public void showLastNotificationsList() {
		this.tabPanel.setSelectedChild(1);
	}

	@Override
	public void showNewNotificationForm() {
		this.notificationEditor.setVisible(true);
		this.notificationDetail.setVisible(false);
		this.tabPanel.setSelectedChild(2);

	}

	@Override
	public void showNewNotificationForm(double[] position) {
		this.notificationEditor.setVisible(true);
		this.notificationDetail.setVisible(false);
		this.notificationEditor.setPosition(position);
		this.tabPanel.setSelectedChild(2);
	}

	@Override
	public void showSettings() {
		this.tabPanel.setSelectedChild(3);
	}

	@Override
	public void showNotificationDetail(Notification notification) {
		this.notificationEditor.setVisible(false);
		this.notificationDetail.setVisible(true);
		this.notificationDetail.setNotification(notification);
		this.tabPanel.setSelectedChild(2);
	}

	@Override
	public void cleatNotificationDetail() {
		this.notificationDetail.clear();
	}

	@Override
	public void clearNotifications() {
		this.map.clear();
		this.notificationList.clear();
	}

	@Override
	public void clearNotificationForm() {
		this.notificationEditor.clear();
	}
	
	@Override
	public void setAcceptedNotificationTypes(List<NotificationType> types) {
		this.notificationTypeCheckList.setCheckedNotificationType(types);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		this.notificationDetail.setPresenter(presenter);
		this.notificationEditor.setPresenter(presenter);
	}

}
