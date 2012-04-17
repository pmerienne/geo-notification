package com.pmerienne.geonotification.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.pmerienne.geonotification.shared.model.NotificationType;

public class NotificationTypeCheckList extends WidgetList implements HasValueChangeHandlers<List<NotificationType>> {

	private Map<MCheckBox, NotificationType> notificationTypes = new HashMap<MCheckBox, NotificationType>();

	public NotificationTypeCheckList() {
		super();

		// Add types
		for (NotificationType type : NotificationType.values()) {
			this.addCheckBox(type);
		}
	}

	private void addCheckBox(NotificationType type) {
		// Container
		HTMLPanel container = new HTMLPanel("");

		// Type name
		Label typeName = new Label(type.getName());
		typeName.getElement().getStyle().setFloat(Float.LEFT);
		container.add(typeName);

		// Check box
		MCheckBox checkBox = new MCheckBox();
		checkBox.getElement().getStyle().setFloat(Float.RIGHT);
		// Send an event on value change
		checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				List<NotificationType> types = NotificationTypeCheckList.this.getCheckedNotificationTypes();
				ValueChangeEvent.fire(NotificationTypeCheckList.this, types);
			}
		});
		container.add(checkBox);

		this.notificationTypes.put(checkBox, type);
		this.add(container);
	}

	private List<NotificationType> getCheckedNotificationTypes() {
		List<NotificationType> types = new ArrayList<NotificationType>();
		for (MCheckBox checkBox : this.notificationTypes.keySet()) {
			if (checkBox.getValue()) {
				NotificationType type = this.notificationTypes.get(checkBox);
				types.add(type);
			}
		}
		return types;
	}

	public void setCheckedNotificationType(List<NotificationType> types) {
		for (MCheckBox checkBox : this.notificationTypes.keySet()) {
			NotificationType type = this.notificationTypes.get(checkBox);
			checkBox.setValue(types.contains(type), false);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<NotificationType>> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}

}
