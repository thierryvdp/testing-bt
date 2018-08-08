package com.example.e4.rcp.todo.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
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
	@Inject
	ESelectionService	selectionService;
	@Inject
	EHandlerService		handlerService;
	@Inject
	ECommandService		commandService;
	@Inject
	IEclipseContext		eclipseContext;
	@Inject
	UISynchronize		sync;

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
		//		List<Todo> todos = todoService.getTodos();
		updateViewer();

		Button button = new Button(_parent, SWT.PUSH);
		button.setText("Delete Selected");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = viewer.getSelection();
				IStructuredSelection sel = (IStructuredSelection) selection;
				if (sel.size() > 0) {
					// l'action a besoin d'un todo selectionné
					selectionService.setSelection(sel.getFirstElement());
					// verif que dans e4xmi l'ID de la commande pour virer un todo est bien com.example.e4.rcp.todo.command.remove
					ParameterizedCommand cmd = commandService.createCommand("com.example.e4.rcp.todo.command.remove", null);
					handlerService.executeHandler(cmd, eclipseContext);

					// mettre à jour le viewer ...
					//					List<Todo> todos = todoService.getTodos();
					updateViewer();
					// plus besoin d'avoir le todo Selectionné, on l'a delete ...
					selectionService.setSelection(null);

					//					Todo firstElement = (Todo) sel.getFirstElement();
					//					todoService.deleteTodo(firstElement.getId());
					//					updateViewer(todoService.getTodos());
				}
			}
		});

	}

	@Focus
	public void onFocus() {
		viewer.getControl().setFocus();
	}

	private void updateViewer() {

		Job job = new Job("Loading") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final List<Todo> list = todoService.getTodos();
				sync.asyncExec(new Runnable() {
					@Override
					public void run() {
						viewer.setInput(list);
						if (list.size() > 0) {
							viewer.setSelection(new StructuredSelection(list.get(0)));
						}
					}
				});
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

}
