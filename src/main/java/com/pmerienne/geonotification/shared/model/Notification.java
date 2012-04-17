package com.pmerienne.geonotification.shared.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Notification implements Serializable {

	private static final long serialVersionUID = 4652642721488179452L;

	private String id;

	private String name;

	private String description;

	private Date creationDate;

	private NotificationType type;

	private double[] position;

	private Integer plus;

	private Integer minus;

	private Integer ended;

	public Notification() {
		super();
		this.creationDate = new Date();
		this.type = NotificationType.OTHER;
		this.plus = 0;
		this.minus = 0;
		this.ended = 0;
	}

	public Notification(NotificationType type, String name, double[] position) {
		this();
		this.name = name;
		this.type = type;
		this.position = position;
	}

	public Notification(NotificationType type, String name, String description, double[] position) {
		this(type, name, position);
		this.description = description;
	}

	public void votePlus() {
		this.plus++;
	}

	public void voteMinus() {
		this.minus++;
	}

	public void voteEnd() {
		this.ended++;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public Integer getPlus() {
		return plus;
	}

	public void setPlus(Integer plus) {
		this.plus = plus;
	}

	public Integer getMinus() {
		return minus;
	}

	public void setMinus(Integer minus) {
		this.minus = minus;
	}

	public Integer getEnded() {
		return ended;
	}

	public void setEnded(Integer ended) {
		this.ended = ended;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(position);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Notification other = (Notification) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(position, other.position))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", type=" + type + ", name=" + name + ", position=" + Arrays.toString(position) + "]";
	}

}
