/*
 * PersistenceServiceException.java
 *
 * Created on 27. Januar 2005, 03:12
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
 * An instance of this class is thrown when storing or loading a persistent
 * object graph to or from an XML file has failed.
 * This exception is always instantiated with a valid cause and has no detail
 * message.
 * <p>
 * <b>Note:</b> Why is this class needed if the class of the cause could be
 * thrown instead? Because this class still indicates that something bad
 * happened when a persistent object has been tried to store or load. The
 * general class {@link Exception} does not indicate this.
 *
 * @author Christian Schlichtherle
 */
public class PersistenceServiceException extends Exception {

    /**
     * Constructs an instance of <code>PersistenceServiceException</code>
     * with the specified <tt>cause</tt>.
     *
     * @param cause The cause for this exception to be thrown.
     */
    public PersistenceServiceException(Throwable cause) {
        super(cause);
    }

}
