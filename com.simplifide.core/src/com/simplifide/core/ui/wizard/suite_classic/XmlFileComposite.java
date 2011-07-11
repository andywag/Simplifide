package com.simplifide.core.ui.wizard.suite_classic;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureFile;


public class XmlFileComposite extends org.eclipse.swt.widgets.Composite {
	private Label label1;
	private Button enableLocation;
	private Button copyButton;
	private Link openNew;
	private Button browseButton;
	private Text locationText;
	
	private String checkBoxName = "";

	
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	public XmlFileComposite(org.eclipse.swt.widgets.Composite parent, int style, String chName) {
		super(parent, style);
		this.checkBoxName = chName;
		initGUI();	
	}
	
	public void setDisabled() {
		this.disableEntry();
		this.enableLocation.setEnabled(false);
	}

	private void initGUI() {
		try {
			this.setLayout(new FormLayout());
			{
				copyButton = new Button(this, SWT.CHECK | SWT.LEFT);
				copyButton.setText("Link");
				FormData copyButtonLData = new FormData();
				copyButtonLData.width = 47;
				copyButtonLData.height = 15;
				copyButtonLData.left =  new FormAttachment(0, 1000, 445);
				copyButtonLData.top =  new FormAttachment(0, 1000, 30);
				copyButton.setLayoutData(copyButtonLData);
				copyButton.setEnabled(false);
			}
			{
				openNew = new Link(this, SWT.NONE);
				openNew.setText("<a href=\"New File\">New File</a>");
				FormData openNewLData = new FormData();
				openNewLData.width = 50;
				openNewLData.height = 15;
				openNewLData.left =  new FormAttachment(0, 1000, 200);
				openNewLData.top =  new FormAttachment(0, 1000, 8);
				openNew.setLayoutData(openNewLData);
				openNew.setEnabled(false);
			}
			{
				enableLocation = new Button(this, SWT.CHECK | SWT.LEFT);
				enableLocation.setText(this.checkBoxName);
				FormData enableLocationLData = new FormData();
				enableLocationLData.width = 220;
				enableLocationLData.height = 16;
				enableLocationLData.left =  new FormAttachment(0, 1000, 0);
				enableLocationLData.top =  new FormAttachment(0, 1000, 8);
				enableLocation.setLayoutData(enableLocationLData);
				enableLocation.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						boolean en = enableLocation.getSelection();
						if (en) enableEntry();
						else disableEntry();
					}
				});
			}
			{
				browseButton = new Button(this, SWT.PUSH | SWT.CENTER);
				browseButton.setText("Browse...");
				FormData browseButtonLData = new FormData();
				browseButtonLData.width = 91;
				browseButtonLData.height = 25;
				browseButtonLData.left =  new FormAttachment(0, 1000, 348);
				browseButtonLData.top =  new FormAttachment(0, 1000, 26);
				browseButton.setLayoutData(browseButtonLData);
				browseButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						handleBrowse();
					}
				});
				browseButton.setEnabled(false);
			}
			{
				locationText = new Text(this, SWT.MULTI | SWT.WRAP | SWT.BORDER);
				FormData locationTextLData = new FormData();
				locationTextLData.width = 280;
				locationTextLData.height = 15;
				locationTextLData.left =  new FormAttachment(0, 1000, 50);
				locationTextLData.top =  new FormAttachment(0, 1000, 28);
				locationText.setLayoutData(locationTextLData);
				locationText.setEnabled(false);
			}
			{
				label1 = new Label(this, SWT.NONE);
				label1.setText("Location:");
				FormData label1LData = new FormData();
				label1LData.width = 50;
				label1LData.height = 15;
				label1LData.left =  new FormAttachment(0, 1000, 0);
				label1LData.top =  new FormAttachment(0, 1000, 30);
				label1.setLayoutData(label1LData);
				label1.setEnabled(false);
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void handleBrowse() {
		FileDialog dialog = new FileDialog(browseButton.getShell());
		String fileLocation = dialog.open();
		locationText.setText(fileLocation);
	}
	
	protected void enableEntry() {
		this.label1.setEnabled(true);
		this.browseButton.setEnabled(true);
		this.locationText.setEnabled(true);
		this.copyButton.setEnabled(true);
		//this.openNew.setEnabled(true);
	}
	protected void disableEntry() {
		this.label1.setEnabled(false);
		this.browseButton.setEnabled(false);
		this.locationText.setEnabled(false);
		this.copyButton.setEnabled(false);
		//this.openNew.setEnabled(false);
	}
	
	public StructureFile getXmlFile() {
		if (enableLocation.getSelection()) {
			String tex = locationText.getText();
			File fi = new File(this.locationText.getText());
			StructureFile ufile = new StructureFile(fi.getName());
			ufile.setLocation(tex);
			if (copyButton.getSelection()) ufile.setLinkType(StructureBase.LINK_ECLIPSE);
			else ufile.setLinkType(StructureBase.LINK_COPY);
			return ufile;
		}
		return null;
	}
	
	public String getLocationText() {
		return this.locationText.getText();
	}
	
	public boolean isLinked() {
		return this.copyButton.getSelection();
	}

}
