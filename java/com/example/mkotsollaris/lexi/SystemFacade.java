package com.example.mkotsollaris.lexi;

/**
 * The Facade pattern; a gateway between the clients and the subsystem.
 */

final class SystemFacade
{
    private EditorController editorController;

    EditorLogic getEditorLogic()
    {
        return editorController.getEditorLogic();
    }

    SystemFacade()
    {
        editorController = new EditorController();
    }

    /**
     * Inserts a new character with the proper Fonts to the according row and charPosition.
     */
    void insertCustomCharacter(CustomCharacter customCharacter,
            FontFace fontFace,
            FontSize fontSize,
            int rowPosition,
            int charPosition)
    {
        editorController.insertCustomCharacter(customCharacter, fontFace, fontSize, rowPosition,
                                               charPosition);
    }

    /**
     * Removes the CustomCharacter from the rowNumber and the charPosition.
     */
    void removeCustomCharacter(int rowNumber, int charPosition)
    {
        editorController.removeCustomCharacter(rowNumber, charPosition);
    }

    /**
     * Removes the row.
     */
    void removeRow(int rowPos)
    {
        editorController.removeRow(rowPos);
    }

    /**
     * Updates the fonts of the CustomCharacter.
     */
    void updateCustomCharacter(int rowNumber,
            int charPosition,
            FontFace fontFace,
            FontSize fontSize)
    {
        editorController.updateCustomCharacter(rowNumber, charPosition, fontFace, fontSize);
    }

    /**
     * Performs the undo action.
     */
    void undo()
    {
        editorController.undo();
    }

    /**
     * Performs the redo action.
     */
    void redo()
    {
        editorController.redo();
    }

    /**
     * Performs the save action.
     */
    void save(String fileName)
    {
        editorController.save(fileName);
    }

    /**
     * Returns the HTML code representing the current TextBuilder.
     */
    String getHTML()
    {
        return editorController.getEditorLogic().getHTML();
    }

    /**
     * Returns the FontSizeType of the requested CustomCharacter.
     */
    FontSize.FontSizeType getFontSizeType(int currLine, int charPosAtRow)
    {
        return editorController.getFontSizeType(currLine, charPosAtRow);
    }

    /**
     * Returns the FontFaceType of the requested CustomCharacter.
     */
    FontFace.FontFaceType getFontFaceType(int currLine, int charPosAtRow)
    {
        return editorController.getFontFaceType(currLine, charPosAtRow);
    }

    /**
     * Counts the words in the text by using the Iterator and Visitor patterns.
     */
    int countCharacters()
    {
        CharacterVisitor characterVisitor = new CharacterVisitor();
        CharacterIterator characterIterator =
                new CharacterIterator(editorController.getCompositeBuilder());
        characterIterator.first();
        while(!characterIterator.isDone())
        {
            CustomCharacter customCharacter = characterIterator.getCurrent();
            customCharacter.accept(characterVisitor);
            characterIterator.next();
        }
        return characterVisitor.getResult();
    }
}
