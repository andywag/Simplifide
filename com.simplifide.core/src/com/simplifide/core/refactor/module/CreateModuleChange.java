package com.simplifide.core.refactor.module;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class CreateModuleChange extends Change {

	private ModInstanceWrap wrap;
	private InstanceModule enclosingModule;
	public CreateModuleChange(ModInstanceWrap wrap,InstanceModule enclosingModule) {
		this.wrap = wrap;
		this.enclosingModule = enclosingModule;
	}
	@Override
	public Object getModifiedElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return this.wrap.getName();
	}

	@Override
	public void initializeValidationData(IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return RefactoringStatus.createInfoStatus("Valid");
	}

	public HashMap createMap(File file) {
		HashMap map = new HashMap();
		String fn = file.getName();
		String fe = fn.substring(fn.lastIndexOf('.')+1, fn.length());
		map.put("FILE_NAME", file.getName());
		map.put("FILE_EXTENSION", fe);
		map.put("FILE_PATH", file.getParentFile().getAbsolutePath());
		map.put("FULL_FILE_NAME", file.getAbsolutePath());
		map.put("DATE", new Date());
		map.put("AUTHOR", System.getProperty("user.name"));
		map.put("object", this.wrap);
	
		String header = "";
		if (this.wrap.isVhdlFile()) {
			header = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VHDL_HEADERDIR);
		}
		else {
			header = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.FILEWIZARD_VERILOG_HEADERDIR);
		}
		String head = "";
		if (!header.equalsIgnoreCase("")) head = TemplateGenerator.generateTemplateFromString(header, map);
		map.put("HEADER", head);
		return map;
	}
	
	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		String cfile = "refactor/verilog/module_file";
    	if (this.wrap.isVhdlFile()) cfile = "refactor/vhdl/entity_arch_file";
    	URI uri = this.enclosingModule.getEntityReference().getLocation().getUri();
    	File ufile = new File(uri);
    	File udir = ufile.getParentFile();
    	String filename = wrap.getName() + (wrap.isVhdlFile() ? ".vhd" : ".v");
    	File nfile = new File(udir,filename);
    	
    	HashMap map = this.createMap(nfile);
    	String temp = TemplateGenerator.generateTemplatewithHash(cfile, map);
		try {
			String fname = nfile.getAbsolutePath();
			FileWriter fstream = new FileWriter(fname);
	        BufferedWriter out = new BufferedWriter(fstream);	
	        out.write(temp);
	        out.close();
	        fstream.close();
	        
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		SourceFile sfile = LocationOperations.getSourceFile(uri);
		if (sfile != null) {
			IContainer cont = sfile.getResource().getParent();
			cont.refreshLocal(IResource.DEPTH_ONE, null);
		}
		
		
    	return null;
		
	}

}
