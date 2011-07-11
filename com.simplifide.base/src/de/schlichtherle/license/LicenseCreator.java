/*
 * LicenseCreator.java
 *
 * Created on 18. August 2005, 02:20
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

import java.rmi.Remote;

/**
 * Provides remote license creation services.
 *
 * @author Christian Schlichtherle
 */
public interface LicenseCreator extends Remote {

    /**
     * Initializes and validates the license content, creates a new signed
     * license certificate for it and compresses, encrypts and returns it
     * as a license key.
     * <p>
     * As a side effect, the given license <tt>content</tt> may be initialized
     * with some reasonable defaults unless the respective properties have
     * already been set.
     *
     * @param content The license content
     *        - may <em>not</em> be <tt>null</tt>.
     *
     * @return The license key
     *         - <tt>null</tt> is never returned.
     *
     * @throws Exception An instance of a subclass of this class for various
     *         reasons.
     *         Note that you should always use
     *         {@link Throwable#getLocalizedMessage()} to get a (possibly
     *         localized) meaningful detail message.
     */
    byte[] create(LicenseContent content) throws Exception;
}
