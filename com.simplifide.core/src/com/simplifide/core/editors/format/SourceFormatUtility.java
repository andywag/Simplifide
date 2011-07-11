package com.simplifide.core.editors.format;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;

public class SourceFormatUtility {
	
	private static List<TopASTNode> findBasesS(TopASTNode sm) {
		ArrayList<TopASTNode> bases = new ArrayList<TopASTNode>();
		TopASTNode format = sm.formatBase();
		if (format != null) {
			bases.add(format);
			return bases;
		}
		TopASTNode child = sm.getFirstASTChild();
		while (child != null) {
			bases.addAll(findBasesS(child));
			child = child.getNextASTSibling();
		}
		return bases;
	}
	
	
	public static FormatSection.Top splitDesign(ParseDescriptor desc, int sp, int ep) {
		FormatSection.Top top = new FormatSection.Top();
		TopASTNode root = desc.getRoot();
		List<TopASTNode> bases = findBasesS(root);
		for (TopASTNode base : bases) {
			NodePosition pos = base.getPosition();
			if (pos.getEndPos() > sp && pos.getStartPos() < ep) {
				FormatSection sect = new FormatSection(base);
				sect.createPositions();
				top.addSection(sect);
			}
		}
		return top;
	}
	
	
	
}
