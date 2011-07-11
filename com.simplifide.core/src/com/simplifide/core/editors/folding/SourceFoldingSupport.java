/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.folding;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.SourceEditor;

public class SourceFoldingSupport {

    private SourceEditor editor;
    private ProjectionAnnotationModel projectionModel;
        
	private HashMap<SourceProjectionAnnotation,Position> oldAnnotations = new HashMap();
	private TopASTNode oldRoot;
	
	
	
	
	public SourceFoldingSupport(SourceEditor editor, ProjectionAnnotationModel projectionModel) {
		this.editor = editor;
        this.projectionModel = projectionModel;	
	}

	private IDocument getDocument() {
	    return this.editor.getDocument();
    }
	
        /** Returns the proper index of the document if the folds didn't exist.
         * @todo This method fails for positions inside a fold or even shown inside a 
         * fold
         *  */
        public int getOffsetPosition(int position, IDocument document) {
            int newpos = position;
            Set<SourceProjectionAnnotation> set = oldAnnotations.keySet();
            for (SourceProjectionAnnotation ann : set) {
                    if (ann.isCollapsed()) {
                            
                            Position pos = oldAnnotations.get(ann);
                            if (newpos >= pos.getOffset()) {
                                    int len = 0;
                                int delim = 1;
                                    try {
                                    int line = document.getLineOfOffset(pos.getLength() + pos.getOffset());
                                     delim = document.getLineDelimiter(line).length();
                                        len = document.getLineInformationOfOffset(pos.getOffset()).getLength();
                                    } catch (BadLocationException e) {
                                            HardwareLog.logError(e);
                                    }
                                    newpos = newpos +pos.getLength() - len - delim;
                            }
                    }
            }
            
            return newpos;
        }
        
        /** Returns the correct position in the document assuming that there are folds. */
	public int getRealPosition(int position, IDocument document) {
            int newpos = position;
            Set<SourceProjectionAnnotation> set = oldAnnotations.keySet();
            for (SourceProjectionAnnotation ann : set) {
                    if (ann.isCollapsed()) {
                            
                            Position pos = oldAnnotations.get(ann);
                            if (position >= pos.getOffset()) {
                                    int len = 0;
                                int delim = 1;
                                    try {
                                    int line = document.getLineOfOffset(pos.getLength() + pos.getOffset());
                                     delim = document.getLineDelimiter(line).length();
                                        len = document.getLineInformationOfOffset(pos.getOffset()).getLength();
                                    } catch (BadLocationException e) {
                                            HardwareLog.logError(e);
                                    }
                                    newpos = newpos -pos.getLength() + len + delim;
                            }
                    }
            }
            
            return newpos;
	}
		
	private void addRealFold(TopASTNode node, HashMap newAnnotations) {
		
                if (node.getPosition() == null) return;
                int startPos = node.getPosition().getStartPos();
                int endPos = node.getPosition().getEndPos();
                
                IDocument doc = this.getDocument();
                try {
                    IRegion str = doc.getLineInformationOfOffset(startPos);
                    IRegion etr = doc.getLineInformationOfOffset(endPos);
                    int line = doc.getLineOfOffset(etr.getOffset() + etr.getLength());
                    String delim = doc.getLineDelimiter(line);
                    if (str.getOffset() != etr.getOffset() && delim != null) {
                    	int offset = str.getOffset();
                    	int length =  etr.getOffset() + etr.getLength() - str.getOffset() + delim.length();
                        if (offset > 0 && length > 0) {
                        	Position pos = new Position(offset,length);
                            SourceProjectionAnnotation annotation = new SourceProjectionAnnotation(node);
                            newAnnotations.put(annotation, pos);
                            
                        }
                    	
                    }
                } catch (BadLocationException e) {
                    HardwareLog.logError(e);
                }	
	}
	
	
	private void addCommentFolds(PositionStream stream, HashMap newAnnotations) {
		if (stream != null) {
			List<TopASTNode> nodeList = stream.getCommentFoldList();
			for (TopASTNode node : nodeList) {
				this.addRealFold(node, newAnnotations);
			}
		}
	}
	
	private void addParseFolds(TopASTNode root, HashMap newAnnotations) {
		if (root != null) {
			if (root.canFold())
				this.addRealFold(root, newAnnotations);
			TopASTNode child = root.getFirstASTChild();
			while (child != null) {
				this.addParseFolds(child, newAnnotations);
				child = child.getNextASTSibling();
			}
		}

	}

	
	
	// A completely Inneficient method for doing this
	private void checkOldAnnotations(HashMap<SourceProjectionAnnotation,Position> newMap) {
		for (SourceProjectionAnnotation proj : this.oldAnnotations.keySet()) {
			if (proj.isCollapsed()) {
				Position upos = this.oldAnnotations.get(proj);
				for (SourceProjectionAnnotation newProj : newMap.keySet()) {
					Position npos = newMap.get(newProj);
					if (npos.getLength() == upos.getLength()) {
						String newText = newProj.getNode().getRealText();
						String oldText = proj.getNode().getRealText();
						if (newText.equalsIgnoreCase(oldText)) {
							newProj.markCollapsed();
						}
					}
				}
			}
		}
	}
	
	
	
	public void updateFolds(ParseDescriptor descriptor) {
		// Check to see if this operation is enabled
		if (!HardwareChecker.isFoldEnabled()) return;

		synchronized (this.projectionModel.getLockObject()) {
			
			if (this.projectionModel == null) return;
			this.projectionModel.removeAllAnnotations();
			HashMap newAnnotations = new HashMap();
			// Create the New folds
			TopASTNode root = descriptor.getRoot();
			this.addParseFolds(root,newAnnotations);
			this.addCommentFolds(descriptor.getStream(), newAnnotations);
			
			this.checkOldAnnotations(newAnnotations);
			this.projectionModel.modifyAnnotations(null, newAnnotations, null);

			this.oldRoot = root;
			this.oldAnnotations = newAnnotations;
			
		}
		
                
	}
}
