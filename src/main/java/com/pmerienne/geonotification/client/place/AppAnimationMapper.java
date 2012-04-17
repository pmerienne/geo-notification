package com.pmerienne.geonotification.client.place;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class AppAnimationMapper implements AnimationMapper {

	private final static Animation DEFAULT_ANIMATION = Animation.FADE;

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {
		// Default
		return DEFAULT_ANIMATION;
	}

}
