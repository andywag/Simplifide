package com.simplifide.core.refactor.component;

import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;

public class CreateComponentMainComposite extends Composite {

	private Combo moduleCombo;
	private Combo projectCombo;
	
	private CreateComponentUserInputPage userPage;
	
	
	private List<InstanceModule> modules;
	private List<CoreProjectBasic> projects;
	
	private int selectedModuleIndex;
	private int selectedProjectIndex;
	
	private InstanceModule instanceModule;
	private ModInstanceConnectWrap connect;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CreateComponentMainComposite(Composite parent, int style, 
			ModInstanceConnectWrap def, InstanceModule instanceModule,
			CreateComponentUserInputPage userPage) {
		
		super(parent, SWT.None);
		this.userPage = userPage;
		
		
		EclipseBaseProject proj = (EclipseBaseProject) instanceModule.getProjectRef().getObject();
		EclipseSuite suite = (EclipseSuite) proj.getSuiteReference().getObject();
		this.modules = suite.getAllInstanceModules();
		
		
		Label lblExistingPorts = new Label(this, SWT.NONE);
		lblExistingPorts.setBounds(10, 10, 50, 15);
		lblExistingPorts.setText("Project");	
		
		projectCombo = new Combo(this, SWT.NONE);
		projectCombo.setBounds(60, 8, 200, 15);
		projectCombo.addSelectionListener(new ProjectComboListener());
		
		Label lblExistingPorts1 = new Label(this, SWT.NONE);
		lblExistingPorts1.setBounds(10, 40, 50, 15);
		lblExistingPorts1.setText("Module");	
		
		moduleCombo = new Combo(this, SWT.NONE);
		moduleCombo.setBounds(60, 38, 200, 15);
		moduleCombo.addSelectionListener(new ComboListener());
		
	
		this.updateInstanceModule(instanceModule, def);
		
	}
	
	public void updateInstanceModule(InstanceModule instanceModule, ModInstanceConnectWrap wrap) {
		this.instanceModule = instanceModule;
		this.connect = wrap;
		
		EclipseBaseProject proj = (EclipseBaseProject) this.instanceModule.getProjectRef().getObject();
		EclipseSuite suite = (EclipseSuite) proj.getSuiteReference().getObject();
		
		this.projects = suite.getAllRealProject();
		Collections.sort(this.projects);
		for (CoreProjectBasic project : this.projects) {
			projectCombo.add(project.getname());
		}
		projectCombo.setText(proj.getname());
		this.selectedProjectIndex = projectCombo.getSelectionIndex();
		
		this.modules = proj.getAllInstanceModules();
		Collections.sort(this.modules);
		for (InstanceModule module : this.modules) {
			moduleCombo.add(module.getname());
		}
		moduleCombo.setText(instanceModule.getname());
		this.selectedModuleIndex = moduleCombo.getSelectionIndex();

	
	}
	
	
	private void changeProjectModule(CoreProjectBasic proj) {
		this.modules = proj.getAllInstanceModules();
		Collections.sort(this.modules);
		moduleCombo.removeAll();
		for (InstanceModule module : this.modules) {
			moduleCombo.add(module.getname());
		}
		if (modules.size() > 0) {
			moduleCombo.setText(modules.get(0).getname());
			this.selectedModuleIndex = 0;
		}
		
	}
	
	public  class ComboListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			int sel = moduleCombo.getSelectionIndex();
			if (selectedModuleIndex == -1) {
				selectedModuleIndex = sel;
			}
			else if (sel != selectedModuleIndex) {
				InstanceModule instMod = modules.get(sel);
				selectedModuleIndex = sel;
				userPage.changeInstanceModule(instMod);
			}
		}
	}
	public  class ProjectComboListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			int sel = projectCombo.getSelectionIndex();
			if (selectedProjectIndex == -1) {
				selectedProjectIndex = sel;
			}
			else if (sel != selectedModuleIndex) {
				CoreProjectBasic instMod = projects.get(sel);
				selectedProjectIndex = sel;
				changeProjectModule(projects.get(selectedProjectIndex));
			}
		}
	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
