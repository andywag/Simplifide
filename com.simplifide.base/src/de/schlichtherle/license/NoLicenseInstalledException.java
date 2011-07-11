/*
 * NoLicenseInstalled.java
 *
 * Created on 18. März 2005, 18:40
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

import de.schlichtherle.util.ObfuscatedString;

/**
 * Thrown if and only if a license is to be verified using
 * {@link LicenseManager#verify()} and no license is installed.
 *
 * @author Christian Schlichtherle
 */
public class NoLicenseInstalledException extends java.lang.Exception {

    private static final String EXC_NO_LICENSE_INSTALLED = new ObfuscatedString(new long[] {
        0x4E3A1D690A6899DEL, 0xF4EB1546CB69D2C6L, 0x2A6262DBA2AC86DBL,
        0xC3C9B45280715D5L}).toString(); /* => "exc.noLicenseInstalled" */

    /**
     * Constructs an instance of <code>NoLicenseInstalled</code> for the
     * given licensing subject.
     *
     * @param subject The licensing subject as specified in
     *        {@link LicenseParam#getSubject()}
     *        - may <em>not</em> be <tt>null</tt>.
     */
    public NoLicenseInstalledException(String subject) {
        super(subject);
    }

    public String getLocalizedMessage() {
        return Resources.getString(EXC_NO_LICENSE_INSTALLED, super.getMessage());
    }
}
