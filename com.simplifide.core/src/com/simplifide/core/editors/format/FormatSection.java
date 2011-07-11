package com.simplifide.core.editors.format;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;

public class FormatSection {
	
	private TopASTNode root;
	private FormatPosition position;
	
	public FormatSection(TopASTNode root) {
		this.root = root;
	}
	
	public void createPositions() {
		NodePosition npos = root.getPosition();
		this.position = new FormatPosition(npos.getStartPos(), npos.getEndPos());
		root.format(this.position);
	}
	
	public int getIndent(int sp) {
		return position.getIndent(sp);
	}
	
	public static class Top {
		private List<FormatSection> sections = new ArrayList<FormatSection>();

		public void addSection(FormatSection section) {
			sections.add(section);
		}
		
		public int getIndent(int sp) {
			for (FormatSection section : sections) {
				FormatPosition pos = section.position;
				if (sp >= pos.getStart() && sp <= pos.getStop()) {
					return section.getIndent(sp);
				}
			}
			return -1;
		}
		
		public int getStart() {
			if (sections.size() > 0) return sections.get(0).position.getStart();
			return -1;
		}
		
		public int getStop() {
			if (sections.size() > 0) return sections.get(sections.size()-1).position.getStop();
			return -1;
		}
		
	}
}
