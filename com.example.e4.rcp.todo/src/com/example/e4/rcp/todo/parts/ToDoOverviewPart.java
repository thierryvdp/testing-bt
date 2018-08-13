package com.example.e4.rcp.todo.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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

import com.example.e4.rcp.todo.events.MyEventConstants;
import com.example.e4.rcp.todo.il8n.Messages;
import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class ToDoOverviewPart {

	@Inject
	ITodoService				todoService;
	@Inject
	ESelectionService			selectionService;
	@Inject
	UISynchronize				sync;

	private int					spacing;
	private TableViewer			viewer;
	private String				searchString	= "";
	private Button				dataBtn;

	private WritableList		writableList;

	private Label				dataLbl;
	private TableViewerColumn	colSum;
	private TableViewerColumn	colDes;

	public ToDoOverviewPart() {
		System.out.println("Constructor Overview");
		spacing = 2;
	}

	@PostConstruct
	public void createControls(Composite _parent, EMenuService _menuservice, ITodoService todoService, @Translation Messages message) {

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
		dataBtn = new Button(_parent, SWT.PUSH);
		dataBtn.setText("Load Data");
		final FormData fd_btn = new FormData();
		fd_btn.top = new FormAttachment(0, 0);
		fd_btn.left = new FormAttachment(0, 0);
		dataBtn.setLayoutData(fd_btn);
		dataBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				asyncLoadTodos();
			}
		});

		// label
		dataLbl = new Label(_parent, SWT.NORMAL);
		final FormData fd_lbl = new FormData();
		fd_lbl.top = new FormAttachment(dataBtn, 0, SWT.CENTER);
		fd_lbl.left = new FormAttachment(dataBtn, 0, SWT.RIGHT);
		fd_lbl.right = new FormAttachment(100, 0);
		dataLbl.setLayoutData(fd_lbl);

		// search
		Text search = new Text(_parent, SWT.SEARCH | SWT.CANCEL | SWT.ICON_SEARCH);
		search.setMessage("Filter");
		final FormData fd_search = new FormData();
		fd_search.top = new FormAttachment(dataBtn, 0, SWT.BOTTOM);
		fd_search.left = new FormAttachment(dataBtn, 0, SWT.LEFT);
		fd_search.right = new FormAttachment(100, 0);
		search.setLayoutData(fd_search);
		search.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				searchString = source.getText();
				System.out.println("Filtering on:" + searchString);
				viewer.refresh();
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

		// viewer
		viewer = new TableViewer(_parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		Table table = viewer.getTable();
		final FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(search, 0, SWT.BOTTOM);
		fd_table.left = new FormAttachment(0, 0);
		fd_table.right = new FormAttachment(100, 0);
		fd_table.bottom = new FormAttachment(100, 0);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//		viewer.setContentProvider(ArrayContentProvider.getInstance());

		// colonne Summary
		colSum = new TableViewerColumn(viewer, SWT.NONE);
		//		colSum.setLabelProvider(new ColumnLabelProvider() {
		//			@Override
		//			public String getText(Object element) {
		//				Todo todo = (Todo) element;
		//				return todo.getSummary();
		//			}
		//		});
		colSum.getColumn().setWidth(100);
		colSum.getColumn().setText("Summary");

		// colonne Descriptio
		colDes = new TableViewerColumn(viewer, SWT.NONE);
		//		colDes.setLabelProvider(new ColumnLabelProvider() {
		//			@Override
		//			public String getText(Object element) {
		//				Todo todo = (Todo) element;
		//				return todo.getDescription();
		//			}
		//		});
		colDes.getColumn().setWidth(200);
		colDes.getColumn().setText("Description");

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
				return ((Todo) e1).getSummary().toUpperCase().compareTo(((Todo) e2).getSummary().toUpperCase());
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				selectionService.setSelection(selection.getFirstElement());
			}
		});

		//		viewer.setInput(todoService.getTodos());

		// dataBinding
		writableList = new WritableList(new ArrayList<>(), Todo.class);
		ViewerSupport.bind(viewer, writableList, BeanProperties.values(new String[] { Todo.FIELD_SUMMARY, Todo.FIELD_DESCRIPTION }));

		_menuservice.registerContextMenu(viewer.getControl(), "com.example.e4.rcp.todo.popupmenu.tablemenu");

		translateTable(message);

		asyncLoadTodos();

	}

	private void asyncLoadTodos() {
		dataLbl.setText("Loading ...");
		Job job = new Job("Loading") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final List<Todo> list = todoService.getTodos();
				sync.asyncExec(new Runnable() {
					@Override
					public void run() {
						dataLbl.setText("Total Todos:" + list.size());
						updateViewer(list);
					}
				});
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	public void updateViewer(List<Todo> list) {
		if (viewer != null) {
			writableList.clear();
			writableList.addAll(list);
			viewer.refresh();
		}
	}

	@Focus
	public void setFocus() {
		dataBtn.setFocus();
	}

	@Inject
	@Optional
	private void subscribeTopicTodoAllTopics(@UIEventTopic(MyEventConstants.TOPIC_TODO_ALLTOPICS) Map<String, String> event) {
		asyncLoadTodos();
	}

	@Inject
	public void translateTable(@Translation Messages message) {
		if (viewer != null) {
			colSum.getColumn().setText(message.txtSummary);
			colDes.getColumn().setText(message.txtDescription);
		}
	}

}
