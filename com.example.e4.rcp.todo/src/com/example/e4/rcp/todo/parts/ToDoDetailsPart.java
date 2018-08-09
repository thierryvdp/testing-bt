package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class ToDoDetailsPart {

	@Inject
	MDirtyable					dirty;

	private DataBindingContext	dataBindingContext	= new DataBindingContext();

	private int					spacing;
	private Text				sumTxt;
	private Text				descTxt;
	private DateTime			dueDT;
	private Button				done;
	private Todo				todo;

	private IChangeListener		listener			= new IChangeListener() {

														@Override
														public void handleChange(ChangeEvent event) {
															if (dirty != null) {
																dirty.setDirty(true);
															}
														}
													};

	public ToDoDetailsPart() {
		System.out.println("Constructor Details");
		spacing = 5;
	}

	@PostConstruct
	public void createControls(Composite _parent) {

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

		sumTxt = new Text(_parent, SWT.BORDER);
		descTxt = new Text(_parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		dueDT = new DateTime(_parent, SWT.BORDER);
		done = new Button(_parent, SWT.CHECK);
		done.setText("Done");

		standardPositionField(_parent, "Summary", sumTxt, null, -1, 0);
		standardPositionField(_parent, "Due Date", dueDT, sumTxt, -1, 0);
		standardPositionField(_parent, "Done", done, dueDT, -1, 0);
		standardPositionField(_parent, "Description", descTxt, done, -1, -1);

		//      c'est le binding qui s'en occuppe
		//		sumTxt.addModifyListener(new ModifyListener() {
		//			@Override
		//			public void modifyText(ModifyEvent e) {
		//				if (todo != null) {
		//					todo.setSummary(sumTxt.getText());
		//				}
		//			}
		//		});
		//		descTxt.addModifyListener(new ModifyListener() {
		//			@Override
		//			public void modifyText(ModifyEvent e) {
		//				if (todo != null) {
		//					todo.setDescription(descTxt.getText());
		//				}
		//			}
		//		});

	}

	@Focus
	public void onFocus() {
		sumTxt.setFocus();
	}

	@Inject
	public void setTodo(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo _todo) {
		if (dirty != null) {
			if (!dirty.isDirty() || (dirty.isDirty() && MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Confirm", "Current todo is not saved.\nPress Ok to loose changes and display new Todo"))) {
				dirty.setDirty(false);
				updateUserInterface(_todo);
			}
		}
		else { // dialog
			updateUserInterface(_todo);
		}
	}

	@Persist
	public void save(ITodoService todoService) {
		todoService.saveTodo(todo);
		dirty.setDirty(false);
	}

	private void updateUserInterface(Todo _todo) {
		if (_todo == null) {
			enableUserInterface(false);
			return;
		}
		todo = _todo;
		if (sumTxt != null && !sumTxt.isDisposed()) {
			enableUserInterface(true);

			// unregister before dispose
			IObservableList providers = dataBindingContext.getValidationStatusProviders();
			for (Object o : providers) {
				Binding b = (Binding) o;
				b.getTarget().removeChangeListener(listener);
			}

			// cleanup existing bindings
			dataBindingContext.dispose();

			// Summary
			IObservableValue oWidgetSummary = WidgetProperties.text(SWT.Modify).observe(sumTxt);
			IObservableValue oTodoSummary = BeanProperties.value(Todo.FIELD_SUMMARY).observe(_todo);
			dataBindingContext.bindValue(oWidgetSummary, oTodoSummary);

			// Description
			IObservableValue oWidgetDescription = WidgetProperties.text(SWT.Modify).observe(descTxt);
			IObservableValue oTodoDescription = BeanProperties.value(Todo.FIELD_DESCRIPTION).observe(_todo);
			dataBindingContext.bindValue(oWidgetDescription, oTodoDescription);

			// Done
			IObservableValue oWidgetDone = WidgetProperties.selection().observe(done);
			IObservableValue oTodoDone = BeanProperties.value(Todo.FIELD_DONE).observe(_todo);
			dataBindingContext.bindValue(oWidgetDone, oTodoDone);

			// DueDate
			IObservableValue oWidgetDueDate = WidgetProperties.selection().observe(dueDT);
			IObservableValue oTodoDueDate = BeanProperties.value(Todo.FIELD_DUEDATE).observe(_todo);
			dataBindingContext.bindValue(oWidgetDueDate, oTodoDueDate);

			// register new validators
			providers = dataBindingContext.getValidationStatusProviders();
			for (Object o : providers) {
				Binding b = (Binding) o;
				b.getTarget().addChangeListener(listener);
			}

		}
	}

	private void enableUserInterface(boolean enabled) {
		if (sumTxt != null && !sumTxt.isDisposed()) {
			sumTxt.setEnabled(enabled);
			descTxt.setEnabled(enabled);
			dueDT.setEnabled(enabled);
			done.setEnabled(enabled);
		}
	}

	private static void setFormData(String label, Control field, Control belowField, int width, int height) {
		final FormData formdata = new FormData();
		if (label == null) {
			if (belowField == null) {
				formdata.left = new FormAttachment(0, 0);
			}
			else {
				formdata.left = new FormAttachment(belowField, 0, SWT.LEFT);
			}
		}
		else {
			formdata.left = new FormAttachment(0, 100);
		}
		if (!(field instanceof Button)) {
			if (width == -1) {
				formdata.right = new FormAttachment(100, 0);
			}
			else if (width == -2) {
				if (belowField != null) {
					formdata.right = new FormAttachment(belowField, 0, SWT.RIGHT);
				}
				else {
					formdata.right = new FormAttachment(100, 0);
				}
			}
			else if (width > 0) {
				formdata.width = width;
			}
		}
		if (belowField == null) {
			formdata.top = new FormAttachment(0, 0);
		}
		else {
			formdata.top = new FormAttachment(belowField, 0, SWT.BOTTOM);
		}
		if (height == -1) {
			formdata.bottom = new FormAttachment(100, 0);
		}
		else if (height > 0) {
			formdata.height = height;
		}
		field.setLayoutData(formdata);
	}

	private static Label standardPositionField(Composite parent, String label, Control field, Control belowField, int width, int height) {
		setFormData(label, field, belowField, width, height);
		if (label != null) {
			Label fieldLabel = new Label(parent, SWT.RIGHT);
			fieldLabel.setText(label);
			final FormData fd_fieldLabel = new FormData();
			fd_fieldLabel.right = new FormAttachment(field, 0, SWT.LEFT);
			fd_fieldLabel.top = new FormAttachment(field, 0, SWT.CENTER);
			fieldLabel.setLayoutData(fd_fieldLabel);
			return fieldLabel;
		}
		return null;
	}

}
