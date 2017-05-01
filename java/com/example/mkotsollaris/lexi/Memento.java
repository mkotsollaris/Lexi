package com.example.mkotsollaris.lexi;

import java.util.Stack;

/**
 * Implements the memento pattern.
 */
public class Memento
{
    private Stack<RowComposite> rowCompositeStack = new Stack<>();

    public Stack<RowComposite> getState()
    {
        return rowCompositeStack;
    }

    public void setState(Stack<RowComposite> rowCompositeStack)
    {
        this.rowCompositeStack = rowCompositeStack;
    }

    @Override public String toString()
    {
        //System.out.println("size " + rowCompositeStack.size());
        String s = "";
        for(int i = 0; i < rowCompositeStack.size(); i++)
        {
            s += rowCompositeStack.get(i) + "\n";
        }
        return s;
    }
}