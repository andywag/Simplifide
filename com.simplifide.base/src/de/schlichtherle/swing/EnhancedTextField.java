/*
 * EnhancedTextField.java
 *
 * Created on 7. Dezember 2005, 12:32
 */
/*
 * Copyright 2005-2006 Schlichtherle IT Services
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

package de.schlichtherle.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * Like its super class, but pays attention to the property <tt>editable</tt>
 * of the class {@link EnhancedDocument} when used as its document model
 * (which is the case by default).
 *
 * @author Christian Schlichtherle
 */
public class EnhancedTextField extends JTextField {

    private final PropertyChangeListener listener = new Listener();

    public EnhancedTextField() {
    }

    public EnhancedTextField(String text) {
        super(text);
    }

    public EnhancedTextField(int columns) {
        super(columns);
    }

    public EnhancedTextField(String text, int columns) {
        super(text, columns);
    }

    public EnhancedTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    /**
     * As a supplement to its super class implementation, this implementation
     * also applies the property change to its model if it is an instance of
     * {@link EnhancedDocument}.
     *
     * @see JTextField#setEditable
     */
    public void setEditable(final boolean newEditable) {
        final boolean oldEditable = isEditable();
        if (newEditable == oldEditable)
            return;

        super.setEditable(newEditable);

        final Document model = getDocument();
        if (model instanceof EnhancedComboBoxModel)
            ((EnhancedComboBoxModel) model).setEditable(newEditable);
    }

    /**
     * Creates the default implementation of the model
     * to be used at construction if one isn't explicitly 
     * given.
     * An instance of {@link EnhancedDocument} is returned by the
     * implementation in this class.
     *
     * @return The default model implementation.
     */
    protected Document createDefaultModel() {
        return new EnhancedDocument();
    }

    /**
     * As a supplement to its super class implementation, the implementation
     * in this class also inherits the properties from its model if it is
     * an instance of {@link EnhancedDocument}.
     *
     * @see JTextField#setDocument
     */
    public void setDocument(final Document newDocument) {
        final Document oldDocument = getDocument();
        if (oldDocument instanceof EnhancedDocument)
            ((EnhancedDocument) oldDocument).removePropertyChangeListener(
                    EnhancedDocument.PROPERTY_EDITABLE, listener);

        super.setDocument(newDocument);

        if (newDocument instanceof EnhancedDocument) {
            final EnhancedDocument edoc
                    = (EnhancedDocument) newDocument;
            edoc.addPropertyChangeListener(
                    EnhancedDocument.PROPERTY_EDITABLE, listener);
            setEditable(edoc.isEditable());
        }
    }

    private final class Listener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            setEditable(Boolean.TRUE.equals(evt.getNewValue()));
        }
    }
}
