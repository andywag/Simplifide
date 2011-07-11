package com.simplifide.core.make;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.verilog.editor.describer.VerilogFile;
import com.simplifide.core.vhdl.describer.VhdlFile;

public abstract class DesignWrapper {

	private DesignFile design;

	public static DesignWrapper createWrapper(DesignFile dfile) {
		if (dfile instanceof VerilogFile) return new Verilog(dfile);
		if (dfile instanceof VhdlFile) return new Vhdl(dfile);
		return null;
	}
	
	public DesignWrapper(DesignFile design) {
		this.design = design;
	}

	public String getName() {
		return design.getname();
	}
	
	public List<DesignWrapper> getRequired() {
		List<DesignWrapper> files = new ArrayList<DesignWrapper>();
		List<DesignFile> designs = design.getCompileInfo().getParentArrayList();
		for (DesignFile design: designs) {
			if (design.getProjectRef() == this.design.getProjectRef()) {
				files.add(DesignWrapper.createWrapper(design));
			}
			
		}
		return files;
	}
	
	
	public List<DesignUnitWrapper> getUnits() {
		SuperModule<ModuleObjectNew> smod = design.getModuleRef().getObject();
		List<DesignUnitWrapper> units = new ArrayList<DesignUnitWrapper>();
		for (ReferenceItem<? extends ModuleObjectNew> ref : smod.getGenericSelfList()) {
			ModuleObjectNew obj = ref.getObject();
			DesignUnitWrapper wrap = DesignUnitWrapper.createWrapper(obj);
			units.add(wrap);
		}
		return units;
	}
	
	public boolean isVerilog() {
		return false;
	}
	public boolean isVhdl()    {
		return false;
	}
	
	public static class Verilog extends DesignWrapper{

		public Verilog(DesignFile design) {
			super(design);
		}
		
		public boolean isVerilog() {return true;}
		
	}
	
	public static class Vhdl extends DesignWrapper {

		public Vhdl(DesignFile design) {
			super(design);
	
		}
		public boolean isVhdl() {
			return true;
		}

	}
	
	
}
