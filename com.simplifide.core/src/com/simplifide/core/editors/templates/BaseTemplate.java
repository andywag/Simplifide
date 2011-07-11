/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.templates;

import org.eclipse.jface.text.templates.Template;

import com.simplifide.base.api.template.TemplateCompletionInterface;

public class BaseTemplate extends Template{

	public BaseTemplate(TemplateCompletionInterface object, String contextTypeId) {
		super(object.getTemplateDisplayName(),
			  object.getTemplateDescription(),
			  contextTypeId,
			  object.getTemplatePattern(),
			  object.isAutoComplete());
		
	}
	
	public BaseTemplate(TemplateCompletionInterface object, String contextTypeId, String prefix) {
		super(object.getTemplateDisplayName(),
			  object.getTemplateDescription(),
			  contextTypeId,
			  prefix + object.getTemplatePattern(),
			  object.isAutoComplete());
		
	}
	

	
	public static class LiteralInt implements TemplateCompletionInterface {

		private String lit;

		
		public LiteralInt(String literalName) {
			this.lit = literalName;
		}
		
		public String getTemplateDescription() {
			return "Literal " + lit;
		}

		public String getTemplateDisplayName() {
			return lit;
		}

		public String getTemplatePattern() {
			return lit;
		}

		public boolean isAutoComplete() {
			return true;
		}
		
	}
	
}
