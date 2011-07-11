/*
 * XMLConstants.java
 *
 * Created on 30. Januar 2005, 11:41
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
 * Provides some constants used throughout this package.
 *
 * @author Christian Schlichtherle
 */
interface XMLConstants {
    
    /**
     * <tt>"UTF-8"</tt> - the character encoding used by <tt>XMLEncoder</tt>
     * and <tt>XMLDecoder</tt>.
     */
    String XML_CHARSET = "UTF-8";
    
    /**
     * 10KB - the default buffer size for buffered I/O.
     */
    int DEFAULT_BUFSIZE = 10 * 1024;
}
