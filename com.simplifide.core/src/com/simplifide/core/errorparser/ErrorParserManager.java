/*******************************************************************************
 *  Copyright (c) 2005, 2010 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     IBM Corporation - initial API and implementation
 *     Sergey Prigogin (Google)
 *     James Blackburn (Broadcom) - Bug 247838
 *     Andrew Gvozdev (Quoin Inc)
 *     Dmitry Kozlov (CodeSourcery) - Build error highlighting and navigation  
 *******************************************************************************/
package com.simplifide.core.errorparser;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.URIUtil;
import org.osgi.service.prefs.BackingStoreException;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.SourceLocationMap;

/**
 * The purpose of ErrorParserManager is to delegate the work of error parsing 
 * build output to {@link IErrorParser}s, assist in finding {@link IResource}s, and
 * help create appropriate error/warning/info markers to be displayed
 * by the Problems view.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ErrorParserManager {
	/**
	 * The list of error parsers stored in .project for 3.X projects
	 * as key/value pair with key="org.eclipse.cdt.core.errorOutputParser"
	 * @deprecated since CDT 4.0.
	 */
	
	/**
	 * Delimiter for error parsers presented in one string.
	 * @since 5.2
	 */
	public final static char ERROR_PARSER_DELIMITER = ';';

	
	private int lineCounter=0;

	//private final IMarkerGenerator fMarkerGenerator;

	private Map<String, IErrorParser[]> fErrorParsers = new HashMap<String, IErrorParser[]>();

	

	private String previousLine;
	
	private final StringBuilder currentLine = new StringBuilder();

	
	private final IProject fProject;
	private CommandDefinition command;
	private ErrorHolder errorHolder;
	
	
	public ErrorParserManager(IProject project,CommandDefinition command) {
		this.fProject = project;
		this.command = command;
		IErrorParser parser = command.getParser();
		this.fErrorParsers.put("parser", new IErrorParser[] {parser});
		this.setErrorHolder(new ErrorHolder(command));
		this.getErrorHolder().clearList();
	}
		
	public IProject getProject() {
		return this.fProject;
	}
	
	/**
	 * Parses the input and tries to generate error or warning markers
	 */
	public void processLine(String line) {
		String lineTrimmed = line.trim();
		lineCounter++;

		ProblemMarkerInfo marker=null;
		
outer:
		for (IErrorParser[] parsers : fErrorParsers.values()) {
			for (IErrorParser parser : parsers) {
				IErrorParser curr = parser;
				if (parser instanceof ErrorParserNamedWrapper) {
					curr = ((ErrorParserNamedWrapper)parser).getErrorParser();
				}
				int types = IErrorParser2.NONE;
				if (curr instanceof IErrorParser2) {
					types = ((IErrorParser2) curr).getProcessLineBehaviour();
				}
				if ((types & IErrorParser2.KEEP_LONGLINES) == 0) {
					// long lines are not given to parsers, unless it wants it
					if (lineTrimmed.length() > 1000)
						continue;
				}
				// standard behavior (pre 5.1) is to trim the line
				String lineToParse = lineTrimmed;
				if ((types & IErrorParser2.KEEP_UNTRIMMED) !=0 ) {
					// untrimmed lines
					lineToParse = line;
				}

				boolean consume = false;
				// Protect against rough parsers who may accidentally
				// throw an exception on a line they can't handle.
				// It should not stop parsing of the rest of output.
				try {
					consume = curr.processLine(lineToParse, this);
				} catch (Exception e){
					String id = "";  //$NON-NLS-1$
					if (parser instanceof IErrorParserNamed)
						id = ((IErrorParserNamed)parser).getId();
					@SuppressWarnings("nls")
					String message = "Errorparser " + id + " failed parsing line [" + lineToParse + "]";
					HardwareLog.logError(message, e);
				} 

				if (consume)
					break outer;
			}
		}
		
	}
	


	

	/**
	 * Returns the file with the given (partial) location if that file can be uniquely identified.
	 * Otherwise returns {@code null}.
	 * <br><br>
	 * The passed in String 'partialLoc' is treated as a partial filesystem location for the
	 * resource. Resolution is attempted with the following precedence:<br>
	 *           If partialLoc is an absolute fs location:<br>
	 *            - Resolve it to an IFile in the Project<br>
	 *            - Resolve it to an IFile in the Workspace<br>
	 *           If partialLoc is a relative path:<br>
	 *            - Resolve it relative to the Current Working Directory<br>
	 *            - Resolve just the segments provided<br>
	 *
	 * @param partialLoc - file name could be plain file name, absolute path or partial path
	 * @return - file in the workspace or {@code null}.
	 */
	public IFile findFileName(String partialLoc) {
		
		// To be able to parse Windows paths on Linux systems, see bug 263977
		IPath path = new Path(partialLoc.replace('\\', IPath.SEPARATOR));
		// Try to find exact match. If path isnot absolute - searching in working directory.
		//File file = findFileInWorkspace(path);
		//LocationOperations.getSourceFile(file.getLocationURI());
		// Try to find best match considering known partial path
		String[] names = partialLoc.split("/");
		String fname = names[names.length - 1];
		IFile file = null;
		if (file==null) {
			SourceFile sfile = SourceLocationMap.getInstance().findPartialFile(fname);
			if (sfile != null) return (IFile) sfile.getResource();
		}

		return file;
	}	
	

	/**
	 * Add marker to the list of error markers.
	 * 
	 * @param file - resource to add the new marker.
	 * @param lineNumber - line number of the error.
	 * @param desc - description of the error.
	 * @param severity - severity of the error.
	 * @param varName - variable name.
	 */
	public void generateMarker(IResource file, int lineNumber, String desc, int severity, String varName) {
		generateExternalMarker(file, lineNumber, desc, severity, varName, null);
	}

	/**
	 * Add marker to the list of error markers.
	 * 
	 * @param file - resource to add the new marker.
	 * @param lineNumber - line number of the error.
	 * @param desc - description of the error.
	 * @param severity - severity of the error, one of
	 *        <br>{@link IMarkerGenerator#SEVERITY_INFO},
	 *        <br>{@link IMarkerGenerator#SEVERITY_WARNING},
	 *        <br>{@link IMarkerGenerator#SEVERITY_ERROR_RESOURCE},
	 *        <br>{@link IMarkerGenerator#SEVERITY_ERROR_BUILD}
	 * @param varName - variable name.
	 * @param externalPath - external path pointing to a file outside the workspace.
	 */
	public void generateExternalMarker(IResource file, int lineNumber, String desc, int severity, String varName, IPath externalPath) {
		ProblemMarkerInfo problemMarkerInfo = new ProblemMarkerInfo(file, lineNumber, desc, severity, varName, externalPath);
		this.getErrorHolder().addError(problemMarkerInfo);
		/*fMarkerGenerator.addMarker(problemMarkerInfo);
		if (severity == IMarkerGenerator.SEVERITY_ERROR_RESOURCE)
			hasErrors = true;
		*/
	}

	

	public static int getIndexOfParser(String id) {
		int index = 0;
		for (String str : getErrorParserAvailableIds()) {
			if (id.equalsIgnoreCase(str)) return index;
			index++;
		}
		return -1;
	}
	
	
	/**
	 * Set and store in workspace area user defined error parsers.
	 *
	 * @param errorParsers - array of user defined error parsers
	 * @throws CoreException in case of problems
	 * @since 5.2
	 */
	public static void setUserDefinedErrorParsers(IErrorParserNamed[] errorParsers) throws CoreException {
		ErrorParserExtensionManager.setUserDefinedErrorParsers(errorParsers);
	}

	/**
	 * @return available error parsers IDs which include contributed through extension + user defined ones
	 * from workspace
	 * @since 5.2
	 */
	public static String[] getErrorParserAvailableIds() {
		return ErrorParserExtensionManager.getErrorParserAvailableIds();
	}

	/**
	 * @return IDs of error parsers contributed through error parser extension point.
	 * @since 5.2
	 */
	public static String[] getErrorParserExtensionIds() {
		return ErrorParserExtensionManager.getErrorParserExtensionIds();
	}

	/**
	 * Set and store default error parsers IDs to be used if error parser list is empty.
	 *
	 * @param ids - default error parsers IDs
	 * @throws BackingStoreException in case of problem with storing
	 * @since 5.2
	 */
	public static void setDefaultErrorParserIds(String[] ids) throws BackingStoreException {
		ErrorParserExtensionManager.setDefaultErrorParserIds(ids);
	}

	/**
	 * @return default error parsers IDs to be used if error parser list is empty.
	 * @since 5.2
	 */
	public static String[] getDefaultErrorParserIds() {
		return ErrorParserExtensionManager.getDefaultErrorParserIds();
	}

	/**
	 * @param id - ID of error parser
	 * @return cloned copy of error parser. Note that {@link ErrorParserNamedWrapper} returns
	 * shallow copy with the same instance of underlying error parser.
	 * @since 5.2
	 */
	public static IErrorParserNamed getErrorParserCopy(String id) {
		return ErrorParserExtensionManager.getErrorParserCopy(id);
	}

	/**
	 * @param ids - array of error parser IDs
	 * @return error parser IDs delimited with error parser delimiter ";"
	 * @since 5.2
	 */
	public static String toDelimitedString(String[] ids) {
		String result=""; //$NON-NLS-1$
		for (String id : ids) {
			if (result.length()==0) {
				result = id;
			} else {
				result += ERROR_PARSER_DELIMITER + id;
			}
		}
		return result;
	}

	public void setErrorHolder(ErrorHolder errorHolder) {
		this.errorHolder = errorHolder;
	}

	public ErrorHolder getErrorHolder() {
		return errorHolder;
	}
}
