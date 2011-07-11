/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.SourceEditor;

public class EditorUtilities {

	/** Returns the editor which is in use if it is a General
	 *  Editor
	 */
	public static GeneralEditor getActiveGeneralEditor() {
		 IWorkbench wb = PlatformUI.getWorkbench();
		   IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		   if (win == null) return null;
		   IWorkbenchPage page = win.getActivePage();
		   if (page == null) return null;
		   IEditorPart part = page.getActiveEditor();
		   if (part instanceof GeneralEditor) {
			   return (GeneralEditor) part;
		   }

		return null;
	}
	/** Returns the editor which is in use if it is a Source
	 *  Editor
	 */
	public static SourceEditor getActiveSourceEditor() {
		 IWorkbench wb = PlatformUI.getWorkbench();
		   IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		   if (win == null) return null;
		   IWorkbenchPage page = win.getActivePage();
		   if (page == null) return null;
		   IEditorPart part = page.getActiveEditor();
		   if (part instanceof SourceEditor) {
			   return (SourceEditor) part;
		   }

		return null;
	}
	
	public static String getLineIndent(IDocument doc, int pos) {
		StringBuilder builder = new StringBuilder();
		try {
			
			IRegion reg = doc.getLineInformationOfOffset(pos);
			int start = reg.getOffset();
			int end = reg.getOffset() + reg.getLength();
			while (start < end) {
				char uchar = doc.getChar(start);
				if (uchar == ' ' || uchar == '\t') {
					builder.append(uchar);
				}
				else {
					return builder.toString();
				}
				start++;
			}
			
			
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
		return "";
	}
	
}
