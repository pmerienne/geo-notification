package com.pmerienne.geonotification.server.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("applicationContextHolder")
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext CONTEXT = null;

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		CONTEXT = ctx;
	}

	public static ApplicationContext getContext() {
		return CONTEXT;
	}

}
