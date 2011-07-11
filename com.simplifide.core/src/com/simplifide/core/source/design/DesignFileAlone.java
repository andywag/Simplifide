package com.simplifide.core.source.design;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.hyperlink.SourceHyperlink;
import com.simplifide.core.verilog.editor.describer.VerilogFileAlone;
import com.simplifide.core.vhdl.describer.VhdlFileAlone;

/** Design File which is used only in standalone mode for an editor */
public abstract class DesignFileAlone extends DesignFile{

	public DesignFileAlone(URI uri) {
		super(uri);
		
		this.baseInit();
	}
	
	public boolean isAloneFile() {
		return true;
	}
	
	public String getname() {
		return this.getRealName();
		
	}
	
	public InputStream getInputStream() {
		try {
			return this.getFileStore().openInputStream(0, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	/** Returns the text at this line number. Used for search */
	public String getTextAtLine(int line) {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
			String str  = "";
			for (int i=0;i<line;i++) {
				str = reader.readLine();
			}
			return str;
		}
		catch (IOException e) {
			HardwareLog.logError(e);
		}
		return "";
	}
	
	
	public static DesignFile resolveObject(IFileStore store) {
		String us = store.getName();
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(store.getName());
		String id = desc.getId();
		if (id.endsWith("VhdlEditor")) {
			return new VhdlFileAlone(store.toURI());
		}
		else if(id.endsWith("VerilogEditor")){
			return new VerilogFileAlone(store.toURI());
		}

		return null;
	}

	public URI getResourceUri() {
		return this.getFileStore().toURI();
	}
	
	public void goToHyperlink(SourceHyperlink link) {
		ReferenceItem item = link.getItem();

		if (item != null) {
			if (item.getLocation() != null) {
				this.getEditor().goToPosition(item.getLocation().getDocPosition(),item.getLocation().getLength());
			}
		}
	}
	
	public boolean tooLarge() {
		return false;
	}
	
	@Override
	public void replaceText(ReferenceLocation loc, String newText) {
		// Not Supported
	}
	
	@Override
	protected DesignFileBuilder createBuilder() {
		return null;
	}
	
	protected DesignFileCompileInfo createCompileInfo() {
		return new DesignFileCompileInfoAlone(this);
	}

	

}
