/*
 * FunctionComparator.java
 *
 * Created on March 19, 2007, 1:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.newfunction;

import java.util.List;

import com.simplifide.base.basic.struct.ReferenceItemComparator;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.types.TypeComparator;

/**
 *
 * @author Andy
 */
public class FunctionComparator{
    
    private static BaseFunctionComparator BASE_COMPARE = new BaseFunctionComparator();
    private static FunctionDeclarationComparator DEC_COMPARE = new FunctionDeclarationComparator();
    /** Creates a new instance of FunctionComparator */
    
    public static BaseFunctionComparator getBaseInstance() {return BASE_COMPARE;}
    public static FunctionDeclarationComparator getDecInstance() {return DEC_COMPARE;}
    
    public static class BaseFunctionComparator<T extends BaseFunction> extends ReferenceItemComparator<T> {
        public BaseFunctionComparator() {super();}
        
         public int compare(ReferenceItem<T> o1, ReferenceItem<T> o2) {
            ReferenceItem<FunctionDeclaration> funcRef1 = o1.getObject().getDeclarationRef();
            T obj2 = o2.getObject();
            ReferenceItem<FunctionDeclaration> funcRef2 = obj2.getDeclarationRef();
            return getDecInstance().compare(funcRef1, funcRef2);
        }
    }
    
    public static class FunctionDeclarationComparator<T extends FunctionDeclaration> extends ReferenceItemComparator<T> {
        public FunctionDeclarationComparator() {}
        
        private int checkNumberFields(ReferenceItem<T> o1, ReferenceItem<T> o2) {
            int number1 = o1.getObject().getPortsR().getObject().getnumber();
            int number2 = o2.getObject().getPortsR().getObject().getnumber();
            if (number1 > number2) return 1;
            if (number1 < number2) return -1;
            return 0;
        }
        
        /*private int checkOutputType(ReferenceItem<T> o1, ReferenceItem<T> o2) {
            ReferenceItem<? extends TypeVar> type1 = o1.getObject().getOutputType();
            ReferenceItem<? extends TypeVar> type2 = o2.getObject().getOutputType();
            
            return TypeComparator.getInstance().compare(type1,type2);
        }*/
        
        private int checkTypeFields(ReferenceItem<T> o1, ReferenceItem<T> o2) {
        	
            List<PortTop> list1 = o1.getObject().getOrderedList();
            List<PortTop> list2 = o2.getObject().getOrderedList();
            for (int i=0;i<list1.size();i++) {
                PortTop port1 = list1.get(i);
                PortTop port2 = list2.get(i);
                int compare = TypeComparator.getInstance().compare(port1.getTypeReference(),port2.getTypeReference());
                if (compare != 0) return compare;
            }
            return 0;
        }
        
        public int compare(ReferenceItem<T> o1, ReferenceItem<T> o2) {
            int check1 = super.compare(o1,o2);
            if (check1 != 0) return check1;
            int check2 = checkNumberFields(o1,o2);
            if (check2 != 0) return check2;
            //int check3 = checkOutputType(o1,o2);
            //if (check3 != 0) return check3;
            int check4 = checkTypeFields(o1,o2);
            
            return check4;
        }
    }
    
    
}
