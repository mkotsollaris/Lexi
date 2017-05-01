package com.example.mkotsollaris.lexi;

/**
 * Abstract class for implementing the Composite pattern. Concrete items should inherit it.
 */

abstract class Component implements VisitorAccepter
{
    abstract Component createComponentObject();

    public void addChild(Component rowComponent)
    {
    }

    public void removeChild(int index)
    {
    }

    public Component getChild(int index)
    {
        return null;
    }

    public int childrenNumber()
    {
        return 0;
    }
}
