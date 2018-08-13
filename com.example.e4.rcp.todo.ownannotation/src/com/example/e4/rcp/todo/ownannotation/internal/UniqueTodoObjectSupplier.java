package com.example.e4.rcp.todo.ownannotation.internal;

import java.util.Date;

import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.core.di.suppliers.IRequestor;

import com.example.e4.rcp.todo.model.Todo;

public class UniqueTodoObjectSupplier extends ExtendedObjectSupplier {

	@Override
	public Object get(IObjectDescriptor descriptor, IRequestor requestor, boolean track, boolean group) {
		System.out.println("Own annotation proc");
		Todo todo = new Todo(15, "Checked", "CHK Desc", false, new Date());
		return todo;
	}

}
