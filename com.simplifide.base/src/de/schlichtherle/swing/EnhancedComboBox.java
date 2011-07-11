/*
 * EnhancedComboBox.java
 *
 * Created on 31. Juli 2006, 22:43
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
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * Like its super class, but pays attention to the properties
 * <code>editable</code> and <code>enabled</code> of the class
 * {@link EnhancedComboBoxModel} when used as its model
 * (which is the case by default).
 *
 * @author Christian Schlichtherle
 * @since TrueLicense 1.28
 */
public class EnhancedComboBox extends JComboBox {

    /** The name of the property <code>pushingPropertyChanges</code>. */
    public static final String PROPERTY_PUSHING_PROPERTY_CHANGES
            = "pushingPropertyChanges";

    private final PropertyChangeListener listener = new Listener();

    public EnhancedComboBox(ComboBoxModel aModel) {
        // Don't call super class constructor with model argument:
        // This will call setModel while listener is not yet initialized!
        setModel(aModel);
    }

    public EnhancedComboBox(final Object items[]) {
        // Don't call super class constructor with model argument:
        // This will call setModel while listener is not yet initialized!
        setModel(new EnhancedComboBoxModel(items));
    }

    public EnhancedComboBox(Vector items) {
        // Don't call super class constructor with model argument:
        // This will call setModel while listener is not yet initialized!
        setModel(new EnhancedComboBoxModel(items));
    }

    public EnhancedComboBox() {
        // Don't call super class constructor with model argument:
        // This will call setModel while listener is not yet initialized!
        setModel(new EnhancedComboBoxModel());
    }

    /**
     * As a supplement to its super class implementation, this implementation
     * also applies the property change to its model if it is an instance of
     * {@link EnhancedComboBoxModel}.
     *
     * @see JComboBox#setEditable
     */
    public void setEditable(final boolean newEditable) {
        final boolean oldEditable = isEditable();
        if (newEditable == oldEditable)
            return;

        super.setEditable(newEditable);

        final ComboBoxModel model = getModel();
        if (model instanceof EnhancedComboBoxModel)
            ((EnhancedComboBoxModel) model).setEditable(newEditable);
    }

    /**
     * As a supplement to its super class implementation, the implementation
     * in this class also applies the property change to its model if it is
     * an instance of {@link EnhancedComboBoxModel}.
     *
     * @see JComboBox#setEnabled
     */
    public void setEnabled(final boolean newEnabled) {
        final boolean oldEnabled = isEnabled();
        if (newEnabled == oldEnabled)
            return;

        super.setEnabled(newEnabled);

        final ComboBoxModel model = getModel();
        if (model instanceof EnhancedComboBoxModel)
            ((EnhancedComboBoxModel) model).setEnabled(newEnabled);
    }

    /**
     * As a supplement to its super class implementation, the implementation
     * in this class also inherits the properties from its model if it is
     * an instance of {@link EnhancedComboBoxModel}.
     *
     * @see JComboBox#setModel
     */
    public void setModel(final ComboBoxModel newModel) {
        final ComboBoxModel oldModel = getModel();
        if (newModel == oldModel)
            return;

        if (oldModel instanceof EnhancedComboBoxModel)
            ((EnhancedComboBoxModel) oldModel).removePropertyChangeListener(
                    listener);

        super.setModel(newModel);

        if (newModel instanceof EnhancedComboBoxModel) {
            final EnhancedComboBoxModel emodel
                    = (EnhancedComboBoxModel) newModel;
            emodel.addPropertyChangeListener(listener);
            final boolean editable = emodel.isEditable();
            setEditable(editable);
            final boolean enabled = emodel.isEnabled();
            setEnabled(enabled);
        }
    }

    private final class Listener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent e) {
            final String property = e.getPropertyName();
            if (EnhancedComboBoxModel.PROPERTY_EDITABLE.equals(property))
                setEditable(Boolean.TRUE.equals(e.getNewValue()));
            else if (EnhancedComboBoxModel.PROPERTY_ENABLED.equals(property))
                setEnabled(Boolean.TRUE.equals(e.getNewValue()));
        }
    }
}
