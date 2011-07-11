/*
 * DefaultLicenseParam.java
 *
 * Created on 9. August 2005, 14:03
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
 * This is a convenience class implementing the {@link LicenseParam} interface.
 * 
 * @author Christian Schlichtherle
 */
public class DefaultLicenseParam implements LicenseParam {
    
    private final String subject;
    private final Preferences preferences;
    private final KeyStoreParam keyStoreParam;
    private final CipherParam cipherParam;
    
    /**
     * Creates a new instance of DefaultLicenseParam.
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
    public DefaultLicenseParam(
            String subject,
            Preferences preferences,
            KeyStoreParam keyStoreParam,
            CipherParam cipherParam) {
        this.subject = subject;
        this.preferences = preferences;
        this.keyStoreParam = keyStoreParam;
        this.cipherParam = cipherParam;
    }

    public String getSubject() {
        return subject;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public KeyStoreParam getKeyStoreParam() {
        return keyStoreParam;
    }

    public CipherParam getCipherParam() {
        return cipherParam;
    }
}
