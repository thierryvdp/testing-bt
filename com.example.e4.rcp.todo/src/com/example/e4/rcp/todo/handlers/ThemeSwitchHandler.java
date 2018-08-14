package com.example.e4.rcp.todo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;

public class ThemeSwitchHandler {

	private static final String	DEFAULT_THEME	= "com.example.e4.rcp.todo.default";
	private static final String	RAINBOW_THEME	= "com.example.e4.rcp.todo.rainbow";
	private static final String	DARK_THEME		= "com.example.e4.rcp.todo.dark";

	@Execute
	public void switchTheme(IThemeEngine engine) {
		if (engine.getActiveTheme().getId().equals(DEFAULT_THEME)) {
			engine.setTheme(RAINBOW_THEME, true);
		}
		else if (engine.getActiveTheme().getId().equals(RAINBOW_THEME)) {
			engine.setTheme(DARK_THEME, true);
		}
		else {
			engine.setTheme(DEFAULT_THEME, true);
		}
	}

}
