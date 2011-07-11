package com.simplifide.core.ui.filebrowser;

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

import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;


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
public class StructureFileTreeFullDialog extends Dialog {

	private Shell dialogShell;
	private StructureFileTreeFullComposite structureFileTreeComposite1;
	private Button cancelButton;
	private Button okButton;

	private StructureFile structureFile;
	
	private StructureDirectory defaultStructure;
	private int projectType;
	
	public StructureFileTreeFullDialog(Shell parent, int style, StructureDirectory dir,
			int projectType) {
		super(parent, style);
		this.defaultStructure = dir;
		this.projectType = projectType;
		
	}

	public void open() {
		
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(615, 444);
			{
				cancelButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData cancelButtonLData = new FormData();
				cancelButtonLData.left =  new FormAttachment(0, 1000, 528);
				cancelButtonLData.top =  new FormAttachment(0, 1000, 350);
				cancelButtonLData.width = 48;
				cancelButtonLData.height = 25;
				cancelButton.setLayoutData(cancelButtonLData);
				cancelButton.setText("Cancel");
				cancelButton.addSelectionListener(new CancelListener());
			}
			{
				okButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData okButtonLData = new FormData();
				okButtonLData.left =  new FormAttachment(0, 1000, 456);
				okButtonLData.top =  new FormAttachment(0, 1000, 350);
				okButtonLData.width = 54;
				okButtonLData.height = 25;
				okButton.setLayoutData(okButtonLData);
				okButton.setText("OK");
				okButton.addSelectionListener(new OkListener());
			}
			{
				FormData structureFileTreeComposite1LData = new FormData();
				structureFileTreeComposite1LData.left =  new FormAttachment(0, 1000, 12);
				structureFileTreeComposite1LData.top =  new FormAttachment(0, 1000, 9);
				structureFileTreeComposite1LData.width = 575;
				structureFileTreeComposite1LData.height = 340;
				structureFileTreeComposite1 = new StructureFileTreeFullComposite(dialogShell, 
						SWT.NONE,this.projectType);
				structureFileTreeComposite1.setLayoutData(structureFileTreeComposite1LData);
				structureFileTreeComposite1.setDefaultStructure(this.defaultStructure);
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
	
	public void setDefaultStructure(StructureDirectory struct) {
		this.structureFileTreeComposite1.setDefaultStructure(struct);
	}
	
	
	public void setStructureFile(StructureFile structureFile) {
		this.structureFile = structureFile;
	}

	public StructureFile getStructureFile() {
		return structureFile;
	}



	public class CancelListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			dialogShell.close();
		}
	}
	
	public class OkListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			structureFile = structureFileTreeComposite1.getStructureFile();
			dialogShell.close();
		}
	}
	
}
