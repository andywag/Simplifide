/*
 * EnhancedDocument.java
 *
 * Created on 31. Juli 2006, 15:00
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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.text.PlainDocument;

/**
 * A {@link PlainDocument} which introduces bound properties
 * to notify {@link EnhancedTextField} observers.
 * Adds some text methods for convenience as well.
 * <p>
 * This class is designed to be thread safe.
 *
 * @author Christian Schlichtherle
 * @since TrueSwing 1.28 (refactored from
 *        <code>de.schlichtherle.swing.model.EnhancedTextFieldDocument</code>).
 */
public class EnhancedDocument extends PlainDocument {

    /** The name of the property <code>editable</code>. */
    public static final String PROPERTY_EDITABLE = "editable";
    
    private boolean editable = true;

    protected PropertyChangeSupport changeSupport;

    public EnhancedDocument() {
    }

    public EnhancedDocument(Content c) {
        super(c);
    }

    public EnhancedDocument(String str) {
        ComboBoxDocumentMediator.setText(this, str);
    }

    public synchronized boolean isEditable() {
        return editable;
    }

    public synchronized void setEditable(boolean newEditable) {
        boolean oldEditable = isEditable();
        if (oldEditable == newEditable)
            return;

        this.editable = newEditable;
        firePropertyChange(PROPERTY_EDITABLE, oldEditable, newEditable);
    }

    /**
     * @return The entire text of this document model - never <code>null</code>.
     */
    public synchronized String getText() {
        return ComboBoxDocumentMediator.getText(this);
    }

    /**
     * Replaces the entire text of this document model with the given string.
     */
    public synchronized void setText(String str) {
        ComboBoxDocumentMediator.setText(this, str);
    }

    /**
     * Identical to <code>getText()</code>.
     */
    public String toString() {
        return getText();
    }

    //
    // Property change support.
    // This is derived from javax.swing.JComponent, but does use
    // PropertyChangeSupport instead of SwingPropertyChangeSupport.
    //

    /**
     * Reports a bound property change.
     *
     * @param propertyName The programmatic name of the property
     *        that was changed.
     * @param oldValue The old value of the property (as a boolean).
     * @param newValue The new value of the property (as a boolean).
     *
     * @see #firePropertyChange(java.lang.String, boolean, boolean)
     */
    protected void firePropertyChange(
            String propertyName,
            boolean oldValue,
            boolean newValue) {
        if (changeSupport != null)
            changeSupport.firePropertyChange(
                    propertyName,
                    Boolean.valueOf(oldValue),
                    Boolean.valueOf(newValue));
    }

    /**
     * Adds a <code>PropertyChangeListener</code> to the listener list.
     * The listener is registered for all properties.
     * <p>
     * A <code>PropertyChangeEvent</code> will get fired in response
     * to setting a bound property.
     *
     * @param listener The <code>PropertyChangeListener</code> to be added.
     */
    public synchronized void addPropertyChangeListener(
            PropertyChangeListener listener) {
        if (changeSupport == null)
            changeSupport = new PropertyChangeSupport(this);
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a <code>PropertyChangeListener</code> from the listener list.
     * This removes a <code>PropertyChangeListener</code> that was registered
     * for all properties.
     *
     * @param listener The <code>PropertyChangeListener</code> to be removed.
     */
    public synchronized void removePropertyChangeListener(
            PropertyChangeListener listener) {
        if (changeSupport != null)
            changeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Adds a <code>PropertyChangeListener</code> for a specific property.
     * The listener will be invoked only when a call on
     * <code>firePropertyChange</code> names that specific property.
     * <p>
     * If listener is <code>null</code>, no exception is thrown and no
     * action is performed.
     *
     * @param propertyName The name of the property to listen on.
     * @param listener The <code>PropertyChangeListener</code> to be added.
     */
    public synchronized void addPropertyChangeListener(
            String propertyName,
            PropertyChangeListener listener) {
	if (listener == null)
	    return;
	if (changeSupport == null)
	    changeSupport = new PropertyChangeSupport(this);
	changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes a <code>PropertyChangeListener</code> for a specific property.
     * If listener is <code>null</code>, no exception is thrown and no
     * action is performed.
     *
     * @param propertyName  the name of the property that was listened on
     * @param listener  the <code>PropertyChangeListener</code> to be removed
     */
    public synchronized void removePropertyChangeListener(
            String propertyName,
            PropertyChangeListener listener) {
	if (listener == null)
	    return;
	if (changeSupport == null)
	    return;
	changeSupport.removePropertyChangeListener(propertyName, listener);
    }
}