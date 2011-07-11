/*
 * ListObjectScalarInt.java
 *
 * Created on August 5, 2006, 2:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct.reference;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.basic.support.TopObjectCookie;

/**
 *
 * @author awagner
 */
public interface ListObjectScalarInt 
{
    public void deleteObject();
    public void deleteObjectwithChildren();
    public TopObjectBase getObject(int i);
    public TopObjectCookie getTopObjectCookie();

}
