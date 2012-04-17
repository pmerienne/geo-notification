package com.pmerienne.geonotification.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort.DENSITY;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.pmerienne.geonotification.client.activity.AppActivityMapper;
import com.pmerienne.geonotification.client.place.AppAnimationMapper;
import com.pmerienne.geonotification.client.place.AppPlaceHistoryMapper;
import com.pmerienne.geonotification.client.place.HomePlace;
import com.pmerienne.geonotification.client.theme.ApplicationTheme;

public class Application implements EntryPoint {

	@Override
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				// Window.alert("uncaught: " + e.getMessage());
				e.printStackTrace();
			}
		});

		new Timer() {
			@Override
			public void run() {
				start();
			}

		}.schedule(1);
	}

	@SuppressWarnings("deprecation")
	private void start() {
		// MGWT settings
		MGWTStyle.setTheme(ApplicationTheme.get());
		ViewPort viewPort = new MGWTSettings.ViewPort();
		viewPort.setTargetDensity(DENSITY.HIGH);
		viewPort.setUserScaleAble(false).setMinimumScale(1.0).setMinimumScale(1.0).setMaximumScale(1.0);
		MGWTSettings settings = new MGWTSettings();
		settings.setViewPort(viewPort);
		settings.setAddGlosToIcon(true);
		settings.setIconUrl("logo.png");
		settings.setFullscreen(true);
		settings.setPreventScrolling(true);
		MGWT.applySettings(settings);

		// Init display
		final ClientFactory clientFactory = new ClientFactoryImpl();
		AnimatableDisplay display = GWT.create(AnimatableDisplay.class);
		RootPanel.get().add(display);

		// Init activity and places mappers
		AppActivityMapper appActivityMapper = new AppActivityMapper(clientFactory);
		AppAnimationMapper appAnimationMapper = new AppAnimationMapper();
		AnimatingActivityManager activityManager = new AnimatingActivityManager(appActivityMapper, appAnimationMapper, clientFactory.getEventBus());
		activityManager.setDisplay(display);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(clientFactory.getPlaceController(), (EventBus) clientFactory.getEventBus(), new HomePlace());
		historyHandler.handleCurrentHistory();
	}
}
