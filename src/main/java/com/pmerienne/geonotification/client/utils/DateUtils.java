package com.pmerienne.geonotification.client.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateUtils {

	private final static List<String> SINGULAR = Arrays.asList("seconde", "minute", "heure", "jour", "semaine", "mois", "année");
	private final static List<String> PLURAL = Arrays.asList("secondes", "minutes", "heures", "jours", "semaines", "mois", "années");
	private final static List<Long> LENGTHS = Arrays.asList(1000L, 60 * 1000L, 60 * 60 * 1000L, 24 * 60 * 60 * 1000L, 7 * 24 * 60 * 60 * 1000L, 31 * 7 * 24
			* 60 * 60 * 1000L, 365 * 31 * 7 * 24 * 60 * 60 * 1000L);

	private final static String AGO = "Il y a ";
	private final static String NOW = "Actuellement";

	public static String timeAgo(Date date) {
		Date now = new Date();
		long difference = now.getTime() - date.getTime();

		// If in the future then it's now!!
		if (difference < 0) {
			return NOW;
		}

		// Calculate lowest dividable amount
		int i = 7;
		long amount = 0;
		while (i > 0 && amount < 1) {
			i--;
			amount = difference / LENGTHS.get(i);
		}

		if (amount == 0) {
			return NOW;
		}

		boolean isPlural = amount > 1;
		return AGO + " " + amount + " " + (isPlural ? PLURAL.get(i) : SINGULAR.get(i));
	}
}
