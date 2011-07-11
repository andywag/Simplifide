/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.hover;

import java.util.Enumeration;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Tag;

import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.simplifide.core.baseeditor.color.ColorManager;

public class SourceHtmlParser extends HTMLEditorKit.ParserCallback{

	private StringBuilder builder = new StringBuilder();
	private TextPresentation present;
	
	private boolean bold = false;
	private boolean italic = false;
	private boolean underline = false;
	private boolean strike = false;
	
	private String textTag;
	
	private int fontsize = -1;
	private Color color = null;
	
	private boolean opened = false;
	
	

	public SourceHtmlParser(TextPresentation present) {
		this.present = present;
	}
	
	//private void handleStyleRegionChange()
	
	@Override
	public void handleStartTag(Tag arg0, MutableAttributeSet arg1, int arg2) {
		//HardwareLog.logInfo("Start Tag <" + arg0 + "> at" + arg2 + arg1);
		String tagString = arg0.toString();
		if (tagString.equalsIgnoreCase("b")) {
			this.bold = true;
		}
		else if (tagString.equalsIgnoreCase("i")) {
			this.italic = true;
		}
		else if (tagString.equalsIgnoreCase("ul")) {
			this.underline = true;
		}
		else if (tagString.equalsIgnoreCase("s")) {
			this.strike = true;
		}
		else if (tagString.equalsIgnoreCase("type")) {
			this.bold = true;
			RGB col = new RGB(0,85,0);
			this.color = ColorManager.getStaticColor(col);
		}
		else if (tagString.equalsIgnoreCase("key")) {
			this.bold = true;
			RGB col = new RGB(0,0,153);
			this.color = ColorManager.getStaticColor(col);
		}
		else if (tagString.equalsIgnoreCase("object")) {
			this.bold = true;
			RGB col = new RGB(0,85,0);
			this.color = ColorManager.getStaticColor(col);
		}
		else if (tagString.equalsIgnoreCase("define")) {
			this.italic = true;
			RGB col = new RGB(153,0,153);
			this.color = ColorManager.getStaticColor(col);
		}
		
		else if (tagString.equalsIgnoreCase("font")) {
			
			Enumeration enum1 = arg1.getAttributeNames();
			while (enum1.hasMoreElements()) {
				Object element = enum1.nextElement();
				Object value = arg1.getAttribute(element);
				String st = element.toString();
				if (st.equalsIgnoreCase("size")) {
					this.fontsize = Integer.parseInt((String)value);
				}
				else if (st.equalsIgnoreCase("color")) {
					String uval = (String) value;
					String r = uval.substring(1,3);
					String g = uval.substring(3,5);
					String b = uval.substring(5,7);
					int ri = Integer.parseInt(r, 16);
					int gi = Integer.parseInt(g,16);
					int bi = Integer.parseInt(b,16);
					RGB col= new RGB(ri,gi,bi);
					this.color = ColorManager.getStaticColor(col);
				}
			}
		}
		
		
		super.handleStartTag(arg0, arg1, arg2);
	}
	
	
	
	@Override
	public void handleEndTag(Tag arg0, int arg1) {
		//HardwareLog.logInfo("Eng Tag <" + arg0 + ">at" + arg1);
		String tagString = arg0.toString();
		if (tagString.equalsIgnoreCase("b")) {
			this.bold = false;
		}
		else if (tagString.equalsIgnoreCase("i")) {
			this.italic = false;
		}
		else if (tagString.equalsIgnoreCase("ul")) {
			this.underline = false;
		}
		else if (tagString.equalsIgnoreCase("s")) {
			this.strike = false;
		}
		else if (tagString.equalsIgnoreCase("font")) {
			this.color = null;
			this.fontsize = -1;
		}
		else if (tagString.equalsIgnoreCase("type")) {
			this.color = null;
			this.bold = false;
		}
		else if (tagString.equalsIgnoreCase("key")) {
			this.color = null;
			this.bold = false;
		}
		else if (tagString.equalsIgnoreCase("object")) {
			this.color = null;
			this.bold = false;
		}
		else if (tagString.equalsIgnoreCase("define")) {
			this.color = null;
			this.italic = false;
		}
		super.handleEndTag(arg0, arg1);
	}

	@Override
	public void handleError(String arg0, int arg1) {
		// TODO Auto-generated method stub
		super.handleError(arg0, arg1);
	}

	@Override
	public void handleSimpleTag(Tag arg0, MutableAttributeSet arg1, int arg2) {
		//HardwareLog.logInfo("Simple Tag " + arg0 + "at" + arg2);
		if (arg0.toString().equalsIgnoreCase("br")) {
			builder.append('\r');
			builder.append('\n');
		}
		else if (arg0.toString().equalsIgnoreCase("space")) {
			builder.append(' ');
		}
		else if (arg0.toString().equalsIgnoreCase("tab")) {
			builder.append('\t');
		}
		else if (arg0.toString().equalsIgnoreCase("type")   ||
				 arg0.toString().equalsIgnoreCase("key")    || 
				 arg0.toString().equalsIgnoreCase("object") ||
				 arg0.toString().equalsIgnoreCase("define")) {
			if (opened) {
				this.handleEndTag(arg0, arg2);
				this.opened = false;
			}
			else {
				this.handleStartTag(arg0, arg1, arg2);
				this.opened = false;
			}
		}
		
		super.handleSimpleTag(arg0, arg1, arg2);
	}

	
	private StyleRange createStyleRange(int start, int length) {
		int textType = 0;
		if (bold == true) textType = SWT.BOLD;
		if (italic == true) textType = textType | SWT.ITALIC;
		StyleRange range = new StyleRange(start,length,null,null,textType);
		if (this.strike == true) range.strikeout = true;
		if (this.underline == true) range.underline = true;
		if (this.color != null) range.foreground = this.color;
		
		if (this.fontsize != -1) {
			//range.length = this.fontsize;
			/*
			Font cfont = this.present.getDefaultStyleRange().font;
			FontData cfontdata = cfont.getFontData()[0];
			Font ufont = new Font(cfont.getDevice(),cfontdata.getName(),this.fontsize,0);
			range.font = ufont;
			*/
		}
		
		return range;
	}
	
	// Only Append text when the tags don't exist
	public void handleText(char[] arg0, int arg1) {
		//HardwareLog.logInfo("Handle Text " + "at" + arg1);
		int off = builder.length();
		int len = arg0.length;
		StyleRange range = this.createStyleRange(off,len);
		this.present.addStyleRange(range);
		this.getBuilder().append(arg0);
		
		super.handleText(arg0, arg1);
	}
	
	@Override
	public void handleComment(char[] arg0, int arg1) {
		// TODO Auto-generated method stub
		super.handleComment(arg0, arg1);
	}

	@Override
	public void handleEndOfLineString(String arg0) {
		// TODO Auto-generated method stub
		
		super.handleEndOfLineString(arg0);
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
	}

	public StringBuilder getBuilder() {
		return builder;
	}

	
}
