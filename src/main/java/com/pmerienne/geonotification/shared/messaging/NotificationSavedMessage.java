package com.pmerienne.geonotification.shared.messaging;

import com.google.gwt.event.shared.GwtEvent;
import com.pmerienne.geonotification.shared.model.Notification;

import de.novanic.eventservice.client.event.Event;

public class NotificationSavedMessage extends GwtEvent<NotificationSavedHandler> implements Event {

	private static final long serialVersionUID = -6017567341228759706L;

	private static Type<NotificationSavedHandler> TYPE;

	public static Type<NotificationSavedHandler> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<NotificationSavedHandler>());
	}

	@Override
	protected void dispatch(NotificationSavedHandler handler) {
		handler.onNotificationSaved(this);
	}

	@Override
	public GwtEvent.Type<NotificationSavedHandler> getAssociatedType() {
		return getType();
	}

	private Notification notification;

	@Deprecated
	public NotificationSavedMessage() {
	}

	public NotificationSavedMessage(Notification notification) {
		this.notification = notification;
	}

	public Notification getNotification() {
		return notification;
	}

}