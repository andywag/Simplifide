package com.simplifide.core.ui.wizard.suite_import;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;




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
public class SuiteStructureBottomComposite extends Composite{

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	

	
	

	private Label lable;
	private Button linkedStructure;
	private Label label1;
	private Text structureLocationText;
	private Button existingStructure;
	private Button browseButton;
	
	public SuiteStructureBottomComposite(Composite parent, int style) {
		super(parent, style);
		init();
	}
	
	private void init() {
		this.setBackground(SWTResourceManager.getColor(255, 255, 255));
		this.setSize(650, 100);
		{
			setExistingStructure(new Button(this, SWT.CHECK | SWT.LEFT));
			getExistingStructure().setText("Existing Structure.xml");
			getExistingStructure().setBounds(19, 12, 158, 30);
			getExistingStructure().setBackground(SWTResourceManager.getColor(255, 255, 255));
			getExistingStructure().addSelectionListener(new ExistingListener());
		}
		{
			linkedStructure = new Button(this, SWT.CHECK | SWT.LEFT);
			linkedStructure.setText("Linked Structure.xml");
			linkedStructure.setBounds(183, 12, 181, 30);
			linkedStructure.setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		{
			label1 = new Label(this, SWT.NONE);
			label1.setText("Structure Location");
			label1.setBounds(19, 48, 109, 19);
			label1.setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		{
			structureLocationText = new Text(this, SWT.BORDER);
			structureLocationText.setBounds(128, 48, 360, 19);
		}
		{
			browseButton = new Button(this, SWT.PUSH | SWT.CENTER);
			browseButton.setText("Browse");
			browseButton.setBounds(500, 42, 60, 30);
			browseButton.addSelectionListener(new BrowseListener());
		}
		this.handleEnableStructure(false);
	}
	
	public void handleEnableStructure(boolean selected) {
		structureLocationText.setEnabled(selected);
		browseButton.setEnabled(selected);

	}
	
	public void setExistingStructure(Button existingStructure) {
		this.existingStructure = existingStructure;
	}

	public Button getExistingStructure() {
		return existingStructure;
	}

	public boolean isExisting() {
		return this.existingStructure.getSelection();
	}
	
	public boolean isLinked() {
		return this.linkedStructure.getSelection();
	}
	
	public String getLocationText() {
		return this.structureLocationText.getText();
	}
	
	public StructureFile getStructureFile() {
		if (this.existingStructure.getSelection()) {
			StructureFile structure = new StructureFile("Structure.xml");
			structure.setLocation(this.getLocationText());
			if (this.isLinked()) structure.setLinkType(StructureBase.LINK_ECLIPSE);
			else structure.setLinkType(StructureBase.LINK_COPY);
			return structure;
		}
		return null;
	}
	
	public class BrowseListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			FileDialog dialog = new FileDialog(getShell());
			dialog.open();
			String na = dialog.getFileName();
			if (na != null && !na.equalsIgnoreCase("")) {
				File ufile = new File(dialog.getFilterPath(),dialog.getFileName());
				structureLocationText.setText(ufile.getAbsolutePath());
				StructureFile file = new StructureFile(ufile.getName());
				file.setLocation(ufile.getAbsolutePath());
				WorkspaceDirectoryStructure dir = RootStructureLoader.loadFromFileOnly(file);
				

			}
			
		}
	}
	
	public class ExistingListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			boolean selected = getExistingStructure().getSelection();
			handleEnableStructure(selected);
		}
	}

}
