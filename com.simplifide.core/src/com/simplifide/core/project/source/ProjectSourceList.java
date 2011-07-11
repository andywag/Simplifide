package com.simplifide.core.project.source;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.suitecontents.FileContentObject;
import com.simplifide.core.project.suitecontents.ProjectContentLoader;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.DesignFileBuilder;
import com.simplifide.core.util.FileUtility;

public class ProjectSourceList extends ProjectSourceFolderTop {

    public static final String SOURCE_LOCATION = "Source.xml";

	private UniqueList<DesignFile> sources = new UniqueList<DesignFile>();
	private List<FileContentObject> files;
	
	public ProjectSourceList(EclipseBaseProject project,
			ReferenceItem<? extends CoreProjectSuite> suiteRef,
			List<FileContentObject> files) {
		super(project, suiteRef);
		this.files = files;
	}

	private void addDesignFileInternal(DesignFile dfile) {
		if (dfile != null) {
			((DesignFileBuilder)dfile.getBuilder()).attachProject(this.getProject());
			dfile.setProjectRef(this.getProject().createReferenceItem());
			sources.addObject(dfile.createReferenceItem());
		}
	}
	
	public static List<FileContentObject> getFixedFiles(EclipseBaseProject project) {
		IFolder base = project.getBaseFolder();
		boolean exists = base.exists();
		
		IFile path = project.getBaseFolder().getFile(SOURCE_LOCATION);
		if (path != null && path.getLocation() != null) {
			File xmlFile = path.getLocation().toFile();
			List<FileContentObject> files = ProjectContentLoader.loadContents(xmlFile);
			return files;
		}
		return new ArrayList<FileContentObject>();
	}
	
	@Override
	public void loadFiles() {
		EclipseSuite suite = (EclipseSuite) this.getSuiteReference().getObject();
		URI suiteURI = suite.getProject().getLocationURI();
		for (FileContentObject file : this.files) {
			if (file != null) {
				String location = file.getLocation();
				File ufile = new File(location);	

				if (ufile.isAbsolute()) {
					URI uri = ufile.toURI();
					
					URI rel = suiteURI.relativize(uri);
					if (!rel.equals(uri)) { // Relative Position from Workspace
						DesignFile dfile = LocationOperations.getDesignFile(uri);
						this.addDesignFileInternal(dfile);
					}
					else { // Not in Workspace Need to Create Link
						IFolder folder = this.getProject().getDesignIFolder();
						IFile nfile = folder.getFile(ufile.getName());
						if (!nfile.exists()) FileUtility.createFileLink(uri, nfile);
						DesignFile dfile = LocationOperations.getDesignFile(nfile.getLocationURI());
						this.addDesignFileInternal(dfile);
					}

				}
				else {
					IFile ifile = this.getProject().getBaseFolder().getFile(location);
					DesignFile dfile = LocationOperations.getDesignFile(ifile.getLocationURI());
					this.addDesignFileInternal(dfile);				
				}
			}
			
		}
	}
	
	@Override
	public void build(int kind, IProgressMonitor monitor) {
		for (ReferenceItem<? extends DesignFile> dfileR : this.sources.getGenericSelfList()) {
			dfileR.getObject().getBuilder().build(kind, monitor);
		}
		
	}

	@Override
	public void convertContextList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		for (ReferenceItem<? extends DesignFile> sfile : sources.getGenericSelfList()) {
			if (sfile.getObject() != null) {
				sfile.getObject().getCompileInfo().convertContextList(suiteRef);
			}
		}
		
	}

	@Override
	public UniqueList<DesignFile> createCompileList() {
		UniqueList<DesignFile> dlist = new UniqueList<DesignFile>();
		for (DesignFile dfile : this.sources.getRealSelfList()) {
			if (!dfile.getCompileInfo().isFinished()) {
				dlist.addAll(dfile.getCompileInfo().createCompileList(this.getSuiteReference()));
			}
		}
		return dlist;
	}

	

	@Override
	public UniqueList<DesignFile> getSourceList() {
		return this.sources;
	}


	public void setSources(UniqueList<DesignFile> sources) {
		this.sources = sources;
	}

	public UniqueList<DesignFile> getSources() {
		return sources;
	}

	
}
