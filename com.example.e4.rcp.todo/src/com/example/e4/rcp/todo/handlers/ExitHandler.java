package com.example.e4.rcp.todo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ExitHandler {

	@Execute
	public void execute(EPartService partService, IWorkbench _workbench, Shell shell) {
		if (partService != null && !partService.getDirtyParts().isEmpty()) {
			if (MessageDialog.openConfirm(shell, "Unsaved Data", "You have unsaved Data do you want to save before close ?")) {
				partService.saveAll(false);
				_workbench.close();
				// workbench continue tu run until the handler closes
				return;
			}
		}
		if (MessageDialog.openConfirm(shell, "Close", "confirm you want to close the application")) {
			_workbench.close();
		}
	}

}
