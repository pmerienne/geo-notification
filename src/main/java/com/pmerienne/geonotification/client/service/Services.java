package com.pmerienne.geonotification.client.service;

import com.google.gwt.core.client.GWT;
import com.pmerienne.geonotification.shared.service.NotificationService;
import com.pmerienne.geonotification.shared.service.NotificationServiceAsync;

public class Services {

	private final static NotificationServiceAsync eventService = GWT.create(NotificationService.class);

	public static NotificationServiceAsync getNotifcationService() {
		return eventService;
	}
}
