package com.simplifide.core.baseeditor.actions.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;

public class TabsToSpacesActionDelegate extends FormatActionDelegate{

	public TabsToSpacesActionDelegate() {}
	public TabsToSpacesActionDelegate(GeneralEditor editor) {super(editor);}
	
	
	
	
	@Override
	public void run(GeneralEditor editor) {
		
	    int len = EditorsUI.getPreferenceStore().getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
	    StringBuilder sp = new StringBuilder();
	    for (int i=0;i<len;i++) {
	    	sp.append(" ");
	    }
		
		String tot = this.getEditor().getDocument().get();
		String utot = tot.replace("\t", sp.toString());
		try {
			this.getEditor().getDocument().replace(0, this.getEditor().getDocument().getLength(), utot);
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
		
	}

	public static class TabsToSpacesHandler extends GeneralHandler {

		@Override
		public TabsToSpacesActionDelegate createDelegate(GeneralEditor editor) {
			return new TabsToSpacesActionDelegate(editor);
		}
		
	}	
	
	
}
