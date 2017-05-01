package com.example.mkotsollaris.lexi;

import java.util.ArrayList;

/**
 * Implements the Composite Pattern
 */
class Composite extends Component
{
    protected ArrayList<Component> childrenArrayList = new ArrayList<>();

    public void addChild(Component customCharacter)
    {
        childrenArrayList.add(customCharacter);
    }

    public void removeChild(int index)
    {
        childrenArrayList.remove(index);
    }

    @Override Component createComponentObject()
    {
        return new Composite();
    }

    @Override public String toString()
    {
        String s = "";
        for(Component rowComponent : childrenArrayList)
        {
            s += rowComponent;
        }
        return s;
    }

    @Override public void accept(CharacterVisitor characterVisitor)
    {
        //empty code
    }
}