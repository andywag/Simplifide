/*
 * InvisibleTitledBorder.java
 *
 * Created on 2. Februar 2005, 14:46
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

import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * This singleton class represents an <tt>EmptyBorder</tt> with the same
 * insets as a <tt>TitledBorder</tt>.
 *
 * @author Christian Schlichtherle
 */
public class InvisibleTitledBorder extends EmptyBorder {

    private static InvisibleTitledBorder emptyTitledBorder;
    
    public static InvisibleTitledBorder createEmptyTitledBorder() {
        if (emptyTitledBorder == null) {
            TitledBorder prototype = BorderFactory.createTitledBorder(
                    null,
                    "Test", // NOI18N
                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                    new Font("Dialog", Font.PLAIN, 11));
            emptyTitledBorder = new InvisibleTitledBorder(
                    prototype.getBorderInsets(null));
        }
        return emptyTitledBorder;
    }
    
    /** You cannot instantiate this class. */
    protected InvisibleTitledBorder(Insets borderInsets) {
        super(borderInsets);
    }
}
