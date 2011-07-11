/*
 * AbstractLicenseParam.java
 *
 * Created on 9. August 2005, 17:19
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

import java.util.prefs.Preferences;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.util.ObfuscatedString;

/**
 * This is a convenience class implementing some methods of the
 * {@link LicenseParam} interface.
 *
 * @author Christian Schlichtherle
 */
public abstract class AbstractLicenseParam
        extends DefaultLicenseParam
        implements LicenseParam {

    private static final String LICENSE_CONTENT_INFO
            = Resources.getString(new ObfuscatedString(new long[] {
        0x30D3DC32CB325528L, 0x49530D107781940AL, 0xAD7049DAEA85009EL,
        0x4AF2194E755E89CDL
    }).toString()); /* => "licenseContent.info" */


    private final KeyStoreParam ftpKeyStoreParam;
    private final int ftpDays;
    
    /**
     * Creates a new instance of AbstractLicenseParam.
     * 
     * @param subject The licensing subject
     *        to be returned by {@link #getSubject()}.
     * @param preferences The preferences node used to store the license key
     *        to be returned by {@link #getPreferences()}.
     * @param keyStoreParam The key store parameters
     *        to be returned by {@link #getKeyStoreParam()}.
     * @param cipherParam The cipher parameters
     *        to be returned by {@link #getCipherParam()}.
     */
    protected AbstractLicenseParam(
            String subject,
            Preferences preferences,
            KeyStoreParam keyStoreParam,
            KeyStoreParam ftpKeyStoreParam,
            int ftpDays,
            CipherParam cipherParam) {
        super(subject, preferences, keyStoreParam, cipherParam);
        this.ftpKeyStoreParam = ftpKeyStoreParam;
        this.ftpDays = ftpDays;
    }
    
    public LicenseContent createFTPLicenseContent() {
        LicenseContent content = new LicenseContent();
        content.setInfo(LICENSE_CONTENT_INFO);
        return content;
    }

    public KeyStoreParam getFTPKeyStoreParam() {
        return ftpKeyStoreParam;
    }

    public int getFTPDays() {
        return ftpDays;
    }
}
