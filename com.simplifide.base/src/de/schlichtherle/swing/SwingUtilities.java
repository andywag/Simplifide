/*
 * SwingUtilities.java
 *
 * Created on 31. Mai 2005, 23:59
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

/**
 * @author Christian Schlichtherle
 */
public class SwingUtilities {

    /**
     * Executes the given runnable from the AWT Event Dispatcher Thread,
     * even if this method is executed by the EDT.
     */
    public static void runOnEventDispatchThread(final Runnable r)
    throws java.lang.reflect.InvocationTargetException {
        if (javax.swing.SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            try {
                javax.swing.SwingUtilities.invokeAndWait(r);
            } catch (InterruptedException interrupted) {
                
            }
        }
    }
    
    /**
     * Returns the ancestor {@link Window} of the given <code>component</code>
     * or <code>null</code> if the component is not (yet) placed in a
     * <code>Window</code>.
     */
    public static Window getAncestorWindow(final Component component) {
        Container parent = component.getParent();
        while (parent != null && !(parent instanceof Window))
            parent = parent.getParent();

        return (Window) parent;
    }
    
    protected SwingUtilities() { } // You cannot instantiate this class
}
