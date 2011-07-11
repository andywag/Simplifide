/*
 * AbstractKeyStoreParam.java
 *
 * Created on 9. August 2005, 08:17
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is a convenience class implementing the
 * {@link KeyStoreParam#getStream()} method.
 *
 * @author Christian Schlichtherle
 */
public abstract class AbstractKeyStoreParam implements KeyStoreParam {
    
    private final Class clazz;
    private final String resource;
    
    /**
     * Creates a new instance of AbstractKeyStoreParam which will look up
     * the given resource using the classloader of the given class when
     * calling {@link #getStream()}.
     */
    protected AbstractKeyStoreParam(Class clazz, String resource) {
        this.clazz = clazz;
        this.resource = resource;
    }
    
    /**
     * Looks up the resource provided to the constructor using the classloader
     * provided to the constructor and returns it as an {@link InputStream}.
     */
    public InputStream getStream() throws IOException {
        InputStream in = clazz.getResourceAsStream(resource);
        if (in == null)
            throw new FileNotFoundException(resource);
        return in;
    }
    
    /**
     * Returns <tt>true</tt> if and only if these key store parameters seem to
     * address the same key store entry as the given object.
     */
    public boolean equals(Object o) {
        if (!(o instanceof KeyStoreParam))
            return false;
        
        final AbstractKeyStoreParam aKSP = (AbstractKeyStoreParam) o;
        return clazz.getResource(resource).equals(
                aKSP.clazz.getResource(aKSP.resource))
            && getAlias().equals(aKSP.getAlias());
    }
}
