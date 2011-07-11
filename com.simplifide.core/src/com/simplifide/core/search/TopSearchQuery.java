/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.search;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;


/** Top level class containg the search query which is used for 
 * all searches.
 */
public abstract class TopSearchQuery implements ISearchQuery{

	
	private EditorFindItem findItem;
	private TopSearchResult result;
	
	public TopSearchQuery(EditorFindItem item) {
		
		this.setFindItem(item);
		this.init();
	}
	
	private void init() {
		setResult(this.createSearchResult());
	}
	
	public boolean canRerun() {
		return true;
	}

	public boolean canRunInBackground() {
		return true;
	}

	public ISearchResult getSearchResult() {
		return getResult();
	}

	protected InstanceModule getEnclosingInstanceModule() {
		ModuleObject obj = this.getFindItem().getEnclosingItem().getObject();
		if (obj instanceof Entity) {
			return (InstanceModule) ((Entity)obj).getInstanceModRef().getObject();
		}
		else if (obj instanceof HardwareModule) {
			return (InstanceModule) ((HardwareModule)obj).getInstModRef().getObject();
		}
		return null;
	}
	
	protected abstract TopSearchResult createSearchResult();
	public abstract String getLabel();
	public abstract IStatus run(IProgressMonitor monitor) throws OperationCanceledException;

	
	

	public void setResult(TopSearchResult result) {
		this.result = result;
	}

	public TopSearchResult getResult() {
		return result;
	}

	public void setFindItem(EditorFindItem findItem) {
		this.findItem = findItem;
	}

	public EditorFindItem getFindItem() {
		return findItem;
	} 

}
