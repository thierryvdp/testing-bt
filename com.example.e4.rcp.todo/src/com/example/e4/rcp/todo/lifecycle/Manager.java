package com.example.e4.rcp.todo.lifecycle;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.dialogs.PasswordDialog;
import com.example.e4.rcp.todo.preferences.PreferenceConstants;

public class Manager {

	// use optional nodePath param allows to move handler to another plugin
	@Inject
	@Preference(nodePath = PreferenceConstants.NODEPATH, value = PreferenceConstants.PREF_KEY_USER)
	private String user;

	@PostContextCreate
	void postContextCreate(@Preference IEclipsePreferences prefs, IApplicationContext appContext, Display display, IEclipseContext context) {

		final Shell shell = new Shell(SWT.SHELL_TRIM);
		PasswordDialog dialog = new PasswordDialog(shell);
		if (user != null) {
			dialog.setUser(user);
		}

		// close static splash
		appContext.applicationRunning();

		// position shell
		setLocation(display, shell);

		String cssURI = "platform:/plugin/com.example.e4.rcp.todo/css/rainbow.css";
		context.set(E4Workbench.CSS_URI_ARG, cssURI);
		PartRenderingEngine.initializeStyling(shell.getDisplay(), context);

		if (dialog.open() != Window.OK) {
			// close application
			System.exit(-1);
		}
		else {
			// get user from dialog to persist
			String userValue = dialog.getUser();
			if (userValue != null && !userValue.isEmpty()) {
				prefs.put(PreferenceConstants.PREF_KEY_USER, userValue);
				try {
					prefs.flush();
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
