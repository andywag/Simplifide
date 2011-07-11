package com.simplifide.core.baseeditor.hyperlink;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;

public abstract class GeneralHyperlinkDetector implements IHyperlinkDetector{

	private GeneralEditor editor;
	
	public GeneralHyperlinkDetector(GeneralEditor editor) {
		this.setEditor(editor);
	}
	
	protected IHyperlink[] handleHttp(IRegion region) {
		IDocument doc = this.getEditor().getDocument();
		if (doc == null) return null;
		else {
			try {
				int loc = region.getOffset();
				IRegion reg = doc.getLineInformationOfOffset(region.getOffset());
				int lineIndex = region.getOffset() - reg.getOffset();
				String text = doc.get(reg.getOffset(), reg.getLength());
				int ind = text.indexOf("http://");
				if (ind < 0) return null; 
				String[] us = text.substring(ind).split(" ");
				//if (lineIndex > loc && lineIndex < loc + us[0].length()) {
					Region nreg = new Region(reg.getOffset() + ind, us[0].length());
					return new IHyperlink[] {new HttpHyperlink(us[0],nreg)};
				//}
				//return null;
				
				
				
			} catch (BadLocationException e) {
				HardwareLog.logError(e);
			}
			return null;

		}
	}
	
	

	public void setEditor(GeneralEditor editor) {
		this.editor = editor;
	}

	public GeneralEditor getEditor() {
		return editor;
	}

}
