package com.example.e4.rcp.todo.handlers;

import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import com.example.e4.rcp.todo.model.Todo;

public class OpenEditorHandler {

	private static final String	bundleName	= "com.example.e4.rcp.todo";
	private static final String	className	= "com.example.e4.rcp.todo.parts.EditorPart";

	@Execute
	public void execute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo, MApplication application, EModelService modelService, EPartService partService) {
		System.out.println("Will open editor soon ...");

		// pour être sur
		if (todo == null) {
			return;
		}

		String id = String.valueOf(todo.getId());

		// check editor deja ouvert
		List<MPart> parts = (List<MPart>) partService.getParts();
		for (MPart mPart : parts) {
			String currentId = mPart.getPersistedState().get(Todo.FIELD_ID);
			if (currentId != null && currentId.equals(id)) {
				partService.showPart(mPart, EPartService.PartState.ACTIVATE);
				return;
			}
		}

		// editor not open create it
		MPart part = modelService.createModelElement(MPart.class);

		// pointer le part en question avec l'id (bunlde+class)
		part.setContributionURI("bundleclass://" + bundleName + "/" + className);
		part.getPersistedState().put(Todo.FIELD_ID, id);

		// super titre cool
		String header = "ID:" + id + " " + todo.getSummary();
		part.setLabel(header);
		part.setElementId(id);
		part.setCloseable(true);

		// ajout a un stack existant
		MPartStack stack = (MPartStack) modelService.find("com.example.e4.rcp.todo.partstack.bottom", application);
		stack.getChildren().add(part);
		partService.showPart(part, EPartService.PartState.ACTIVATE);
	}

	@CanExecute
	public boolean canExecute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo) {
		return todo != null;
	}

}
