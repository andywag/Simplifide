/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.search;

import java.net.URI;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorPart;

import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFileAlone;

public abstract class TopSearchResult extends AbstractTextSearchResult implements IEditorMatchAdapter, IFileMatchAdapter{

	private ISearchQuery query;

	public TopSearchResult(ISearchQuery query) {
	    this.query = query;
	    
	}
	
	 
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return this;
	}

	 
	public IFileMatchAdapter getFileMatchAdapter() {
		return this;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract String getLabel();

	public ISearchQuery getQuery() {
		return this.query;
	}

	public String getTooltip() {
	    return "";
	}
	

	
	 
	public Match[] computeContainedMatches(AbstractTextSearchResult result, IEditorPart editor) {
		if (editor instanceof SourceEditor) {
			SourceEditor edit = (SourceEditor) editor;
			IFile file = edit.getDesignFile().getIFile();
			return this.computeContainedMatches(result, file);
		}
		return new Match[0];
	}
	
	 
	public Match[] computeContainedMatches(AbstractTextSearchResult result,IFile file) {
		//URI uri = file.getLocationURI();
		Object[] objs = this.getElements();
		ArrayList<Match> matches = new ArrayList<Match>();
		for (Object obj : objs) {
			Match[] umatch = this.getMatches(obj);
			for (Match mat : umatch) {
				if (this.isShownInFile(mat, file)) {
					matches.add(mat);

				}
			}
		}
		
		Match[] mat = matches.toArray(new Match[matches.size()]);
		return mat;
	}

	 
	public boolean isShownInEditor(Match match, IEditorPart editor) {
		SourceEditor edit = (SourceEditor) editor;
		if (edit.getDesignFile() instanceof DesignFileAlone) return true;
		
		URI uri = edit.getDesignFile().getUri();
		SourceMatch umatch = (SourceMatch) match;
		URI muri = umatch.getUsage().getLocation().getUri();
		boolean res = uri.equals(muri);
		return res;
	}

	public boolean isShownInFile(Match match, IFile file) {
		if (file == null) return false;
		URI uri = file.getLocationURI();
		if (match instanceof SourceMatch) {
			SourceMatch umatch = (SourceMatch) match;
			URI muri = umatch.getUsage().getLocation().getUri();
			boolean res = uri.equals(muri);
			return res;
		}
		return false;
	}
	

	 
	public IFile getFile(Object element) {
		if (element instanceof ReferenceUsage) {
			ReferenceUsage use = (ReferenceUsage) element;
			return LocationOperations.getDesignFile(use.getLocation().getUri()).getIFile();
		}
		// TODO Auto-generated method stub
		return null;
	}


   

}
