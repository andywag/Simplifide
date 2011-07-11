package com.simplifide.core.errorparser.pref;

import java.util.Iterator;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.errorparser.newui.ParserListComposite;

public class ExternalErrorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	private ParserListComposite parserComposite;
	
	  public ExternalErrorPreferencePage() {
	        super("External Error Parsers", FieldEditorPreferencePage.INFORMATION);
	        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	    }
	
	  
	  protected Control createContents(Composite parent) {
		  //Composite fieldEditorParent = new Composite(parent, SWT.NULL);
		  parserComposite = new ParserListComposite(parent, SWT.NONE); 
	      return parserComposite;
	  } 
	  
	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub	
	}
	
	 public boolean performOk() {
	        parserComposite.saveState();
	        return true;
	    }

}
