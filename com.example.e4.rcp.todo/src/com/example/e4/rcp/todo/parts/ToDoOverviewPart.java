package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.ITodoService;

public class ToDoOverviewPart {

	public ToDoOverviewPart() {
		System.out.println("Constructor Overview");
	}

	@PostConstruct
	public void createControls(Composite _parent, EMenuService _menuservice, ITodoService todoService) {
		System.out.println("nombre de todos:" + todoService.getTodos().size());
		TableViewer viewer = new TableViewer(_parent, SWT.FULL_SELECTION);
		_menuservice.registerContextMenu(viewer.getControl(), "com.example.e4.rcp.todo.popupmenu.tablemenu");
	}

}
