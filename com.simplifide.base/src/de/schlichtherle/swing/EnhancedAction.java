/*
 * EnhancedAction.java
 *
 * Created on 30. Juli 2005, 12:24
 */
/*
 * Copyright 2004-2005 Schlichtherle IT Services
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

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * This class decodes it's constructor's text to set the mnemonic to the
 * first character following an ampersand (<tt>'&'</tt>) which is not an
 * ampersand itself. Any single instances of ampersands are removed from
 * the text.
 *
 * @author Christian Schlichtherle
 */
public abstract class EnhancedAction extends AbstractAction {
    
    /**
     * Creates a new instance of EnhancedAction
     * with the given name.
     */
    public EnhancedAction(String name) {
        this(name, null, null);
    }
    
    /**
     * Creates a new instance of EnhancedAction
     * with the given name and tool tip.
     */
    public EnhancedAction(String name, String toolTip) {
        this(name, toolTip, null);
    }
    
    /**
     * Creates a new instance of EnhancedAction
     * with the given name, tool tip and icon.
     */
    public EnhancedAction(String name, String toolTip, Icon icon) {
        /*if (icon != null && name != null && !"".equals(name.trim()))
            name = " " + name;*/
        MnemonicText mt = new MnemonicText(name);
        String text = mt.getText();
        putValue(NAME, text);
        if (mt.getMnemonicIndex() >= 0)
            putValue(MNEMONIC_KEY, new Integer(mt.getMnemonic()));
        if (toolTip != null)
            putValue(SHORT_DESCRIPTION, toolTip);
        if (icon != null)
            putValue(SMALL_ICON, icon);
    }
}
