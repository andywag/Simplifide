package com.simplifide.core.refactor.port.remove;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.base.refactor.model.PortWrap;

public class RemovePortTopComposite extends Composite {

	private ModInstanceWrap existing;
	private ModInstanceWrap delta;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RemovePortTopComposite(Composite parent, int style, ModInstanceDefault def, boolean vhdlFile) {
		
		super(parent, style);
		InstanceModule instanceModule = null;
		if (def != null  && def.getInstanceModRef() != null) {
			instanceModule = (InstanceModule) def.getInstanceModRef().getObject();
		}

		{
			Label lblExistingPorts = new Label(this, SWT.NONE);
			lblExistingPorts.setBounds(500, 10, 300, 15);
			lblExistingPorts.setText("Select the Ports for Removal in the Selected Column");
		}
		{
			existing = new ModInstanceWrap(def,vhdlFile);
			Composite composite = new RemovePortTableComposite(this, SWT.NONE,existing,false,instanceModule);
			composite.setBounds(10, 40, 1300, 390);
		}
		
	}
	
	public ModInstanceWrap getWrapper() {
		ModInstanceWrap cop = new ModInstanceWrap(existing.getName(),existing.isVhdlFile());
		for (PortWrap port : existing.getGenerics()) {
			if (!port.isSelected()) {
				cop.addPort(port);
			}
		}
		for (PortWrap port : existing.getPorts()) {
			if (!port.isSelected()) {
				cop.addPort(port);
			}
		}
		cop.orderPorts();
		
		return cop;
	}
	
	public ModInstanceWrap getDelta() {
		ModInstanceWrap cop = new ModInstanceWrap(existing.getName(),existing.isVhdlFile());
		for (PortWrap port : existing.getGenerics()) {
			if (port.isSelected()) {
				cop.addPort(port);
			}
		}
		for (PortWrap port : existing.getPorts()) {
			if (port.isSelected()) {
				cop.addPort(port);
			}
		}
		cop.orderPorts();
		
		return cop;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
