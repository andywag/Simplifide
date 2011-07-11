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

public abstract class VhdlFileWizard extends FileNewWizard{

	
	public static String VHDL_ENTITY_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.VhdlEntityWizard";
	public static String VHDL_PACKAGE_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.VhdlPackageWizard";
	public static String VHDL_ARCH_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.VhdlArchWizard";
	public static String VHDL_ENT_ARCH_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.VhdlEntityArchWizard";
	public static String VHDL_TESTBENCH_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.VhdlTestBenchWizard";
	public static String VHDL_TESTER = CoreActivator.PLUGIN_ID + ".ui.wizard.VhdlTester";

	public VhdlFileWizard(String name) {
		super(name);
	}

	@Override
	public final String getDefaultExtension() {
		return "vhd";
	}
	
	@Override
	public final String getHeaderTemplate() {
		return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VHDL_HEADERDIR);
	}
	
	@Override
	public String getDefaultHeaderFile() {
		return "file/vhdl/vhdl_header2";
	}
	
	@Override
	public String getTemplateDirectory() {
		return "file/vhdl/";
	}

	public static class Package extends VhdlFileWizard {
		public Package() {
			super("Vhdl Package");
		}
		
		@Override
		public String getTemplateName() {
			return "package_file";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VHDL_PACKAGE_TEMPLATE);
		}
	}
	
	public static class Entity extends VhdlFileWizard {
		public Entity() {
			super("Vhdl Entity");
		}
		
		@Override
		public String getTemplateName() {
			return "entity_file";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VHDL_ENTITY_TEMPLATE);
		}
	}
	
	public static class Arch extends VhdlFileWizard {
		public Arch() {
			super("Vhdl Architecture");
		}
		
		@Override
		public String getTemplateName() {
			return "arch_file";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VHDL_ARCHITECTURE_TEMPLATE);
		}
	}
	
	public static class EntityArch extends VhdlFileWizard {
		public EntityArch() {
			super("Combined Vhdl Entity and Architecture");
		}
		
		@Override
		public String getTemplateName() {
			return "entity_arch_file";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VHDL_ENTITYARCHITECTURE_TEMPLATE);
		}
	}
	
	public static class TestBench extends VhdlFileWizard {
		private String signals;
		private String instance;
		IResource res;
		public TestBench() {
			super("Vhdl Test Bench");
		}
		
		public TestBench(String signals, String instance, IResource res) {
			super("Vhdl Testbench");
			this.signals = signals;
			this.instance = instance;
			this.res = res;
		}
		
		@Override
		public String getTemplateName() {
			return "vhdl_testbench";
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
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VHDL_TESTBENCH);
		}
		
	}
	
	public static class Tester extends VhdlFileWizard {
		public Tester() {
			super("Vhdl Tester");
		}
		
		@Override
		public String getTemplateName() {
			return "vhdl_tester";
		}
			
		@Override
		public String getFileTemplate() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VHDL_TESTER);
		}
		
	}	
	
	

	
	
	
	
}
