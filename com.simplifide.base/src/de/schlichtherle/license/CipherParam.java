/*
 * CipherParam.java
 *
 * Created on 8. Mai 2005, 20:49
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
 * Configures parameters for the PKCS-5 algorithm used to encrypt/decrypt a
 * compressed, signed {@link de.schlichtherle.xml.GenericCertificate}.
 * This interface is used by the {@link LicenseManager} to perform the
 * encryption/decyrption of license keys.
 * <p>
 * <b>Note:</b> To protect your application against reverse engineering
 * and thus reduce the risk to compromise the privacy of your passwords,
 * it is highly recommended to obfuscate all JAR files which contain class
 * files that implement this interface with a tool like
 * <a href="http://proguard.sourceforge.net/">ProGuard</a>.
 *
 * @author Christian Schlichtherle
 */
public interface CipherParam {

    /**
     * Returns the password used to generate a secret key for
     * encryption/decryption of license keys.
     * - <tt>null</tt> is never returned.
     * <p>
     * Note that the {@link Policy} class provides additional constraints
     * for the returned password.
     *
     * @see Policy#checkPwd(String)
     */
    String getKeyPwd();
}
