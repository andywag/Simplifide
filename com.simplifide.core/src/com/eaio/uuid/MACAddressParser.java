/*
 * MACAddressParserTest.java
 * 
 * Created 30.01.2006.
 * 
 * eaio: UUID - an implementation of the UUID specification
 * Copyright (c) 2003-2008 Johann Burkard (jb@eaio.com) http://eaio.com.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.eaio.uuid;

/**
 * The MAC address parser attempts to find the following patterns:
 * <ul>
 * <li>.{1,2}:.{1,2}:.{1,2}:.{1,2}:.{1,2}:.{1,2}</li>
 * <li>.{1,2}-.{1,2}-.{1,2}-.{1,2}-.{1,2}-.{1,2}</li>
 * </ul>
 *
 * @author <a href="mailto:jb@eaio.com">Johann Burkard</a>
 * @version $Id: MACAddressParser.java,v 1.4 2008/02/20 07:37:40 Johann Exp $
 */
class MACAddressParser {

    /**
     * No instances needed.
     */
    private MACAddressParser() {}

    /**
     * Attempts to find a pattern in the given String.
     * 
     * @param in the String, may not be <code>null</code>
     * @return the substring that matches this pattern or <code>null</code>
     */
    static String parse(String in) {
        
        // lanscan
        
        int hexStart = in.indexOf("0x");
        if (hexStart != -1 && in.indexOf("ETHER") != -1) {
            int hexEnd = in.indexOf(' ', hexStart);
            if (hexEnd > hexStart + 2) {
                return in.substring(hexStart, hexEnd);
            }
        }
        
        int octets = 0;
        int lastIndex, old, end;

        if (in.indexOf('-') > -1) {
            in = in.replace('-', ':');
        }
        
        lastIndex = in.lastIndexOf(':');

        if (lastIndex > in.length() - 2) return null;

        end = Math.min(in.length(), lastIndex + 3);

        ++octets;
        old = lastIndex;
        while (octets != 5 && lastIndex != -1 && lastIndex > 1) {
            lastIndex = in.lastIndexOf(':', --lastIndex);
            if (old - lastIndex == 3 || old - lastIndex == 2) {
                ++octets;
                old = lastIndex;
            }
        }

        if (octets == 5 && lastIndex > 1) {
            return in.substring(lastIndex - 2, end).trim();
        }
        return null;
    }

}
