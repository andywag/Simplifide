/*
 * Defaults.java
 *
 * Created on 17. Dezember 2004, 14:31
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

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * Provides some static fields used as Swing GUI defaults.
 *
 * @author Christian Schlichtherle
 */
public class Defaults {

    //
    // Default font attribubtes of JLabels
    //
    
    public static final Font labelPlainFont = new JLabel("").getFont(); // NOI18N
    public static final int labelFontStyle = labelPlainFont.getStyle();
    public static final int labelFontSize = labelPlainFont.getSize();
    public static final Font labelBoldFont = labelPlainFont.deriveFont(Font.BOLD);
    public static final Font monoPlainFont
            = new Font("Monospaced", Font.PLAIN, labelFontSize); // NOI18N
    public static final Font monoBoldFont
            = new Font("Monospaced", Font.BOLD,  labelFontSize); // NOI18N
    
    //
    // Some useful borders
    //
    
    public static final Border emptyBorder
            = BorderFactory.createEmptyBorder();
    public static final Border emptyTitledBorder
            = InvisibleTitledBorder.createEmptyTitledBorder();
    
    /** You cannot instantiate this class. */
    protected Defaults() { }

}
