/*
 * GenericCertificateNotLockedException.java
 *
 * Created on 17. August 2005, 11:50
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

package de.schlichtherle.xml;

/**
 * An instance of this class is thrown if a {@link GenericCertificate}'s
 * content is tried to be retrieved but the certificate has neither signed
 * nor verified any content.
 *
 * @author Christian Schlichtherle
 */
public class GenericCertificateNotLockedException
        extends IllegalStateException {
}
