/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.template;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateTranslator;

import com.simplifide.core.HardwareLog;

public class GenerateTemplateContext extends DocumentTemplateContext{

	public GenerateTemplateContext(TemplateContextType type, IDocument document, int offset, int length) {
		super(type, document, offset, length);
		
		
	}

	private String getIndent() {
		int spos = this.getStart();
		try {
			IRegion reg = this.getDocument().getLineInformationOfOffset(this.getStart());
			String text = this.getDocument().get(reg.getOffset(),spos-reg.getOffset());
			for (int i=0;i<text.length();i++) {
				if (!Character.isWhitespace(text.charAt(i))) return "";
			}
			return text;
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
		return "";
	}
	
	public TemplateBuffer evaluate(Template template) throws BadLocationException, TemplateException {
		
		String delim = this.getDocument().getLineDelimiter(0);
		
		if (!canEvaluate(template))
			return null;
		
		String tstr = template.getPattern();
		while (Character.isWhitespace(tstr.charAt(0))) {
			tstr = tstr.substring(1);
		}
		
		String indent = this.getIndent();
		
		StringBuilder builder = new StringBuilder();
		for (int i=0;i<tstr.length();i++) {
			char tc = tstr.charAt(i);
			//builder.append(tc);
			if (tc == '\n') { // \n only
				builder.append(delim);
				builder.append(indent);
			}
			else if (tc == '\r') {
				if (i < tstr.length()-2 && tstr.charAt(i+1) != '\n') {
					builder.append(delim);
					builder.append(indent);
				}
				else {
					builder.append(delim);
					builder.append(indent);
					i++;
				}
				//builder.append(indent);
			}
			else {
				builder.append(tc);
			}
		}
	
		
		String nstr  = builder.toString();
		
		
		
		//tstr = tstr.replaceAll("\r", "\n" + this.getIndent());


		TemplateTranslator translator= new TemplateTranslator();
		TemplateBuffer buffer= translator.translate(nstr);

		getContextType().resolve(buffer, this);

		return buffer;
		
	}
}
