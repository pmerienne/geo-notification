package com.pmerienne.geonotification.server.messaging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pmerienne.geonotification.shared.model.Notification;

@Service("messagingService")
public class MessagingServiceImpl implements MessagingService {

	private List<MessageHandler> handlers = new ArrayList<MessageHandler>();

	public void onNotificationSaved(Notification event) {
		for (MessageHandler handler : this.handlers) {
			handler.onNotificationSaved(event);
		}
	}

	@Override
	public void addHandler(MessageHandler handler) {
		this.handlers.add(handler);
	}

	@Override
	public void removeHandler(MessageHandler handler) {
		this.handlers.remove(handler);
	}

}
