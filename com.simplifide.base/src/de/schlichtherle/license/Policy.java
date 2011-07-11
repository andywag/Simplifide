/*
 * Policy.java
 *
 * Created on 9. August 2005, 15:14
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

package de.schlichtherle.license;

/**
 * Provides an interface for security policies and implements the default
 * policy.
 *
 * @author Christian Schlichtherle
 */
// TODO: This should be an interface which's implementation is provided by a
// factory.
public class Policy {

    /** The current policy. */
    private static Policy current;

    //
    // Static methods.
    //
    
    /**
     * Sets the current policy.
     *
     * @param current The new current policy.
     *        May be <tt>null</tt> to cause the default policy implemented by
     *        this class to be installed and returned by {@link #getCurrent()}.
     */
    public static void setCurrent(Policy current) {
        Policy.current = current;
    }

    /**
     * Returns the current policy.
     *
     * If no current policy has been set, the default policy implemented by
     * this class is installed as the current policy and returned.
     */
    public static Policy getCurrent() {
        if (current == null)
            current = new Policy();
        return current;
    }
    
    //
    // Constructors and instance methods.
    //
    
    /** Only subclasses can instantiate this class. */
    protected Policy() { }
    
    /**
     * Checks the given password for compliance to the current password policy.
     * <p>
     * The default policy implemented by this class ensures that the
     * password is at least six characters long and consists of letters and
     * digits.
     *
     * @throws IllegalPasswordException If the given password does not comply
     *         to the current policy.
     */
    public void checkPwd(String pwd) throws IllegalArgumentException {
        final int l = pwd.length(); // may throw NullPointerException
        if (pwd == null)
            throw new IllegalPasswordException();
        if (l < 6)
            throw new IllegalPasswordException();
        boolean hasLetter = false, hasDigit = false;
        for (int i = 0; i < l; i++) {
            final char c = pwd.charAt(i);
            if (Character.isLetter(c))
                hasLetter = true;
            else if (Character.isDigit(c))
                hasDigit = true;
        }
        if (!hasLetter || !hasDigit)
            throw new IllegalPasswordException();
    }
}
