/*
 * LicenseContentException.java
 *
 * Created on 27. Januar 2005, 09:31
 */
/*
 * Copyright 2005 Schlichtherle IT Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.schlichtherle.license;

import java.util.Date;

/**
 * Thrown if validating a license certificate fails.
 *
 * @author Christian Schlichtherle
 */
public class LicenseContentException extends java.lang.Exception {
    
	
	
	private int type;
	public Date date;
    /**
     * Constructs an instance of <tt>LicenseContentException</tt>
     * with the given <tt>resourceKey</tt> to lookup the localized detail
     * message with.
     *
     * @param resourceKey The key to use to lookup the localized detail
     *        message when {@link #getLocalizedMessage()} is called
     *        - may <em>not</em> be <tt>null</tt>.
     */
    public LicenseContentException(String resourceKey) {
        super(resourceKey);
    }

    public String getLocalizedMessage() {
        return Resources.getString(super.getMessage());
    }
    
    public static class Expired extends LicenseContentException {
    	
    	public Expired(String key, Date date) {
    		super(key);
    		this.date = date;
    	}
    }
    
    public static class NotValid extends LicenseContentException {
    	
    	public NotValid(String key, Date date) {
    		super(key);
    		this.date = date;
    	}
    }
    
}
