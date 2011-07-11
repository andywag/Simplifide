/*
 * HierarchyInterface.java
 *
 * Created on March 30, 2007, 12:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.project;

import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy
 */
public interface HierarchyInterface {
    
    public void openHierarchy(ReferenceItem<CoreProjectSuite> suite);
    public void changeProject(CoreProjectSuite suite);
    public void closeHierarchy();
}
