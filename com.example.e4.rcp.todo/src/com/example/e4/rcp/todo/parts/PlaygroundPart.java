package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.example.e4.bundleresourceloader.IBundleResourceLoader;

public class PlaygroundPart {

	public PlaygroundPart() {
		System.out.println("Constructor Playground");
	}

	@PostConstruct
	public void createControls(Composite parent, IBundleResourceLoader loader) {
		System.out.println(this.getClass().getSimpleName() + " @PostConstruct method called.");
		Label label = new Label(parent, SWT.NONE);
		ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(), label);
		Image image = resourceManager.createImage(loader.getImageDescriptor(this.getClass(), "images/BIORIM.png"));
		label.setImage(image);
	}

}
