package com.simplifide.core.errorparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseSuite;

public class ErrorHolder {

	private List<ProblemMarkerInfo> errors = new ArrayList<ProblemMarkerInfo>();
	private CommandDefinition command;
	
	public ErrorHolder(CommandDefinition command) {
		this.command = command;
	}
	
	public void clearList() {
		errors.clear();
	}
	
	public void addError(ProblemMarkerInfo info) {
		this.errors.add(info);
	}
	
	public void writeErrors() {
		this.addExternalError();
	}
	
	private void addErrors() throws CoreException{
		for (ProblemMarkerInfo error : errors) {
			
			IFile resource  = (IFile) error.file;
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getContents()));
			
			String str  = "";
			Pos pos = null;
			try {
				
				StringBuilder build = new StringBuilder();
				int ch = reader.read();
				while (ch != -1) {
					build.append((char)ch);
					ch = reader.read();
				}
				String[] stra = build.toString().split("\n");
				if (error.lineNumber < stra.length){
					int len = 0;
					for (int i=0;i<error.lineNumber;i++) {
						pos = new Pos(len,len + stra[i].length());
						len += stra[i].length() + 1;	
					}
				}
				
				
			} catch (IOException e) {
				HardwareLog.logError(e);
			}
			
			if (pos==null) pos = new Pos(0,1);
			IMarker marker = resource.createMarker(this.getId());
			marker.setAttribute(IMarker.SEVERITY,error.severity);
			marker.setAttribute(IMarker.MESSAGE, error.description);
			marker.setAttribute(IMarker.LINE_NUMBER, error.lineNumber);

			marker.setAttribute(IMarker.CHAR_START,pos.start);
			marker.setAttribute(IMarker.CHAR_END, pos.stop);
			
			
			
		}
	}
	
	private String getId() {
		return "com.simplifide.core.externalMarker";
	}
	
	public void addExternalError() {

		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) {
					EclipseSuite suite = ActiveSuiteHolder.getDefault()
							.getSuite();
					IProject project = suite.getProject();
					try {
						suite.getProject().deleteMarkers(getId(), false,
								IResource.DEPTH_INFINITE);
						addErrors();
					} catch (CoreException e) {
						HardwareLog.logError(e);
					}

				}
			}, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}

	}
	
	public static class Pos {
		public int start;
		public int stop;
		public Pos(int start, int stop) {
			this.start = start;
			this.stop  = stop;
		}
	}
	
}
