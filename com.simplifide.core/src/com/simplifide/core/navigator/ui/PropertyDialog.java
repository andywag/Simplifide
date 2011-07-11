package com.simplifide.core.navigator.ui;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.builder.HardwareBuilder;
import com.simplifide.core.builder.ProjectBuild;
import com.simplifide.core.project.EclipseSuite;

public class PropertyDialog extends Dialog {

	private IWorkbench workbench;
	private EclipseSuite suite;
	private PropertyComposite propertyComposite;
	
	public PropertyDialog(IWorkbench workbench, EclipseSuite suite) {
		super(workbench.getActiveWorkbenchWindow().getShell());
		this.workbench = workbench;
		this.suite = suite;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		propertyComposite = new PropertyComposite(container, SWT.NONE, suite);
		propertyComposite.setBounds(0, 0, 600, 300);
		
		return container;
	}

	@Override
	protected void okPressed() {
		this.propertyComposite.okPressed();
		try {
			suite.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		super.okPressed();
	}
	
	
}
