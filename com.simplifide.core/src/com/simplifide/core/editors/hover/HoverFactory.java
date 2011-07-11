/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.hover;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.hover.HoverTemplate;
import com.simplifide.core.freemarker.TemplateGenerator;


public class HoverFactory {

	
	public static String getTextHover(ModuleObject object, int languageType) {
		String templateName = "moduleobject";
        templateName = HoverTemplate.getTextHover(object);
		String tstr = TemplateGenerator.generateHoverTemplate(templateName, object,0, languageType);
		return tstr;
	}
	
}
