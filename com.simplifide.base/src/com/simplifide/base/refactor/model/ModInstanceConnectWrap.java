package com.simplifide.base.refactor.model;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.instance.ModInstanceConnect;



public class ModInstanceConnectWrap extends ModInstanceWrap{

	private String connectName;
	
	public ModInstanceConnectWrap(String name, boolean vhdlFile) {
		super(name, vhdlFile);
	}
	
	public ModInstanceConnectWrap(ModInstanceConnect connect, boolean vhdlFile) {
		super(connect,vhdlFile);
		this.setConnectName(connect.getConnectionName());
	}
	
	public void convertPorts(String src,String dest) {
		
		for (PortWrap port : this.getPorts()) {
			port.convertPort(src,dest);
		}
		for (PortWrap port : this.getGenerics()) {
			port.convertPort(src,dest);
		}
	}
	
	/** Remove ports from this instance. This is used for removing ports 
	 *  during the remove port refactoring. 
	 * @param delta
	 */
	public void removePorts(ModInstanceWrap delta) {
		for (PortWrap port : delta.getGenerics()) {
			for (PortWrap uport : this.getGenerics()) {
				if (port.getName().equalsIgnoreCase(uport.getName())) {
					this.getGenerics().remove(uport);
					break;
				}
			}
		}
		for (PortWrap port : delta.getPorts()) {
			for (PortWrap uport : this.getPorts()) {
				if (port.getName().equalsIgnoreCase(uport.getName())) {
					this.getPorts().remove(uport);
					break;
				}
			}
		}
	}
	/** Remove ports from this instance. This is used for removing ports 
	 *  during the remove port refactoring
	 * @param delta
	 */
	public void addPorts(ModInstanceWrap delta) {
		for (PortWrap port : delta.getGenerics()) {
			if (!port.getName().equalsIgnoreCase("")) {
				PortConnectWrap con = new PortConnectWrap(port.getType(),port.getName(),port.getIoType(),port.getDescription(),port.isVhdlPort());
				con.setInitial(port.getInitial());
				con.setExternVar(new ModuleObject(port.getName()));
				this.addPort(con);
			}
			
		}
		for (PortWrap port : delta.getPorts()) {
			if (!port.getName().equalsIgnoreCase("")) {
				PortConnectWrap con = new PortConnectWrap(port.getType(),port.getName(),port.getIoType(),port.getDescription(),port.isVhdlPort());
				con.setInitial(port.getInitial());
				con.setExternVar(new ModuleObject(port.getName()));
				this.addPort(con);
			}
		}
		this.orderPorts();
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	public String getConnectName() {
		return connectName;
	}

	
	
	
	
	
}
