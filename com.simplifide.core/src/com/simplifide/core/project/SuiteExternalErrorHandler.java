/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.error.external.ExternalError;
import com.simplifide.core.error.external.ExternalFileError;
import com.simplifide.core.error.external.ExternalSuiteError;
import com.simplifide.core.error.external.ExternalSuiteErrorHolder;
import com.simplifide.core.source.SourceLocationHandler;
import com.simplifide.core.util.FileUtility;

public class SuiteExternalErrorHandler {

	public static String MARKER_ID = "com.simplifide.core.externalMarker";
	private EclipseSuite suite;


	public SuiteExternalErrorHandler(EclipseSuite suite) {
		this.suite = suite;
	}

	private int[] getLineStarts(IFile ufile, ArrayList<ExternalError> errors) {
		if (errors.size() == 0) return null;
		int cline = 0, ccol = 0, cpos = 0;
		int[] outarr = new int[2*errors.size()];
		int index = 0;
		InputStream stream;
		try {
			stream = ufile.getContents();
			NodePosition lastPosition = null;
			for (ExternalError error : errors) {
				NodePosition pos = error.getPosition();
				if (lastPosition != null) {
					if (pos.getStartLine() == lastPosition.getStartLine()) {
						outarr[index]   = outarr[index-2];
						outarr[index+1] = outarr[index-1];
						index = index + 2;
						lastPosition = pos;
						continue;
					}
				}
				while (cline < pos.getStartLine() || ccol <= pos.getStartCol()) {
					char uchar = (char) stream.read();
					if (uchar == '\n') {
						cline++;
						ccol = 0;
					}
					if (uchar == '\t') {
						ccol+= 4;
					}
					cpos++;
					ccol++;
				}
				outarr[index] = cpos;
				if (pos.getLength() > 0) {
					outarr[index +1] = cpos + pos.getLength();
				}
				else {
					char uchar;
					
					while ( (uchar = (char) stream.read()) != '\n') {
						if (stream.available() <= 0) {
							outarr[outarr.length-1] = outarr[outarr.length-2] + 1;
							return outarr;
						}
						if (uchar == '\t') {
							ccol+= 4;
						}
						cpos++;
						ccol++;
					}
					cpos++;
					cline++;
					outarr[index+1] = cpos;
				}
				lastPosition = pos;
				index = index + 2;
				
			}

			

			stream.close();
		} catch (CoreException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		
		
		return outarr;

	}

	private void addFileErrors(IFile resource, ArrayList<ExternalError> errors) throws CoreException {
		//final String MarkerID = CoreActivator.PLUGIN_ID + ".syntaxMarker";
		
		if (resource != null) {
			if (!resource.exists()) return;
			int[] spos = this.getLineStarts(resource,errors);
			int index = 0;
			for (ExternalError error : errors) {
				NodePosition pos = error.getPosition();
				IMarker marker = resource.createMarker(MARKER_ID);
				marker.setAttribute(IMarker.SEVERITY,error.getSeverity());
				marker.setAttribute(IMarker.MESSAGE, error.getMessage());
				marker.setAttribute(IMarker.LINE_NUMBER, pos.getStartLine());

				marker.setAttribute(IMarker.CHAR_START,spos[index]);
				marker.setAttribute(IMarker.CHAR_END, spos[index+1]);
				index = index + 2;
			}
		}
	
	}

	
	
	private void addErrors(ExternalSuiteErrorHolder errorHolder) throws CoreException{
		for (ExternalSuiteError errorList : errorHolder.getErrorList()) {
			for (ExternalFileError error : errorList.getErrorList()) {
				String fname = error.getFileName();
				IFile ufile = FileUtility.getResourceforPath(fname);
				if (ufile == null) {
					SourceFile sf = SourceLocationHandler.getInstance().getSimpleFile(fname);
					if (sf != null) {
						ufile = (IFile) sf.getResource();
					}
				}
				this.addFileErrors(ufile, error.getErrorList());
			}
		}
	}

	public void addExternalError(final ExternalSuiteErrorHolder errorList) {


		try {
			ResourcesPlugin.getWorkspace().run(
					new IWorkspaceRunnable() {
						public void run(IProgressMonitor monitor) {

							try {
								suite.getProject().deleteMarkers(MARKER_ID, false, IResource.DEPTH_INFINITE);
								addErrors(errorList);
							} catch (CoreException e) {
								HardwareLog.logError(e);
							}


						}
					},null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}



	}




}
