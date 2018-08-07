package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.Todo;

public class ToDoDetailsPart {

	private int			spacing;
	private Text		sumTxt;
	private Text		descTxt;
	private DateTime	dueDT;
	private Button		done;
	private Todo		todo;

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

		sumTxt.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (todo != null) {
					todo.setSummary(sumTxt.getText());
				}
			}
		});

		descTxt.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (todo != null) {
					todo.setDescription(descTxt.getText());
				}
			}
		});

	}

	@Focus
	public void onFocus() {
		sumTxt.setFocus();
	}

	@Inject
	public void setTodo(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo _todo) {
		if (_todo != null) {
			todo = _todo;
		}
		updateUserInterface(_todo);
	}

	private void updateUserInterface(Todo _todo) {
		if (_todo == null) {
			enableUserInterface(false);
			return;
		}
		todo = _todo;
		if (sumTxt != null && !sumTxt.isDisposed()) {
			enableUserInterface(true);
			sumTxt.setText(todo.getSummary());
			descTxt.setText(todo.getDescription());
			dueDT.setData(todo.getDueDate());
			done.setSelection(false);
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
