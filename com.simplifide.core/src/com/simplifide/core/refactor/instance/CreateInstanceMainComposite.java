package com.simplifide.core.refactor.instance;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;

public class CreateInstanceMainComposite extends Composite {

	private Combo projectCombo;
	private Combo moduleCombo;
	private Text instanceText;
	private Button componentButton;
	
	private CreateInstanceUserInputPage userPage;
	
	private List<CoreProjectBasic> projects;
	private List<InstanceModule> modules;
	
	private int selectedProjectIndex;
	private int selectedModuleIndex;
	
	private InstanceModule instanceModule;
	private ModInstanceConnectWrap connect;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CreateInstanceMainComposite(Composite parent, int style, 
			ModInstanceConnectWrap def, InstanceModule instanceModule,
			CreateInstanceUserInputPage userPage) {
		
		super(parent, SWT.None);
		this.userPage = userPage;
		
		
		
		
		Label lblExistingPorts1 = new Label(this, SWT.NONE);
		lblExistingPorts1.setBounds(10, 10, 50, 15);
		lblExistingPorts1.setText("Library");	
		
		projectCombo = new Combo(this, SWT.NONE);
		projectCombo.setBounds(100, 8, 240, 15);
		projectCombo.addSelectionListener(new ProjectComboListener());
		
		Label lblExistingPorts = new Label(this, SWT.NONE);
		lblExistingPorts.setBounds(400, 10, 50, 15);
		lblExistingPorts.setText("Module");	
		
		
		moduleCombo = new Combo(this, SWT.NONE);
		moduleCombo.setBounds(480, 8, 200, 15);
		moduleCombo.addSelectionListener(new ComboListener());

		Label lblExistingPorts2 = new Label(this, SWT.NONE);
		lblExistingPorts2.setBounds(10, 50, 80, 15);
		lblExistingPorts2.setText("Instance Name");
		
		this.instanceText = new Text(this, SWT.BORDER);
		this.instanceText.setBounds(100, 50, 240, 20);
		
		this.instanceText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				connect.setName(instanceText.getText());	
			}
		});
			
		this.instanceModule = instanceModule;
		this.loadComboBoxes();
		
	
		this.updateInstanceModule(instanceModule, def);
		
		this.componentButton = new Button(this, SWT.CHECK);
		this.componentButton.setBounds(400, 50, 100, 15);

		this.componentButton.setText("Component");
		this.componentButton.addSelectionListener( new SelectionListener() {
			
			
			public void widgetSelected(SelectionEvent e) {
				getProcessor().setComponent(componentButton.getSelection());
			}
			
			
			public void widgetDefaultSelected(SelectionEvent e) {
				getProcessor().setComponent(componentButton.getSelection());

			}
		});
		
	}
	
	private void loadComboBoxes() {
		EclipseBaseProject proj = (EclipseBaseProject) instanceModule.getProjectRef().getObject();
		EclipseSuite suite = (EclipseSuite) proj.getSuiteReference().getObject();
		
		this.projects = suite.getAllRealProject();
		int index = 0;
		for (CoreProjectBasic module : this.projects) {
			projectCombo.add(module.getname());
			if (proj.getname().equalsIgnoreCase(module.getname())) {
				this.selectedProjectIndex = index;
			}
			index++;
		}
		projectCombo.setText(proj.getname());

		this.updateModuleCombo(proj, this.instanceModule);
		//if (this.instanceModule != null) userPage.changeInstanceModule(this.instanceModule);
		/*
		this.modules = proj.getAllInstanceModules();

		for (InstanceModule module : this.modules) {
			moduleCombo.add(module.getname());
		}
		moduleCombo.setText(instanceModule.getname());
		this.selectedModuleIndex = moduleCombo.getSelectionIndex();
		*/
	}
	
	
	
	private void updateModuleCombo(CoreProjectBasic project, InstanceModule imod) {
		this.moduleCombo.removeAll();
		this.modules = project.getAllInstanceModules();

		for (InstanceModule module : this.modules) {
			moduleCombo.add(module.getname());
		}
		if (imod != null) {
			moduleCombo.setText(imod.getname());
		}
		else if (this.modules.size() > 0){
			moduleCombo.setText(this.modules.get(0).getname());
		}
		this.selectedModuleIndex = moduleCombo.getSelectionIndex();

	}
	
	
	public CreateInstanceRefactorProcessor getProcessor() {
		return userPage.getRefactoring().getProcessor();
	}
	
	public void updateInstanceModule(InstanceModule instanceModule, ModInstanceConnectWrap wrap) {
		this.instanceModule = instanceModule;
		this.connect = wrap;
		
		EclipseBaseProject proj = (EclipseBaseProject) this.instanceModule.getProjectRef().getObject();
		this.modules = proj.getAllInstanceModules();
		

		this.instanceText.setText(this.connect.getName());
		this.redraw();

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
			if (sel != selectedProjectIndex) {
				selectedProjectIndex = sel;
				projectCombo.setText(projects.get(sel).getname());
				updateModuleCombo(projects.get(sel), null);
				
				int sel1 = moduleCombo.getSelectionIndex();
				if (sel1 > 0) {
					userPage.changeInstanceModule(modules.get(sel1));
				}
				else if (modules.size() > 0) {
					userPage.changeInstanceModule(modules.get(0));
				}
				
			}
		}
	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setComponentButton(Button componentButton) {
		this.componentButton = componentButton;
	}

	public Button getComponentButton() {
		return componentButton;
	}
}
