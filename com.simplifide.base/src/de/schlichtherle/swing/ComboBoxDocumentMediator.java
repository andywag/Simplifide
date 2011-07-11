/*
 * ComboBoxDocumentMediator.java
 *
 * Created on 31. Juli 2006, 19:38
 */
/*
 * Copyright 2006 Schlichtherle IT Services
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
import java.io.Serializable;

import javax.swing.ComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * A control which mediates between a {@link ComboBoxModel} and a
 * {@link Document}.
 * This allows to have the document text updated whenever the current
 * selection of the comboBoxModel box model changes and vice versa.
 * <p>
 * This class is most useful if used with an {@link EnhancedComboBoxModel}
 * and an {@link EnhancedDocument}, in which case the document is also made
 * editable/ineditable whenever the comboBoxModel box is enabled/disabled
 * and vice versa.
 * <p>
 * This class is designed to be thread safe!
 * 
 * @author Christian Schlichtherle
 * @since TrueLicense 1.28
 */
public class ComboBoxDocumentMediator implements Serializable {

    private final Listener listener = new Listener();
    private Document document;
    private ComboBoxModel comboBoxModel;

    /**
     * Used to inhibit mutual recursive event firing.
     */
    private transient boolean recursion;

    public synchronized Document getDocument() {
        return document;
    }
    
    public synchronized void setDocument(final Document newDoc) {
        final Document oldDoc = getDocument();
        if (newDoc == oldDoc)
            return;

        if (oldDoc != null) {
            oldDoc.removeDocumentListener(listener);
            if (oldDoc instanceof EnhancedDocument)
                ((EnhancedDocument) oldDoc).removePropertyChangeListener(
                        EnhancedDocument.PROPERTY_EDITABLE, listener);
        }

        document = newDoc;

        if (newDoc != null) {
            newDoc.addDocumentListener(listener);
            documentUpdated();
            if (newDoc instanceof EnhancedDocument) {
                ((EnhancedDocument) newDoc).addPropertyChangeListener(
                        EnhancedDocument.PROPERTY_EDITABLE, listener);
                documentEditableChanged();
            }
        }
    }
    
    public synchronized ComboBoxModel getComboBoxModel() {
        return comboBoxModel;
    }
    
    public synchronized void setComboBoxModel(final ComboBoxModel newCBM) {
        final ComboBoxModel oldCBM = getComboBoxModel();
        if (newCBM == oldCBM)
            return;

        if (oldCBM != null) {
            oldCBM.removeListDataListener(listener);
            if (oldCBM instanceof EnhancedComboBoxModel)
                ((EnhancedComboBoxModel) oldCBM).removePropertyChangeListener(
                        EnhancedComboBoxModel.PROPERTY_ENABLED, listener);
        }

        comboBoxModel = newCBM;

        if (newCBM != null) {
            newCBM.addListDataListener(listener);
            comboBoxModelSelectionChanged();
            if (newCBM instanceof EnhancedComboBoxModel) {
                ((EnhancedComboBoxModel) newCBM).addPropertyChangeListener(
                        EnhancedComboBoxModel.PROPERTY_ENABLED, listener);
                comboBoxModelEnabledChanged();
            }
        }
    }

    private synchronized void documentUpdated() {
        if (lock())
            return;
        try {
            final ComboBoxModel cbm = getComboBoxModel();
            if (cbm == null)
                return;

            final Document doc = getDocument();
            final String text = getText(doc);
            cbm.setSelectedItem(text);
        } finally {
            unlock();
        }
    }

    private synchronized void documentEditableChanged() {
        if (lock())
            return;
        try {
            final ComboBoxModel cbm = getComboBoxModel();
            if (cbm instanceof EnhancedComboBoxModel) {
                final EnhancedComboBoxModel ecbm = (EnhancedComboBoxModel) cbm;
                final boolean e = ((EnhancedDocument) getDocument()).isEditable();
                ecbm.setEditable(e);
                ecbm.setEnabled(e);
            }
        } finally {
            unlock();
        }
    }

    private synchronized void comboBoxModelSelectionChanged() {
        if (lock())
            return;
        try {
            final Document doc = getDocument();
            if (doc == null)
                return;

            final ComboBoxModel cbm = getComboBoxModel();
            final Object item = cbm.getSelectedItem();

            setText(doc, item != null ? item.toString() : null);
        } finally {
            unlock();
        }
    }

    private synchronized void comboBoxModelEnabledChanged() {
        if (lock())
            return;
        try {
            final Document doc = getDocument();
            if (doc instanceof EnhancedDocument)
                ((EnhancedDocument) doc).setEditable(
                        ((EnhancedComboBoxModel) getComboBoxModel()).isEnabled());
        } finally {
            unlock();
        }
    }

    /**
     * Locks out mutual recursive event notification.
     * <b>Warning:</b> This method works in a synchronized or single threaded
     * environment only!
     * 
     * @return Whether or not recursion was already locked.
     */
    private final boolean lock() {
        if (recursion)
            return true;
        recursion = true;
        return false;
    }

    /**
     * Unlocks mutual recursive event notification.
     * <b>Warning:</b> This method works in a synchronized or single threaded
     * environment only!
     */
    private final void unlock() {
        recursion = false;
    }

    public static String getText(Document document) {
        try {
            return document.getText(0, document.getLength());
        } catch (BadLocationException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
    }

    public static void setText(Document document, String str) {
        try {
            document.remove(0, document.getLength());
            document.insertString(0, str, null);
        } catch (BadLocationException cannotHappen) { 
            throw new AssertionError(cannotHappen);
        }
    }

    private final class Listener
            implements DocumentListener, ListDataListener, PropertyChangeListener {
        public void insertUpdate(DocumentEvent e) {
            documentUpdated();
        }

        public void removeUpdate(DocumentEvent e) {
            documentUpdated();
        }

        public void changedUpdate(DocumentEvent e) {
            documentUpdated();
        }

        public void intervalAdded(ListDataEvent e) {
        }

        public void intervalRemoved(ListDataEvent e) {
        }

        public void contentsChanged(ListDataEvent e) {
            // Negative indices are a ComboBoxModel's way to tell us that
            // the current selection has changed rather than a value in
            // the comboBoxModel.
            if (e.getIndex0() < 0 && e.getIndex1() < 0)
                comboBoxModelSelectionChanged();
        }

        public void propertyChange(PropertyChangeEvent e) {
            final String property = e.getPropertyName();
            if ("enabled".equals(property))
                comboBoxModelEnabledChanged();
            else if ("editable".equals(property))
                documentEditableChanged();
            else
                throw new AssertionError(
                        "Received change event for unknown property: "
                        + property);
        }
    }
}
