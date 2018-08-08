package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class DynamicPart {

	private Button button;

	@PostConstruct
	public void createControls(Composite _parent, final Shell _shell) {
		button = new Button(_parent, SWT.PUSH);

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(_shell, "Dynamis", "Dynamic is working");
			}
		});
		button.setText("VALIDATE");
	}

	@Focus
	public void setFocus() {
		button.setFocus();
	}

}
