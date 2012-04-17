package com.pmerienne.geonotification.shared.messaging;

import com.google.gwt.event.shared.EventHandler;

public interface NotificationSavedHandler extends EventHandler {

	void onNotificationSaved(NotificationSavedMessage message);
}