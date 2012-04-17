package com.pmerienne.geonotification.client.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.storage.client.Storage;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;
import com.pmerienne.geonotification.shared.model.NotificationType;

public class Preferences implements Serializable, JsonSerializable {

	private static final long serialVersionUID = 8771272397841778887L;

	private static final String PREFERENCES_KEY = "PREFERENCES";

	private List<NotificationType> acceptedTypes = new ArrayList<NotificationType>();

	public Preferences() {
		this.acceptedTypes = Arrays.asList(NotificationType.values());
	}

	public Preferences(List<NotificationType> acceptedTypes) {
		this.acceptedTypes = acceptedTypes;
	}

	public List<NotificationType> getAcceptedTypes() {
		return acceptedTypes;
	}

	public void setAcceptedTypes(List<NotificationType> acceptedTypes) {
		this.acceptedTypes = acceptedTypes;
	}

	public static Preferences loadStoredPreferences() {
		Preferences preferences = null;
		// Find preferences in storage
		if (Storage.isLocalStorageSupported()) {
			preferences = LocalStorageUtils.find(PREFERENCES_KEY, Preferences.class);
		}

		// Create default preference if no preferences was found
		if (preferences == null) {
			preferences = new Preferences();
		}

		return preferences;
	}

	public static void storePreferences(Preferences preferences) {
		if (Storage.isLocalStorageSupported()) {
			LocalStorageUtils.save(PREFERENCES_KEY, preferences);
		}
	}
}
