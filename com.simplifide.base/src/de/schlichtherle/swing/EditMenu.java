/*
 * I18NEditMenu.java
 *
 * Created on 22. Juli 2005, 00:19
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



import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

/**
 * This class represents an internationalized edit menu.
 * It's resources are stored in the file Resources[_locale].properties files
 * in the same directory.
 *
 * @author Christian Schlichtherle
 */
public class EditMenu extends JMenu {
    
    private static final String CLASS_NAME
            = "de/schlichtherle/swing/EditMenu".replace('/', '.'); // beware of code obfuscation!
    
    // Don't do this - it will create memory leaks!
    // Components like AbstractButton add themselves as PropertyChangeListeners
    //public static final Action EDIT_MENU_ACTION = new EditMenuAction();
    //public static final Action CUT_ACTION = new CutAction();
    //public static final Action COPY_ACTION = new CopyAction();
    //public static final Action PASTE_ACTION = new PasteAction();
    
    public static class CutAction extends ResourceBundleAction {
        private static final Action action = new DefaultEditorKit.CutAction();

        public CutAction() {
            super(CLASS_NAME, EditMenu.class.getClassLoader(), "cut");

            putValue(ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            action.actionPerformed(e);
        }
    }
    
    public static class CopyAction extends ResourceBundleAction {
        private static final Action action = new DefaultEditorKit.CopyAction();

        public CopyAction() {
            super(CLASS_NAME, EditMenu.class.getClassLoader(), "copy");

            putValue(ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            action.actionPerformed(e);
        }
    }

    public static class PasteAction extends ResourceBundleAction {
        private static final Action action = new DefaultEditorKit.PasteAction();

        public PasteAction() {
            super(CLASS_NAME, EditMenu.class.getClassLoader(), "paste");

            putValue(ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            action.actionPerformed(e);
        }
    }

    public static class EditMenuAction extends ResourceBundleAction {
        public EditMenuAction() {
            super(CLASS_NAME, EditMenu.class.getClassLoader(), "editMenu");

            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // do nothing
        }
    }

    /**
     * Creates a new instance of I18NEditMenu 
     */
    public EditMenu() {
        super(new EditMenuAction());
        add(new CutAction());
        add(new CopyAction());
        add(new PasteAction());
    }
}
