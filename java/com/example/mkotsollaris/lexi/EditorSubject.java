package com.example.mkotsollaris.lexi;

import java.util.ArrayList;

public abstract class EditorSubject
{
    private ArrayList<EditorObserver> editorObserverList = new ArrayList<>();

    public void attach(EditorObserver editorObserver)
    {
        editorObserverList.add(editorObserver);
    }

    /**
     * Notifies observers to run execute
     */
    protected void notifyObservers()
    {
        for(int i = 0; i < editorObserverList.size(); i++)
        {
            editorObserverList.get(i).update();
        }
    }
}