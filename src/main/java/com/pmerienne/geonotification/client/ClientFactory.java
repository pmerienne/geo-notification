package com.pmerienne.geonotification.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.pmerienne.geonotification.client.messaging.RemoteMessageDispatcher;
import com.pmerienne.geonotification.client.view.HomeView;
import com.pmerienne.geonotification.client.view.PendingView;

public interface ClientFactory {

	EventBus getEventBus();

	RemoteMessageDispatcher getRemoteMessageDispatcher();

	PlaceController getPlaceController();

	HomeView getHomeView();

	PendingView getPendingView();
}
