package com.example.mkotsollaris.lexi;

/**
 * Implements the Command pattern.
 */

public abstract class Command
{
    protected CompositeBuilder compositeBuilder;

    protected Memento memento;

    public abstract void execute();

    public abstract void unExecute();
}