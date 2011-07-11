/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard;

import java.util.HashMap;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.ui.wizard.other.NewFilePage;

public class VerilogFileWizard extends FileNewWizard{

	public static String MODULE_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.verilog.module";
	public static String CLASS_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.verilog.class";
	public static String INTERFACE_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.verilog.interface";
	public static String PROGRAM_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.verilog.program";
	public static String PACKAGE_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.verilog.package";
	public static String PACKAGE_TESTBENCH = CoreActivator.PLUGIN_ID + ".ui.wizard.verilog.testbench";
	
	public VerilogFileWizard(String name) {
		super(name);
	}

	@Override
	public String getTemplateDirectory() {
		return "file/verilog/";
	}
	
	@Override
	public String getTemplateName() {
		return "verilog_file";
	}

	@Override
	public String getDefaultExtension() {
		return "v";
	}


	@Override
	public String getHeaderTemplate() {
		return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_HEADERDIR);
	}
	
	@Override
	public String getFileTemplate() {
		return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_TEMPLATE);
	}
	
	@Override
	public String getDefaultHeaderFile() {
		return "file/verilog/verilog_header2";
	}

	public static class Module extends VerilogFileWizard {
		public Module() {
			super("Verilog Module");
		}
		
		@Override
		public String getTemplateName() {
			return "module";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_TEMPLATE);
		}
	}
	
	
	
	
	public static class Class1 extends VerilogFileWizard {
		public Class1() {
			super("Verilog Class");
		}
		
		@Override
		public String getTemplateName() {
			return "class";
		}
			 
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_CLASS);
		}
	}
	
	public static class Program extends VerilogFileWizard {
		public Program() {
			super("Verilog Program");
		}
		
		@Override
		public String getTemplateName() {
			return "program";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_PROGRAM);
		}
	}
	
	public static class Interface extends VerilogFileWizard {
		public Interface() {
			super("Verilog Interface");
		}
		
		@Override
		public String getTemplateName() {
			return "interface";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_INTERFACE);
		}
	}
	
	public static class Package extends VerilogFileWizard {
		public Package() {
			super("Verilog Package");
		}
		
		@Override
		public String getTemplateName() {
			return "package";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_PACKAGE);
		}
	}
	
	public static class Testbench extends VerilogFileWizard {
		private String signals;
		private String instance;
		IResource res;
		public Testbench() {
			super("Verilog Testbench");
		}
		public Testbench(String signals, String instance, IResource res) {
			super("Verilog Testbench");
			this.signals = signals;
			this.instance = instance;
			this.res = res;
		}
		
		protected NewFilePage createNewFilePage(String name, IStructuredSelection sel) {
			if (res == null) return super.createNewFilePage(name, sel);
			NewFilePage page = new NewFilePage(this,name,sel);
			String path = res.getParent().getFullPath().toString();
			String name1 = res.getName();
			page.setPath(path, name1);
			
			return page;
		}
		
		protected void addToMap(HashMap<String,Object> map){
			map.put("signals", signals);
			map.put("instance", instance);
		}

		@Override
		public String getTemplateName() {
			return "testbench";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_PACKAGE);
		}
	}
	
	
}
