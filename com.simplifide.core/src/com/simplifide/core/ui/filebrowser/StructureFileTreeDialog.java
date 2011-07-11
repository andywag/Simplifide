package com.simplifide.core.ui.filebrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


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
public class StructureFileTreeDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private StructureFileTreeComposite structureFileTreeComposite1;
	private Button cancelButton;
	private Button okButton;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/

	public StructureFileTreeDialog(Shell parent, int style) {
		super(parent, style);
		
	}

	public void open() {
		
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(615, 390);
			{
				cancelButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData cancelButtonLData = new FormData();
				cancelButtonLData.left =  new FormAttachment(0, 1000, 528);
				cancelButtonLData.top =  new FormAttachment(0, 1000, 315);
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
				okButtonLData.top =  new FormAttachment(0, 1000, 314);
				okButtonLData.width = 54;
				okButtonLData.height = 25;
				okButton.setLayoutData(okButtonLData);
				okButton.setText("OK");
			}
			{
				FormData structureFileTreeComposite1LData = new FormData();
				structureFileTreeComposite1LData.left =  new FormAttachment(0, 1000, 12);
				structureFileTreeComposite1LData.top =  new FormAttachment(0, 1000, 9);
				structureFileTreeComposite1LData.width = 575;
				structureFileTreeComposite1LData.height = 313;
				structureFileTreeComposite1 = new StructureFileTreeComposite(dialogShell, SWT.NONE);
				structureFileTreeComposite1.setLayoutData(structureFileTreeComposite1LData);
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
	
	
	
	public class CancelListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			dialogShell.close();
		}
	}
	
	public class OkListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			
		}
	}
	
}
