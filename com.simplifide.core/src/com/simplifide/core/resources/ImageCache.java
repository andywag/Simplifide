package com.simplifide.core.resources;

import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ImageCache {

	private static HashMap<String,Image> imageMap = new HashMap<String,Image>();
	
	public static Image getImage(String name) {
		Image im = imageMap.get(name);
		if (im == null) {
			ImageDescriptor desc = BasicIconManager.getImage(name);
			im = desc.createImage();
			imageMap.put(name, im);
		}
		if (im.isDisposed()) {
			ImageDescriptor desc = BasicIconManager.getImage(name);
			im = desc.createImage();
			imageMap.put(name, im);
		}
		return im;
	}
	
}
