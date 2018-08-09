package com.example.e4.rcp.todo.handlers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.events.MyEventConstants;
import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.wizards.TodoWizard;
import com.example.e4.rcp.todo.wizards.TodoWizardPage1;
import com.example.e4.rcp.todo.wizards.TodoWizardPage2;

public class NewTodoHandler {

	@Execute
	public void execute(Shell shell, ITodoService todoService, IEventBroker broker, IEclipseContext contexte) {

		IEclipseContext wizardContexte = contexte.createChild();

		Todo todo = new Todo(-1);
		todo.setDueDate(new Date());
		wizardContexte.set(Todo.class, todo);

		TodoWizardPage1 page1 = ContextInjectionFactory.make(TodoWizardPage1.class, wizardContexte);
		wizardContexte.set(TodoWizardPage1.class, page1);
		TodoWizardPage2 page2 = ContextInjectionFactory.make(TodoWizardPage2.class, null);
		wizardContexte.set(TodoWizardPage2.class, page2);

		TodoWizard wizard = ContextInjectionFactory.make(TodoWizard.class, wizardContexte);

		WizardDialog dialog = new WizardDialog(shell, wizard);

		if (dialog.open() == WizardDialog.OK) {
			todoService.saveTodo(todo);
			// async
			String todoId = String.valueOf(todo.getId());
			broker.post(MyEventConstants.TOPIC_TODO_NEW, createEventData(MyEventConstants.TOPIC_TODO_NEW, todoId));
		}
	}

	private Map<String, String> createEventData(String topic, String todoId) {
		Map<String, String> map = new HashMap<>();
		map.put(MyEventConstants.TOPIC_TODO, topic);
		map.put(Todo.FIELD_ID, todoId);
		return map;
	}
}
