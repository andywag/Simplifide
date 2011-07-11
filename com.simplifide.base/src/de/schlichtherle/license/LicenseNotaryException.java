/*
 * LicenseNotaryException.java
 *
 * Created on 17. August 2005, 13:21
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

/**
 * An instance of this class is thrown to indicate that the license notary
 * could not access the private or public key in a key store due to
 * insufficient or incorrect parameters in the corresponding
 * <tt>KeyStoreParam</tt> instance.
 *
 * @author Christian Schlichtherle
 */
public class LicenseNotaryException
        extends java.security.GeneralSecurityException {

    /** The alias of the entry in the key store. */
    private String alias;
    
    /**
     * Constructs an instance of <tt>LicenseNotaryException</tt>
     * with the given <tt>resourceKey</tt> to lookup the localized detail
     * message with and parameterize it with the given <tt>alias</tt>.
     *
     * @param resourceKey The key to use to lookup the localized detail
     *        message when {@link #getLocalizedMessage()} is called
     *        - may <em>not</em> be <tt>null</tt>.
     * @param alias The alias of the entry in the key store
     *        - may be <tt>null</tt>.
     */
    public LicenseNotaryException(String resourceKey, String alias) {
        super(resourceKey);
        this.alias = alias;
    }

    public String getLocalizedMessage() {
        return Resources.getString(super.getMessage(), alias);
    }
}
