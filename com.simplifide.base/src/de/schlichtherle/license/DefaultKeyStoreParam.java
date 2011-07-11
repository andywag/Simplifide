/*
 * DefaultKeyStoreParam.java
 *
 * Created on 9. August 2005, 12:56
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
 * This is a convenience class implementing the {@link KeyStoreParam} interface.
 *
 * @author Christian Schlichtherle
 */
public class DefaultKeyStoreParam extends AbstractKeyStoreParam {
    
    private final String alias, storePwd, keyPwd;
    
    /**
     * Creates a new instance of AbstractKeyStoreParam.
     * 
     * @param clazz Used to retrieve the classloader required to load the
     *        keystore as a resource.
     * @param resource The resource identifier for the keystore
     *        to be returned by {@link #getStream()}.
     * @param alias The alias for the key entry in the key store
     *        to be returned by {@link #getAlias()}.
     * @param storePwd The key store password
     *        to be returned by {@link #getStorePwd()}.
     * @param keyPwd The password for the private key in the key store entry
     *        to be returned by {@link #getStorePwd()}.
     */
    public DefaultKeyStoreParam(
            Class clazz,
            String resource,
            String alias,
            String storePwd,
            String keyPwd) {
        super(clazz, resource);
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    public String getAlias() {
        return alias;
    }

    public String getStorePwd() {
        return storePwd;
    }

    public String getKeyPwd() {
        return keyPwd;
    }
}
