/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.loop;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.generate.ParameterSpecification;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.loop.LoopIterator;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// FOR parameter_specification

public class IterationForNode extends TopASTNode{

    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        ReferenceItem<ParameterSpecification> paramRef = (ReferenceItem<ParameterSpecification>) child.generateModule(context);
        LoopIterator.For fo = new LoopIterator.For(paramRef);
        return fo.createReferenceItem();
    }
}
