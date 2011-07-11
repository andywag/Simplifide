package com.simplifide.core.ui.wizard.suite_prjfile;

import java.io.ByteArrayInputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureFile;


public class VerilogFileDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private VerilogFileComposite projectPropertyPage1; 
	private Button cancelButton;
	private Button okButton;

	private StructureBase structureFile;
	
	//private java.util.List<File> initialFiles;
	private ProjectGeneratorOptions.Existing  existing;
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/

	public VerilogFileDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	public VerilogFileDialog(Shell parent, int style, ProjectGeneratorOptions.Existing existing) {
		super(parent, style);
		this.existing = existing;
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(600, 500);
			{
				okButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData okButtonLData = new FormData();
				okButtonLData.left =  new FormAttachment(0, 1000, 397);
				okButtonLData.top =  new FormAttachment(0, 1000, 413);
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
				projectPropertyPage1LData.width = 560;
				projectPropertyPage1LData.height = 389;
				if (existing != null) {
					projectPropertyPage1 = new VerilogFileComposite(dialogShell, SWT.NONE,existing.initialFiles());
				}
				else {
					projectPropertyPage1 = new VerilogFileComposite(dialogShell, SWT.NONE);
				}
				
				projectPropertyPage1.setLayoutData(projectPropertyPage1LData);
			}
			{
				cancelButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 477);
				button1LData.top =  new FormAttachment(0, 1000, 413);
				button1LData.width = 68;
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
	
	public void setStructureFile(StructureBase structureFile) {
		this.structureFile = structureFile;
	}

	public StructureBase getStructureFile() {
		return structureFile;
	}

	public class CancelListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			dialogShell.close();
		}
	}
	
	public class OkListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			structureFile = projectPropertyPage1.getStructureFile();
			if (existing == null) {
				dialogShell.close();
			}
			else {
				structureFile = projectPropertyPage1.getStructureFile();
				existing.createSourceXml((StructureFile)structureFile);
			}
			dialogShell.close();
			
		}
	}
	
}
