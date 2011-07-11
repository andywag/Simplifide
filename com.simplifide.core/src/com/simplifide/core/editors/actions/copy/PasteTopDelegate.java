package com.simplifide.core.editors.actions.copy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.wizard.WizardDialog;

import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.ui.wizard.VerilogFileWizard;
import com.simplifide.core.ui.wizard.VhdlFileWizard;
import com.simplifide.core.verilog.editor.VerilogEditor;

public abstract class PasteTopDelegate extends EditorActionDelegate{

	public PasteTopDelegate() {}
	public PasteTopDelegate(SourceEditor editor) {
		super(editor);
	}
	
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		InstanceModule imod = InstanceModuleHolder.getInstance().getInstanceModule();
		if (imod != null) return true;
		return false;
	}

	public boolean isVhdl() {
		if (this.getEditor() instanceof VerilogEditor) return false;
		return true;
	}
	
	protected void createFromTemplate(ModInstanceWrap wrap,String cfile) {

    	
    	String temp = TemplateGenerator.generateTemplate(cfile, wrap);
    	IDocument doc = this.getEditor().getDocument();
    	try {
			doc.replace(this.getEditor().getCaretPosition(), 0, temp);
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
	}
	
	protected void createFromTemplate(String type) {
		String cfile = "refactor/verilog/" + type;
    	if (isVhdl()) cfile = "refactor/vhdl/" + type;
    	InstanceModule imod = InstanceModuleHolder.getInstance().getInstanceModule();
    	ModInstanceDefault def = (ModInstanceDefault) imod.getEntityHolder().getConnectRef().getObject();
    	ModInstanceWrap wrap = new ModInstanceWrap(def,this.isVhdl());
    	//wrap.orderPorts();
    	this.createFromTemplate(wrap, cfile);
    	
	}
	
	
	
	public static class Component extends PasteTopDelegate {
		public Component() {}
		public Component(SourceEditor editor) {
			super(editor);
		}

		@Override
		public void run(GeneralEditor editor) {		
			this.createFromTemplate("component");
		}
		
	}
	
	public static class ComponentHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Component((SourceEditor)editor);
		}
		
	} 
	
	public static class Entity extends PasteTopDelegate {
		public Entity() {}
		public Entity(SourceEditor editor) {
			super(editor);
		}

		@Override
		public void run(GeneralEditor editor) {
			this.createFromTemplate("entity");
		}
		
	}
	
	public static class EntityHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Entity((SourceEditor)editor);
		}
		
	} 
	
	public static class Instance extends PasteTopDelegate {
		public Instance() {}
		public Instance(SourceEditor editor) {
			super(editor);
		}

		@Override
		public void run(GeneralEditor editor) {
			String cfile = "refactor/verilog/instance";
	    	if (isVhdl()) cfile = "refactor/vhdl/instance";
	    	InstanceModule imod = InstanceModuleHolder.getInstance().getInstanceModule();
	    	ModInstanceDefault def     = (ModInstanceDefault) imod.getEntityHolder().getConnectRef().getObject();
	    	ModInstanceConnect connect = def.createDefaultConnection(imod.getEntityHolder());
	    	ModInstanceConnectWrap wrap = new ModInstanceConnectWrap(connect, isVhdl());
	    	String src = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_EXPR);
	    	String dest = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_DEST);

	    	wrap.convertPorts(src,dest);
	    	
	    	this.createFromTemplate(wrap, cfile);
		}
		
		public static String getInstance(boolean vhdl) {
			String cfile = "refactor/verilog/instance";
	    	if (vhdl) cfile = "refactor/vhdl/instance";
	    	InstanceModule imod = InstanceModuleHolder.getInstance().getInstanceModule();
	    	ModInstanceDefault def     = (ModInstanceDefault) imod.getEntityHolder().getConnectRef().getObject();
	    	ModInstanceConnect connect = def.createDefaultConnection(imod.getEntityHolder());
	    	ModInstanceConnectWrap wrap = new ModInstanceConnectWrap(connect, vhdl);
	    	String src = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_EXPR);
	    	String dest = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_DEST);

	    	wrap.convertPorts(src,dest);
	    	String temp = TemplateGenerator.generateTemplate(cfile, wrap);
	    	return temp;
		}
		
		
	}
	
	public static class InstanceHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Instance((SourceEditor)editor);
		}
		
	} 
	
	public static class Signals extends PasteTopDelegate {
		public Signals() {}
		public Signals(SourceEditor editor) {
			super(editor);
		}

		@Override
		public void run(GeneralEditor editor) {
			String cfile = "refactor/verilog/signals";
	    	if (isVhdl()) cfile = "refactor/vhdl/signals";
			InstanceModule imod = InstanceModuleHolder.getInstance().getInstanceModule();
	    	List<PortTop> ports = imod.getEntityHolder().getPortList();
	    	List<SystemVar> vars = new ArrayList<SystemVar>();
	    	for (PortTop port : ports) {
	    		vars.add(port.getLocalVar());
	    	}
	    	String temp = TemplateGenerator.generateTemplate(cfile, vars);
	    	IDocument doc = this.getEditor().getDocument();
	    	try {
				doc.replace(this.getEditor().getCaretPosition(), 0, temp);
			} catch (BadLocationException e) {
				HardwareLog.logError(e);
			}
	    	
		}
		
		public static String getSignals(boolean vhdl) {
			String cfile = "refactor/verilog/signals";
	    	if (vhdl) cfile = "refactor/vhdl/signals";
			InstanceModule imod = InstanceModuleHolder.getInstance().getInstanceModule();
	    	List<PortTop> ports = imod.getEntityHolder().getPortList();
	    	List<SystemVar> vars = new ArrayList<SystemVar>();
	    	for (PortTop port : ports) {
	    		vars.add(port.getLocalVar());
	    	}
	    	String temp = TemplateGenerator.generateTemplate(cfile, vars);
	    	return temp;
		}
		
		
		
	}
	
	public static class SignalHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Signals((SourceEditor)editor);
		}
		
	} 
	
	public static class Testbench extends PasteTopDelegate {
		public Testbench() {}
		public Testbench(SourceEditor editor) {
			super(editor);
		}

		@Override
		public void run(GeneralEditor editor) {			
			//String cfile = "file/verilog/testbench";
	    	//if (isVhdl()) cfile = "file/vhdl/testbench";
	    	String sig = Signals.getSignals(isVhdl());
	    	String ins = Instance.getInstance(isVhdl());
	    	DesignFile dfile = ((SourceEditor)editor).getDesignFile();
	    	IResource res = dfile.getResource();
	    	IContainer par = res.getParent();
	    	InstanceModule imod = InstanceModuleHolder.getInstance().getInstanceModule();
	    	String fname = imod.getname() + "_test.v";
	    	if (isVhdl()) fname = imod.getname() + "_test.vhd";
	    	IResource child = par.getFile(new Path(fname));
	    	
	    	if (!isVhdl()) {
	    		VerilogFileWizard.Testbench test = new VerilogFileWizard.Testbench(sig,ins,child);
		    	test.init(editor.getSite().getWorkbenchWindow().getWorkbench(), null);
		    	WizardDialog wd = new  WizardDialog(editor.getSite().getShell(), test);
		    	wd.open();
	    	}
	    	else {
	    		VhdlFileWizard.TestBench test = new VhdlFileWizard.TestBench(sig,ins,child);
		    	test.init(editor.getSite().getWorkbenchWindow().getWorkbench(), null);
		    	WizardDialog wd = new  WizardDialog(editor.getSite().getShell(), test);
		    	wd.open();
	    	}
	    	
	    	

		
		}
		
		
		
	}
	
	public static class TestbenchHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Testbench((SourceEditor)editor);
		}
		
	} 
	
	
	
}
