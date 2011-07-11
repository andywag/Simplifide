package com.simplifide.core.ui.wizard.suite_import.project;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.ui.filebrowser.StructureFileTreeFullDialog;
import com.simplifide.core.ui.wizard.suite_prjfile.VerilogFileDialog;




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
public class ProjectPropertyComposite extends Composite {
	private Label project_location;
	private Button newButton;
	private Button existingButton;
	private Button copyProjectButton;
	private Button linkedProjectButton;
	private Button srcOnly;
	private Button fileListButton;
	private Button structureEnableButton;
	private Button fileCreateButton;
	private Button structureCreateButton;
	private Text structureLocation;
	private Text fileNameLocation;
	private Text projectNameText;
	private Label projectName;
	private Button projectBrowseButton;
	private Text projectLocationText;

	
	private StructureBase structureFile;
	private StructureBase sourceFile;
	
	private String type;
	
	private StructureDirectory defaultStructure;
	private int projectType;
	private ProjectGeneratorOptions.Existing existing;
	private FormData text1LData;

	
	public ProjectPropertyComposite(Composite parent, 
			int style, String type, StructureDirectory defaultStructure, int projectType) {
		super(parent, style);
		this.defaultStructure = defaultStructure;
		this.type = type;
		this.projectType = projectType;
		initGUI();
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ProjectPropertyComposite(Composite parent, 
			int style, ProjectGeneratorOptions options) {
		super(parent, style);
		
		if (options instanceof ProjectGeneratorOptions.Existing) {
			ProjectGeneratorOptions.Existing exist = (ProjectGeneratorOptions.Existing) options;
			this.existing = exist;
			this.defaultStructure = exist.getDefaultStructure();
			this.type = exist.getType();
			this.projectType = exist.getProjectType();
		}
		
		initGUI();
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(675, 340);
			

			int startColumn = 20;
			int textColumn = 110;
			
			int startRow = 20;
			int row2 = 50;
			int row3 = 80;
			int row4 = 110;
			int row5 = 140;
			// ROW 1
			{
				projectName = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, startColumn);
				label1LData.top =  new FormAttachment(0, 1000, startRow);
				label1LData.width = 86;
				label1LData.height = 15;
				projectName.setLayoutData(label1LData);
				projectName.setText(type + " Name");
			}
			{
				text1LData = new FormData();
				text1LData.left =  new FormAttachment(0, 1000, textColumn);
				text1LData.top =  new FormAttachment(0, 1000, startRow-1);
				text1LData.width = 170;
				text1LData.height = 17;
				projectNameText = new Text(this, SWT.BORDER);
				projectNameText.setLayoutData(text1LData);
			}
			{
				newButton = new Button(this, SWT.RADIO | SWT.LEFT);
				text1LData.right = new FormAttachment(newButton, -39);
				FormData newButtonLData = new FormData();
				newButtonLData.left =  new FormAttachment(0, 1000, 330);
				newButtonLData.top =  new FormAttachment(0, 1000, 21);
				newButtonLData.width = 66;
				newButtonLData.height = 16;
				newButton.setLayoutData(newButtonLData);
				newButton.setText("New");
				newButton.addSelectionListener(new NewListener());
				newButton.setSelection(true);
			}
			{
				existingButton = new Button(this, SWT.RADIO | SWT.LEFT);
				FormData button1LDataa = new FormData();
				button1LDataa.top = new FormAttachment(projectName, 0, SWT.TOP);
				button1LDataa.left = new FormAttachment(newButton, 6);
				button1LDataa.width = 80;
				button1LDataa.height = 16;
				existingButton.setLayoutData(button1LDataa);
				existingButton.setText("Existing");
				//existingButton.addSelectionListener(new CopyListener());

			}
			{
				copyProjectButton = new Button(this, SWT.RADIO | SWT.LEFT);
				FormData button1LData2 = new FormData();
				button1LData2.left =  new FormAttachment(0, 1000, 580);
				button1LData2.top =  new FormAttachment(0, 1000, 21);
				button1LData2.width = 80;
				button1LData2.height = 16;
				copyProjectButton.setLayoutData(button1LData2);
				copyProjectButton.setText("Copy");
				copyProjectButton.addSelectionListener(new CopyListener());

			}
			{
				linkedProjectButton = new Button(this, SWT.RADIO | SWT.LEFT);
				FormData linkedProjectLData = new FormData();
				linkedProjectLData.left =  new FormAttachment(0, 1000, 500);
				linkedProjectLData.top =  new FormAttachment(0, 1000, 21);
				linkedProjectLData.width = 60;
				linkedProjectLData.height = 16;
				linkedProjectButton.setLayoutData(linkedProjectLData);
				linkedProjectButton.setText("Link");
				linkedProjectButton.addSelectionListener(new LinkListener());
			}
			// ROW 2
			{
				project_location = new Label(this, SWT.NONE);
				FormData project_locationLData = new FormData();
				project_locationLData.left =  new FormAttachment(0, 1000, 20);
				project_locationLData.top =  new FormAttachment(0, 1000, 53);
				project_locationLData.width = 150;
				project_locationLData.height = 17;
				project_location.setLayoutData(project_locationLData);
				project_location.setText(type + " Location");
			}
			{
				FormData projectLocationLData = new FormData();
				projectLocationLData.left =  new FormAttachment(0, 1000, 192);
				projectLocationLData.top =  new FormAttachment(0, 1000, 53);
				projectLocationLData.width = 340;
				projectLocationLData.height = 17;
				projectLocationText = new Text(this, SWT.BORDER);
				projectLocationText.setLayoutData(projectLocationLData);
			}
			{
				projectBrowseButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData browseButtonLData = new FormData();
				browseButtonLData.left =  new FormAttachment(0, 1000, 571);
				browseButtonLData.top =  new FormAttachment(0, 1000, 50);
				browseButtonLData.width = 71;
				browseButtonLData.height = 25;
				projectBrowseButton.setLayoutData(browseButtonLData);
				projectBrowseButton.setText("Browse");
				projectBrowseButton.addSelectionListener(new ProjectBrowseListener());
			}
			// Row 3
			{
				srcOnly = new Button(this, SWT.CHECK | SWT.LEFT);
				FormData srcOnlyLData = new FormData();
				srcOnlyLData.left =  new FormAttachment(0, 1000, 23);
				srcOnlyLData.top =  new FormAttachment(0, 1000, 84);
				srcOnlyLData.width = 136;
				srcOnlyLData.height = 16;
				srcOnly.setLayoutData(srcOnlyLData);
				srcOnly.setText("Source Only");
			}
			// Row 4
			{
				fileListButton = new Button(this, SWT.CHECK | SWT.LEFT);
				FormData fileListButtonLData = new FormData();
				fileListButtonLData.left =  new FormAttachment(0, 1000, 23);
				fileListButtonLData.top =  new FormAttachment(0, 1000, 114);
				fileListButtonLData.width = 147;
				fileListButtonLData.height = 16;
				fileListButton.setLayoutData(fileListButtonLData);
				fileListButton.setText("File List");
				fileListButton.addSelectionListener(new FileListListener());
			}
			{
				fileNameLocation = new Text(this, SWT.BORDER);
				FormData FileListLocationLData = new FormData();
				FileListLocationLData.left =  new FormAttachment(0, 1000, 192);
				FileListLocationLData.top =  new FormAttachment(0, 1000, 109);
				FileListLocationLData.width = 340;
				FileListLocationLData.height = 17;
				fileNameLocation.setLayoutData(FileListLocationLData);
			}
			
			{
				fileCreateButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button2LData = new FormData();
				button2LData.left =  new FormAttachment(0, 1000, 571);
				button2LData.top =  new FormAttachment(0, 1000, 109);
				button2LData.width = 71;
				button2LData.height = 25;
				fileCreateButton.setLayoutData(button2LData);
				fileCreateButton.setText("Create");
				fileCreateButton.addSelectionListener(new CreateFileListener());
			}
			// Row 5
			{
				structureEnableButton = new Button(this, SWT.CHECK | SWT.LEFT);
				FormData newFileListLData = new FormData();
				newFileListLData.left =  new FormAttachment(0, 1000, 22);
				newFileListLData.top =  new FormAttachment(0, 1000, 148);
				newFileListLData.width = 252;
				newFileListLData.height = 16;
				structureEnableButton.setLayoutData(newFileListLData);
				structureEnableButton.setText("Non-Default Directory Structure");
				structureEnableButton.addSelectionListener(new StructureListListener());
			}
		
			{
				FormData text2LData = new FormData();
				text2LData.left =  new FormAttachment(0, 1000, 292);
				text2LData.top =  new FormAttachment(0, 1000, 146);
				text2LData.width = 240;
				text2LData.height = 17;
				structureLocation = new Text(this, SWT.BORDER);
				structureLocation.setLayoutData(text2LData);
			}
			{
				structureCreateButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button1LData1 = new FormData();
				button1LData1.left =  new FormAttachment(0, 1000, 571);
				button1LData1.top =  new FormAttachment(0, 1000, 146);
				button1LData1.width = 71;
				button1LData1.height = 25;
				structureCreateButton.setLayoutData(button1LData1);
				structureCreateButton.setText("Create");
				structureCreateButton.addSelectionListener(new CreateStructListener());
			}
			
			
			enableFileList(false);
			enableStructureList(false);
			enableNew(true);
			enableProjectLocation(false);
			this.layout();
			initOnProject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initOnProject() {
		if (this.existing == null) return;
		this.projectNameText.setText(existing.getName());
		this.projectLocationText.setText(existing.getLocation());
		this.linkedProjectButton.setSelection(existing.isLink());
		this.newButton.setSelection(false);
		this.newButton.setEnabled(false);
		this.copyProjectButton.setEnabled(false);
		this.linkedProjectButton.setEnabled(false);
		this.structureEnableButton.setEnabled(false);
		this.srcOnly.setEnabled(false);
		this.srcOnly.setSelection(existing.srcOnly());
		this.fileListButton.setEnabled(false);
		this.fileListButton.setSelection(existing.fileList());
		if (existing.fileList()) {
			this.fileCreateButton.setEnabled(true);
		}
	}
	
	private void enableProjectLocation(boolean en) {
		project_location.setEnabled(en);
		projectBrowseButton.setEnabled(en);
		projectLocationText.setEnabled(en);
	}
	
	private StructureFile getStructureXmlFile() {
		return (StructureFile) this.structureFile;
	}
	
	private StructureDirectory getStructureLocation() {
		String location = projectLocationText.getText();
		if (location != null && !location.equalsIgnoreCase("")) {
			File ufile = new File(location);
			StructureDirectory dir = new StructureDirectory(ufile.getName());
			dir.setLocation(location);
			if (linkedProjectButton.getSelection()) dir.setLinkType(StructureBase.LINK_ECLIPSE);
			else if (copyProjectButton.getSelection()) dir.setLinkType(StructureBase.LINK_COPY);
			return dir;
		}
		
		return null;
	}
	
	
	
	public ProjectGeneratorOptions getProjectGeneratorOptions() {
		ProjectGeneratorOptions options = new ProjectGeneratorOptions(this.projectNameText.getText());
		
		options.setStructureLocation(this.getStructureLocation());
		if (structureEnableButton.getSelection()) { // Structure XML File
			options.setStructureXmlFile(this.getStructureXmlFile());
		}
		if (fileListButton.getSelection()) { // Source XML File
			options.setAllFiles((StructureFile)this.sourceFile);
		}
		options.setSourceOnly(srcOnly.getSelection());
		options.setNewproject(newButton.getSelection());
		options.setLink(linkedProjectButton.getSelection());
		options.setCopy(copyProjectButton.getSelection());
	
		return options;
	}
	
	public void enableFileList(boolean en) {
		fileNameLocation.setEnabled(en);
		fileCreateButton.setEnabled(en);
	}
	
	public void enableStructureList(boolean en) {
		structureLocation.setEnabled(en);
		structureCreateButton.setEnabled(en);
	}
	
	private void enableNew(boolean en) {
		/*copyProjectButton.setEnabled(!en);
		linkedProjectButton.setEnabled(!en);
		projectLocationText.setEnabled(!en);
		projectBrowseButton.setEnabled(!en);
		
		fileListButton.setEnabled(en);
		srcOnly.setEnabled(en);
		structureCreateButton.setEnabled(en);
		structureEnableButton.setEnabled(en);
		fileCreateButton.setEnabled(en);*/
	}
	
	public class ExistingListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			boolean en = newButton.getSelection();
			enableProjectLocation(!en);
			//linkedProjectButton.setSelection(false);
			//copyProjectButton.setSelection(false);
		}
	}
	
	public class NewListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			boolean en = newButton.getSelection();
			enableProjectLocation(!en);
			//linkedProjectButton.setSelection(false);
			//copyProjectButton.setSelection(false);
		}
	}
	
	public class CopyListener  extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			boolean en = newButton.getSelection();
			enableProjectLocation(!en);
			//newButton.setSelection(false);
			//copyProjectButton.setSelection(copy);
			//linkedProjectButton.setSelection(!copy);
		}
	}
	
	public class LinkListener  extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			boolean en = newButton.getSelection();
			enableProjectLocation(!en);
			//newButton.setSelection(false);
			//copyProjectButton.setSelection(!copy);
			//linkedProjectButton.setSelection(copy);
		}
	}
	
	
	public class FileListListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			boolean en = fileListButton.getSelection();
			enableFileList(en);
		}
	}
	
	public class StructureListListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			boolean en = structureEnableButton.getSelection();
			enableStructureList(en);
			
			
		}
	}
	

	
	public class ProjectBrowseListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			DirectoryDialog dialog = new DirectoryDialog(getShell(),SWT.None);
			dialog.open();
			String path = dialog.getFilterPath();
			if (path != null) {
				projectLocationText.setText(path);
			}
		}
	}
	
	public class CreateFileListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			VerilogFileDialog dialog;
			if (existing != null) {
				
				dialog = new VerilogFileDialog(getShell(), SWT.NONE, existing);
			}
			else {
				dialog = new VerilogFileDialog(getShell(), SWT.NONE);
			}
			dialog.open();
			sourceFile = dialog.getStructureFile();
		}
	}
	
	public class CreateStructListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			StructureFileTreeFullDialog dialog = new StructureFileTreeFullDialog(getShell(),
					SWT.NONE, defaultStructure, projectType);
			dialog.open();
			if (dialog.getStructureFile().getLocation() != null) structureLocation.setText(dialog.getStructureFile().getLocation());

			structureFile = dialog.getStructureFile();
		}
	}
}
