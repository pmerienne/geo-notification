package com.pmerienne.geonotification.shared.model;

public enum NotificationType {

	OTHER("Autres", 1.0), TRANSPORT_INCIDENT("Incident de transport", 1.0), RADAR_MOBILE("Radar mobile", 0.5), ACCIDENT("Accident", 0.5), PARKING(
			"Place de parking libre", 0.1), TRAVAUX("Travaux", 2);

	private String name;

	private double durationInDay;

	NotificationType(String name, double durationInDay) {
		this.name = name;
		this.durationInDay = durationInDay;
	}

	public String getName() {
		return name;
	}

	public double getDuration() {
		return durationInDay;
	}

}
