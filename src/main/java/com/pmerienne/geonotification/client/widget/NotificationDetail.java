package com.pmerienne.geonotification.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.pmerienne.geonotification.client.utils.DateUtils;
import com.pmerienne.geonotification.client.view.HomeView.Presenter;
import com.pmerienne.geonotification.shared.model.Notification;

public class NotificationDetail extends Composite {

	private static NotificationDetailUiBinder uiBinder = GWT.create(NotificationDetailUiBinder.class);

	interface NotificationDetailUiBinder extends UiBinder<Widget, NotificationDetail> {
	}

	@UiField
	HeadingElement title;

	@UiField
	HTML timeAgo;

	@UiField
	HTML eventDescription;

	@UiField
	Button votePlus;

	@UiField
	Button voteMinus;

	@UiField
	Button voteEnd;

	private Presenter presenter;

	private Notification notification;

	public NotificationDetail() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("votePlus")
	protected void onVotePlusTaped(TapEvent event) {
		this.presenter.votePlus(notification);
	}

	@UiHandler("voteMinus")
	protected void onVoteMinusTaped(TapEvent event) {
		this.presenter.voteMinus(notification);
	}

	@UiHandler("voteEnd")
	protected void onVoteEndTaped(TapEvent event) {
		this.presenter.voteEnd(notification);
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
		if (notification != null) {
			// Title and description
			this.title.setInnerHTML(notification.getType().getName() + " : " + notification.getName());
			this.timeAgo.setText(DateUtils.timeAgo(notification.getCreationDate()) + " : ");
			this.eventDescription.setText(notification.getDescription());

			// Vote button
			this.votePlus.setText("+1 (" + notification.getPlus() + ")");
			this.voteMinus.setText("-1 (" + notification.getMinus() + ")");
			this.voteEnd.setText("Fin (" + notification.getEnded() + ")");
		} else {
			clear();
		}
	}

	public void clear() {
		this.title.setInnerHTML("");
		this.eventDescription.setText("");
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
