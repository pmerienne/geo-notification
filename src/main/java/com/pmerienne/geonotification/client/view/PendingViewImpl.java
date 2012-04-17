package com.pmerienne.geonotification.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class PendingViewImpl extends Composite implements PendingView {

	private static PendingViewImplUiBinder uiBinder = GWT.create(PendingViewImplUiBinder.class);

	interface PendingViewImplUiBinder extends UiBinder<Widget, PendingViewImpl> {
	}

	@UiField
	HTML message;

	public PendingViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setMessage(String message) {
		this.message.setText(message);
	}

	@Override
	public void clear() {
		this.message.setText("Veuillez patienter");
	}
}
