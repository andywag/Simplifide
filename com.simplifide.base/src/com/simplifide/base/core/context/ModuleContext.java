/*
 * Context.java
 *
 * Created on January 31, 2006, 3:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.context;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;


/**
 *
 * @author awagner
 */
public class ModuleContext extends ModuleObject
{

    private ModuleObject library;
    /** Creates a new instance of Context */
    public ModuleContext() {}
    public ModuleContext(String name){super(name);}
    public ModuleContext(ModuleObject library)
    {
        super(library.getname() +"(Context)");
        this.library = library;
    }
    
    

   
    
    /** Creates a copy of this context item */
    public TopObjectBase copyContext() {
        ModuleContext ncontext = new ModuleContext(this.getLibrary());
        ncontext.addSelfList(this);
        return ncontext;
    }
    
  

   /** Adds a copy of this context expression to the current if the library is the same
    *  This method is used only for create a common context for multiple elements */
   public void addUniqueContext(ModuleContext context) 
   {

       for (int i=0;i<context.getnumber();i++)
        {
            ModuleContextItem item = (ModuleContextItem) context.getObject(i);
            for (int j=0;j<this.getnumber();j++)
            {
                ModuleContextItem litem = (ModuleContextItem) this.getObject(j);
                if (item.getItem().equals(litem.getItem())) return;
            }

            this.addObject(item);
        }
   }
   
   

    public ModuleObject getLibrary() {
        return library;
    }

    public void setLibrary(ModuleObject library) {
        this.library = library;
    }

  
  
    
}
