/*
 * PopupMenuMouseListener.java
 *
 * Created on 17. Juli 2006, 23:14
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * A mouse listener that brings up a popup menu.
 *
 * @author Christian Schlichtherle
 */
public class PopupMenuMouseListener extends MouseAdapter {

    private final JPopupMenu popup;
    
    /**
     * Creates a new instance of <code>PopupMenuMouseListener</code>.
     *
     * @param popup The popup menu to show if the popup menu mouse gesture
     *        has been triggered.
     */
    public PopupMenuMouseListener(final JPopupMenu popup) {
        this.popup = popup;
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private final void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger())
            popup.show(e.getComponent(), e.getX(), e.getY());
    }
}
