package com.simplifide.core.ui.wizard.suite_classic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public abstract class SvExampleMainPage extends WizardNewProjectCreationPage{

	Combo exampleCombo;
	private String selectedProject;
	
	public SvExampleMainPage(String pageName) {
		super(pageName);
	}
	
	public abstract String[] getItems();
	
	public void createControl(Composite parent) {
        super.createControl(parent);
        Composite control = (Composite) this.getControl();
		Composite comp = new Composite(control,SWT.NULL);
		comp.setLayout(new GridLayout());
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lab = new Label(comp,SWT.NONE);
		lab.setText("Example Name");
		
        exampleCombo = new Combo(comp,SWT.READ_ONLY);
        exampleCombo.setItems(this.getItems());
        exampleCombo.setText(this.getItems()[0]);
        this.setSelectedProject(this.getItems()[0]);
      
        exampleCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
              int sel = exampleCombo.getSelectionIndex();
              setSelectedProject(getItems()[sel]);
            }
          });

          
        
    }
	
	
	
	public void setSelectedProject(String selectedProject) {
		this.selectedProject = selectedProject;
	}

	public String getSelectedProject() {
		return selectedProject;
	}



	public static class Ovm extends SvExampleMainPage {

		public Ovm(String pageName) {
			super(pageName);
		}
		
		public String[] getItems() {
			return new String[] {"basic_examples",
								 "callbacks",
								 "configuration",
								 "factory",
								 "hello_world",
								 "objections",
								 "phases",
								 "sequences",
								 "tlm",
								 "trivial",
								 "xbus"};
			
			}
		}
	
	public static class Uvm extends SvExampleMainPage {

		public Uvm(String pageName) {
			super(pageName);
		}
		
		public String[] getItems() {
			return new String[] {"basic_examples",
								 "callbacks",
								 "configuration",
								 "factory",
								 "hello_world",
								 "objections",
								 "phases",
								 "sequences",
								 "tlm",
								 "trivial",
								 "xbus"};
			
			}
		}
	
	public static class Vmm extends SvExampleMainPage {

		public Vmm(String pageName) {
			super(pageName);
		}
		
		public String[] getItems() {
			return new String[] {"HAL",
								 "perf",
								 "RAL",
								 "sb",
								 "std_lib",
								 "sub_env"};
			
			}
		}
		
}
