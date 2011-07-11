package com.simplifide.core.verilog.editor.describer;

import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;

/** Adds extra support for system verilog type functionallity which 
 *  mainly includes knowledge of the header location
 *
 */
public class SystemVerilogFile extends VerilogFile{
	
	public SystemVerilogFile(URI uri) {
		super(uri);
	}
	
	public boolean isHeaderFile() {return false;}
	public boolean isBodyFile() {return false;}
	
	protected IFile getOppositeFile(IFile file) {
		String fname =  file.getName();
		String[] fn = fname.split("\\."); 
		
		if (fn.length < 2) return null;
		
		String name = fn[0];
		String ext = fn[1];
		String next = "svh";
		if (ext.equalsIgnoreCase("svh")) next = "sv";
		
		IContainer parent = file.getParent();
		return parent.getFile(new Path(name + "." + next));
	}
	
	public static class Head extends SystemVerilogFile {

		private SystemVerilogFile body;
		public Head(URI file) {
			super(file);
		}
		public boolean isHeaderFile() {return true;}
		
		
		public void setBody(SystemVerilogFile body) {
			this.body = body;
		}
		public SystemVerilogFile getBody() {
			return body;
		}
		
	}
	
	public static class Body extends SystemVerilogFile {

		private SystemVerilogFile head;
		public Body(URI file) {
			super(file);
		}
		public boolean isBodyFile() {return true;}
		
		public void setHead(SystemVerilogFile head) {
			this.head = head;
		}
		public SystemVerilogFile getHead() {
			return head;
		}
		
	}

}
