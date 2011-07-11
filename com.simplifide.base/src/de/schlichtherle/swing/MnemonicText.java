/*
 * MnemonicText.java
 *
 * Created on 29. Januar 2005, 18:39
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

import java.io.Serializable;

/**
 * Decodes the constructor's string parameter to retrieve extended information
 * which can be used to configure the mnemonic of a <tt>JComponent</tt>.
 *
 * @author Christian Schlichtherle
 */
public class MnemonicText implements Serializable {

    /**
     * This buffer is shared by all instances to reformat the text
     * provided to the constructor.
     */
    private static final StringBuffer buf = new StringBuffer();

    private final String text;
    private final int mnemonicIndex;
    private final char mnemonic;
    private final boolean isHtmlText;
    
    /**
     * Scans the given <tt>text</tt> for the character <tt>'&'</tt> to
     * retrieve the mnemonic and its index.
     * <p>
     * Every occurence of <tt>'&'</tt> will be removed from the text unless
     * it is followed by another <tt>'&'</tt>
     * in which case this sequence is substituted by a single <tt>'&'</tt>.
     */
    public MnemonicText(final String text) {
        int mnemonicIndex = -1;
        char mnemonic = 0;
        synchronized (buf) {
            buf.setLength(0);
            buf.ensureCapacity(text.length());
            int l = text.length();
            for (int i = 0; i < l; i++) {
                char c = text.charAt(i);
                if (c == '&') {
                    if (++i >= l) // skip this ampersand
                        break;
                    if ((c = text.charAt(i)) != '&' && mnemonicIndex == -1)  {
                        mnemonic = c;
                        mnemonicIndex = buf.length();
                    }
                }
                buf.append(c);
            }
            if (buf.length() != l)
                this.text = buf.toString();
            else
                this.text = text; // text and buf are equal
        }

        this.mnemonicIndex = mnemonicIndex;
        this.mnemonic = mnemonic;
        String trim = text.trim();
        isHtmlText = trim
                .substring(0, Math.min(htmlTagLength, trim.length()))
                .equalsIgnoreCase(htmlTag);
    }

    private static final String htmlTag = "<html>";
    private static final int htmlTagLength = htmlTag.length();

    /**
     * Returns the decoded text.
     */
    public String getText() {
        return text;
    }

    /** Returns the index of the mnemonic character or -1 if not existent. */
    public int getMnemonicIndex() {
        return mnemonicIndex;
    }
    
    /**
     * Returns the mnemonic character.
     * This value is undefined if <tt>{@link #getMnemonicIndex()} < 0</tt>
     * is true.
     */
    public char getMnemonic() {
        return mnemonic;
    }

    /**
     * Returns true if the decoded text starts with an HTML tag.
     */
    public final boolean isHtmlText() {
        return isHtmlText;
    }
}
