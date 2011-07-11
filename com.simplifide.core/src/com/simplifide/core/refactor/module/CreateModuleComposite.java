package com.simplifide.core.refactor.module;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.refactor.port.connect.SourceListContentProvider;

public class CreateModuleComposite extends Composite {

	private ListViewer signalListViewer;
	private ListViewer inputListViewer;
	private ListViewer outputListViewer;
	
	private Button addInputButton;
	private Button addOutputButton;
	private Button removeButton;
	
	private Text instanceText;
	
	private InstanceModule instanceModule;
	
	private List<SystemVar> varList;
	private List<SystemVar> inputList;
	private List<SystemVar> outputList;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CreateModuleComposite(Composite parent, int style, 
			InstanceModule instanceModule) {
		
		super(parent, SWT.None);
		this.instanceModule = instanceModule;
		
		Label lblExistingPorts1 = new Label(this, SWT.NONE);
		lblExistingPorts1.setBounds(10, 10, 80, 15);
		lblExistingPorts1.setText("Modport Name");
		
		this.instanceText = new Text(this, SWT.BORDER);
		this.instanceText.setBounds(100, 10, 320, 20);
		
		Label lbl = new Label(this, SWT.NONE);
		lbl.setBounds(30, 50, 200, 15);
		lbl.setText("Signals to Connect to Module");
		
		Label lbli = new Label(this, SWT.NONE);
		lbli.setBounds(300, 50, 60, 15);
		lbli.setText("Inputs");
		
		Label lblo = new Label(this, SWT.NONE);
		lblo.setBounds(300, 270, 60, 15);
		lblo.setText("Outputs");
		
		this.varList = this.instanceModule.getAllVars();
		this.outputList = new ArrayList<SystemVar>();
		this.inputList = new ArrayList<SystemVar>();
		
		this.signalListViewer = new ListViewer(this,SWT.BORDER | SWT.V_SCROLL | SWT.MULTI );
		this.signalListViewer.setContentProvider(new SourceListContentProvider());
		this.signalListViewer.setLabelProvider(new SourceListContentProvider.Label());
		signalListViewer.getList().setBounds(10, 70, 200, 420);
		signalListViewer.setInput(this.varList);
		
		this.inputListViewer = new ListViewer(this,SWT.BORDER | SWT.V_SCROLL | SWT.MULTI );
		this.inputListViewer.setContentProvider(new SourceListContentProvider());
		this.inputListViewer.setLabelProvider(new SourceListContentProvider.Label());
		inputListViewer.getList().setBounds(220, 70, 200, 190);
		
		
		this.outputListViewer = new ListViewer(this,SWT.BORDER | SWT.V_SCROLL | SWT.MULTI );
		this.outputListViewer.setContentProvider(new SourceListContentProvider());
		this.outputListViewer.setLabelProvider(new SourceListContentProvider.Label());
		outputListViewer.getList().setBounds(220, 300, 200, 190);
		
		this.addInputButton = new Button(this,SWT.None);
		this.addInputButton.setText("Add Inputs");
		this.addInputButton.setBounds(10,500,90,20);
		this.addInputButton.addSelectionListener(new InputListener());
		
		this.addOutputButton = new Button(this,SWT.None);
		this.addOutputButton.setText("Add Outputs");
		this.addOutputButton.setBounds(120,500,90,20);
		this.addOutputButton.addSelectionListener(new OutputListener());
		
		this.removeButton = new Button(this,SWT.None);
		this.removeButton.setText("Remove Signals");
		this.removeButton.setBounds(270,500,100,20);
		this.removeButton.addSelectionListener(new RemoveListener());
	}
	
	public ModInstanceWrap getInstanceWrap(boolean vhdl) {
		ModInstanceWrap wrap = new ModInstanceWrap(this.getModuleName(),vhdl);
		for (SystemVar tvar : this.getInputs()) {
			tvar.setOpTypeVar(new OperatingTypeVar.IOVar(OperatingTypeVar.IOVar.INPUT));
			wrap.addPort(tvar, vhdl);
		}
		for (SystemVar tvar : this.getOutputs()) {
			tvar.setOpTypeVar(new OperatingTypeVar.IOVar(OperatingTypeVar.IOVar.OUTPUT));
			wrap.addPort(tvar, vhdl);
		}
		return wrap;
	}
	
	public String getModuleName() {
		return this.instanceText.getText();
	}
	
	public List<SystemVar> getInputs() {
		return this.inputList;
	}
	
	public List<SystemVar> getOutputs() {
		return this.outputList;
	}
	
	public class InputListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			StructuredSelection select = (StructuredSelection) signalListViewer.getSelection();
			List<SystemVar> vars = select.toList();
			inputList.addAll(vars);
			varList.removeAll(vars);
			signalListViewer.setInput(varList);
			inputListViewer.setInput(inputList);
		}
	}
	public class OutputListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			StructuredSelection select = (StructuredSelection) signalListViewer.getSelection();
			List<SystemVar> vars = select.toList();
			outputList.addAll(vars);
			varList.removeAll(vars);
			signalListViewer.setInput(varList);
			outputListViewer.setInput(outputList);
		}
	}
	public class RemoveListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			StructuredSelection select = (StructuredSelection) outputListViewer.getSelection();
			List<SystemVar> vars = select.toList();
			outputList.removeAll(vars);
			varList.addAll(vars);
			select = (StructuredSelection) inputListViewer.getSelection();
			vars = select.toList();
			inputList.removeAll(vars);
			varList.addAll(vars);
			signalListViewer.setInput(varList);
			inputListViewer.setInput(inputList);
			outputListViewer.setInput(outputList);
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	
	
	
}
