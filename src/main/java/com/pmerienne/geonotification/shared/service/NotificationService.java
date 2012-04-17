package com.pmerienne.geonotification.shared.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pmerienne.geonotification.shared.model.Bounds;
import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;

@RemoteServiceRelativePath("notificationService.rpc")
public interface NotificationService extends RemoteService {

	Notification findById(String id);

	List<Notification> findNotifications(Bounds bounds, List<NotificationType> acceptedTypes);

	List<Notification> findNotifications(List<NotificationType> acceptedTypes, Integer limit);

	Notification create(NotificationType type, String name, String description, double[] position);

	void votePlus(String notificationId);

	void voteMinus(String notificationId);

	void voteEnd(String notificationId);

}
