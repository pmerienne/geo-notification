package com.pmerienne.geonotification.client.messaging;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.pmerienne.geonotification.client.ClientFactory;
import com.pmerienne.geonotification.shared.model.NotificationType;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public class RemoteMessageDispatcher implements RemoteEventListener {

	private EventBus eventBus;

	private final static Domain EVENT_DOMAIN = DomainFactory.getDomain("EVENT_DOMAIN");

	private RemoteEventService remoteEventService;

	public RemoteMessageDispatcher(ClientFactory clientFactory) {
		this.eventBus = clientFactory.getEventBus();
		this.remoteEventService = (RemoteEventService) RemoteEventServiceFactory.getInstance().getRemoteEventService();
		this.remoteEventService.addListener(EVENT_DOMAIN, this);
	}

	public void updateFilter(List<NotificationType> types) {
		this.remoteEventService.deregisterEventFilter(EVENT_DOMAIN);
		this.remoteEventService.registerEventFilter(EVENT_DOMAIN, new NotificationFilter(types));
	}

	@Override
	public void apply(Event event) {
		if (event instanceof GwtEvent<?>) {
			this.eventBus.fireEvent((GwtEvent<?>) event);
		}
	}

}
