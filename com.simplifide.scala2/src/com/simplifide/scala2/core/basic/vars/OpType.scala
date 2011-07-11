/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

/** Class which contains the type of signal related to it's operation
*/
class OpType {
  /** Returns a list of signal declarations associated with this type */  
  def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] = List()
  def getIODeclaration(signal:SignalNew):List[SignalDeclarationNew] = List()
  def isOutput:Boolean = false
  def isInput:Boolean  = false;

}

object OpType {
   object Input extends OpType{
    override def isInput:Boolean = true;
    override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
      List(new SignalDeclarationNew.IOExtra(signal))
    override def getIODeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
      List(new SignalDeclarationNew.Input(signal))
  }
  
   object Output extends OpType{
     override def isOutput:Boolean = true;
     override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
      List(new SignalDeclarationNew.IOExtra(signal))
    override def getIODeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
      List(new SignalDeclarationNew.Output(signal))
  }
  
   object Signal extends OpType{
    override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
      List(new SignalDeclarationNew.Signal(signal))
    }
    
   object Signalr extends OpType{
    override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
      List(new SignalDeclarationNew.Reg(signal))
    }

   object SignalAndReg extends OpType{
    override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] =
      List(new SignalDeclarationNew.Signal(signal),new SignalDeclarationNew.Reg(signal))
    }
   
   object Reg extends OpType{
     override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
      List(new SignalDeclarationNew.Reg(signal))
    }
   object Constant extends OpType 
  
   object ModuleInput extends OpType {
      override def isInput:Boolean = true;
      override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
        List(new SignalDeclarationNew.ModuleInput(signal))
   }
   
   object ModuleOutput extends OpType {
     override def isOutput:Boolean = true;
     override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] = 
      List(new SignalDeclarationNew.ModuleOutput(signal))
   }

   object ModuleRegOutput extends OpType {
     override def isOutput:Boolean = true;
     override def getSignalDeclaration(signal:SignalNew):List[SignalDeclarationNew] =
      List(new SignalDeclarationNew.ModuleOutput(signal),new SignalDeclarationNew.Reg(signal))
   }

  
}
