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
import com.simplifide.core.errorparser.CommandDefinition;
import com.simplifide.core.errorparser.CommandExtensionManager;
import com.simplifide.core.errorparser.newui.ParserListComposite;
import com.simplifide.core.errorparser.newui.TargetComposite;

public class TargetPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	private TargetComposite target;
	
	  public TargetPreferencePage() {
	        super("Build Command Preference", FieldEditorPreferencePage.INFORMATION);
	        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	    }
	
	  
	  protected Control createContents(Composite parent) {
		  //Composite fieldEditorParent = new Composite(parent, SWT.NULL);
	      target = new TargetComposite(parent, SWT.NONE); 
	      return target;
	  } 
	  
	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}
	
	  public boolean performOk() {
	       CommandExtensionManager.createStore(target.performOK());
	       return true;
	  }

}
