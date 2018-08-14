package com.example.e4.rcp.todo.parts;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.example.e4.bundleresourceloader.IBundleResourceLoader;
import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.ownannotation.UniqueTodo;

public class PlaygroundPart {

	private Text	text;
	private Browser	browser;

	public PlaygroundPart() {
		System.out.println("Constructor Playground");
	}

	@Inject
	@Optional
	public void trackUserSettings(@Preference(value = "user") String user) {
		System.out.println("user:" + user);
	}

	@PostConstruct
	public void createControls(Composite parent, IBundleResourceLoader loader) {

		parent.setLayout(new GridLayout(2, false));

		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(), label);
		Image image = resourceManager.createImage(loader.getImageDescriptor(this.getClass(), "images/BIORIM.png"));
		label.setImage(image);

		text = new Text(parent, SWT.BORDER);
		text.setMessage("Enter City");
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Search");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String city = text.getText();
				if (city.isEmpty()) {
					return;
				}
				try {
					// not supported at the moment by Google.
					// browser.setUrl("http://maps.google.com/maps?q="
					// + URLEncoder.encode(city, "UTF-8")
					// + "&output=embed");
					browser.setUrl("https://www.google.com/maps/place/" + URLEncoder.encode(city, "UTF-8") + "/&output=embed");

				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}
		});

		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

	}

	@Inject
	public void setTodo(@UniqueTodo Todo todo) {
		if (todo != null) {
			System.out.println("Injected Todo");
			System.out.println("Id         :" + todo.getId());
			System.out.println("Summary    :" + todo.getSummary());
			System.out.println("Description:" + todo.getDescription());
			System.out.println("DueDate    :" + todo.getDueDate());
		}
	}

}
