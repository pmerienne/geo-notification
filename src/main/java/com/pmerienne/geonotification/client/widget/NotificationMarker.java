package com.pmerienne.geonotification.client.widget;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.pmerienne.geonotification.client.theme.ApplicationTheme;
import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;

public class NotificationMarker extends Marker {

	@SuppressWarnings("serial")
	private final static Map<NotificationType, String> ICONS = new HashMap<NotificationType, String>() {
		{
			this.put(NotificationType.ACCIDENT, ApplicationTheme.get().getApplicationThemeBundle().accident().getSafeUri().asString());
			this.put(NotificationType.OTHER, ApplicationTheme.get().getApplicationThemeBundle().divers().getSafeUri().asString());
			this.put(NotificationType.PARKING, ApplicationTheme.get().getApplicationThemeBundle().parking().getSafeUri().asString());
			this.put(NotificationType.RADAR_MOBILE, ApplicationTheme.get().getApplicationThemeBundle().radar().getSafeUri().asString());
			this.put(NotificationType.TRANSPORT_INCIDENT, ApplicationTheme.get().getApplicationThemeBundle().divers().getSafeUri().asString());
			this.put(NotificationType.TRAVAUX, ApplicationTheme.get().getApplicationThemeBundle().travaux().getSafeUri().asString());
		}
	};

	private Notification notification;

	public NotificationMarker(Notification notification) {
		super(LatLng.newInstance(notification.getPosition()[0], notification.getPosition()[1]), NotificationMarker.createOptions(notification));
		this.notification = notification;
	}

	private static MarkerOptions createOptions(Notification notification) {
		// Create icon
		String imageUrl = ICONS.get(notification.getType());
		Icon icon = Icon.newInstance(imageUrl);
		icon.setIconSize(Size.newInstance(40, 40));
		icon.setIconAnchor(Point.newInstance(20, 20));

		// Create options
		MarkerOptions options = MarkerOptions.newInstance();
		options.setIcon(icon);
		return options;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

}
