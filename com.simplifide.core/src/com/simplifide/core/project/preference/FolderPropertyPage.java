package com.simplifide.core.project.preference;


import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceObject;

public class FolderPropertyPage extends PropertyPage{

	private Button notInProjectButton;
	private Button testButton;
	private Button mainButton;
	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		this.noDefaultAndApplyButton();
		this.createNotInProjectButton(parent);
		this.createTestButton(parent);
		
		return null;
		
	}

	private void createNotInProjectButton(Composite parent) {
		IResource folder = (IResource) this.getElement();
		
		try {
			String noProject = folder.getPersistentProperty(SourceObject.SOURCE_IGNORE);
			this.notInProjectButton = new Button(parent,SWT.CHECK);
			this.notInProjectButton.setText("Not In Project");
			if (noProject != null && noProject.equalsIgnoreCase(SourceObject.TRUE)) {
				this.notInProjectButton.setSelection(true);
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	private void createTestButton(Composite parent) {
		IResource folder = (IResource) this.getElement();
		
		try {
			String noProject = folder.getPersistentProperty(SourceObject.SOURCE_TEST);
			this.testButton = new Button(parent,SWT.CHECK);
			this.testButton.setText("Test File");
			if (noProject != null && noProject.equalsIgnoreCase(SourceObject.TRUE)) {
				this.testButton.setSelection(true);
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	private void createMainButton(Composite parent) {
		IResource folder = (IResource) this.getElement();
		
		try {
			String noProject = folder.getPersistentProperty(SourceObject.SOURCE_TEST);
			this.testButton = new Button(parent,SWT.CHECK);
			this.testButton.setText("Test File");
			if (noProject != null && noProject.equalsIgnoreCase(SourceObject.TRUE)) {
				this.testButton.setSelection(true);
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public boolean performOk() {
		boolean testEnabled = this.testButton.getSelection();
		boolean notProjectEnabled = this.notInProjectButton.getSelection();
		IResource res = (IResource) this.getElement();
		try {
			if (testEnabled) {
				res.setPersistentProperty(SourceObject.SOURCE_TEST, SourceObject.TRUE);
			}
			else {
				res.setPersistentProperty(SourceObject.SOURCE_TEST, null);
			}
			if (notProjectEnabled) {
				res.setPersistentProperty(SourceObject.SOURCE_IGNORE, SourceObject.TRUE);	
			}
			else {
				res.setPersistentProperty(SourceObject.SOURCE_IGNORE, null);
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return true;
	}
	
	
	
}
