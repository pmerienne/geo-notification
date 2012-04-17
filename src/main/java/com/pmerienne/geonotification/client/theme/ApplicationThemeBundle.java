package com.pmerienne.geonotification.client.theme;

import com.google.gwt.resources.client.ImageResource;
import com.googlecode.mgwt.ui.client.theme.base.HeaderCss;
import com.googlecode.mgwt.ui.client.theme.base.MGWTClientBundleBaseThemeIPhone;
import com.googlecode.mgwt.ui.client.theme.base.MainCss;

public interface ApplicationThemeBundle extends MGWTClientBundleBaseThemeIPhone {

	@Override
	@Source({ "css/main.css" })
	public MainCss getMainCss();

	@Override
	@Source({ "css/header.css" })
	HeaderCss getHeaderCss();

	/*
	 * Tab bar
	 */
	@Source("image/world.png")
	ImageResource world();

	@Source("image/clock.png")
	ImageResource clock();

	@Source("image/tag.png")
	ImageResource tag();

	@Source("image/settings.png")
	ImageResource settings();

	/*
	 * Markers
	 */
	@Source("image/accident.png")
	ImageResource accident();

	@Source("image/divers.png")
	ImageResource divers();

	@Source("image/parking.png")
	ImageResource parking();

	@Source("image/radar.png")
	ImageResource radar();

	@Source("image/travaux.png")
	ImageResource travaux();

}
