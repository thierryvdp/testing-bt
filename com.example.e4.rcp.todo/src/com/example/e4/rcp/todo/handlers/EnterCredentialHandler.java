package com.example.e4.rcp.todo.handlers;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.dialogs.PasswordDialog;
import com.example.e4.rcp.todo.preferences.PreferenceConstants;

public class EnterCredentialHandler {

	@Inject
	@Preference(nodePath = PreferenceConstants.NODEPATH, value = PreferenceConstants.PREF_KEY_USER)
	String userPref;

	@Execute
	public void execute(Shell shell, @Preference IEclipsePreferences prefs) {
		PasswordDialog dialog = new PasswordDialog(shell);

		if (userPref != null) {
			dialog.setUser(userPref);
		}

		if (dialog.open() == Window.OK) {
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

}
