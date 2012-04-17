package com.pmerienne.geonotification.client.theme;

import com.google.gwt.core.client.GWT;
import com.googlecode.mgwt.ui.client.theme.MGWTClientBundle;
import com.googlecode.mgwt.ui.client.theme.MGWTTheme;

public class ApplicationTheme implements MGWTTheme {

	private final static ApplicationTheme INSTANCE = new ApplicationTheme();

	private final ApplicationThemeBundle bundle = GWT.create(ApplicationThemeBundle.class);

	public static ApplicationTheme get() {
		return INSTANCE;
	}

	@Override
	public MGWTClientBundle getMGWTClientBundle() {
		return bundle;
	}

	public ApplicationThemeBundle getApplicationThemeBundle() {
		return bundle;
	}
}
