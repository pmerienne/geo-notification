package com.pmerienne.geonotification.client.widget.tabbar;

import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.theme.base.TabBarCss;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;
import com.pmerienne.geonotification.client.theme.ApplicationTheme;

public class MapTabBarButton extends TabBarButton {

	public MapTabBarButton() {
		this(MGWTStyle.getTheme().getMGWTClientBundle().getTabBarCss());
	}

	public MapTabBarButton(TabBarCss css) {
		super(css, ApplicationTheme.get().getApplicationThemeBundle().world());
		setText("Autour de moi");
	}

}