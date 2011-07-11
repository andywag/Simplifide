package com.simplifide.core.refactor.function.change;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.base.refactor.model.PortWrap;

public class AddPortTopComposite extends Composite {

	private ModInstanceWrap existing;
	private ModInstanceWrap newWrap;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AddPortTopComposite(Composite parent, int style, ModInstanceDefault def, InstanceModule instanceModule, boolean vhdlFile) {
		super(parent, style);
		
		

		{
			Label lblExistingPorts = new Label(this, SWT.NONE);
			lblExistingPorts.setBounds(640, 10, 92, 15);
			lblExistingPorts.setText("Existing Ports");
		}
		{
			existing = new ModInstanceWrap(def,vhdlFile);
			Composite composite = new AddPortTableComposite(this, SWT.NONE,existing,false,instanceModule);
			composite.setBounds(10, 40, 1300, 390);
		}
		{
			Label lblNewPorts = new Label(this, SWT.NONE);
			lblNewPorts.setBounds(640, 450, 92, 15);
			lblNewPorts.setText("New Ports");
		}
		{
			newWrap = ModInstanceWrap.singlePortWrap(vhdlFile);
			Composite composite = new AddPortTableComposite(this, SWT.BOTTOM,newWrap,true,instanceModule);
			composite.setBounds(10, 480, 1300, 200);
		}
	}
	
	public ModInstanceWrap getTotalInstanceWrap() {
		ModInstanceWrap cop = existing.copy();
		for (PortWrap port : newWrap.getPorts()) {
			if (!port.getName().equalsIgnoreCase("")) {
				cop.addPort(port);
			}
		}
		for (PortWrap port : newWrap.getGenerics()) {
			if (!port.getName().equalsIgnoreCase("")) {
				cop.addPort(port);
			}
		}
		cop.orderPorts();
		return cop;
	}
	
	public ModInstanceWrap getDeltaInstanceWrap() {
		return newWrap;
	}
	
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
