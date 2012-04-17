package com.pmerienne.geonotification.server.messaging;

import com.pmerienne.geonotification.shared.model.Notification;

public interface MessageHandler {

	void onNotificationSaved(Notification notification);
}
