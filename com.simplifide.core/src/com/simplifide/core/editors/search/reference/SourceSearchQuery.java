/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.reference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;

import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.search.SourceMatch;
import com.simplifide.core.search.TopSearchQuery;
import com.simplifide.core.search.TopSearchResult;
import com.simplifide.core.search.UsagesListUtility;

public class SourceSearchQuery extends TopSearchQuery{

	private boolean global;
	
	public SourceSearchQuery(EditorFindItem item, boolean global) {
		super(item);
		this.global = global;
	}
	
	@Override
	protected TopSearchResult createSearchResult() {
		return new SourceSearchResult(this);
	}
	


	public String getLabel() {
		return "Finding Usages";
	}

	private void delay() {
		Date now = new Date();
        long cur = now.getTime();
        long exit = cur + 200;
        while (cur < exit) {
        	now = new Date();
        	cur = now.getTime();
        }
	}

	private void handleLocalFind() {
		List<ReferenceUsage> usages = UsagesListUtility.getFilteredUsages(this.getFindItem(),null);
        this.delay();
        int index =0;
        SourceMatch[] umatch = new SourceMatch[usages.size()];
        for (ReferenceUsage usage : usages) {
            SourceMatch match = new SourceMatch(usage);
            umatch[index] = match;
            index++;
        }
        this.getResult().addMatches(umatch);
	}
	
	
	
	private void handleGlobalFind() {

	    InstanceModule imod = this.getEnclosingInstanceModule();
	    PathTreeElement root = ActiveSuiteHolder.getDefault().getSuite().getHierListReference().getObject().getTreeRoot();
	    ArrayList<PathTreeElement> paths = root.getPathsToEntity(imod);
	    root.getClass();
		
	}
	
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		this.handleLocalFind();
		/*if (this.global) {
			this.handleGlobalFind();
		}
		else {
			this.handleLocalFind();
		}*/
		return Status.OK_STATUS;
	}

	





}
