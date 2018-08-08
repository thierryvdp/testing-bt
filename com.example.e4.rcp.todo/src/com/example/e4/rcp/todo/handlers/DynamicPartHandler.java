package com.example.e4.rcp.todo.handlers;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class DynamicPartHandler {
	// used as ref
	private final String STACK_ID = "com.example.e4.rcp.todo.partstack.bottom";

	@Execute
	public void execute(MApplication application, EPartService partService, EModelService modelService, Shell shell) {
		// create part basé sur le partDescriptor ID e4xmi
		MPart part = partService.createPart("com.example.e4.rcp.todo.partdescriptor.dynamic");

		// search part stack to add part
		// must have a part stack with STACK_ID ID in application model e4xmi
		List<MPartStack> stacks = modelService.findElements(application, STACK_ID, MPartStack.class, null);
		if (stacks.size() < 1) {
			MessageDialog.openError(shell, "Error", "PartStack ID:" + STACK_ID + " Not found");
		}
		else {
			// ajouter
			stacks.get(0).getChildren().add(part);
			// puis activer
			partService.showPart(part, PartState.ACTIVATE);
		}

	}

}
