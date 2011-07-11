/* 
 * Hex.java
 * 
 * Created 04.07.2003.
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
package com.simplifide.mac;

/**
 * A utility class for number-to-hexadecimal and hexadecimal-to-number
 * conversions.
 * 
 * @author <a href="mailto:jb@eaio.com">Johann Burkard</a>
 * @version $Id: Hex.java,v 1.3 2008/02/20 07:37:41 Johann Exp $
 */
public final class Hex {

    /**
     * No instances needed.
     */
    private Hex() {}

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Transforms a <code>byte</code> to a character array of hex octets.
     * 
     * @param in the byte
     * @return char[] the hex byte array
     */
    public static char[] asChars(byte in) {
        return asChars(in, 2);
    }

    /**
     * Transforms a <code>byte</code> to a character array of hex octets.
     * 
     * @param in the byte
     * @param length the number of octets to produce
     * @return char[] the hex byte array
     */
    public static char[] asChars(byte in, int length) {
        char[] out = new char[length--];
        for (int i = length; i > -1; i--) {
            out[i] = DIGITS[(byte) (in & 0x0F)];
            in >>= 4;
        }
        return out;
    }

    /**
     * Transforms a <code>int</code> to a character array of hex octets.
     * 
     * @param in the integer
     * @return char[] the hex byte array
     */
    public static char[] asChars(int in) {
        return asChars(in, 8);
    }

    /**
     * Transforms an <code>int</code> to a character array of hex octets.
     * 
     * @param in the integer
     * @param length the number of octets to produce
     * @return char[]
     */
    public static char[] asChars(int in, int length) {
        char[] out = new char[length--];
        for (int i = length; i > -1; i--) {
            out[i] = DIGITS[(byte) (in & 0x0F)];
            in >>= 4;
        }
        return out;
    }

    /**
     * Transforms a <code>long</code> to a character array of hex octets.
     * 
     * @param in the long
     * @return char[] the hex byte array
     */
    public static char[] asChars(long in) {
        return asChars(in, 16);
    }

    /**
     * Transforms a <code>long</code> to a character array of hex octets.
     * 
     * @param in the long
     * @param length the number of octets to produce 
     * @return char[] the hex byte array
     */
    public static char[] asChars(long in, int length) {
        char[] out = new char[length--];
        for (int i = length; i > -1; i--) {
            out[i] = DIGITS[(byte) (in & 0x0F)];
            in >>= 4;
        }
        return out;
    }

    /**
     * Transforms a <code>short</code> to a character array of hex octets.
     * 
     * @param in the integer
     * @return char[] the hex byte array
     */
    public static char[] asChars(short in) {
        return asChars(in, 4);
    }

    /**
     * Transforms a <code>short</code> to a character array of hex octets.
     * 
     * @param in the integer
     * @param length the number of octets to produce
     * @return char[] the hex byte array
     */
    public static char[] asChars(short in, int length) {
        char[] out = new char[length--];
        for (int i = length; i > -1; i--) {
            out[i] = DIGITS[(byte) (in & 0x0F)];
            in >>= 4;
        }
        return out;
    }

    /**
     * Transform an array of bytes into a character array of hex octets.
     * 
     * @param b the <code>byte</code> array
     * @return char[] the hex byte array
     */
    public static char[] asChars(byte[] b) {
        int len = b.length << 1;
        char[] out = new char[len--];
        for (int i = b.length - 1; i > -1; i--) {
            out[len--] = DIGITS[(byte) (b[i] & 0x0F)];
            out[len--] = DIGITS[(byte) ((b[i] & 0xF0) >> 4)];
        }
        return out;
    }

    /**
     * Parses a <code>long</code> from a hex encoded number. This method will skip
     * all characters that are not 0-9 and a-f (the String is lower cased first).
     * Returns 0 if the String does not contain any interesting characters.
     * 
     * @param s the String to extract a <code>long</code> from, may not be <code>null</code>
     * @return a <code>long</code>
     * @throws NullPointerException if the String is <code>null</code>
     */
    public static long parseLong(String s) throws NullPointerException {
        s = s.toLowerCase();
        long out = 0;
        byte shifts = 0;
        char c;
        for (int i = 0; i < s.length() && shifts < 16; i++) {
            c = s.charAt(i);
            if ((c > 47) && (c < 58)) {
                out <<= 4;
                ++shifts;
                out |= c - 48;
            }
            else if ((c > 96) && (c < 103)) {
                ++shifts;
                out <<= 4;
                out |= c - 87;
            }
        }
        return out;
    }

    /**
     * Parses an <code>int</code> from a hex encoded number. This method will skip
     * all characters that are not 0-9 and a-f (the String is lower cased first).
     * Returns 0 if the String does not contain any interesting characters.
     * 
     * @param s the String to extract an <code>int</code> from, may not be <code>null</code>
     * @return an <code>int</code>
     * @throws NullPointerException if the String is <code>null</code>
     */
    public static int parseInt(String s) throws NullPointerException {
        s = s.toLowerCase();
        int out = 0;
        byte shifts = 0;
        char c;
        for (int i = 0; i < s.length() && shifts < 8; i++) {
            c = s.charAt(i);
            if ((c > 47) && (c < 58)) {
                out <<= 4;
                ++shifts;
                out |= c - 48;
            }
            else if ((c > 96) && (c < 103)) {
                ++shifts;
                out <<= 4;
                out |= c - 87;
            }
        }
        return out;
    }

    /**
     * Parses a <code>short</code> from a hex encoded number. This method will skip
     * all characters that are not 0-9 and a-f (the String is lower cased first).
     * Returns 0 if the String does not contain any interesting characters.
     * 
     * @param s the String to extract a <code>short</code> from, may not be <code>null</code>
     * @return a <code>short</code>
     * @throws NullPointerException if the String is <code>null</code>
     */
    public static short parseShort(String s) throws NullPointerException {
        s = s.toLowerCase();
        short out = 0;
        byte shifts = 0;
        char c;
        for (int i = 0; i < s.length() && shifts < 4; i++) {
            c = s.charAt(i);
            if ((c > 47) && (c < 58)) {
                out <<= 4;
                ++shifts;
                out |= c - 48;
            }
            else if ((c > 96) && (c < 103)) {
                ++shifts;
                out <<= 4;
                out |= c - 87;
            }
        }
        return out;
    }

    /**
     * Parses a <code>byte</code> from a hex encoded number. This method will skip
     * all characters that are not 0-9 and a-f (the String is lower cased first).
     * Returns 0 if the String does not contain any interesting characters.
     * 
     * @param s the String to extract a <code>byte</code> from, may not be <code>null</code>
     * @return a <code>byte</code>
     * @throws NullPointerException if the String is <code>null</code>
     */
    public static byte parseByte(String s) throws NullPointerException {
        s = s.toLowerCase();
        byte out = 0;
        byte shifts = 0;
        char c;
        for (int i = 0; i < s.length() && shifts < 2; i++) {
            c = s.charAt(i);
            if ((c > 47) && (c < 58)) {
                out <<= 4;
                ++shifts;
                out |= c - 48;
            }
            else if ((c > 96) && (c < 103)) {
                ++shifts;
                out <<= 4;
                out |= c - 87;
            }
        }
        return out;
    }

}
