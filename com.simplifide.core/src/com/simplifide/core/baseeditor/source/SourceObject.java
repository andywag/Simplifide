/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.source;


import java.io.File;
import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentType;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.source.EclipseSourceFolder;
import com.simplifide.core.source.SourceFolder;
import com.simplifide.core.source.SourceLocationMap;
import com.simplifide.core.util.FileUtility;
import com.simplifide.core.verilog.editor.describer.SystemVerilogFile;
import com.simplifide.core.verilog.editor.describer.VerilogFileAlone;
import com.simplifide.core.vhdl.describer.VhdlFile;
import com.simplifide.core.vhdl.describer.VhdlFileAlone;

public class SourceObject<T extends SourceObject> extends ModuleObjectWithList<T>{
	
	public static final QualifiedName SOURCE_LINK = new QualifiedName(CoreActivator.PLUGIN_ID,"sourcefile");
	public static final QualifiedName SOURCE_FILE = new QualifiedName(CoreActivator.PLUGIN_ID,"filetype");
	
	public static final QualifiedName SOURCE_IGNORE = new QualifiedName(CoreActivator.PLUGIN_ID,"ignore");
	public static final QualifiedName SOURCE_TEST = new QualifiedName(CoreActivator.PLUGIN_ID,"test");

	public static final String TRUE = "True";
	public static final String FALSE = "False";
	
	private static String VERILOG = "com.simplifide.core.content-type.verilog";
	private static String VHDL    = "com.simplifide.core.content-type.vhdl";
	
	private URI uri;
	
	public SourceObject() {}
	public SourceObject(URI uri) {
		super();
		this.uri = uri;
		if (uri != null) this.setname(this.getRealName());
	}
	
	/** Convenience Method to get the file URI location */
	
	public static boolean isIgnored(URI uri) {
		IResource res = FileUtility.convertURItoResource(uri);
		if (res == null) return true;
		String test;
		try {
			test = res.getPersistentProperty(SOURCE_IGNORE);
			if (test != null && test.equalsIgnoreCase("true")) return true;
			IContainer parent = res.getParent();
			if (parent != null) return SourceObject.isIgnored(parent.getLocationURI());
		} catch (CoreException e) {}
		return false;
	}
	
	public String getRealName() {
		try {
			IFileStore store = EFS.getStore(uri);
			return store.getName();
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return "";
	}
		
	public void deleteObject() {
		super.deleteObject();
	}
	
	
	public static SourceObject retrieveSourceObject(IResource resource) {
		try {
			if (resource != null && resource.exists()) {
				return (SourceObject) resource.getSessionProperty(SOURCE_LINK);
			}
			return null;
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	private static boolean isDirectory(URI resource) {
		if (resource.getScheme().equalsIgnoreCase("file")) {
			File ufile = new File(resource);
			if (ufile.isDirectory()) return true;
		}
		return false;
	}
	
	private static String getFileName(URI resource) {
		if (resource.getScheme().equalsIgnoreCase("file")) {
			File ufile = new File(resource);
			return ufile.getName();
		}
		String path = resource.getPath();
		String[] us = path.split("\\.");
		if (us.length == 0) return path;
		return us[us.length-1];
	}
	
	public static SourceObject resolveObject(URI resource) {
		if (resource == null) return null;
		
		SourceFile sfile = SourceLocationMap.getInstance().getSourceFile(resource);
		
		if (sfile != null && !sfile.isAloneFile()) return sfile;
		
		//if (sfile != null) return sfile;
		
		if (isDirectory(resource)) {
			return new SourceFolder(resource);
		}
		else {
			IFile afile = FileUtility.convertURItoFile(resource);

			String filename = getFileName(resource);
			IContentType type = Platform.getContentTypeManager().findContentTypeFor(filename);
			if (type == null) return new SourceFile(resource);
			if (type.getId().equalsIgnoreCase(VERILOG)) {
				if (afile != null) {
					return new SystemVerilogFile(resource);
				}
				else {
					return new VerilogFileAlone(resource);
				}
			}
			else if (type.getId().equalsIgnoreCase(VHDL)){
				if (afile != null) {
					return new VhdlFile(resource);
				}
				else {
					return new VhdlFileAlone(resource);
				}
			}
			return new SourceFile(resource);
		}
		
	}
	
	
	public IFileStore getFileStore() {
		try {
			return EFS.getStore(this.uri);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	
	public void resolveDesignItems(EclipseBaseProject basic, EclipseSourceFolder folder) {
		
	}
	
	public IResource getResource() {
		return FileUtility.convertURItoResource(uri);
	}
	
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public URI getUri() {
		return this.uri;
	}

	
	private String convertBoolean(boolean bool) {
		if (bool) return "true";
		return "false";
	}
	
	public void setTestFile(boolean test) {
		try {
			this.getResource().setPersistentProperty(SOURCE_TEST, this.convertBoolean(test));
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public boolean isTestFile() {
		try {
			String test = this.getResource().getPersistentProperty(SOURCE_TEST);
			if (test != null && test == "true") return true;
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return false;
	}
	
	public void setIgnoredFile(boolean test) {
		try {
			this.getResource().setPersistentProperty(SOURCE_IGNORE, this.convertBoolean(test));
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public boolean isIgnoredFile() {
		try {
			if (this.getResource() == null ) {
				return true;
			}
			// This seems strange but there is an issue with the creation order
			if (!this.getResource().exists()) return false;
			String test = this.getResource().getPersistentProperty(SOURCE_IGNORE);
			if (test != null && test.equalsIgnoreCase("true")) return true;
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return false;
	}
	
	public boolean hasCheckedIgnored() {
		try {
			String test = this.getResource().getPersistentProperty(SOURCE_IGNORE);
			if (test == null) return false;
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return true;
	}
	
	
	
}
