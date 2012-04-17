package com.pmerienne.geonotification.client.activity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.pmerienne.geonotification.client.ClientFactory;
import com.pmerienne.geonotification.client.service.Services;
import com.pmerienne.geonotification.client.utils.Preferences;
import com.pmerienne.geonotification.client.view.HomeView;
import com.pmerienne.geonotification.client.widget.NotificationList;
import com.pmerienne.geonotification.shared.messaging.NotificationSavedHandler;
import com.pmerienne.geonotification.shared.messaging.NotificationSavedMessage;
import com.pmerienne.geonotification.shared.model.Bounds;
import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;

public class HomeActivity extends MobileActivity implements HomeView.Presenter {

	private HandlerRegistration notificationSavedHandlerRegistration;

	private Preferences preferences;

	public HomeActivity(ClientFactory clientFactory) {
		super(clientFactory);
		bind();
	}

	@Override
	protected IsWidget getView() {
		return this.clientFactory.getHomeView();
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		// Load view
		final HomeView view = this.clientFactory.getHomeView();
		view.setPresenter(this);

		// Load preferences
		this.preferences = Preferences.loadStoredPreferences();
		view.setAcceptedNotificationTypes(this.preferences.getAcceptedTypes());


		// Load last notifications
		this.loadLastNotifications(NotificationList.LIMIT);

	}

	@Override
	public void onStop() {
		super.onStop();
		unbind();
	}

	private void bind() {
		this.notificationSavedHandlerRegistration = this.clientFactory.getEventBus().addHandler(NotificationSavedMessage.getType(),
				new NotificationSavedHandler() {
					@Override
					public void onNotificationSaved(NotificationSavedMessage message) {
						clientFactory.getHomeView().addNewNotification(message.getNotification());
					}
				});
	}

	private void unbind() {
		if (this.notificationSavedHandlerRegistration != null) {
			this.notificationSavedHandlerRegistration.removeHandler();
		}
	}

	@Override
	public void updateAcceptedTypes(List<NotificationType> acceptedTypes) {
		// Set and save preferences
		this.preferences.setAcceptedTypes(acceptedTypes);
		Preferences.storePreferences(preferences);

		HomeView view = this.clientFactory.getHomeView();

		// Change event filtering
		this.clientFactory.getRemoteMessageDispatcher().updateFilter(acceptedTypes);

		// Reload notifications
		view.clearNotifications();
		this.loadLastNotifications(NotificationList.LIMIT);
		this.loadMapNotifications(view.getDisplayedBounds());
	}

	@Override
	public void loadMapNotifications(Bounds bounds) {
		// Load events
		Services.getNotifcationService().findNotifications(bounds, this.preferences.getAcceptedTypes(), new AsyncCallback<List<Notification>>() {
			@Override
			public void onSuccess(List<Notification> notifications) {
				clientFactory.getHomeView().setMapNotifications(notifications);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to load notifications : " + caught.getMessage());
			}
		});
	}

	@Override
	public void loadLastNotifications(Integer limit) {
		// Load events
		Services.getNotifcationService().findNotifications(this.preferences.getAcceptedTypes(), limit, new AsyncCallback<List<Notification>>() {
			@Override
			public void onSuccess(List<Notification> notifications) {
				clientFactory.getHomeView().setLastNotifications(notifications);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to load notifications : " + caught.getMessage());
			}
		});
	}
	
	@Override
	public void loadNotificationDetail(String notificationId) {
		Services.getNotifcationService().findById(notificationId, new AsyncCallback<Notification>() {
			@Override
			public void onSuccess(Notification notification) {
				clientFactory.getHomeView().showNotificationDetail(notification);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Impossible de charger la publication : " + caught.getMessage());
			}
		});
	}

	@Override
	public void create(NotificationType type, String name, String description, double[] position) {
		this.setPending(true);
		Services.getNotifcationService().create(type, name, description, position, new AsyncCallback<Notification>() {
			@Override
			public void onFailure(Throwable caught) {
				setPending(false);
				Window.alert("Save failed : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Notification result) {
				clientFactory.getHomeView().clearNotificationForm();
				clientFactory.getHomeView().showLastNotificationsList();
				setPending(false);
			}
		});
	}

	@Override
	public void votePlus(Notification notification) {
		this.setPending(true);
		Services.getNotifcationService().votePlus(notification.getId(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				setPending(false);
				Window.alert("Vote impossible : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				clientFactory.getHomeView().showNewNotificationForm();
				clientFactory.getHomeView().showMap();
				setPending(false);
			}
		});
	}

	@Override
	public void voteMinus(Notification notification) {
		this.setPending(true);
		Services.getNotifcationService().voteMinus(notification.getId(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				setPending(false);
				Window.alert("Vote impossible : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				clientFactory.getHomeView().showNewNotificationForm();
				clientFactory.getHomeView().showMap();
				setPending(false);
			}
		});
	}

	@Override
	public void voteEnd(Notification notification) {
		this.setPending(true);
		Services.getNotifcationService().voteEnd(notification.getId(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				setPending(false);
				Window.alert("Vote impossible : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				clientFactory.getHomeView().showNewNotificationForm();
				clientFactory.getHomeView().showMap();
				setPending(false);
			}
		});
	}

	@Override
	public void goTo(Place place) {
		this.clientFactory.getPlaceController().goTo(place);
	}

}
