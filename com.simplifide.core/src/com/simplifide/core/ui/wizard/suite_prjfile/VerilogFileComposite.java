package com.simplifide.core.ui.wizard.suite_prjfile;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.simplifide.core.project.source.ProjectSourceList;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.suitecontents.ProjectContentLoader;

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
public class VerilogFileComposite extends Composite {
	private List fileList;
	private Button linkButton;
	private Button copyButton;
	private Button addDirectoryButton;
	private Button addFileButton;
	private Button fileListCheck;
	private Button addFileListButton;
	private Button browseButton;
	private Text fileListLocation;
	private Button removeFileButton;


	private java.util.List<File> initialFiles;
	
	public VerilogFileComposite(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public VerilogFileComposite(Composite parent, int style, java.util.List<File> files) {
		super(parent, style);
		this.initialFiles = files;
		initGUI();
	}
	

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(492, 411);
			
			{
				copyButton = new Button(this, SWT.CHECK | SWT.LEFT);
				FormData copyButtonLData = new FormData();
				copyButtonLData.left =  new FormAttachment(0, 1000, 127);
				copyButtonLData.top =  new FormAttachment(0, 1000, 53);
				copyButtonLData.width = 63;
				copyButtonLData.height = 16;
				copyButton.setLayoutData(copyButtonLData);
				copyButton.setText("Copy");
			}
			{
				linkButton = new Button(this, SWT.CHECK | SWT.LEFT);
				FormData linkButtonLData = new FormData();
				linkButtonLData.left =  new FormAttachment(0, 1000, 39);
				linkButtonLData.top =  new FormAttachment(0, 1000, 53);
				linkButtonLData.width = 63;
				linkButtonLData.height = 16;
				linkButton.setLayoutData(linkButtonLData);
				linkButton.setText("Link");
			}
			{
				fileListLocation = new Text(this, SWT.BORDER);
				FormData fileListLocationLData = new FormData();
				fileListLocationLData.left =  new FormAttachment(0, 1000, 127);
				fileListLocationLData.top =  new FormAttachment(0, 1000, 25);
				fileListLocationLData.width = 198;
				fileListLocationLData.height = 14;
				fileListLocation.setLayoutData(fileListLocationLData);
			}
			{
				fileListCheck = new Button(this, SWT.CHECK | SWT.LEFT );
				FormData fileListLData = new FormData();
				fileListLData.left =  new FormAttachment(0, 1000, 39);
				fileListLData.top =  new FormAttachment(0, 1000, 25);
				fileListLData.width = 82;
				fileListLData.height = 16;
				fileListCheck.setLayoutData(fileListLData);
				fileListCheck.setText("Use File List");
				fileListCheck.addSelectionListener(new CheckListener());
				

			}
			{
				removeFileButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData removeButtonLData = new FormData();
				removeButtonLData.left =  new FormAttachment(0, 1000, 360);
				removeButtonLData.top =  new FormAttachment(0, 1000, 195);
				removeButtonLData.width = 113;
				removeButtonLData.height = 27;
				removeFileButton.setLayoutData(removeButtonLData);
				removeFileButton.setText("Remove Files");
				removeFileButton.addSelectionListener(new RemoveFileListener());
			}
			{
				addFileButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData addButtonLData = new FormData();
				addButtonLData.left =  new FormAttachment(0, 1000, 360);
				addButtonLData.top =  new FormAttachment(0, 1000, 82);
				addButtonLData.width = 113;
				addButtonLData.height = 25;
				addFileButton.setLayoutData(addButtonLData);
				addFileButton.setText("Add Files");
				addFileButton.addSelectionListener(new AddFileListener());
			}
			{
				FormData libraryListLData = new FormData();
				libraryListLData.left =  new FormAttachment(0, 1000, 39);
				libraryListLData.top =  new FormAttachment(0, 1000, 82);
				libraryListLData.width = 291;
				libraryListLData.height = 293;
				fileList = new List(this,SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
				fileList.setLayoutData(libraryListLData);
			}
			{
				browseButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 360);
				button1LData.top =  new FormAttachment(0, 1000, 20);
				button1LData.width = 113;
				button1LData.height = 25;
				browseButton.setLayoutData(button1LData);
				browseButton.setText("Browse");
				browseButton.addSelectionListener(new BrowseListener());
			}
			{
				addFileListButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button2LData = new FormData();
				button2LData.left =  new FormAttachment(0, 1000, 360);
				button2LData.top =  new FormAttachment(0, 1000, 119);
				button2LData.width = 113;
				button2LData.height = 25;
				addFileListButton.setLayoutData(button2LData);
				addFileListButton.setText("Add File List");
				addFileListButton.addSelectionListener(new AddFileListListener());
			}
			{
				addDirectoryButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button1LData1 = new FormData();
				button1LData1.left =  new FormAttachment(0, 1000, 360);
				button1LData1.top =  new FormAttachment(0, 1000, 156);
				button1LData1.width = 113;
				button1LData1.height = 25;
				addDirectoryButton.setLayoutData(button1LData1);
				addDirectoryButton.setText("Add Directory");
				addDirectoryButton.addSelectionListener(new AddDirectoryListener());
			}
			this.initOnProject();
			this.layout();
			this.toggleListEnable(fileListCheck.getSelection());
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public void initOnProject() {
		for (java.io.File init : initialFiles) {
			this.addFile(init);
		}
	}
	
	
	public String[] getFiles() {
		return fileList.getItems();
	}
	
	public void toggleListEnable(boolean enable) {
		addFileButton.setEnabled(!enable);
		addFileListButton.setEnabled(!enable);
		addDirectoryButton.setEnabled(!enable);
		removeFileButton.setEnabled(!enable);
		
		linkButton.setEnabled(enable);
		copyButton.setEnabled(enable);
		browseButton.setEnabled(enable);
		fileListLocation.setEnabled(enable);
	}
	
	private ArrayList<String> getListFromFile() {
		FileDialog dialog = new FileDialog(getShell(),SWT.MULTI);
		dialog.open();
		String fname1 = dialog.getFileName();
		String fpath = dialog.getFilterPath();
		File fname = new File(fpath,fname1);
		return convertListFromFile(fname.getAbsolutePath());
	}
	
	private ArrayList<String> convertListFromFile(String fname) {
		File ufile = new File(fname);
		ArrayList<String> files = ProjectContentLoader.getFileContents(ufile);
		return files;
	}
	
	public StructureBase getStructureFile() {
		if (fileListCheck.getSelection()) {
			String location = fileListLocation.getText();
			File locationFile = new File(location);
			StructureFile struct = new StructureFile(locationFile.getName());
			struct.setLocation(location);
			if (linkButton.getSelection()) struct.setLinkType(StructureFile.LINK_ECLIPSE);
			else struct.setLinkType(StructureFile.LINK_COPY);
			return struct;
		}
		else {
			String[] files = this.getFiles();
			String contents = ProjectContentLoader.convertListToXml(files);
			StructureFile struct = new StructureFile(ProjectSourceList.SOURCE_LOCATION);
			struct.setLinkType(StructureFile.LINK_NEW);
			struct.setContents(contents);
			return struct; 
		}
	}
	
	private boolean validPath(String path) {
		String[] str = path.split("\\.");
		String ext = str[str.length-1];
		if (str.length == 0) return false;
		if (ext.equalsIgnoreCase("VHD") ||
			ext.equalsIgnoreCase("VHDL") ||
			ext.equalsIgnoreCase("V") ||
			ext.equalsIgnoreCase("SV") ||
			ext.equalsIgnoreCase("SVH") ||
			ext.equalsIgnoreCase("VT")) {
			return true;
		}
		
		return false;
	}
	
	private void addFile(File ufile) {
		if (ufile.getName().equals(".svn") || 
			ufile.getName().equals(".cvs") || 
			ufile.getName().equals("CVS")) return;
		if (ufile.isDirectory()) {
			for (File file : ufile.listFiles()) {
				addFile(file);
			}
		}
		else {
			if (validPath(ufile.getAbsolutePath())) {
				fileList.add(ufile.getAbsolutePath());
			}
		}
	}
	
	
	public class CheckListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			toggleListEnable(fileListCheck.getSelection());	
		}
	}
	
	public class BrowseListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			FileDialog dialog = new FileDialog(getShell(),SWT.MULTI);
			dialog.open();
			String fname1 = dialog.getFileName();
			String fpath = dialog.getFilterPath();
			File fname = new File(fpath,fname1);
			ArrayList<String> files = convertListFromFile(fname.getAbsolutePath());
			for (String file : files) {
				fileList.add(file);
			}
			fileListLocation.setText(fname.getAbsolutePath());
		}
	}
	
	public class AddFileListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			FileDialog dialog = new FileDialog(getShell(),SWT.MULTI);
			dialog.open();
			String paths = dialog.getFilterPath();
			String[] files = dialog.getFileNames();
			
			for (String file : files) {
				File ufile = new File(paths,file);
				fileList.add(ufile.getAbsolutePath());
			}
			
		}
	}
	
	public class AddDirectoryListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			DirectoryDialog dialog = new DirectoryDialog(getShell(),SWT.MULTI);
			dialog.open();
			String path = dialog.getFilterPath();
			
			File ufile = new File(path);
			addFile(ufile);
			
			
		}
	}
	
	public class RemoveFileListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			int[] selected = fileList.getSelectionIndices();
			fileList.remove(selected);
		}
	}
	
	public class AddFileListListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			ArrayList<String> files = getListFromFile();
			for (String file : files) {
				fileList.add(file);
			}
		}
	}
	

}
