package com.example.e4.rcp.todo.wizards;

import org.eclipse.jface.wizard.Wizard;

import com.example.e4.rcp.todo.model.Todo;

public class TodoWizard extends Wizard {

	private Todo todo;

	public TodoWizard(Todo _todo) {
		setWindowTitle("New Wizard");
		todo = _todo;
	}

	@Override
	public void addPages() {
		addPage(new TodoWizardPage1(todo));
		addPage(new TodoWizardPage2());
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
