package com.pmerienne.geonotification.client.messaging;

import java.util.List;

import com.pmerienne.geonotification.shared.messaging.NotificationSavedMessage;
import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.filter.EventFilter;

public class NotificationFilter implements EventFilter {

	private static final long serialVersionUID = 2400905688929276373L;

	private List<NotificationType> types;

	@Deprecated
	public NotificationFilter() {
		super();
	}

	public NotificationFilter(List<NotificationType> types) {
		super();
		this.types = types;
	}

	@Override
	public boolean match(Event e) {
		boolean accept = false;
		if (e instanceof NotificationSavedMessage) {
			Notification notification = ((NotificationSavedMessage) e).getNotification();
			accept = notification != null && isTypeAccepted(notification);
		}
		return !accept;
	}

	private boolean isTypeAccepted(Notification notification) {
		return this.types == null ? true : this.types.contains(notification.getType());
	}

}
