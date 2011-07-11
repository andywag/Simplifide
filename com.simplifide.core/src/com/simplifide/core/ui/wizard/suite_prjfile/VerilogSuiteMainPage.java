package com.simplifide.core.ui.wizard.suite_prjfile;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.simplifide.base.license.info.HardwareChecker;

public class VerilogSuiteMainPage extends WizardNewProjectCreationPage{

	private Button useXilinxProj;
	private Text locationText;
	private Button browseButton;
	private Label location;
	
	public VerilogSuiteMainPage() {
		super("New Linked Suite");
		this.setTitle("New Linked Suite");
		String description = "A suite which links files and libraries into the workspace.";
		
		if (!HardwareChecker.isWizardEnabled()) {
			description += "This wizard is not available in the free version";
		}
		this.setDescription(description);
	}
	
	
	
	public void createControl(Composite parent)  {
		super.createControl(parent);
		Composite control = (Composite) this.getControl();
		Composite ncomp = new Composite(control,SWT.None);
		GridLayout layout = new GridLayout();
		ncomp.setLayout(layout);
		useXilinxProj = new Button(ncomp,SWT.CHECK);
		useXilinxProj.setText("Use Xilinx Style Project File");
		useXilinxProj.setSelection(false);
		useXilinxProj.addSelectionListener(new UseXilinxListener());
		
		
		
		Composite projectGroup = new Composite(ncomp, SWT.NONE);
        layout = new GridLayout();
        layout.numColumns = 3;
        location = new Label(projectGroup,SWT.None);
        location.setText("Location:");
        projectGroup.setLayout(layout);
        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        locationText = new Text(projectGroup,SWT.BORDER);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = 400;
        locationText.setLayoutData(data);
        browseButton = new Button(projectGroup,SWT.NONE);
        browseButton.setText("Browse...");
        browseButton.addSelectionListener(new BrowseListener()); 
		
        enableXilinx(useXilinxProj.getSelection());
	}
	
	public boolean useProjectFile() {
		return useXilinxProj.getSelection();
	}
	
	public String getProjectFileLocation() {
		return this.locationText.getText();
	}
	
	
	public void enableXilinx(boolean enable) {
		browseButton.setEnabled(enable);
		locationText.setEnabled(enable);
		location.setEnabled(enable);
	}
	
	public class UseXilinxListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			enableXilinx(useXilinxProj.getSelection());
		}
	}
	
	public class BrowseListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			FileDialog dialog = new FileDialog(getShell());
			dialog.open();
			File ustr = new File(dialog.getFilterPath(),dialog.getFileName());
			locationText.setText(ustr.getAbsolutePath());
		}
	}
	
}
