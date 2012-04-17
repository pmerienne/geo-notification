package com.pmerienne.geonotification.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.storage.client.Storage;
import com.kfuntak.gwt.json.serialization.client.Serializer;

public class LocalStorageUtils {

	private final static Storage STORAGE = Storage.getLocalStorageIfSupported();
	private final static Serializer SERIALIZER = (Serializer) GWT.create(Serializer.class);

	public final static String KEY_SEPARATOR = ":";

	public static void save(String id, Object obj) {
		if (STORAGE == null) {
			throw new IllegalArgumentException("Storage not available");
		}
		String key = LocalStorageUtils.getKey(id, obj.getClass());
		String json = SERIALIZER.serialize(obj);
		STORAGE.setItem(key, json);
	}

	@SuppressWarnings("unchecked")
	public static <T> T find(String id, Class<T> clazz) {
		if (STORAGE == null) {
			throw new IllegalArgumentException("Storage not available");
		}
		T obj = null;
		try {
			String key = LocalStorageUtils.getKey(id, clazz);
			String json = STORAGE.getItem(key);
			if (json != null) {
				obj = (T) SERIALIZER.deSerialize(json);
			}
		} catch (Throwable caught) {
			GWT.log("Load fail.", caught);
			obj = null;
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> findAll(Class<T> clazz) {
		if (STORAGE == null) {
			return null;
		}
		String keyPrefix = getPrefix(clazz);

		List<T> results = new ArrayList<T>();
		String key;
		T obj;
		// Iterate all over the storage
		for (int i = 0; i < STORAGE.getLength(); i++) {
			key = STORAGE.key(i);
			if (key.startsWith(keyPrefix)) {
				try {
					obj = (T) SERIALIZER.deSerialize(STORAGE.getItem(key));
					results.add(obj);
				} catch (Throwable caught) {
					GWT.log("Load fail.", caught);
				}
			}
		}
		return results;
	}

	public static void delete(String id, Class<?> clazz) {
		try {
			String key = LocalStorageUtils.getKey(id, clazz);
			STORAGE.removeItem(key);
		} catch (Throwable caught) {
			GWT.log("Delete fail.", caught);
		}
	}

	public static void clearData() {
		STORAGE.clear();
	}

	public static String getKey(String id, Class<?> clazz) {
		return getPrefix(clazz) + id;
	}

	public static String getPrefix(Class<?> clazz) {
		return clazz.getName() + KEY_SEPARATOR;
	}
}
