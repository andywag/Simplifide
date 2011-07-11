package com.simplifide.core.ui.wizard.suite_import.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.simplifide.core.project.generator.ProjectGeneratorOptions;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ProjectPropertyDialog extends Dialog {

	private Shell dialogShell;
	private ProjectPropertyComposite projectPropertyPage1; 
	private Button cancelButton;
	private Button okButton;

	private String type;
	
	private ProjectGeneratorOptions projectGeneratorOptions;
	
	

	public ProjectPropertyDialog(Shell parent, int style, String type) {
		super(parent, style);
		this.type = type;
	}
	
	public ProjectPropertyDialog(Shell parent, int style, String type, ProjectGeneratorOptions proj) {
		super(parent, style);
		this.type = type;
		this.projectGeneratorOptions = proj;
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(712, 300);
			{
				okButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData okButtonLData = new FormData();
				okButtonLData.left =  new FormAttachment(0, 1000, 476);
				okButtonLData.top =  new FormAttachment(0, 1000, 230);
				okButtonLData.width = 68;
				okButtonLData.height = 25;
				okButton.setLayoutData(okButtonLData);
				okButton.setText("Ok");
				okButton.addSelectionListener(new OkListener());
			}
			{
				FormData projectPropertyPage1LData = new FormData();
				projectPropertyPage1LData.left =  new FormAttachment(0, 1000, 12);
				projectPropertyPage1LData.top =  new FormAttachment(0, 1000, 12);
				projectPropertyPage1LData.width = 655;
				projectPropertyPage1LData.height = 195;
				if (projectGeneratorOptions == null) {
					projectPropertyPage1 = new ProjectPropertyComposite(dialogShell, SWT.NONE, type,
							null,0);
				}
				else {
					projectPropertyPage1 = new ProjectPropertyComposite(dialogShell, SWT.NONE, projectGeneratorOptions);
				}
				
				projectPropertyPage1.setLayoutData(projectPropertyPage1LData);
			}
			{
				cancelButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 571);
				button1LData.top =  new FormAttachment(0, 1000, 230);
				button1LData.width = 82;
				button1LData.height = 25;
				cancelButton.setLayoutData(button1LData);
				cancelButton.setText("Cancel");
				cancelButton.addSelectionListener(new CancelListener());
			}
			
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public ProjectGeneratorOptions getProjectGeneratorOptions() {
		return projectGeneratorOptions;
	}
	
	public class CancelListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			dialogShell.close();
		}
	}
	
	public class OkListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			projectGeneratorOptions = projectPropertyPage1.getProjectGeneratorOptions();
			dialogShell.close();
		}
	}
	
}
