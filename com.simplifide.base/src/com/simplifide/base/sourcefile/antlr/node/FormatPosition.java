package com.simplifide.base.sourcefile.antlr.node;

import java.util.ArrayList;
import java.util.List;

public class FormatPosition {

	private int start;
	private int stop;
	
	private int indent = -1;
	private int minimum = -1;
	
	
	
	private List<FormatPosition> positions = new ArrayList<FormatPosition>();
	
	public FormatPosition(int start, int stop) {
		this.setStart(start);
		this.setStop(stop);
	}
	
	public FormatPosition addNewPosition(int start, int stop) {
		FormatPosition pos = new FormatPosition(start, stop);
		getPositions().add(pos);
		return pos;
	}
	
	public int getIndent(int sp) {
		for (FormatPosition position : positions) {
			if (position.getStart() <= sp && position.getStop() > sp) {
				if (minimum > -1) return minimum;
				else return position.getIndent(sp);
			}
		}
		if (this.indent < this.minimum) return this.minimum;
		return this.indent;
		
	}
	
	public int getIndentOrZero() {
		if (indent <=0) return 0;
		return indent;
	}
	
	
	public String singleLineString() {
		String ustr = "[" + start + "," + stop + "]";
		return ustr;
	}
	
	public String toString() {
		String ostr = this.singleLineString() + "\n";
		return ostr;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStart() {
		return start;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}

	public int getStop() {
		return stop;
	}

	public void setPositions(List<FormatPosition> positions) {
		this.positions = positions;
	}

	public List<FormatPosition> getPositions() {
		return positions;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMinimum() {
		return minimum;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public int getIndent() {
		return indent;
	}


	
}
