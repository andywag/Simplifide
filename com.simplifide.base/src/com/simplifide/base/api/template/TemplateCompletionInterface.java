/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.api.template;

public interface TemplateCompletionInterface {

	public String getTemplateDisplayName();
	public String getTemplatePattern();
	public String getTemplateDescription();
	public boolean isAutoComplete();
}
