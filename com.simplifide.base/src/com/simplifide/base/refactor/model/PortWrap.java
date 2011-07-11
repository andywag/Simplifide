package com.simplifide.base.refactor.model;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.var.realvars.SystemVar;

/** Convenience Class to contain the ports for this table */
public  class PortWrap implements Comparable<PortWrap> {
	
	private boolean vhdlPort;
	private String  type;
	private String  name;
	private Integer ioType;
	private String  description;
	private String  initial="";
	private boolean selected = false;
	private HdlDoc doc;
	
	public static Integer GENERIC = new Integer(0);
	public static Integer INPUT = new Integer(1);
	public static Integer OUTPUT = new Integer(2);
	public static Integer INOUT = new Integer(3);
	public static Integer BUFFER = new Integer(4);
	public static Integer SIGNAL = new Integer(5);
	public static Integer VARIABLE = new Integer(6);
	
	
	
	public PortWrap(String type, String name, Integer ioType, String description, boolean vhdl) {
		this.setType(type);
		this.setName(name);
		this.setIoType(ioType);
		this.setDescription(description);	
	}
	
	public PortWrap(SystemVar porttop, boolean vhdl) {
		Integer type = INPUT;
		if (porttop == null) {
			type = GENERIC;
			return;
		}
		if (porttop.isGeneric()) type = GENERIC;
		else if (porttop.getVariableType() == SystemVar.INPUT) type = INPUT;
		else if (porttop.getVariableType() == SystemVar.OUTPUT) type = OUTPUT;
		else if (porttop.getVariableType() == SystemVar.INOUT) type = INOUT;
		else if (porttop.getVariableType() == SystemVar.SIGNAL) type = SIGNAL;
		else if (porttop.getVariableType() == SystemVar.VARIABLE) type = VARIABLE;
		else if (porttop.getVariableType() == SystemVar.BUFFER) type = BUFFER;
		
		String typeString = "";
		if (porttop.getType() != null) typeString = porttop.getType().getDisplayName();
		if (!vhdl) typeString = porttop.getType().getVerilogDisplayName();

		this.type = typeString;
		this.name = porttop.getname();
		this.ioType = type;
		this.setVhdlPort(vhdl);			
		if (porttop.getDefaultValue() != null) this.initial = porttop.getDefaultString();
		this.doc = porttop.getDoc();
		
	}
	
	public void convertPort(String src, String dest) {}
	
	public PortConnectWrap getDefaultConnectWrap() {
		PortConnectWrap wrap = new PortConnectWrap(this.type,this.name,this.ioType,this.description,this.isVhdlPort());
		wrap.setExternVar(new ModuleObject(this.name));
		return wrap;
	}
	
	public PortWrap copy() {
		return new PortWrap(this.type,this.name,this.ioType,this.description,this.isVhdlPort());
	}
	
	public int compareTo(PortWrap o) {
		if (o.getIoType() == null) return -1;
		if (this.getIoType() == null) return 1;
		int io = this.getIoType().compareTo(o.getIoType());
		if (io != 0) return io;
		return this.getName().compareTo(o.getName());
	}
	
	public String getVhdlDec() {
		if (ioType.intValue() == VARIABLE.intValue()) return "variable";
		else return "signal";
	}
	
	public String getVerilogDec() {
		if (ioType.intValue() == VARIABLE.intValue()) return "wire";
		else return "wire";
	}
	
	public String getVhdlIOString() {
		if (ioType.intValue() == INPUT.intValue()) return "in";
		else if (ioType.intValue() == OUTPUT.intValue()) return "out";
		else if (ioType.intValue() == INOUT.intValue()) return "inout";
		else if (ioType.intValue() == BUFFER.intValue()) return "buffer";
		return "";
	}
	
	public String getVerilogIOString() {
		String ostr = "";
		if (ioType.intValue() == INPUT.intValue()) ostr =  "input";
		else if (ioType.intValue() == OUTPUT.intValue()) ostr =  "output";
		else if (ioType.intValue() == INOUT.intValue()) ostr =  "inout";
		
		
		return ostr;
	}
	
	public String getVerilogType() {
		return this.getType().replace("bit", "");
	}
	
	public boolean hasInitialValue() {
		if (this.initial == null) return false;
		if (this.initial.equalsIgnoreCase("")) return false;
		return true;
	}


	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setIoType(Integer ioType) {
		this.ioType = ioType;
	}

	public Integer getIoType() {
		return ioType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getInitial() {
		return initial;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setVhdlPort(boolean vhdlPort) {
		this.vhdlPort = vhdlPort;
	}

	public boolean isVhdlPort() {
		return vhdlPort;
	}

	public void setDoc(HdlDoc doc) {
		this.doc = doc;
	}

	public HdlDoc getDoc() {
		return doc;
	}

	

	
	
}

