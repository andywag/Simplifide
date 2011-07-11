package com.simplifide.core.ui.filebrowser;

import java.io.File;

import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.core.project.structure.StructureBase;

public class FileTreeComposite extends Composite{

	private TreeViewer treeViewer;
	private FileTreeCompositeContent.Label label;
	
	public FileTreeComposite(Composite parent, int style) {
		super(parent, style);
		init();
	}

	private void init() {
		this.setLayout(new GridLayout(1, false));


	    // Create the tree viewer to display the file tree
	    setTreeViewer(new TreeViewer(this));
	    getTreeViewer().getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
	    getTreeViewer().setContentProvider(new FileTreeCompositeContent.Content());
	    this.label = new FileTreeCompositeContent.Label();
	    getTreeViewer().setLabelProvider(this.label);
	    getTreeViewer().setInput("root"); // pass a non-null that will be ignored

	}

	public void setData(StructureFileTreeCompositeData data) {
		this.label.setData(data);
	}
	
	public void setStructureBase(StructureBase base) {
		this.getTreeViewer().setInput(base);
	}
	
	public void setRootFile(String location) {
		File ufile = new File(location);
		getTreeViewer().setInput(ufile);
	}
	
	public File getSelectedFile() {
		ITreeSelection sel = (ITreeSelection) getTreeViewer().getSelection();
		Object obj = sel.getFirstElement();
		if (obj instanceof File) {
			File ufile = (File) sel.getFirstElement();
			return ufile;
		}
		return null;
	}
	
	public StructureBase getSelectedStructureBase() {
		ITreeSelection sel = (ITreeSelection) getTreeViewer().getSelection();
		Object obj = sel.getFirstElement();
		if (obj instanceof StructureBase) {
			return (StructureBase) obj;
		}
		return null;
	}

	public void setTreeViewer(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
	
}
