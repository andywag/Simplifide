/*
 * KeyStoreParam.java
 *
 * Created on 8. Mai 2005, 02:34
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

import java.io.IOException;
import java.io.InputStream;

/**
 * Configures access parameters for a {@link java.security.KeyStore} which
 * holds the private and public keys to sign and verify a
 * {@link de.schlichtherle.xml.GenericCertificate} by the {@link LicenseNotary}.
 * All methods in this class should return constant references when called
 * multiple times because the return values might get cached.
 * <p>
 * <b>Note:</b> To protect your application against reverse engineering
 * and thus reduce the risk to compromise the privacy of your passwords,
 * it is highly recommended to obfuscate all JAR files which contain class
 * files that implement this interface with a tool like e.g.
 * <a href="http://proguard.sourceforge.net/">ProGuard</a>.
 *
 * @author Christian Schlichtherle
 */
public interface KeyStoreParam {

    /**
     * Returns the keystore as an input stream
     * - <tt>null</tt> is never returned.
     *
     * @throws IOException If the key store cannot get opened.
     */
    InputStream getStream() throws IOException;

    /**
     * Returns the alias for the key entry in the key store
     * - <tt>null</tt> is never returned.
     */
    String getAlias();

    /**
     * Returns the password for the keystore
     * - <tt>null</tt> is never returned.
     * <p>
     * Note that the {@link Policy} class provides additional constraints
     * for the returned password.
     *
     * @see Policy#checkPwd(String)
     */
    String getStorePwd();

    /**
     * Returns the password for the private key in the keystore.
     * This password is <em>only</em> required to sign a
     * <tt>GenericCertificate</tt> and must be <tt>null</tt> in your
     * client application (the one which just needs to install or verify
     * license certificates rather than creating them).
     * The {@link LicenseNotary} class may check that there is no private
     * key in the Java key store if this password is <tt>null</tt>
     * <em>and</em> vice versa and throw an exception if you don't
     * adhere to this contract.
     * <p>
     * Note that the {@link Policy} class provides additional constraints
     * for the returned password.
     *
     * @see Policy#checkPwd(String)
     */
    String getKeyPwd();
}
