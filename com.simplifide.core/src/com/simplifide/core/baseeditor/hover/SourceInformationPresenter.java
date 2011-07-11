/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.hover;

import java.io.IOException;
import java.io.StringReader;

import javax.swing.text.html.parser.ParserDelegator;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.DefaultInformationControl.IInformationPresenter;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.widgets.Display;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class SourceInformationPresenter implements IInformationPresenter,
					DefaultInformationControl.IInformationPresenterExtension {

	private String text;
	
	public SourceInformationPresenter() {
		
	}
	
	public String updatePresentation(Display display, 
			String hoverInfo, 
			TextPresentation presentation, 
			int maxWidth, 
			int maxHeight) {
		//return parseNaturalString(hoverInfo, presentation);
		
		String uhtml = hoverInfo.replaceAll("\n", "<br>");
		uhtml = uhtml.replaceAll("\r", "");
		this.parseString(uhtml,presentation);
		return text;
		
	}

	private String parseNaturalString(String html, TextPresentation present) {
		SourceNaturalParser parser = new SourceNaturalParser(present);
		return parser.parseInputText(html);
	}
	
	
	
	private String convertHtmlKeys(String html) {
		String shtml = html.replace("<key>",      "<b><font color=\"#000099\">");
		shtml        = shtml.replace("</key>",    "</font></b>");
		shtml        = shtml.replace("<type>",    "<b><font color=\"#005500\">");
		shtml        = shtml.replace("</type>",   "</font></b>");
		shtml        = shtml.replace("<object>",  "<b>");
		shtml        = shtml.replace("</object>", "</b>");
		shtml        = shtml.replace("<define>",  "<b><font color=\"#550055\">");
		shtml        = shtml.replace("</define>", "</font></b>");
		return shtml;
	}
	
	private void parseString(String html, TextPresentation present) {
		ParserDelegator parse = new ParserDelegator();
		String shtml = this.convertHtmlKeys(html);
		StringReader reader = new StringReader(shtml);
		SourceHtmlParser parser = new SourceHtmlParser(present);
		
		try {
			
			parse.parse(reader, parser, true);
			this.text = parser.getBuilder().toString();
		} catch (IOException e) {
			HardwareLog.logError(e);
			this.text = html;
		}
		
		
		
	}



	public String updatePresentation(Drawable drawable, String hoverInfo,
			TextPresentation presentation, int maxWidth, int maxHeight) {
		if (CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.HOVER_NATURAL)) {
			return parseNaturalString(hoverInfo, presentation);
		}
		else {
			String uhtml = hoverInfo.replaceAll("\n", "<br>");
			uhtml = uhtml.replaceAll("\r", "");
			this.parseString(uhtml,presentation);
			return text;
		}
		
		
	}

	
}
