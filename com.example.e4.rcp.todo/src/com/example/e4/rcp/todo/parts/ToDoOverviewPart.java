package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class ToDoOverviewPart {

	private int			spacing;
	private TableViewer	viewer;
	private String		searchString	= "";

	public ToDoOverviewPart() {
		System.out.println("Constructor Overview");
		spacing = 2;
	}

	@PostConstruct
	public void createControls(Composite _parent, EMenuService _menuservice, ITodoService todoService) {

		// parent composite
		final FormLayout formLayout = new FormLayout();
		formLayout.spacing = spacing;
		formLayout.marginWidth = spacing;
		formLayout.marginHeight = spacing;
		formLayout.marginBottom = spacing;
		formLayout.marginLeft = spacing;
		formLayout.marginRight = spacing;
		formLayout.marginTop = spacing;
		_parent.setLayout(formLayout);
		final FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 0);
		fd_composite.left = new FormAttachment(0, 0);
		fd_composite.right = new FormAttachment(100, 0);
		fd_composite.bottom = new FormAttachment(100, 0);
		_parent.setLayoutData(fd_composite);

		// bouton
		Button dataBtn = new Button(_parent, SWT.PUSH);
		dataBtn.setText("Load Data");
		final FormData fd_btn = new FormData();
		fd_btn.top = new FormAttachment(0, 0);
		fd_btn.left = new FormAttachment(0, 0);
		//		fd_btn.right = new FormAttachment(100, 0);
		//		fd_btn.bottom = new FormAttachment(100, 0);
		dataBtn.setLayoutData(fd_btn);

		// label
		Label dataLbl = new Label(_parent, SWT.NORMAL);
		dataLbl.setText("Data Not Available");
		final FormData fd_lbl = new FormData();
		fd_lbl.top = new FormAttachment(dataBtn, 0, SWT.CENTER);
		fd_lbl.left = new FormAttachment(dataBtn, 0, SWT.RIGHT);
		fd_lbl.right = new FormAttachment(100, 0);
		//		fd_lbl.bottom = new FormAttachment(100, 0);
		dataLbl.setLayoutData(fd_lbl);

		// listener
		dataBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dataLbl.setText("Total Todos:" + todoService.getTodos().size());
				viewer.setInput(todoService.getTodos());
			}
		});

		Text search = new Text(_parent, SWT.SEARCH | SWT.CANCEL | SWT.ICON_SEARCH);
		search.setMessage("Filter");
		final FormData fd_search = new FormData();
		fd_search.top = new FormAttachment(dataBtn, 0, SWT.BOTTOM);
		fd_search.left = new FormAttachment(dataBtn, 0, SWT.LEFT);
		fd_search.right = new FormAttachment(100, 0);
		//		fd_lbl.bottom = new FormAttachment(100, 0);
		search.setLayoutData(fd_search);
		search.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				searchString = source.getText();
			}
		});
		search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					Text source = (Text) e.getSource();
					source.setText("");
				}
			}
		});

		viewer = new TableViewer(_parent, SWT.FULL_SELECTION);
		Table table = viewer.getTable();
		final FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(search, 0, SWT.BOTTOM);
		fd_table.left = new FormAttachment(0, 0);
		fd_table.right = new FormAttachment(100, 0);
		fd_table.bottom = new FormAttachment(100, 0);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		// colonne Summary
		TableViewerColumn colSum = new TableViewerColumn(viewer, SWT.NONE);
		colSum.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary();
			}
		});
		colSum.getColumn().setWidth(100);
		colSum.getColumn().setText("Summary");

		// colonne Descriptio
		TableViewerColumn colDes = new TableViewerColumn(viewer, SWT.NONE);
		colDes.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Todo todo = (Todo) element;
				return todo.getDescription();
			}
		});
		colDes.getColumn().setWidth(200);
		colDes.getColumn().setText("Description");

		dataLbl.setText("Total Todos:" + todoService.getTodos().size());
		viewer.setInput(todoService.getTodos());
		//		_menuservice.registerContextMenu(viewer.getControl(), "com.example.e4.rcp.todo.popupmenu.tablemenu");

		viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary().contains(searchString) || todo.getDescription().contains(searchString);
			}
		});

		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				// TODO Auto-generated method stub
				return ((Todo) e1).getSummary().compareTo(((Todo) e2).getSummary());
			}
		});
		;

	}

	@Focus
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
