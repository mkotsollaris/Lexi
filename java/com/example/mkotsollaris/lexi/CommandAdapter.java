package com.example.mkotsollaris.lexi;

import java.util.ArrayList;

/**
 * Implements the Adapter Pattern.
 */

public class CommandAdapter implements GOFList
{
    private ArrayList<Command> arrayList = new ArrayList<>();
    /**
     * Helps for the undo/redo implementation
     */
    private int pointer;

    @Override public int size()
    {
        return arrayList.size();
    }

    @Override public Command get(int index)
    {
        return arrayList.get(index);
    }

    @Override public void add(Object object)
    {
        //todo
    }

    /**
     * Before adding, remove all the commands from pointer and after.
     */
    @Override public void add(int index, Object object)
    {
        removeCommandsAfterPointer();
        arrayList.add(index, (Command) object);
    }

    @Override public void delete(int index)
    {
        arrayList.remove(index);
    }


    @Override public void deleteAll()
    {
        arrayList.clear();
    }

    @Override public void printAll()
    {
        for(int i = 0; i < arrayList.size(); i++) System.out.println(arrayList.get(i));
    }

    private void removeCommandsAfterPointer()
    {
        for(int i = pointer; i < arrayList.size(); i++)
        {
            arrayList.remove(i);
        }
    }
}
