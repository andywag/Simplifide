/*
 * GenericCertificateException.java
 *
 * Created on 29. Januar 2005, 12:17
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
 * An instance of this class is thrown if the integrity of a
 * {@link GenericCertificate} has been detected to be compromised.
 *
 * @author Christian Schlichtherle
 */
public class GenericCertificateIntegrityException
        extends java.security.GeneralSecurityException {
}