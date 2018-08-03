package com.example.e4.bundleresourceloader;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IBundleResourceLoader {

	// service does not recycle image
	// the consumer is responsible for calling dispos

	public ImageDescriptor getImageDescriptor(Class<?> clazz, String path);

}
