package com.example.e4.rcp.todo.contribute.processors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.example.e4.rcp.todo.contribute.handlers.ExitHandlerWithCheck;

public class MenuProcessor {

	@Inject
	@Named("com.example.e4.rcp.todo.menu.file")
	private MMenu menu;

	@Execute
	public void execute(EModelService modelService) {
		// remove du old menu tous les exit
		if (!menu.getChildren().isEmpty()) {
			List<MMenuElement> list = new ArrayList<>();
			for (MMenuElement element : menu.getChildren()) {
				// use id pas label qui sera transformé plus tard
				if (element.getElementId() != null) {
					if (element.getElementId().contains("exit")) {
						list.add(element);
					}
				}
			}
			menu.getChildren().removeAll(list);
		}
		// et on ajoute notre contrib de l'exit
		MDirectMenuItem menuItem = modelService.createModelElement(MDirectMenuItem.class);
		menuItem.setLabel("Another Exit");
		menuItem.setContributionURI("bundleclass://com.example.e4.rcp.todo.contribute/" + ExitHandlerWithCheck.class.getName());
		menu.getChildren().add(menuItem);
	}

}
