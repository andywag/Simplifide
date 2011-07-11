/*
 * EnhancedFrame.java
 *
 * Created on 15. Januar 2005, 02:34
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

package de.schlichtherle.swing;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;

/**
 * Adds some enhancements to a {@link JFrame}.
 * <p>
 * <ul>
 * <li>It offers the method <tt>setDefaultButton(JButton)</tt> to compensate
 *     for a deficiency of many look and feels which do not highlight the
 *     default button.
 * <li>The default button is highlighted in bold face and it requests the
 *     focus whenever this <code>Component</code> is shown.
 * <li>It looks up the resource file
 *     <code>"images/applIcon.[png|jpeg|jpg|gif]"</code> or
 *     <code>"resources/applIcon.[png|jpeg|jpg|gif]"</code> on the class path,
 *     loads it and uses it as the minimized icon for this frame.
 * </ul>
 *
 * @author Christian Schlichtherle
 */
public class EnhancedFrame extends JFrame {

    private static final String suffixes[] = {
        ".png",
        ".jpeg",
        ".jpg",
        ".gif",
    };
    
    //
    // Constructors.
    //
    
    public EnhancedFrame() throws HeadlessException {
        super();
        initComponents();
    }

    public EnhancedFrame(GraphicsConfiguration gc) {
        super(gc);
        initComponents();
    }

    public EnhancedFrame(String title) throws HeadlessException {
        super(title);
        initComponents();
    }
    
    public EnhancedFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        initComponents();
    }
    
    /**
     * This method is called from all constructors.
     */
    private void initComponents() {
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();

        URL url = null;
        for (int i = 0, l = suffixes.length; i < l && url == null; i++) {
            final String suffix = suffixes[i];
            url = cl.getResource("images/applIcon" + suffix);
            if (url == null)
                url = cl.getResource("resources/applIcon" + suffix);
        }
        if (url != null)
            setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }

    protected JRootPane createRootPane() {
        return new EnhancedRootPane();
    }
    
    /**
     * Boldfaces <tt>button</tt> and sets it as the default button for this
     * dialog.
     * This method compensates for some look and feels which do not highlight
     * the default button.
     * <p>
     * Make sure that the given button really is on this dialog.
     */
    public void setDefaultButton(JButton button) {
        final JRootPane rp = getRootPane();
        if (rp instanceof EnhancedRootPane)
            ((EnhancedRootPane) rp).setRestorableDefaultButton(button);
        else
            rp.setDefaultButton(button);
    }
    
    public JButton getDefaultButton() {
        return getRootPane().getDefaultButton();
    }
}
