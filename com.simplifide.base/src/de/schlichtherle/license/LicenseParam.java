/*
 * LicenseParam.java
 *
 * Created on 8. Mai 2005, 03:18
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

import java.util.prefs.Preferences;

/**
 * Configures basic parameters required by the {@link LicenseManager}.
 *
 * @author Christian Schlichtherle
 */
public interface LicenseParam {

    /**
     * Returns the license manager's subject as a descriptive string
     *         - <tt>null</tt> is never returned.
     *
     * @return A string which must compare equal to the "subject" property
     *         of the {@link LicenseContent} JavaBean.
     *         Note that this is public information which may be displayed
     *         to the user in a wizard or some other form.
     */
    String getSubject();
    
    /**
     * Returns the preferences node where the encoded licence is stored
     * or should get stored.
     * This method may return <tt>null</tt> if the license manager is not
     * used to {@link LicenseManager#install(java.io.File)} a license or
     * to {@link LicenseManager#verify()} an installed license.
     * <p>
     * Note that the preferences node should be globally unique;
     * otherwise, another application could overwrite your license key!
     * Thus, it is recommended to follow Sun's guideline of creating globally
     * unique package names by prefixing them with your globally unique
     * Internet domain.
     * You should then put the main class of your application in this package
     * and return its user preferences node. This is because across platforms
     * you will normally only have write access to the user preferences.
     */
    Preferences getPreferences();

    /**
     * Returns the keystore configuration parameters for the license manager
     *         - <tt>null</tt> is never returned.
     */
    KeyStoreParam getKeyStoreParam();

    /**
     * Returns the cipher configuration parameters for the license manager
     *         - <tt>null</tt> is never returned.
     */
    CipherParam getCipherParam();    
}
