package com.simplifide.base.refactor.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.port.PortConnect;

public class PortConnectWrap extends PortWrap{

	private ModuleObject externVar;
	
	public PortConnectWrap(String type, String name, Integer ioType,
			String description, boolean vhdl) {
		super(type, name, ioType, description, vhdl);
	}

	
	public PortConnectWrap(PortConnect porttop,  boolean vhdl) {
		super(porttop.getLocalVar(),vhdl);
		this.externVar = porttop.getExternVar().getObject();
	}

	public void convertPort(String srcExp, String destExp) {
		if (this.getName() == null) return;
		if (this.getName().equalsIgnoreCase(externVar.getname())) {
			String extern = externVar.getname();
			Pattern pat = Pattern.compile(srcExp);
			Matcher mat = pat.matcher(extern);
			String dif = mat.replaceAll(destExp);
			if (!dif.equalsIgnoreCase(extern)) {
				externVar = new ModuleObjectNew(dif);
			}
		}
	}
	

	public void setExternVar(ModuleObject externVar) {
		this.externVar = externVar;
	}


	public ModuleObject getExternVar() {
		return externVar;
	}
	
	
}
