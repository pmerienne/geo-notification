package com.pmerienne.geonotification.shared.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pmerienne.geonotification.shared.model.Bounds;
import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;

public interface NotificationServiceAsync {

	void findById(String id, AsyncCallback<Notification> callback);

	void create(NotificationType type, String name, String description, double[] position, AsyncCallback<Notification> callback);

	void findNotifications(Bounds bounds, List<NotificationType> acceptedTypes, AsyncCallback<List<Notification>> callback);

	void findNotifications(List<NotificationType> acceptedTypes, Integer limit, AsyncCallback<List<Notification>> callback);

	void votePlus(String notificationId, AsyncCallback<Void> callback);

	void voteMinus(String notificationId, AsyncCallback<Void> callback);

	void voteEnd(String notificationId, AsyncCallback<Void> callback);

}
