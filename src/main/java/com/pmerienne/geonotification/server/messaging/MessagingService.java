package com.pmerienne.geonotification.server.messaging;

public interface MessagingService extends MessageHandler {

	void addHandler(MessageHandler handler);

	void removeHandler(MessageHandler handler);
}
