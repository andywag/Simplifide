/*
 * EnhancedRootPane.java
 *
 * Created on 5. Februar 2006, 19:25
 */

package de.schlichtherle.swing;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JRootPane;

/**
 * Enhances its base class by requesting the focus for its default button
 * whenever the parent window is shown again.
 *
 * @author Christian Schlichtherle
 */
public class EnhancedRootPane extends JRootPane {

    private JButton defaultButton;
    
    final ComponentAdapter parentListener = new ComponentAdapter() {
        public void componentShown(ComponentEvent evt) {
            if (defaultButton != null)
                defaultButton.requestFocusInWindow();
        }
    };

    public void setRestorableDefaultButton(final JButton defaultButton) {
        final JButton oldDefault = this.defaultButton;
        /*if (oldDefault != null)
            oldDefault.setFont(Defaults.labelPlainFont);*/
        if (oldDefault != defaultButton) {
            this.defaultButton = defaultButton;
            if (defaultButton != null) {
                //defaultButton.setFont(Defaults.labelBoldFont);
                defaultButton.requestFocusInWindow();
                getParent().addComponentListener(parentListener);
            } else {
                getParent().removeComponentListener(parentListener);
            }
        }

        super.setDefaultButton(defaultButton);
    }
}
