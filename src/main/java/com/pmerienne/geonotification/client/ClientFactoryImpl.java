package com.pmerienne.geonotification.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.pmerienne.geonotification.client.messaging.RemoteMessageDispatcher;
import com.pmerienne.geonotification.client.view.HomeView;
import com.pmerienne.geonotification.client.view.HomeViewImpl;
import com.pmerienne.geonotification.client.view.PendingView;
import com.pmerienne.geonotification.client.view.PendingViewImpl;

public class ClientFactoryImpl implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();

	private final RemoteMessageDispatcher remoteMessageDispatcher = new RemoteMessageDispatcher(this);

	@SuppressWarnings("deprecation")
	private final PlaceController placeController = new PlaceController(eventBus);

	private final HomeView homeView = new HomeViewImpl();
	private final PendingView pendingView = new PendingViewImpl();

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public RemoteMessageDispatcher getRemoteMessageDispatcher() {
		return remoteMessageDispatcher;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public HomeView getHomeView() {
		return homeView;
	}

	@Override
	public PendingView getPendingView() {
		return pendingView;
	}
}
