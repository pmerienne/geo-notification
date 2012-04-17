package com.pmerienne.geonotification.shared.model;

import java.io.Serializable;
import java.util.Arrays;

import com.google.gwt.maps.client.geom.LatLngBounds;

public class Bounds implements Serializable {

	private static final long serialVersionUID = 1L;

	private double[] northEast = new double[2];

	private double[] southWest = new double[2];

	public Bounds() {
	}

	public Bounds(LatLngBounds bounds) {
		this.northEast[0] = bounds.getNorthEast().getLatitude();
		this.northEast[1] = bounds.getNorthEast().getLongitude();
		this.southWest[0] = bounds.getSouthWest().getLatitude();
		this.southWest[1] = bounds.getSouthWest().getLongitude();
	}

	public double[] getNorthEast() {
		return northEast;
	}

	public void setNorthEast(double[] northEast) {
		this.northEast = northEast;
	}

	public double[] getSouthWest() {
		return southWest;
	}

	public void setSouthWest(double[] southWest) {
		this.southWest = southWest;
	}

	public boolean isValid() {
		if (this.northEast.equals(this.southWest)) {
			return false;
		}
		if (this.northEast[0] == this.southWest[0] || this.northEast[1] == this.southWest[1]) {
			return false;
		}
		return true;
	}

	public boolean contains(double[] position) {
		double notificationLatitude = position[0];
		double notificationLongitude = position[1];

		// Border value
		double maxLatitude = this.getNorthEast()[0];
		double minLatitude = this.getSouthWest()[0];
		double maxLongitude = this.getNorthEast()[1];
		double minLongitude = this.getSouthWest()[1];

		// Compare to border
		return notificationLatitude > minLatitude && notificationLatitude < maxLatitude && notificationLongitude > minLongitude
				&& notificationLongitude < maxLongitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(northEast);
		result = prime * result + Arrays.hashCode(southWest);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bounds other = (Bounds) obj;
		if (!Arrays.equals(northEast, other.northEast))
			return false;
		if (!Arrays.equals(southWest, other.southWest))
			return false;
		return true;
	}

}
