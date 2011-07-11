package com.simplifide.core.freemarker;

import java.io.Writer;

import com.simplifide.core.HardwareLog;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class MainErrorHandler implements TemplateExceptionHandler{

	
	public void handleTemplateException(TemplateException arg0,Environment arg1, Writer arg2) throws TemplateException {
		HardwareLog.logError(arg0);
	}

}
