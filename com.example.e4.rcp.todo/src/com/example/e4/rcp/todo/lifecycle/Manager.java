package com.example.e4.rcp.todo.lifecycle;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.dialogs.PasswordDialog;

public class Manager {

	@PostContextCreate
	void postContextCreate(IApplicationContext appContext, Display display) {
		final Shell shell = new Shell(SWT.SHELL_TRIM);
		PasswordDialog dialog = new PasswordDialog(shell);

		// close static splash
		appContext.applicationRunning();

		// position shell
		setLocation(display, shell);

		if (dialog.open() != Window.OK) {
			// close application
			System.exit(-1);
		}

	}

	private void setLocation(Display display, Shell shell) {
		Monitor monit = display.getPrimaryMonitor();
		Rectangle monitRect = monit.getBounds();
		Rectangle shellRect = shell.getBounds();
		int x = monitRect.x + (monitRect.width - shellRect.width) / 2;
		int y = monitRect.y + (monitRect.height - shellRect.height) / 2;
		shell.setLocation(x, y);
	}

}
