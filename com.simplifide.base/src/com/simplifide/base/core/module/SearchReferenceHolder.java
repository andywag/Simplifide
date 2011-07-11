/*
 * SearchReferenceHolder.java
 *
 * Created on October 23, 2006, 9:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.module;

import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy Wagner
 */
public interface SearchReferenceHolder {
    public ReferenceItem getSearchReference();
    public ReferenceItem getContext();
    public void setContext(ReferenceItem context);
}
