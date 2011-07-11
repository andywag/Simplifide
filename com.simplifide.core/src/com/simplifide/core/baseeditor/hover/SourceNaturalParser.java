/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.hover;

import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.simplifide.core.baseeditor.color.ColorManager;

public class SourceNaturalParser {

	private StringBuilder builder = new StringBuilder();
	private TextPresentation present;
	
	private boolean bold = false;
	private boolean italic = false;
	private boolean underline = false;
	private boolean strike = false;
	
	private String textTag;
	
	private int fontsize = -1;
	private Color color = null;
	
	
	private int start;
	private int state;
	
	private static int STATE_NONE = 0;
	private static int STATE_TILDA = 1;
	
	private static RGB COLOR_KEY    = new RGB(0,0,153);
	private static RGB COLOR_TYPE   = new RGB(0,85,0);
	private static RGB COLOR_OBJECT = new RGB(0,0,0);
	private static RGB COLOR_DEFINE = new RGB(153,0,153);
	
	public SourceNaturalParser(TextPresentation present) {
		this.present = present;
	}
	
	private void createTildaRange(int off,int length) {
		StyleRange range = new StyleRange(off, length, null,null,SWT.BOLD);
		range.foreground = ColorManager.getStaticColor(COLOR_KEY);
		this.present.addStyleRange(range);
	}
	
	private void createItalicRange(int off,int length) {
		StyleRange range = new StyleRange(off, length, null,null,SWT.ITALIC);
		range.foreground = ColorManager.getStaticColor(COLOR_TYPE);
		this.present.addStyleRange(range);
	}
	
	private void createTagRange(char tag, int off,int length) {
		StyleRange range = null;
		if (tag == 'k') {
			range = new StyleRange(off, length, null,null,SWT.BOLD);
			range.foreground = ColorManager.getStaticColor(COLOR_KEY);
		}
		else if (tag == 't') {
			range = new StyleRange(off, length, null,null,SWT.BOLD);
			range.foreground = ColorManager.getStaticColor(COLOR_TYPE);
		}
		else if (tag == 'o') {
			range = new StyleRange(off, length, null,null,SWT.BOLD);
			range.foreground = ColorManager.getStaticColor(COLOR_OBJECT);
		}
		else if (tag == 'd') {
			range = new StyleRange(off, length, null,null,SWT.ITALIC);
			range.foreground = ColorManager.getStaticColor(COLOR_DEFINE);
		}
		else if (tag == 'b') {
			range = new StyleRange(off, length, null,null,SWT.BOLD);
		}
		else if (tag == 'i') {
			range = new StyleRange(off, length, null,null,SWT.ITALIC);
		}
		this.present.addStyleRange(range);
	}
	
	private String basicTransforms(String text) {
		String stext = text.replace("<space>", " ");
		stext = stext.replace("<tab>",     "   ");
		stext = stext.replace("<key>",     "<k>");
		stext = stext.replace("<type>",    "<t>");
		stext = stext.replace("<object>",  "<o>");
		stext = stext.replace("<define>",  "<d>");
		stext = stext.replace("</key>",     "<k>");
		stext = stext.replace("</type>",    "<t>");
		stext = stext.replace("</object>",  "<o>");
		stext = stext.replace("</define>",  "<d>");
		stext = stext.replace("</i>",  "<i>");
		stext = stext.replace("</b>",  "<b>");
		stext = stext.replace("&nbsp;",  " ");
		stext = stext.replace("\r", "");
		return stext;
	}
	
	private String removeLines(String text) {
		int empty = 0;
		StringBuilder build = new StringBuilder();
		String[] lines = text.split("\n");
		for (String line : lines) {
			String uline = line.trim();
			if (uline.contains("---------")) {}
			else if (uline.toUpperCase().startsWith("TOPIC:")){}
			else if (uline.toUpperCase().startsWith("CLASS:")) {}
			else if (uline.toUpperCase().startsWith("TASK:")) {}
			else if (uline.toUpperCase().startsWith("FUNCTION:")) {}
			else if (uline.toUpperCase().startsWith("GROUP:")) {}
			else if (uline.length() == 0) {
				if (empty < 1) build.append("\n");
				empty++;
			}
			else {
				build.append(line + "\n");
				empty = 0;
			}
		}
		return build.toString();
		
	}
	
	private boolean checkTags(StringBuilder tag) {
		char tag0 = tag.charAt(0);
		char tag1 = tag.charAt(1);
		if (tag1 == '>' && 
		   (tag0 == 'k' || tag0 == 't' || 
		    tag0 == 'o' || tag0 == 'd' ||
		    tag0 == 'b' || tag0 == 'i')) {
			return true;
		}
		return false;
	}
	
	
	private void handlePotentialTag(StringBuilder instring, StringBuilder outstring) {
		char type = instring.charAt(0);
		int  st   = outstring.length();
		instring.delete(0, 2);
		while (instring.length() > 0) {
			char u = instring.charAt(0);
			if (u != '<') {
				outstring.append(u);
				instring.deleteCharAt(0);
			}
			else {
				instring.delete(0, 3);
				break;
			}
		}
		this.createTagRange(type, st, outstring.length() - st);
	}
	
	private void handleSimpleTag(StringBuilder instring, StringBuilder outstring) {
		int st = outstring.length();
		while (instring.length() > 0) {
			char uchar = instring.charAt(0);
			instring.deleteCharAt(0);
			if (uchar == '>') {
				this.createTagRange('b', st, outstring.length() - st);
				break;
			}
			else {
				outstring.append(uchar);
			}
		}
	}
	
	private void handleOrText(StringBuilder instring, StringBuilder outstring) {
		instring.deleteCharAt(0);
		int st = outstring.length();
		while (instring.length() > 0) {
			char uchar = instring.charAt(0);
			instring.deleteCharAt(0);
			outstring.append(uchar);
			if (uchar == '\n') {
				if (instring.length() > 0 && instring.charAt(0) != '|') {
					this.createItalicRange(st, outstring.length() - st);
					break;
				}
				instring.deleteCharAt(0);
			}
		}
	}
	
	public String parseInputText(String text) {
		String ttext = basicTransforms(text);
		ttext = removeLines(ttext);
		StringBuilder instring = new StringBuilder(ttext);
		StringBuilder outstring = new StringBuilder();
			
		while (instring.length() > 0) {
			// Get Charachter and Remove it
			char uchar = instring.charAt(0);
			instring.deleteCharAt(0);
			if (uchar == '~') {
				if (state == STATE_TILDA) {
					this.createTildaRange(this.start, outstring.length() - this.start);
					this.state = STATE_NONE;
				}
				else {
					this.start = outstring.length();
					this.state = STATE_TILDA;
				}
			}
			else if (uchar == '<')
				if (checkTags(instring)) {
					this.handlePotentialTag(instring, outstring);
				}
				else {
					this.handleSimpleTag(instring, outstring);
			}
			else if (uchar == '\n' && instring.length() > 0 && instring.charAt(0) == '|') {
				outstring.append('\n');
				this.handleOrText(instring, outstring);
			}
			else {
				outstring.append(uchar);
			}
		}
		return outstring.toString();
	}
	
	
}
