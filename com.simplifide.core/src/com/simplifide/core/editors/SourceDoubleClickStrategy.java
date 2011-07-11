/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NameTopASTNode;
import com.simplifide.base.sourcefile.antlr.walk.PositionWalker;

public class SourceDoubleClickStrategy extends SourceDoubleClickStrategyTop {
	
	protected ITextViewer fText;

	private SourceEditor editor;
	private boolean clicked = false;
	private int clickedMode = 0;
	
	public SourceDoubleClickStrategy(SourceEditor editor) {
		this.editor = editor;
		
		
	}
	
	
	
	public void doubleClicked(ITextViewer part) {
		this.fText  = part;
		
		int startPart = part.getSelectedRange().x;
		int length   = part.getSelectedRange().y;
		
		ParseDescriptor desc = editor.getDesignFile().getParseDescriptor();
		List<TopASTNode> nodeList = PositionWalker.getPathTo(desc.getRoot(),startPart);
		for (int i =0;i<nodeList.size();i++) {
			TopASTNode node = nodeList.get(i);
			if (node instanceof NameTopASTNode) {
				NameTopASTNode newNode = (NameTopASTNode) node;
				NodePosition pos = newNode.getNodeRange();
				if (pos == null) {
					super.doubleClicked(part);
					break;
				}
				int sp = pos.getStartPos();
				int ep = pos.getEndPos();
				if (startPart >= sp && startPart <= ep) {
					if (clickedMode == 0) {
						clickedMode = 1;
					}
					else if (clickedMode == 1) {
						TopASTNode baseNode = nodeList.get(i-1);
						NodePosition pos1 = baseNode.getPosition();
						this.selectRange(pos1.getStartPos()-1, pos1.getEndPos());
						clickedMode = 2;
						return;
					}
					else {
						this.clickedMode = 0;
					}
					
				}
				
			}
		} 
		super.doubleClicked(part);
		
	}
	protected boolean selectComment(int caretPos) {
		IDocument doc = fText.getDocument();
		int startPos, endPos;

		try {
			int pos = caretPos;
			char c = ' ';

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (c == '\\') {
					pos -= 2;
					continue;
				}
				if (c == Character.LINE_SEPARATOR || c == '\"')
					break;
				--pos;
			}

			if (c != '\"')
				return false;

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();
			c = ' ';

			while (pos < length) {
				c = doc.getChar(pos);
				if (c == Character.LINE_SEPARATOR || c == '\"')
					break;
				++pos;
			}
			if (c != '\"')
				return false;

			endPos = pos;

			int offset = startPos + 1;
			int len = endPos - offset;
			fText.setSelectedRange(offset, len);
			return true;
		} catch (BadLocationException x) {
		}

		return false;
	}
	protected boolean selectWord(int caretPos) {

		IDocument doc = fText.getDocument();
		int startPos, endPos;

		try {

			int pos = caretPos;
			char c;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
					break;
				--pos;
			}

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();

			while (pos < length) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
					break;
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			return true;

		} catch (BadLocationException x) {
		}

		return false;
	}

	private void selectRange(int startPos, int stopPos) {
		int offset = startPos + 1;
		int length = stopPos - offset;
		fText.setSelectedRange(offset, length);
	}
}
