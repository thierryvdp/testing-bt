package com.example.e4.rcp.todo.handlers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.events.MyEventConstants;
import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.wizards.TodoWizard;

public class NewTodoHandler {

	@Execute
	public void execute(Shell shell, ITodoService todoService) {

		Todo todo = new Todo(-1);
		todo.setDueDate(new Date());
		WizardDialog dialog = new WizardDialog(shell, new TodoWizard(todo));

		if (dialog.open() == WizardDialog.OK) {
			todoService.saveTodo(todo);
		}
	}

	private Map<String, String> createEventData(String topic, String todoId) {
		Map<String, String> map = new HashMap<>();
		map.put(MyEventConstants.TOPIC_TODO, topic);
		map.put(Todo.FIELD_ID, todoId);
		return map;
	}
}
