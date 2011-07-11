package com.simplifide.core.refactor.changes;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.instance.Component;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.base.refactor.model.PortWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.search.SourceMatch;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class PortChangeMethods {

	public static ArrayList<SourceMatch> EMPTYLIST = new ArrayList<SourceMatch>();
	private static String BASE_VHDL    = "refactor/vhdl/";
	private static String BASE_VERILOG = "refactor/verilog/"; 
	
	 /** Convenience Method fo creating a list with a single source match */
    private static List<SourceMatch> singleMatch(String temp, ReferenceLocation loc) {
    	ArrayList<SourceMatch> smatch = new ArrayList<SourceMatch>();
    	ReferenceUsage use = new ReferenceUsage(null,temp,loc);
		smatch.add(new SourceMatch(use));
    	return smatch;
    }
	
	private static List<SourceMatch> genericTemplateChange(ReferenceLocation loc,
			Object object,
			String base,
			boolean vhdl) {
		String cfile = BASE_VERILOG + base;
    	if (vhdl) cfile = BASE_VHDL + base;
    	
    	if (loc != null) {
    		String temp = TemplateGenerator.generateTemplate(cfile, object);
    		temp = temp.replace("bit", "");
    		return singleMatch(temp, loc);
    	}
    	return new ArrayList<SourceMatch>();
	}
	
	/** Create a variable declaration */
	public static List<SourceMatch> createVariableDeclaration(ReferenceLocation loc,
			PortWrap var,
			boolean vhdl) {
		return genericTemplateChange(loc, var, "signal", vhdl);	
	}
	
	/** Create a variable declaration from a SystemVar */
	public static List<SourceMatch> createVariableDeclaration(ReferenceLocation loc,SystemVar var,boolean vhdl) {
		PortWrap wrap = new PortWrap(var,vhdl);
    	return createVariableDeclaration(loc, wrap, vhdl);
	}
	
	
	
    /** Creates a list (singleton) of changes for updating a component */
    public static List<SourceMatch> createComponentChange(ReferenceLocation loc,
    		ModInstanceWrap wrap,
    		boolean vhdl) {
    	return genericTemplateChange(loc, wrap, "component", vhdl);
    }
    
	/** Creates a list (singleton) of changes for updating a entity */
    public static  List<SourceMatch> createEntityChange(ReferenceLocation loc,
    		ModInstanceWrap wrap,
    		boolean vhdl) {
    	return genericTemplateChange(loc, wrap, "entity", vhdl);
    }
    /** Deletes a variable declaration */
    public static List<SourceMatch> deleteVariableDeclaration(SystemVar tvar) {
    	if (tvar.getDeclarationLocation() != null) {
        	return singleMatch("", tvar.getDeclarationLocation());
    	}
    	return PortChangeMethods.EMPTYLIST;
    }
    
    /** Creates a list (singleton) of changes instantiations with changed connections */
    public static List<SourceMatch> createConnectionChange(ReferenceLocation location,
    		ModInstanceConnectWrap wrap,
    		boolean vhdl) {
    	String src = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_EXPR);
    	String dest = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_DEST);
    	wrap.convertPorts(src, dest);
    	return genericTemplateChange(location, wrap, "instance", vhdl);
    }
    
    /** Creates a list (singleton) of changes for deleting input/output declarations and parameters. 
     *  This method is used for converting verilog modules to ansi based
     **/
    public static List<SourceMatch> createDeleteIoDecs(HardwareModule umod) {
		ArrayList<ReferenceLocation> locs = umod.getIoDeclarationList();
		if (locs.size() == 0) return new ArrayList<SourceMatch>();
		ArrayList<SourceMatch> matchList = new ArrayList<SourceMatch>();
		for (ReferenceLocation loc : locs) {
			ReferenceUsage use = new ReferenceUsage(null,"",loc);
			matchList.add(new SourceMatch(use));
		}
		return matchList;
    }
    
    /** Returns a list of changes when an Entity Port List is changed */
    public static List<SourceMatch> createEntityPortChange(InstanceModule imod,
    		ModInstanceWrap wrap,
    		boolean vhdl) {
    	ArrayList<SourceMatch> matchList = new ArrayList<SourceMatch>();
    	
        ReferenceItem<Component> compRef =  imod.getComponentReference();
        ReferenceItem<Entity> entityRef = imod.getEntityReference();
                
        // Entity Change
        if (entityRef != null) {
        	ReferenceItem<ModInstanceDefault> entModRef = entityRef.getObject().getConnectRef();
            matchList.addAll(PortChangeMethods.createEntityChange(entModRef.getLocation(),wrap,vhdl));
        }
        // Io Conversion for Verilog
        matchList.addAll(PortChangeMethods.createDeleteIoDecs(imod.getArchitecture()));
        
        
        if (compRef != null) {
        	ReferenceItem<ModInstanceDefault> compModRef = compRef.getObject().getConnectRef();
        	matchList.addAll(PortChangeMethods.createComponentChange(compModRef.getLocation(),wrap,vhdl));
        }
        return matchList;
    }
    
    
   
	
}
