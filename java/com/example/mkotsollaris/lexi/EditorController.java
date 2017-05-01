package com.example.mkotsollaris.lexi;

/**
 * Controller for the MVC Pattern.
 */

public class EditorController implements EditorObserver
{
    private EditorLogic editorLogic;

    EditorLogic getEditorLogic()
    {
        return editorLogic;
    }

    public EditorController()
    {
        editorLogic = new EditorLogic();
        editorLogic.attach(this);
    }

    /**
     */
    public void insertCustomCharacter(CustomCharacter customCharacter,
            FontFace fontFace,
            FontSize fontSize,
            int charPos,
            int rowPos)
    {
        editorLogic.insertCustomCharacter(customCharacter, fontFace, fontSize, charPos, rowPos);
    }

    public void removeCustomCharacter(int rowPos, int charPos)
    {
        editorLogic.removeCustomCharacter(rowPos, charPos);
    }

    public void removeRow(int rowPos)
    {
        editorLogic.removeRow(rowPos);
    }

    void undo()
    {
        editorLogic.undo();
    }

    void redo()
    {
        editorLogic.redo();
    }

    /**
     * Saves into the file the CustomCharacters, FontFaces and FontSizes
     */
    void save(String fileName)
    {
        FileUtilities fileUtilities = FileUtilities.getInstance();
        String customCharactersString = editorLogic.getCustomCharacters();
        String fontFacesString = editorLogic.getFontFaceIDs();
        String fontSizesString = editorLogic.getFontSizesIDs();
        String mergedString = merge(customCharactersString, fontFacesString, fontSizesString);
        fileUtilities.createInternalFile(fileName, mergedString);
    }

    /**
     * Merges the CustomCharacter, FontFace and FontSize together.
     */
    private String merge(String customCharactersString,
            String fontFacesString,
            String fontSizesString)
    {
        String s = "";
        for(int i = 0; i < customCharactersString.length(); i++)
        {
            s += String.valueOf(customCharactersString.charAt(i));
            s += String.valueOf(fontFacesString.charAt(i));
            s += String.valueOf(fontSizesString.charAt(i));
        }

        return s;
    }

    @Override public void update()
    {
        System.out.println("@EditorController update");
    }

    public void updateCustomCharacter(int rowNumber,
            int charPosition,
            FontFace fontFace,
            FontSize fontSize)
    {
        editorLogic.updateCustomCharacter(rowNumber, charPosition, fontFace, fontSize);
    }

    public FontSize.FontSizeType getFontSizeType(int currLine, int charPosAtRow)
    {
        return editorLogic.getFontSizeType(currLine, charPosAtRow);
    }

    public FontFace.FontFaceType getFontFaceType(int currLine, int charPosAtRow)
    {
        return editorLogic.getFontFaceType(currLine, charPosAtRow);
    }

    public CompositeBuilder getCompositeBuilder()
    {
        return editorLogic.getCompositeBuilder();
    }
}