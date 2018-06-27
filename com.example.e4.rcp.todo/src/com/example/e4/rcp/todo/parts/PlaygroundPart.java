package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.widgets.Composite;

public class PlaygroundPart {

	public PlaygroundPart() {
		System.out.println("Constructor Playground");
	}

	@PostConstruct
	public void createControls(Composite parent) {
		System.out.println(this.getClass().getSimpleName() + " @PostConstruct method called.");
	}

}
