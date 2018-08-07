package com.example.e4.rcp.todo.wizards;

import javax.inject.Inject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.parts.ToDoDetailsPart;

public class TodoWizardPage1 extends WizardPage {

	private Todo todo;

	@Inject
	public TodoWizardPage1(Todo _todo) {
		super("page1");
		todo = _todo;
		setTitle("New Todo");
		setDescription("Enter the todo data");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		ToDoDetailsPart part = new ToDoDetailsPart();
		part.createControls(container);
		part.setTodo(todo);
		setPageComplete(true);
		setControl(container);
	}

}
