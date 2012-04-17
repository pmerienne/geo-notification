package com.pmerienne.geonotification.client.widget.list;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;

public abstract class BasicCell<T> implements Cell<T> {

	private static Template TEMPLATE = GWT.create(Template.class);

	public interface Template extends SafeHtmlTemplates {
		@SafeHtmlTemplates.Template("<div class=\"{0}\"><p>{1}</p><p style=\"font-size: 75%;\">{2}</p></div>")
		SafeHtml content(String classes, String primary, String secondary);
	}

	@Override
	public void render(SafeHtmlBuilder safeHtmlBuilder, final T model) {
		safeHtmlBuilder.append(TEMPLATE.content("mgwt-List-Cell", SafeHtmlUtils.htmlEscape(getTitleString(model)),
				SafeHtmlUtils.htmlEscape(getSecondaryString(model))));

	}

	public abstract String getTitleString(T model);

	public abstract String getSecondaryString(T model);

	@Override
	public boolean canBeSelected(T model) {
		return true;
	}

}
