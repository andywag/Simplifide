package com.simplifide.core.refactor;

import java.util.HashMap;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class InstanceWriter {

	public static String createComponent(boolean vhdl, InstanceModule module) {
		Entity ent = (Entity) module.getEntityReference().getObject();
		ModInstanceDefault def = (ModInstanceDefault) ent.getConnectRef().getObject();
		ModInstanceWrap wrap = new ModInstanceWrap(def,vhdl);
		return createComponent(vhdl, wrap);
	}
	
	public static String createComponent(boolean vhdl, ModInstanceWrap wrap) {
		String cfile = "refactor/verilog/component";
    	if (vhdl) cfile = "refactor/vhdl/component";
    	
    	String temp = TemplateGenerator.generateTemplate(cfile, wrap);
    	return temp;
	}
	
	public static String createInstance(boolean vhdl,InstanceModule module, HashMap<String,String> map ) {
		EntityHolder holder = module.getEntityHolder();
		if (holder != null) {
			ModInstanceDefault def = (ModInstanceDefault)holder.getConnectRef().getObject();
			ModInstanceConnect connect = def.createDefaultConnection(holder);
			ModInstanceConnectWrap wrap = new ModInstanceConnectWrap(connect, vhdl);
			return createInstance(vhdl, wrap,map);
		}
		return "";
	}
	
	public static String createInstance(boolean vhdl,ModInstanceConnectWrap wrap ) {
		return createInstance(vhdl, wrap, new HashMap<String,String>());
	}
	public static String createInstance(boolean vhdl,ModInstanceConnectWrap wrap,HashMap<String,String> map ) {
		String cfile = "refactor/verilog/instance";
		if (vhdl) cfile = "refactor/vhdl/instance";
		String src = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_EXPR);
		String dest = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_DEST);
		wrap.convertPorts(src, dest);
		for (String key : map.keySet()) {
			wrap.convertPorts(key, map.get(key));
		}
		String temp = TemplateGenerator.generateTemplate(cfile, wrap);
		return temp;
	}
	
	
	
	
}
