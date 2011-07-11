/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.context

abstract class InstanceContext {

    def module:ModuleContext;
    def enclosing:ModuleContext;
	
}
