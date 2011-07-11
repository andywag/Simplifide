/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.occurence;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;

import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.search.SourceMatch;
import com.simplifide.core.search.TopSearchQuery;
import com.simplifide.core.search.TopSearchResult;
import com.simplifide.core.search.UsagesListUtility;
import com.simplifide.core.source.design.DesignFile;

public class OccurenceSearchQuery extends TopSearchQuery{

	private ReferenceUsage[] usages;
	private DesignFile designFile;
	
	public OccurenceSearchQuery(EditorFindItem item, DesignFile designFile) {
		super(item);
		this.designFile = designFile;
	}
	
	public OccurenceSearchQuery(ReferenceUsage[] usages) {
		super(null);
		this.usages = usages;
	}
	
	@Override
	protected TopSearchResult createSearchResult() {
		return new OccurenceSearchResult(this);
	}
	


	public String getLabel() {
		return "";
	}

	
	
	

	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		try {
			if (this.usages != null) {
				SourceMatch[] umatch = new SourceMatch[2];
				umatch[0] = new SourceMatch(usages[0]);
				umatch[1] = new SourceMatch(usages[1]);
	            this.getResult().addMatches(umatch);
			}
			else {
				List<ReferenceUsage> usages = UsagesListUtility.getFilteredUsages(this.getFindItem(),designFile);
	            //int index =0;
	            //SourceMatch[] umatch = new SourceMatch[usages.size()];
	            ArrayList list = new ArrayList<SourceMatch>();
	            for (ReferenceUsage usage : usages) {
	            	if (usage.getLocation() != null) {
	            		SourceMatch match = new SourceMatch(usage);
	 	                list.add(match);
	            		//umatch[index] = match;
	 	                //index++;
	            	}
	            }
	            SourceMatch[] matches = (SourceMatch[]) list.toArray(new SourceMatch[list.size()]);
	            this.getResult().addMatches(matches);
			}
			
			
		}
		catch (Exception e) {
			HardwareLog.logError(e);
		}
		return Status.OK_STATUS;
	}

	





}
