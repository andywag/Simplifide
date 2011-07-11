/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TypedPosition;
import org.eclipse.jface.text.formatter.ContextBasedFormattingStrategy;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IFormattingContext;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.SourceEditor;

public abstract class SourceContentFormattingStrategy extends ContextBasedFormattingStrategy{
	
	public static int INDENT    = 1;
	public static int NO_INDENT = 0;
	public static int DEDENT    = -1;
	
	
	
	private Document doc;
	private TypedPosition pos;
	private SourceEditor editor;
	
	private int beginLevel;
	private int parLevel;

	
	public SourceContentFormattingStrategy(SourceEditor editor) {
		this.editor = editor;
	}
	
	public void formatterStarts(final IFormattingContext context) {
		super.formatterStarts(context);
		this.doc = (Document) context.getProperty(FormattingContextProperties.CONTEXT_MEDIUM);
		this.pos = (TypedPosition) context.getProperty(FormattingContextProperties.CONTEXT_PARTITION);
	}
	
	
	private boolean isWhiteSpace(char u) {
		if (u == ' ' || u == '\t' || u == 'r' || u == '\n') return true;
		return false;
	}
	protected String stripWhiteSpace(String text) {
		int sp = 0;
		for (int i=0;i<text.length();i++) {
			char u = text.charAt(i);
			if (this.isWhiteSpace(u)) break;
			sp = i;
		}
		return text.substring(sp, text.length());
		
	}
	
	protected boolean beginsWith(String line, String compare) {
		String sub = line.substring(0,compare.length());
		return line.equalsIgnoreCase(sub);
	}
	
	
	/** Return the indent level */
	protected abstract int getIndentLevel(String text);
	
	
	public void createIndents(StringBuilder builder, int indent) {
		for (int i=0;i<indent;i++) {
			builder.append(' ');
		}
	}
	
	private int prefixWhiteSpace(String ustr) {
		char[] ac = ustr.toCharArray();
		for (int i=0;i<ustr.length();i++) {
			if (!Character.isWhitespace(ac[i])) {
				return i;
			}
		}
		return ustr.length() - 1;
	}
	
	/** This function will manually go through and implement indentation on the lines */
	public void format() {
		int sp = pos.getOffset();
		int ep = pos.getOffset() + pos.getLength();
		
		FormatSection.Top sections = SourceFormatUtility.splitDesign(editor.getDesignFile().getParseDescriptor(), sp, ep);
		sp = sections.getStart();
		if (sp == -1) return;
		ep = sections.getStop();
		 
		try {
			StringBuilder builder = new StringBuilder();
			int sl = this.doc.getLineOfOffset(sp);
			int el = this.doc.getLineOfOffset(ep);
			for (int i=sl;i<=el;i++) {
				IRegion reg = this.doc.getLineInformation(i);
				String line = this.doc.get(reg.getOffset(), reg.getLength());	
				
				int pre = prefixWhiteSpace(line);
				if (i == sl) {
					sp = sp - pre;
				}
				int pl = reg.getOffset() + pre;
				int indent = sections.getIndent(pl);
				if (indent == -1) {
					builder.append(line);
				}
				else {
					createIndents(builder, indent);
					builder.append(line.trim());
				}
				if (i != el)  builder.append(this.doc.getLineDelimiter(i));
			}
			doc.replace(sp, ep-sp, builder.toString());
			
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
		
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Document getDoc() {
		return doc;
	}

	public void setPos(TypedPosition pos) {
		this.pos = pos;
	}

	public TypedPosition getPos() {
		return pos;
	}

	public void setBeginLevel(int beginLevel) {
		this.beginLevel = beginLevel;
	}

	public int getBeginLevel() {
		return beginLevel;
	}

	public void setParLevel(int parLevel) {
		this.parLevel = parLevel;
	}

	public int getParLevel() {
		return parLevel;
	}

}
