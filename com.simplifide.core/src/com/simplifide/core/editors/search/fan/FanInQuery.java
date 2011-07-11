package com.simplifide.core.editors.search.fan;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;

import com.simplifide.base.core.hierarchy.PathTotalElement;
import com.simplifide.base.core.hierarchy.PathTotalTreeElement;
import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.ActiveSuiteHolder;

public class FanInQuery extends FanQuery{
	public FanInQuery(EditorFindItem item) {
		super(item);
	}
	
	@Override
	public String getLabel() {
		return "Fan In";
	}
	
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
	    InstanceModule imod = this.getEnclosingInstanceModule();
	    
	    PathTreeElement root = ActiveSuiteHolder.getDefault().getSuite().getHierListReference().getObject().getTreeRoot();
	    ArrayList<PathTreeElement> paths = root.getPathsToEntity(imod);
	    
	    PathTotalTreeElement rootPath = new PathTotalTreeElement(null);
	    for (PathTreeElement path : paths) {
	    	PathTotalElement tot = new PathTotalElement(path,this.getVariable(),PathTotalElement.DOWN);
	    	PathTotalTreeElement el  = tot.resolvePathIn();
	    	if (el != null) rootPath.addChild(el);
	    }
	    this.getResult().addMatch(new FanFakeMatch(rootPath));
	    
	    return Status.OK_STATUS;
	}
}
