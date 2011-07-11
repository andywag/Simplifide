/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core

import com.simplifide.scala2.top.InterfaceError

class SegmentError(override val message:String) extends InterfaceError.Error(0,message)

class SegmentErrorList(val errors:List[SegmentError]) extends SegmentError("All")

class SegmentWarning(override val message:String) extends InterfaceError.Warning(0,message)