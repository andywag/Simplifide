/*
 * EnhancedComboBoxModel.java
 *
 * Created on 31. Juli 2006, 17:59
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
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.MutableComboBoxModel;

/**
 * A {@link DefaultComboBoxModel} which introduces bound properties
 * to notify {@link EnhancedComboBoxModel} observers.
 * <p>
 * This class is designed to be thread safe.
 *
 * @author Christian Schlichtherle
 * @since TrueLicense 1.28
 */
public class EnhancedComboBoxModel
        extends DefaultComboBoxModel
        implements MutableComboBoxModel {

    /** The name of the property <code>enabled</code>. */
    public static final String PROPERTY_ENABLED = "enabled";

    /** The name of the property <code>editable</code>. */
    public static final String PROPERTY_EDITABLE = "editable";

    private boolean enabled = true;
    private boolean editable;

    protected PropertyChangeSupport changeSupport;

    /**
     * Constructs an empty <code>EnhancedComboBoxModel</code> object.
     */
    public EnhancedComboBoxModel() {
    }

    /**
     * Constructs an <code>EnhancedComboBoxModel</code> object initialized
     * with an array of objects.
     *
     * @param items An array of objects.
     */
    public EnhancedComboBoxModel(final Object items[]) {
        super(items);
    }

    /**
     * Constructs an <code>EnhancedComboBoxModel</code> object initialized
     * with a vector.
     *
     * @param v A Vector object.
     */
    public EnhancedComboBoxModel(Vector v) {
        super(v);
    }

    /**
     * Returns the value of the <code>enabled</code> property.
     */
    public synchronized boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the <code>enabled</code> property for this combo
     * box model. This is a bound property.
     * 
     * @param newEnabled If this is <code>false</code>, then any observer
     *        should ignore any attempt to change its selection
     *        (though the user may still be able to change the list elements).
     *
     * @see #PROPERTY_ENABLED
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     */
    public synchronized void setEnabled(final boolean newEnabled) {
        final boolean oldEnabled = isEnabled();
        if (newEnabled == oldEnabled)
            return;

        this.enabled = newEnabled;
        firePropertyChange(PROPERTY_ENABLED, oldEnabled, newEnabled);
    }

    /**
     * Returns the value of the <code>editable</code> property.
     */
    public synchronized boolean isEditable() {
        return editable;
    }

    /**
     * Sets the value of the <code>editable</code> property for this combo
     * box model. This is a bound property.
     * 
     * @param newEditable If this is <code>false</code>, then any observer
     *        should ignore any attempt to mutate it's list elements
     *        (though the user may still be able to change the current selection).
     *
     * @see #PROPERTY_EDITABLE
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     */
    public synchronized void setEditable(final boolean newEditable) {
        final boolean oldEditable = isEditable();
        if (newEditable == oldEditable)
            return;

        this.editable = newEditable;
        firePropertyChange(PROPERTY_EDITABLE, oldEditable, newEditable);
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
