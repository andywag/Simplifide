package com.simplifide.core.editors.search.fan;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;

import com.simplifide.base.core.hierarchy.PathTotalElement;
import com.simplifide.base.core.hierarchy.PathTotalTreeElement;
import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.ActiveSuiteHolder;

public class FanOutQuery extends FanQuery{
	public FanOutQuery(EditorFindItem item) {
		super(item);
	}
	
	@Override
	public String getLabel() {
		return "Fan Out";
	}
	
	public List<PathTotalTreeElement> resolvePathOut(PathTotalTreeElement rootPath,
			InstanceModule imod, 
			SystemVar tvar) {
		
		ArrayList<PathTotalTreeElement> npaths = new ArrayList<PathTotalTreeElement>();
	    PathTreeElement root = ActiveSuiteHolder.getDefault().getSuite().getHierListReference().getObject().getTreeRoot();
	    ArrayList<PathTreeElement> paths = root.getPathsToEntity(imod);
		
	    for (PathTreeElement path : paths) {
	    	PathTotalElement tot = new PathTotalElement(path,tvar,PathTotalElement.DOWN);
	    	PathTotalTreeElement els = tot.resolvePathOut();
	    	for (PathTotalTreeElement el : els.getChildren()) {
	    		rootPath.addChild(el);
	    	}
	    	
	    }
	    
	    return npaths;
	    
	}
	
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		InstanceModule imod = this.getEnclosingInstanceModule(); 
	    SystemVar tvar = this.getVariable();
	    
	    PathTotalTreeElement rootPath = new PathTotalTreeElement(null);
	    this.resolvePathOut(rootPath, imod, tvar);
	    this.getResult().addMatch(new FanFakeMatch(rootPath));
	    return Status.OK_STATUS;
	}
}
