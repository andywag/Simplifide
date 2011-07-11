package com.simplifide.core.baseeditor.hyperlink;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.simplifide.core.HardwareLog;

public class HttpHyperlink implements IHyperlink{

	private String link;
	private IRegion region;
	
	public HttpHyperlink(String link, IRegion region) { 
		this.link = link;
		this.region = region;
	}

	
	public IRegion getHyperlinkRegion() {
		return this.region;
	}

	
	public String getTypeLabel() {
		return null;
	}

	
	public String getHyperlinkText() {
		return this.link;
	}

	
	public void open() {
		URL url;
		try {
			url = new URL(link);
			IWorkbenchBrowserSupport browser = PlatformUI.getWorkbench().getBrowserSupport();
			browser.getExternalBrowser().openURL(url);
			
		} catch (MalformedURLException e) {
			HardwareLog.logError(e);
		} catch (PartInitException e) {
			HardwareLog.logError(e);
		}
		
	}
	
}
