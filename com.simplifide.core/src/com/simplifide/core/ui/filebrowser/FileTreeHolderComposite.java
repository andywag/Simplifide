package com.simplifide.core.ui.filebrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;


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
public class FileTreeHolderComposite extends org.eclipse.swt.widgets.Composite {
	private FileTreeComposite fileTreeComposite1;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/

	public FileTreeHolderComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(367, 329);
			{
				FormData fileTreeComposite1LData = new FormData();
				fileTreeComposite1LData.left =  new FormAttachment(0, 1000, 0);
				fileTreeComposite1LData.top =  new FormAttachment(0, 1000, 6);
				fileTreeComposite1LData.width = 207;
				fileTreeComposite1LData.height = 311;
				fileTreeComposite1 = new FileTreeComposite(this, SWT.NONE);
				fileTreeComposite1.setLayoutData(fileTreeComposite1LData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileTreeComposite getFileTreeComposite1() {
		return fileTreeComposite1;
	}

}
