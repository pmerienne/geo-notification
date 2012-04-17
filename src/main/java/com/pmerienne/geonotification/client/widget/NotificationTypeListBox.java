package com.pmerienne.geonotification.client.widget;

import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.pmerienne.geonotification.shared.model.NotificationType;

public class NotificationTypeListBox extends MListBox {

	public NotificationTypeListBox() {
		super();
		for (NotificationType type : NotificationType.values()) {
			this.addItem(type.getName(), type.name());
		}
	}

	public NotificationType getValue() {
		// Get selected index
		int index = this.getSelectedIndex();

		// Return null if no item is selected
		if (index < 0) {
			return null;
		}

		// Gettting type
		String value = this.getValue(index);
		return NotificationType.valueOf(value);
	}
}
