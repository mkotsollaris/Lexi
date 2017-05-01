package com.example.mkotsollaris.lexi;

import java.util.Stack;

/**
 * Represents the Model. Part of the Observer, MVC, Command and Memento Patterns.
 */
class EditorLogic extends EditorSubject
{
    private Stack<Command> commandStack;
    //counter for the undo/redo algorithm
    private int undoRedoPointer = -1;
    private CompositeBuilder compositeBuilder;

    public EditorLogic()
    {
        commandStack = new Stack<>();
        compositeBuilder = new CompositeBuilder();
    }

    /**
     * Performs the Insert Command
     */
    public void insertCustomCharacter(CustomCharacter customCharacter,
            FontFace fontFace,
            FontSize fontSize,
            int rowPos,
            int charPos)
    {
        deleteElementsAfterPointer(undoRedoPointer);
        Command command =
                new InsertCharacterCommand(compositeBuilder, customCharacter, fontFace, fontSize,
                                           rowPos, charPos);
        command.execute();
        commandStack.push(command);
        undoRedoPointer++;
        notifyObservers();
    }

    /**
     * Performs the Remove Command.
     */
    public void removeCustomCharacter(int rowPos, int charPos)
    {
        Command command = new RemoveCharacterCommand(compositeBuilder, rowPos, charPos);
        command.execute();
        commandStack.push(command);
        undoRedoPointer++;
        notifyObservers();
    }

    /**
     * Removes the row from the compositeBuilder.
     */
    public void removeRow(int rowPos)
    {
        compositeBuilder.deleteRow(rowPos);
    }

    /**
     * Updates the fonts of a specific CustomCharacter
     */
    public void updateCustomCharacter(int rowNumber,
            int charPosition,
            FontFace fontFace,
            FontSize fontSize)
    {
        Command command =
                new UpdateCharacterCommand(compositeBuilder, rowNumber, charPosition, fontFace,
                                           fontSize);
        command.execute();
        commandStack.push(command);
        undoRedoPointer++;
        notifyObservers();
    }

    /**
     * Performs the undo command
     */
    void undo()
    {
        System.out
                .println("undoPointer: " + undoRedoPointer + ", stackSize: " + commandStack.size());
        Command command = commandStack.get(undoRedoPointer);
        command.unExecute();
        undoRedoPointer--;
        notifyObservers();
    }

    /**
     * Performs the redo command
     */
    void redo()
    {
        if(undoRedoPointer == commandStack.size() - 1)
        { return; }
        undoRedoPointer++;
        Command command = commandStack.get(undoRedoPointer);
        command.execute();
        notifyObservers();
    }

    private void deleteElementsAfterPointer(int undoRedoPointer)
    {
        if(commandStack.size() < 1)
        { return; }
        for(int i = commandStack.size() - 1; i > undoRedoPointer; i--)
        {
            commandStack.remove(i);
        }
    }

    /**
     * Returns the HTML represented in the CompositeBuilder.
     */
    String getHTML()
    {
        return compositeBuilder.getHTML();
    }

    String getFontFaceIDs()
    {
        return compositeBuilder.getFontFaceIDs();
    }

    String getFontSizesIDs()
    {
        return compositeBuilder.getFontSizeIDs();
    }

    String getCustomCharacters()
    {
        return compositeBuilder.getCustomCharacters();
    }

    public FontSize.FontSizeType getFontSizeType(int currLine, int charPosAtRow)
    {
        return compositeBuilder.getFontSizeType(currLine, charPosAtRow);
    }

    public FontFace.FontFaceType getFontFaceType(int currLine, int charPosAtRow)
    {
        return compositeBuilder.getFontFaceType(currLine, charPosAtRow);
    }

    public CompositeBuilder getCompositeBuilder()
    {
        return compositeBuilder;
    }
}
