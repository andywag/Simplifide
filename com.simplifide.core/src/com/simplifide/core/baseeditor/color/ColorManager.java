/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.color;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager {

	private static ColorManager instance;
	public static ColorManager getInstance() {
		if (instance == null) instance = new ColorManager();
		return instance;
	}
	
	private ColorManager() {}
	
	protected Map fColorTable = new HashMap(10);

	
	public Color getColor(RGB rgb) {
		Color color = (Color) fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}
	
	public static Color getStaticColor(RGB rgb) {
		
			return  new Color(Display.getCurrent(), rgb);
			
	}
}
