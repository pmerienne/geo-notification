package com.pmerienne.geonotification.client.view;

import java.util.List;

import com.google.gwt.geolocation.client.Position;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.pmerienne.geonotification.shared.model.Bounds;
import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;

public interface HomeView extends IsWidget {

	public interface Presenter {

		void goTo(Place place);

		void loadMapNotifications(Bounds bounds);

		void loadLastNotifications(Integer limit);
		
		void loadNotificationDetail(String notificationId);

		void updateAcceptedTypes(List<NotificationType> acceptedTypes);

		void create(NotificationType type, String name, String description, double[] position);

		void votePlus(Notification notification);

		void voteMinus(Notification notification);

		void voteEnd(Notification notification);

	}

	/*
	 * Global
	 */
	void setPresenter(Presenter presenter);

	void addNewNotification(Notification notification);

	void clearNotifications();

	/*
	 * Map
	 */
	void showMap();

	void setMapPosition(Position position);

	Bounds getDisplayedBounds();

	void setMapNotifications(List<Notification> notifications);

	/*
	 * Last notifications
	 */
	void showLastNotificationsList();

	void setLastNotifications(List<Notification> notifications);

	/*
	 * Notification
	 */
	void showNotificationDetail(Notification notification);

	void showNewNotificationForm();

	void showNewNotificationForm(double[] position);

	void clearNotificationForm();

	void cleatNotificationDetail();

	/*
	 * Settings
	 */
	void showSettings();

	void setAcceptedNotificationTypes(List<NotificationType> types);
}
