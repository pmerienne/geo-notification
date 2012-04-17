package com.pmerienne.geonotification.client.widget;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.googlecode.mgwt.ui.client.widget.celllist.CellListWithHeader;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.googlecode.mgwt.ui.client.widget.celllist.HasCellSelectedHandler;
import com.pmerienne.geonotification.client.utils.DateUtils;
import com.pmerienne.geonotification.client.widget.list.BasicCell;
import com.pmerienne.geonotification.shared.model.Notification;

public class NotificationList extends CellListWithHeader<Notification> implements HasCellSelectedHandler {

	public final static Integer LIMIT = 40;

	private LinkedList<Notification> notifications = new LinkedList<Notification>();

	public NotificationList() {
		super(new BasicCell<Notification>() {
			@Override
			public String getTitleString(Notification notification) {
				String title = notification.getType().getName() + " : " + notification.getName();
				return title;
			}

			@Override
			public String getSecondaryString(Notification notification) {
				String note = "+" + notification.getPlus() + " -" + notification.getMinus() + ", fini : " + notification.getEnded();
				String date = DateUtils.timeAgo(notification.getCreationDate());
				return date + " (" + note + ")";
			}
		});
	}

	public void clear() {
		this.notifications = new LinkedList<Notification>();
		this.render();
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = new LinkedList<Notification>(notifications);
		this.render();
	}

	public void addNotification(Notification notification) {
		if (this.notifications.contains(notification)) {
			// On remplace
			int index = this.notifications.indexOf(notification);
			this.notifications.remove(index);
			this.notifications.add(index, notification);
		} else {
			// On ajoute au début
			this.notifications.addFirst(notification);
		}
		this.render();
	}

	public void render() {
		while (this.notifications.size() > LIMIT) {
			this.notifications.removeLast();
		}
		this.getCellList().render(this.notifications);
	}

	public Notification getNotification(int index) {
		return this.notifications.get(index);
	}

	@Override
	public HandlerRegistration addCellSelectedHandler(CellSelectedHandler cellSelectedHandler) {
		return this.getCellList().addCellSelectedHandler(cellSelectedHandler);
	}

	public void setRound(boolean round) {
		this.getCellList().setRound(round);
	}

}
