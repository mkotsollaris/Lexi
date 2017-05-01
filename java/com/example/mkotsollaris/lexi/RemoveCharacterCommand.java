package com.example.mkotsollaris.lexi;

public class RemoveCharacterCommand extends Command
{
    private int rowNumber;
    private int charPosition;

    public RemoveCharacterCommand(CompositeBuilder compositeBuilder, int rowNumber,
            int charPosition)
    {
        this.compositeBuilder = compositeBuilder;
        this.rowNumber = rowNumber;
        this.charPosition = charPosition;
    }

    @Override public void execute()
    {
        memento = compositeBuilder.createMemento();
        compositeBuilder.deleteCharacter(rowNumber, charPosition);
    }

    @Override public void unExecute()
    {
        compositeBuilder.setMemento(memento);
    }
}
