/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.walk;

import java.util.ArrayList;

import antlr.collections.AST;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 21, 2004
 * Time: 2:34:15 PM
 * To change this template use File | Settings | File Templates.
 */

public class NodeIterator
{

    private TopASTNode root;
    private ArrayList list = new ArrayList();
    private int index = 0;

    public NodeIterator(TopASTNode root)
    {
        this.root = root;
        this.createList();
    }

    private void createList()
    {
        list.clear();
        this.createNodes(root);
    }

    private void createNodes(AST node)
    {
        list.add(node);
        AST fchild = (AST) node.getFirstChild();
        while (fchild != null)
        {
            createNodes(fchild);
            fchild = fchild.getNextSibling();
        }

    }

    public TopASTNode getNext()
    {
        index++;
        return (TopASTNode) list.get(index-1);
    }



    public TopASTNode getRoot() {
        return root;
    }

    public void setRoot(TopASTNode root) {
        this.root = root;
    }


}
