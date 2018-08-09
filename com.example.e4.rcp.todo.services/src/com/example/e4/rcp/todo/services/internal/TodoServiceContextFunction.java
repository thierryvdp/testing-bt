package com.example.e4.rcp.todo.services.internal;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;

import com.example.e4.rcp.todo.model.ITodoService;

public class TodoServiceContextFunction extends ContextFunction {

	@Override
	public Object compute(IEclipseContext context, String contextKey) {

		// créer une isntance du servicetodo
		ITodoService todoService = ContextInjectionFactory.make(MyTodoServiceImpl.class, context);

		// ajouter l'instance au contexte d'appli
		MApplication application = context.get(MApplication.class);
		IEclipseContext applicationContext = application.getContext();
		applicationContext.set(ITodoService.class, todoService);

		return todoService;
	}

}
