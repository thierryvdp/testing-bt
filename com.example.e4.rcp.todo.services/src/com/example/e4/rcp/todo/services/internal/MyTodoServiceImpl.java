package com.example.e4.rcp.todo.services.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

/**
 * exemple service impl, data not persisted
 * between application restart
 * 
 * @author batchoperator
 *
 */
public class MyTodoServiceImpl implements ITodoService {

	private static int	current	= 1;
	private List<Todo>	todos;

	public MyTodoServiceImpl() {
		todos = createInitialModel();
	}

	@Override
	public Todo getTodo(long id) {
		Todo todo = findById(id);
		if (todo != null) {
			return todo.copy();
		}
		return null;
	}

	// create a new or update an existing Todo object
	@Override
	public boolean saveTodo(Todo newTodo) {
		Todo updateTodo = findById(newTodo.getId());
		if (updateTodo == null) {
			updateTodo = new Todo(current++);
			todos.add(updateTodo);
		}
		updateTodo.setSummary(newTodo.getSummary());
		updateTodo.setDescription(newTodo.getDescription());
		updateTodo.setDone(newTodo.isDone());
		updateTodo.setDueDate(newTodo.getDueDate());
		return true;
	}

	@Override
	public boolean deleteTodo(long id) {
		Todo todo = findById(id);
		if (todo != null) {
			todos.remove(todo);
			return true;
		}
		return false;
	}

	// always return a new copy of the datas
	@Override
	public List<Todo> getTodos() {

		System.out.println("loading todos");
		// on a un serveur qui rame un peu ..
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Todo> liste = new ArrayList<>();
		for (Todo todo : todos) {
			liste.add(todo);
		}
		System.out.println("todos loaded");
		return liste;
	}

	private Todo findById(long id) {
		for (Todo todo : todos) {
			if (id == todo.getId()) {
				return todo;
			}
		}
		return null;
	}

	private Todo createTodo(String summary, String description) {
		return new Todo(current++, summary, description, false, new Date());
	}

	private List<Todo> createInitialModel() {
		List<Todo> list = new ArrayList<>();
		list.add(createTodo("Application Model", "Flexible and extensible"));
		list.add(createTodo("DI", "@Inject as programming mode"));
		list.add(createTodo("OSGI", "Services"));
		list.add(createTodo("SWT", "Widgets"));
		list.add(createTodo("JFace", "Especialy Viewers"));
		list.add(createTodo("CSS Styling", "Style your application"));
		list.add(createTodo("Eclipse service", "Selection, model, Part"));
		list.add(createTodo("Renderer", "Different UI toolkit"));
		list.add(createTodo("Compatibility Layer", "Run Eclipe 3.X"));
		return list;
	}

}
