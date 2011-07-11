/*
 * DefaultCipherParam.java
 *
 * Created on 9. August 2005, 13:58
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
 * This is a convenience class implementing the {@link CipherParam} interface.
 *
 * @author Christian Schlichtherle
 */
public class DefaultCipherParam implements CipherParam {
    
    private final String keyPwd;
    
    /**
     * Creates a new instance of DefaultCipherParam using the given password
     * to be returned by {@link #getKeyPwd()}.
     */
    public DefaultCipherParam(String keyPwd) {
        this.keyPwd = keyPwd;
    }

    public String getKeyPwd() {
        return keyPwd;
    }
}
