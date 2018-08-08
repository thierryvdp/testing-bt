package com.example.e4.rcp.todo.menu;

import java.util.Date;
import java.util.List;

import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class DynamicMenuEntries {

	private String	bundlePrefix	= "bundleclass://com.example.e4.rcp.todo/";
	private String	className		= "com.example.e4.rcp.todo.handlers.PrintMessageHandler";

	@AboutToShow
	public void aboutToShew(List<MMenuElement> items, EModelService modelService) {
		for (int i = 0; i < 10; i++) {
			MDirectMenuItem dynamicItem = modelService.createModelElement(MDirectMenuItem.class);
			dynamicItem.setLabel("Dynamis Menu Item (" + new Date() + ")");
			dynamicItem.setContributionURI(bundlePrefix + className);
			items.add(dynamicItem);
		}
	}

}
