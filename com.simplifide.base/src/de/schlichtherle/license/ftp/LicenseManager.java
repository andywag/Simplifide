/*
 * LicenseManager.java
 *
 * Created on 10. Mai 2005, 10:55
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

import java.util.Calendar;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import de.schlichtherle.license.IllegalPasswordException;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseNotary;
import de.schlichtherle.license.NoLicenseInstalledException;
import de.schlichtherle.util.ObfuscatedString;

/**
 * Extends {@link de.schlichtherle.license.LicenseManager} in order to provide
 * additional functionality required to manage a free trial period (FTP).
 * <p>
 * This class is designed to be thread safe.
 *
 * @author Christian Schlichtherle
 */
public class LicenseManager extends de.schlichtherle.license.LicenseManager {

    private static final String FTP_KEYSTORE_PARAM = new ObfuscatedString(new long[] {
        0x5AC618555EC967B9L, 0xABE6B36EE9502E44L, 0x58814EE1E5E3CF8FL
    }).toString(); /* => "ftpKeyStoreParam" */

    private static final String EQUAL_KEY_STORE_PARAMS = new ObfuscatedString(new long[] {
        0x4C72769A851F67C3L, 0x40A67DFB8206B61BL, 0x412E3FE142BA378EL,
        0x68B2E25FA4BE7607L, 0x7A6AB29D2E2E5FDDL, 0x0432DC122188C63FL,
        0x3AAAFA7C2F900E82L, 0xE7676924E761DBA6L
    }).toString(); /* => "Equal key store parameters for regular and FTP licenses!" */

    private static final String FTP_DAYS = new ObfuscatedString(new long[] {
        0x73F69B7ABA25F620L, 0x40BA225C709D724EL
    }).toString(); /* => "ftpDays" */

    private LicenseNotary ftpNotary;

    private static final String CN_FTP_USER = CN + Resources.getString(
            new ObfuscatedString(new long[] {
        0xA54F04397F20F4A8L, 0xC56F605BF6AF0408L
    }).toString()); /* => "user" */
    
    /**
     * Creates a new License Manager.
     * <p>
     * <b>Warning:</b> The manager created by this constructor is <em>not</em>
     * valid and cannot be used unless {@link #setLicenseParam(LicenseParam)}
     * is called!
     */
    protected LicenseManager() {
    }
    
    /**
     * Creates a new License Manager.
     *
     * @param param The license configuration parameters
     *        - may <em>not</em> be <tt>null</tt>.
     *
     * @throws NullPointerException If the given parameter object does not
     *         obey the contract of its interface due to a <tt>null</tt>
     *         pointer.
     * @throws IllegalPasswordException If any password in the parameter object
     *         does not comply to the current policy.
     */
    public LicenseManager(LicenseParam param)
    throws  NullPointerException,
            IllegalPasswordException {
        setLicenseParam(param);
    }

    /**
     * Sets the license configuration parameters.
     * Calling this method resets the manager as if it had been newly created.
     * The implementation of this class asserts that the given parameter object
     * is an instance of {@link de.schlichtherle.license.ftp.LicenseParam} and
     * applies some plausibility checks to it to ensure that it adheres to
     * the contract of the parameter interfaces.
     *
     * @param param The license configuration parameters
     *        - may <em>not</em> be <tt>null</tt>.
     *
     * @throws NullPointerException If the given parameter object does not
     *         obey the contract of its interface due to a <tt>null</tt>
     *         pointer.
     * @throws IllegalPasswordException If any password in the parameter object
     *         does not comply to the current policy.
     */
    public synchronized void setLicenseParam(de.schlichtherle.license.LicenseParam param)
    throws  NullPointerException,
            IllegalPasswordException {
        // Check parameters to implement fail-fast behaviour.
        final LicenseParam licenseParam = (LicenseParam) param;
        final KeyStoreParam ftpKeyStoreParam = licenseParam.getFTPKeyStoreParam();
        if (ftpKeyStoreParam == null)
            throw new NullPointerException(FTP_KEYSTORE_PARAM);
        if (ftpKeyStoreParam.equals(licenseParam.getKeyStoreParam()))
            throw new IllegalArgumentException(EQUAL_KEY_STORE_PARAMS);
        final int ftpDays = licenseParam.getFTPDays();
        if (0 >= ftpDays || ftpDays > 365) // unreasonable
            throw new IllegalArgumentException(new ObfuscatedString(new long[] {
                0x8AC2FF8435527B06L, 0x3702F35A60398FC4L
            }).toString()); /* => "ftpDays" */
        
        super.setLicenseParam(licenseParam); // may throw more exceptions!
    }

    protected synchronized LicenseContent verify(final LicenseNotary notary)
    throws Exception {
        try {
            return super.verify(notary);
        } catch (Exception exc) {
            // Checking a regular license failed, now check for an FTP license.

            // Init.
            final LicenseParam param = (LicenseParam) getLicenseParam();
            final LicenseNotary ftpNotary = getFTPLicenseNotary();

            // Check FTP license key suspect.
            final byte[] key = getLicenseKey();
            if (key != null)
                return super.verify(ftpNotary);

            // No license key installed:
            // Check if the consumer is eligible for an FTP license.
            if (!param.isFTPEligible())
                throw new NoLicenseInstalledException(param.getSubject());
            
            // Create and install an FTP license key.
            LicenseContent content = param.createFTPLicenseContent();
            content.setNotAfter(ftpNotAfter(param.getFTPDays())); // enforce expire date
            content = install(create(content, ftpNotary), ftpNotary); // reassign because of clone
            param.removeFTPEligibility();
            param.ftpGranted(content);

            return content; // content of FTP license
        }
    }

    protected synchronized void initialize(final LicenseContent content) {
        if (content.getHolder() == null)
            content.setHolder(new X500Principal(CN_FTP_USER));
        super.initialize(content);
    }

    //
    // Various stuff.
    //

    /**
     * Returns a license notary configured to use the free trial period
     * keystore parameters contained in the current license parameters
     * - <tt>null</tt> is never returned.
     */
    protected synchronized LicenseNotary getFTPLicenseNotary()
    throws Exception {
        if (ftpNotary == null)
            ftpNotary = new LicenseNotary(
                    ((LicenseParam) getLicenseParam()).getFTPKeyStoreParam());
        
        return ftpNotary;
    }

    /**
     * Returns the date after which a free trial period license certificate
     * should expire, based on <tt>ftpDays</tt>.
     */
    protected Date ftpNotAfter(int ftpDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(midnight());
        cal.add(Calendar.DATE, ftpDays);
        
        return cal.getTime();
    }
}
