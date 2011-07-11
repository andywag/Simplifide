/*
 * EnhancedTextFieldDocument.java
 *
 * Created on 15. Dezember 2004, 19:36
 */
/*
 * Copyright 2004-2006 Schlichtherle IT Services
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

package de.schlichtherle.swing.model;

import de.schlichtherle.swing.EnhancedDocument;

/**
 * @deprecated Use the class {@link EnhancedDocument} instead.
 *
 * @author  Christian Schlichtherle
 */
public class EnhancedTextFieldDocument extends EnhancedDocument {

    public EnhancedTextFieldDocument() {
    }

    public EnhancedTextFieldDocument(Content c) {
        super(c);
    }

    public EnhancedTextFieldDocument(String str) {
        super(str);
    }

    public void firePropertyChange(
            String propertyName,
            boolean oldValue,
            boolean newValue) {
        super.firePropertyChange(propertyName, oldValue, newValue);
    }
}