package com.simplifide.core.scripteditor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;

import com.simplifide.scala2.top.InterfaceTop;
import com.simplifide.scala2.top.ParserContext;

public class ScriptUtilities {

	private static StringOffset convertRegion(ITextViewer viewer, int offset) {
		
		try {
			IDocument doc = viewer.getDocument();
			ITypedRegion reg;
			reg = doc.getPartition(offset);
			StringBuilder builder = new StringBuilder();
			
			int baseOffset = offset - reg.getOffset();
			
			String text = doc.get(reg.getOffset(), reg.getLength());
			String[] lines = text.split("\n");
			
			
			int ilength = lines[0].length()+1; // Input Length
			int olength = 0; // Output  Length
			int realoffset = 0;
			
			for (int i=1;i<lines.length-1;i++) {
				String lin = lines[i];
				int clength = lin.length();
				ilength += clength+1;
				if (lin.trim().startsWith("--")) {
					builder.append(lin.replaceFirst("--", ""));
					builder.append("\n");
					olength += clength - 1;
				}
				else {
					builder.append(lin);
					builder.append("\n");
					olength += clength + 1;
				}
				if (baseOffset >= (ilength - clength) && baseOffset <= ilength) {
					realoffset = baseOffset - (ilength - olength);
				}	
			}
			return new StringOffset(builder.toString(),realoffset);
			
			
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	public static ParserContext getScriptContext(ITextViewer viewer,int offset) {
		StringOffset off = convertRegion(viewer, offset);
		//InterfaceTop.createParser();
		ParserContext context = InterfaceTop.getParseContext(off.name, off.value);
		return context;
	}
	
	public static class StringOffset {
		public String name;
		public int value;
		public StringOffset(String name, int value) {
			this.name = name;
			this.value = value;
		}
	}
	
}
