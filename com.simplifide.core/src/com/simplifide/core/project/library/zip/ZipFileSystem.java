/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.simplifide.core.project.library.zip;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;

/**
 * 
 */
public class ZipFileSystem extends FileSystem {
	/**
	 * Scheme constant (value "zip") indicating the zip file system scheme.
	 */
	public static final String SCHEME_ZIP = "har"; //$NON-NLS-1$

	
	
	public IFileStore getStore(URI uri) {
		if (SCHEME_ZIP.equals(uri.getScheme())) {
		
			try {
				String query = uri.getQuery();
				URI newURI = new URI(query);
				ZipFileStorage store = ZipFileStorageMap.getInstance().getStore(newURI);
				return store.getFileStore(uri.getPath());
			} catch (URISyntaxException e) {
				//ignore and fall through below
			}
		}
		return EFS.getNullFileSystem().getStore(uri);
	}
}

