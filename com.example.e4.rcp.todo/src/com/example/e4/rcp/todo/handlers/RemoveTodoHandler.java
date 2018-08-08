package com.example.e4.rcp.todo.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class RemoveTodoHandler {

	@Execute
	public void execute(ITodoService model, @Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo, Shell shell) {
		if (todo != null) {
			if (MessageDialog.openConfirm(shell, "Confimration", "Sure you want to delete todo " + todo.getSummary() + " ?")) {
				model.deleteTodo(todo.getId());
			}
		}
		else {
			MessageDialog.openError(shell, "Delete Impossible", "There's no todo selected");
		}
	}

	@CanExecute
	boolean canExecute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo _todo) {
		return _todo != null;
	}

}
