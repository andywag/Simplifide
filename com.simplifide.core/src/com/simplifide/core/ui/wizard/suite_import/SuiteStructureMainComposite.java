package com.simplifide.core.ui.wizard.suite_import;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;
import com.simplifide.core.ui.filebrowser.StructureFileTreeComposite;


public class SuiteStructureMainComposite extends org.eclipse.swt.widgets.Composite {

	
	private TabFolder tabFolder;
	private SuiteStructureBottomComposite suiteStructureBottomComposite1;
	//private Label lable;
	//private Button linkedStructure;
	//private Button existingStructure;
	//private Button browseButton;
	//private Text structureLocation;

	private TabItem suiteTab;
	private TabItem projectTab;
	private TabItem libraryTab;
	private TabItem subprojectTab;

	private StructureFileTreeComposite suiteStructComp;
	private StructureFileTreeComposite projectStructComp;
	private StructureFileTreeComposite libraryStructComp;
	private StructureFileTreeComposite subprojectStructComp;
	
	
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

	private EclipseSuite suite;
	
	public SuiteStructureMainComposite(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public SuiteStructureMainComposite(Composite parent, int style, EclipseSuite suite) {
		super(parent, style);
		initGUI();
		this.suite = suite;
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(600, 400);
			{
				FormData suiteStructureBottomComposite1LData = new FormData();
				suiteStructureBottomComposite1LData.left =  new FormAttachment(0, 1000, 12);
				suiteStructureBottomComposite1LData.top =  new FormAttachment(0, 1000, 276);
				suiteStructureBottomComposite1LData.width = 576;
				suiteStructureBottomComposite1LData.height = 72;
				suiteStructureBottomComposite1 = new SuiteStructureBottomComposite(this, SWT.NONE);
				suiteStructureBottomComposite1.setLayoutData(suiteStructureBottomComposite1LData);
			}
			{
				tabFolder = new TabFolder(this, SWT.NONE);
				tabFolder.setSize(600,250);
				
				

				{
					suiteTab = new TabItem(tabFolder, SWT.NONE);
					suiteTab.setText("Suite");
					Composite tcomp = new Composite(tabFolder,SWT.None);
					this.suiteStructComp      = new StructureFileTreeComposite(tcomp,SWT.NONE);
					suiteTab.setControl(tcomp);
					StructureDirectory dir = RootStructureLoader.loadSuiteStructure(this.suite).getStructureDirectory();
					
					this.suiteStructComp.setInputStructure(dir);
					
				}
				{
					projectTab = new TabItem(tabFolder, SWT.NONE);
					projectTab.setText("Project");
					Composite tcomp = new Composite(tabFolder,SWT.None);
					this.projectStructComp      = new StructureFileTreeComposite(tcomp,SWT.NONE);
					projectTab.setControl(tcomp);
					StructureDirectory dir = RootStructureLoader.loadProjectStructure(null).getStructureDirectory();
					
					this.projectStructComp.setInputStructure(dir);
				}
				{
					libraryTab = new TabItem(tabFolder, SWT.NONE);
					libraryTab.setText("Library");
					Composite tcomp = new Composite(tabFolder,SWT.None);
					this.libraryStructComp      = new StructureFileTreeComposite(tcomp,SWT.NONE);
					libraryTab.setControl(tcomp);
					StructureDirectory dir = RootStructureLoader.loadLibraryStructure(null).getStructureDirectory();
					
					this.libraryStructComp.setInputStructure(dir);
				}
				{
					subprojectTab = new TabItem(tabFolder, SWT.NONE);
					subprojectTab.setText("SubProject");
					Composite tcomp = new Composite(tabFolder,SWT.None);
					this.subprojectStructComp      = new StructureFileTreeComposite(tcomp,SWT.NONE);
					subprojectTab.setControl(tcomp);
					StructureDirectory dir = RootStructureLoader.loadSubProjectStructure(null).getStructureDirectory();
					this.subprojectStructComp.setInputStructure(dir);
				}
				FormData tabFolderLData = new FormData();
				tabFolderLData.left =  new FormAttachment(0, 1000, 0);
				tabFolderLData.top =  new FormAttachment(0, 1000, 0);
				tabFolderLData.width = 589;
				tabFolderLData.height = 374;
				tabFolder.setLayoutData(tabFolderLData);
				tabFolder.setSelection(0);
				
			}
			this.layout();
			this.suiteStructureBottomComposite1.getExistingStructure().addSelectionListener(new ExistingListener());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private StructureBase createStructureXml() {
		return null;
	}
	
	public StructureFile getStructureXml() {
		boolean existing = this.suiteStructureBottomComposite1.isExisting();
		if (existing) {
			return this.suiteStructureBottomComposite1.getStructureFile();
		}
		
		StructureDirectory suite      = suiteStructComp.getStructureBase();
		StructureDirectory project    = projectStructComp.getStructureBase();
		StructureDirectory library    = libraryStructComp.getStructureBase();
		StructureDirectory subproject = subprojectStructComp.getStructureBase();
		
		String out= WorkspaceDirectoryStructure.getFileContents(suite, project, library, subproject);
		StructureFile struct = new StructureFile("Structure.xml");
		struct.setContents(out);
		struct.setLinkType(StructureFile.LINK_NEW);
		return struct;
	}
	
	
	public void handleEnableStructure(boolean selected) {
		
		suiteStructComp.enableLink(!selected);
		projectStructComp.enableLink(!selected);
		libraryStructComp.enableLink(!selected);
		subprojectStructComp.enableLink(!selected);
	}
	
	public void setDirectoryStructure(WorkspaceDirectoryStructure dir) {
		suiteStructComp.setInputStructure(dir.getSuiteStructure().getStructureDirectory());
		projectStructComp.setInputStructure(dir.getProjectStructure().getStructureDirectory());
		libraryStructComp.setInputStructure(dir.getLibraryStructure().getStructureDirectory());
		subprojectStructComp.setInputStructure(dir.getSubProjectStructure().getStructureDirectory());
	}
	
	
	
	public class ExistingListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			boolean selected = suiteStructureBottomComposite1.getExistingStructure().getSelection();
			handleEnableStructure(selected);
		}
	}

}
