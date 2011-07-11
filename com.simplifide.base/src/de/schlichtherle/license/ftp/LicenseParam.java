/*
 * LicenseParam.java
 *
 * Created on 10. Mai 2005, 10:58
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

import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;

/**
 * Extends {@link de.schlichtherle.license.LicenseParam} in order to provide
 * additional parameters required to manage a free trial period (FTP).
 *
 * @author Christian Schlichtherle
 */
public interface LicenseParam extends de.schlichtherle.license.LicenseParam {

    /**
     * Returns the keystore configuration parameters for the free trial period
     * license.
     * The <tt>KeyStoreParam</tt> instance returned by this call must not
     * <tt>equal()</tt> the one returned by {@link #getKeyStoreParam()}.
     */
    KeyStoreParam getFTPKeyStoreParam();

    /**
     * Returns the duration of the free trial period in days.
     * Must be greater than 0 and less than a year.
     */
    int getFTPDays();

    /**
     * Returns whether or not the license consumer is eligible for a
     * free trial period.
     */
    boolean isFTPEligible();

    /**
     * Returns a newly created and initialized license content suitable for a
     * free trial period license - <tt>null</tt> is never returned.
     * The subject and the expire date do not need to be initialized.
     * This will happen outside of this method.
     */
    LicenseContent createFTPLicenseContent();

    /**
     * Removes the license consumer's eligibility for another free trial period.
     * This method is called if and only if a free trial period license has
     * just been created.
     */
    void removeFTPEligibility();
    
    /**
     * This method is called whenever a free trial period license has been
     * granted.
     * Note that you should not modify the given license content
     * - it may adversely affect functionality provided in future.
     *
     * @param content A clone of the license content that has been
     *        automatically created
     *        - may not be <tt>null</tt>.
     */
    void ftpGranted(LicenseContent content);
}
