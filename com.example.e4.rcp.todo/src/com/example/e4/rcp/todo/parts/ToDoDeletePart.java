package com.example.e4.rcp.todo.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class ToDoDeletePart {

	@Inject
	ITodoService		todoService;
	private ComboViewer	viewer;

	public ToDoDeletePart() {
		System.out.println("Constructor Delete");
	}

	@PostConstruct
	public void createControls(Composite _parent) {
		_parent.setLayout(new GridLayout(2, false));
		viewer = new ComboViewer(_parent, SWT.READ_ONLY);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary();
			}
		});
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		List<Todo> todos = todoService.getTodos();
		updateViewer(todos);

		Button button = new Button(_parent, SWT.PUSH);
		button.setText("Delete Selected");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = viewer.getSelection();
				IStructuredSelection sel = (IStructuredSelection) selection;
				if (sel.size() > 0) {
					Todo firstElement = (Todo) sel.getFirstElement();
					todoService.deleteTodo(firstElement.getId());
					updateViewer(todoService.getTodos());
				}
			}
		});

	}

	@Focus
	public void onFocus() {
		viewer.getControl().setFocus();
	}

	private void updateViewer(List<Todo> todos) {
		viewer.setInput(todos);
		if (todos.size() > 0) {
			viewer.setSelection(new StructuredSelection(todos.get(0)));
		}
	}

}
