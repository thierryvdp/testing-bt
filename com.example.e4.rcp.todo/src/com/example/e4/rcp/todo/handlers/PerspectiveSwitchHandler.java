package com.example.e4.rcp.todo.handlers;

import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class PerspectiveSwitchHandler {

	@Execute
	public void switchPerspective(MPerspective activePerspective, MApplication app, EPartService partService, EModelService modelService, @Named("perspective_parameter") String perspectiveId) {
		List<MPerspective> perspectives = modelService.findElements(app, perspectiveId, MPerspective.class, null);
		if (!perspectives.isEmpty()) {
			if (!activePerspective.equals(perspectives.get(0))) {
				partService.switchPerspective(perspectives.get(0));
			}
		}
	}

	@CanExecute
	public boolean anotherPerspective(MPerspective activePerspective, MApplication app, EPartService partService, EModelService modelService, @Named("perspective_parameter") String perspectiveId) {
		List<MPerspective> perspectives = modelService.findElements(app, perspectiveId, MPerspective.class, null);
		if (!perspectives.isEmpty()) {
			return !activePerspective.equals(perspectives.get(0));
		}
		return false;
	}

}
