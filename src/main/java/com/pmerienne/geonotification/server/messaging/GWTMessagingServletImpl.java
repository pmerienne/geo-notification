package com.pmerienne.geonotification.server.messaging;

import javax.servlet.ServletException;

import com.pmerienne.geonotification.server.service.ApplicationContextHolder;
import com.pmerienne.geonotification.shared.messaging.NotificationSavedMessage;
import com.pmerienne.geonotification.shared.model.Notification;

import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.service.EventServiceImpl;

public class GWTMessagingServletImpl extends EventServiceImpl implements MessageHandler {

	private static final long serialVersionUID = 4925922732085721124L;

	private final static Domain EVENT_DOMAIN = DomainFactory.getDomain("EVENT_DOMAIN");

	@Override
	public void onNotificationSaved(Notification event) {
		this.addEvent(EVENT_DOMAIN, new NotificationSavedMessage(event));
	}

	@Override
	public void init() throws ServletException {
		super.init();
		MessagingService messagingService = ApplicationContextHolder.getContext().getBean(MessagingService.class);
		messagingService.addHandler(this);
	}
}
