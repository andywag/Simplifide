/*
 * Resources.java
 *
 * Created on 20. Februar 2005, 14:35
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

package de.schlichtherle.license.ftp;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import de.schlichtherle.util.ObfuscatedString;

/**
 * Looks up the resources for this package in a Resource Bundle.
 * Provided for comfort.
 *
 * @author Christian Schlichtherle
 */
class Resources {

    private static final String CLASS_NAME = new ObfuscatedString(new long[] {
        0xED417927B5A22343L, 0xFCFE487B94539114L, 0xEB7159A762B47D70L,
        0xDDCF2474803823EAL, 0xAC1F276714EE71F1L, 0xE08BE958E3A83F4DL
    }).toString(); /* => "de.schlichtherle.license.ftp.Resources" */

    private static final ResourceBundle resources
            = ResourceBundle.getBundle(CLASS_NAME);

    /**
     * Looks up a string resource identified by <tt>key</tt> in
     * <tt>resources</tt>.
     */
    public static final String getString(String key) {
        return resources.getString(key);
    }

    /**
     * Looks up a string resource identified by <tt>key</tt> in
     * <tt>resources</tt> and formats it as a message using
     * <tt>MessageFormat.format</tt> with the given <tt>arguments</tt>.
     */
    public static final String getString(String key, Object[] arguments) {
        return MessageFormat.format(getString(key), arguments);
    }

    /**
     * Looks up a string resource identified by <tt>key</tt> in
     * <tt>resources</tt> and formats it as a message using
     * <tt>MessageFormat.format</tt> with the given singular <tt>argument</tt>.
     */
    public static final String getString(String key, Object argument) {
        return MessageFormat.format(getString(key), new Object[] { argument });
    }
    
    /** You cannot instantiate this class. */
    protected Resources() { }
}
