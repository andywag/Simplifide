package com.simplifide.core.ui.wizard.suite_import.project;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.library.har.EclipseHarProject;


public class ProjectListComposite extends org.eclipse.swt.widgets.Composite {
	private List projectList;
	private Button addProjectButton;
	private List libraryList;
	private Button addLibraryButton;
	private Button editButton;
	private Button removeButton;

	private ProjectListCompositeData data = new ProjectListCompositeData();
	private EclipseSuite suite;
	
	public ProjectListComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public ProjectListComposite(org.eclipse.swt.widgets.Composite parent, int style, EclipseSuite suite) {
		super(parent, style);
		this.suite = suite;
		initGUI();
		
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(492, 411);
			{
				editButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData editButtonLData = new FormData();
				editButtonLData.left =  new FormAttachment(0, 1000, 360);
				editButtonLData.top =  new FormAttachment(0, 1000, 106);
				editButtonLData.width = 113;
				editButtonLData.height = 25;
				editButton.setLayoutData(editButtonLData);
				editButton.setText("Edit Project");
				if (suite != null) {
					editButton.setEnabled(true);
					editButton.addSelectionListener(new EditProjectListener());
				}
				
			}
			{
				removeButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData removeButtonLData = new FormData();
				removeButtonLData.left =  new FormAttachment(0, 1000, 360);
				removeButtonLData.top =  new FormAttachment(0, 1000, 143);
				removeButtonLData.width = 113;
				removeButtonLData.height = 27;
				removeButton.setLayoutData(removeButtonLData);
				removeButton.setText("Remove");
				removeButton.addSelectionListener(new RemoveLibraryListener());
			}
			{
				addProjectButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData addButtonLData = new FormData();
				addButtonLData.left =  new FormAttachment(0, 1000, 360);
				addButtonLData.top =  new FormAttachment(0, 1000, 32);
				addButtonLData.width = 113;
				addButtonLData.height = 25;
				addProjectButton.setLayoutData(addButtonLData);
				addProjectButton.setText("Add Project");
				addProjectButton.addSelectionListener(new AddProjectListener());
			}
			{
				FormData libraryListLData = new FormData();
				libraryListLData.left =  new FormAttachment(0, 1000, 39);
				libraryListLData.top =  new FormAttachment(0, 1000, 22);
				libraryListLData.width = 291;
				libraryListLData.height = 162;
				projectList = new List(this, SWT.BORDER);
				projectList.setLayoutData(libraryListLData);
			}
			{
				FormData list1LData = new FormData();
				list1LData.left =  new FormAttachment(0, 1000, 39);
				list1LData.top =  new FormAttachment(0, 1000, 206);
				list1LData.width = 291;
				list1LData.height = 162;
				libraryList = new List(this, SWT.BORDER);
				libraryList.setLayoutData(list1LData);
			}
			{
				addLibraryButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 360);
				button1LData.top =  new FormAttachment(0, 1000, 69);
				button1LData.width = 113;
				button1LData.height = 25;
				addLibraryButton.setLayoutData(button1LData);
				addLibraryButton.setText("Add Library");
				addLibraryButton.addSelectionListener(new AddLibraryListener());
			}
			this.initBaseOnSuite();
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initBaseOnSuite() {
		if (suite == null) return;
		java.util.List<EclipseBaseProject> projects = suite.getProjectList();
		for (EclipseBaseProject project : projects) {
			ProjectGeneratorOptions.Existing ex = new ProjectGeneratorOptions.Existing(project);
			data.addProject(ex);
			projectList.add(ex.getName());
		}
		java.util.List<CoreProjectBasic> libraries = suite.getLibraryList();
		for (CoreProjectBasic project1 : libraries) {
			EclipseBaseProject project = (EclipseBaseProject) project1;
			if (!(project instanceof EclipseHarProject)) {
				ProjectGeneratorOptions.Existing ex = new ProjectGeneratorOptions.Existing(project);
				data.addLibrary(ex);
				libraryList.add(ex.getName());
			}
			
		}
	}
	
	public class EditProjectListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			String[] projects = projectList.getSelection();
			if (projects.length <= 0) return;
			ProjectGeneratorOptions op = data.getProject(projects[0]);
			ProjectPropertyDialog dialog = new ProjectPropertyDialog(getShell(),SWT.MULTI,"Project",op);
			dialog.open();
			
				
		}
	}
	
	public class AddProjectListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			ProjectPropertyDialog dialog = new ProjectPropertyDialog(getShell(),SWT.MULTI,"Project");
			dialog.open();
			ProjectGeneratorOptions opt = dialog.getProjectGeneratorOptions();
			data.addProject(opt);
			if (opt != null) projectList.add(opt.getName()); // If for cancel
				
		}
	}
	
	public class AddLibraryListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			ProjectPropertyDialog dialog = new ProjectPropertyDialog(getShell(),SWT.MULTI,"Library");
			dialog.open();
			ProjectGeneratorOptions opt = dialog.getProjectGeneratorOptions();
			data.addLibrary(opt);
			libraryList.add(opt.getName());
		}
	}
	
	public class RemoveLibraryListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			String[] projects = projectList.getSelection();
			for (String project : projects) {
				data.remove(project);
			}
			projects = libraryList.getSelection();
			for (String project : projects) {
				data.remove(project);
			}
			projectList.remove(projectList.getSelectionIndices());
			libraryList.remove(libraryList.getSelectionIndices());

		}
	}
	
	public ArrayList<ProjectGeneratorOptions> getProjectOptions() {
		return data.getProjects();
	}
	public ArrayList<ProjectGeneratorOptions> getLibraryOptions() {
		return data.getLibraries();
	}
	
	
	
	public String[] getLibraries() {
		return projectList.getItems();
	}
	
}
