package com.simplifide.core.scripteditor.hover;

import java.util.ArrayList;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;

import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.hover.GeneralTextHover;
import com.simplifide.core.scripteditor.ScriptUtilities;
import com.simplifide.scala2.parser.nodes.BaseNode;
import com.simplifide.scala2.top.ParserContext;

public class ScriptTextHover extends GeneralTextHover{

	private BaseNode node;
	public ScriptTextHover(GeneralEditor editor) {
		super(editor);
	}

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (node != null) return node.getDescription();
		return "";
	}

	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		ParserContext context = ScriptUtilities.getScriptContext(textViewer, offset);
		ArrayList<BaseNode> nodes = context.getNodes();
		for (int i=nodes.size()-1;i>=0;i--) {
			BaseNode node = nodes.get(i);
			if (node.containsDescription()) {
				this.node = node;
				return new Region(offset,0);
			}
		}
		return null;
	}
	
}




