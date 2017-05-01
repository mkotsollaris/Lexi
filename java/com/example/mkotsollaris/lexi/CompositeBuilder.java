package com.example.mkotsollaris.lexi;

import java.util.Stack;

/**
 * Class Explanation: Builder pattern for the RowComposite class.
 */

class CompositeBuilder
{
    //stores the composites (RowComposites and CustomCharacters)
    private Stack<RowComposite> compositeRowStack = new Stack<>();

    CompositeBuilder()
    {
        compositeRowStack = new Stack<>();
    }

    /**
     * Adds the new character to the RowComposite. If the RowComposite does not exist, then it's
     * created. In case of the same charPosition, the value replaces the old one.
     *
     * @param customCharacter the CustomCharacter
     * @param fontFace        the FontFace
     * @param fontSize        the FontSize
     * @param rowNumber       the row number
     * @param charPosition    the position where the character will be inserted
     */
    void buildCharacter(CustomCharacter customCharacter,
            FontFace fontFace,
            FontSize fontSize,
            int rowNumber,
            int charPosition)
    {
        if(compositeRowStack.size() == 0)
        {
            buildRowComposite(0);
        }
        if(customCharacter.getChar() == '\n')
        {
            buildRowComposite(rowNumber);
        }
        else
        {
            compositeRowStack.get(rowNumber)
                    .addChild(customCharacter, fontFace, fontSize, charPosition);
            setFonts(rowNumber, fontFace, fontSize, charPosition);
        }
    }

    /**
     * Sets new fonts to the CustomCharacter.
     *
     * @param rowNumber    the row number
     * @param fontFace     the FontFace
     * @param fontSize     the FontSize
     * @param charPosition the position where the character will be inserted
     */
    void setFonts(int rowNumber, FontFace fontFace, FontSize fontSize, int charPosition)
    {
        RowComposite rowComposite = compositeRowStack.get(rowNumber);
        rowComposite
                .setFontFace(fontFace.getFontFaceType(), fontSize.getFontSizeType(), charPosition);
    }

    /**
     * Creates a new row and adds it last in the stack.
     */
    void buildRowComposite(int rowNumber)
    {
        RowComposite rowComposite = new RowComposite(rowNumber);
        compositeRowStack.push(rowComposite);
    }

    /**
     * Deletes a CustomCharacter.
     *
     * @param rowPos  the row number
     * @param charPos the position of the character in the row
     */
    void deleteCharacter(int rowPos, int charPos)
    {
        System.out.println("@deleteCharacter rowPos: " + rowPos + ", charPos" + charPos);
        RowComposite rowComposite = compositeRowStack.get(rowPos);
        rowComposite.removeChild(charPos);
    }

    /**
     * Deletes the row. Transfers all the children (CustomCharacters) to the previous row (if any).
     */
    void deleteRow(int rowPos)
    {
        System.out.println(
                "@deleteRow rowPos: " + rowPos + ", vs stackSize: " + compositeRowStack.size());
        if(rowPos > 0) transferChildren(rowPos, rowPos - 1);
        if(rowPos < compositeRowStack.size())
        {
            compositeRowStack.remove(rowPos);
        }
    }

    /**
     * Transfers the children from the fromRowPos to the row toRowPos.
     * */
    private void transferChildren(int fromRowPos, int toRowPos)
    {
        if(fromRowPos >= compositeRowStack.size()) return;
        RowComposite fromRowComposite = compositeRowStack.get(fromRowPos);
        RowComposite toRowComposite = compositeRowStack.get(toRowPos);
        for(int i = 0; i < fromRowComposite.childrenArrayList.size(); i++)
        {
            FontFace.FontFaceType fontFaceType = fromRowComposite.getFontFace(i);
            FontSize.FontSizeType fontSizeType = fromRowComposite.getFontSize(i);
            toRowComposite.addChild(fromRowComposite.childrenArrayList.get(i));
            toRowComposite.setFontFace(fontFaceType, fontSizeType,
                                       toRowComposite.childrenArrayList.size() - 1);
        }
    }

    public Memento createMemento()
    {
        Memento qMemento = new Memento();
        Stack<RowComposite> copiedRowCompositeStack = new Stack<>();
        deepCopyRowsToStack(copiedRowCompositeStack);
        qMemento.setState(copiedRowCompositeStack);
        return qMemento;
    }

    /**
     * Hard copies the rows by using the prototype pattern (in the RowComposite)
     */
    private void deepCopyRowsToStack(Stack<RowComposite> copiedRowCompositeStack)
    {
        for(int i = 0; i < compositeRowStack.size(); i++)
        {
            RowComposite rowComposite = compositeRowStack.get(i);
            RowComposite newRowComposite = rowComposite.cloneMe();
            copiedRowCompositeStack.push(newRowComposite);
        }
    }

    void setMemento(Memento memento)
    {
        this.compositeRowStack = memento.getState();
    }

    @Override public String toString()
    {
        String s = "";
        for(Component rowComponent : compositeRowStack)
        {
            s += rowComponent + "\n";
        }
        return s;
    }

    String getHTML()
    {
        String s = "";
        for(int i = 0; i < compositeRowStack.size(); i++)
        {
            RowComposite rowComposite = compositeRowStack.get(i);
            s += rowComposite.getHTML();
            if(i != compositeRowStack.size() - 1)
            {
                s += "<br>";
            }
        }
        return s;
    }

    String getFontFaceIDs()
    {
        String s = "";
        for(int i = 0; i < compositeRowStack.size(); i++)
        {
            RowComposite rowComposite = compositeRowStack.get(i);
            s += rowComposite.getFontFaceIDs();
            if(i != compositeRowStack.size() - 1)
            {
                s += "\n";
            }
        }
        return s;
    }

    String getFontSizeIDs()
    {
        String s = "";
        for(int i = 0; i < compositeRowStack.size(); i++)
        {
            RowComposite rowComposite = compositeRowStack.get(i);
            s += rowComposite.getFontSizeIDs();
            if(i != compositeRowStack.size() - 1) s += "\n";
        }
        return s;
    }

    String getCustomCharacters()
    {
        String s = "";
        for(int i = 0; i < compositeRowStack.size(); i++)
        {
            RowComposite rowComposite = compositeRowStack.get(i);
            s += rowComposite.getCustomCharacters();
            if(i != compositeRowStack.size() - 1) s += "\n";
        }
        return s;
    }

    public String getSavedFileInfo()
    {
        String s = "";
        for(int i = 0; i < compositeRowStack.size(); i++)
        {
            RowComposite rowComposite = compositeRowStack.get(i);
            s += rowComposite.getSavedString();
            if(i == compositeRowStack.size() - 1) s += "\n";
        }
        return s;
    }

    public FontSize.FontSizeType getFontSizeType(int currLine, int charPosAtRow)
    {
        return compositeRowStack.get(currLine).getFontSize(charPosAtRow);
    }

    public FontFace.FontFaceType getFontFaceType(int currLine, int charPosAtRow)
    {
        return compositeRowStack.get(currLine).getFontFace(charPosAtRow);
    }

    public CustomCharacter getCustomCharacter(int currentRow, int currentCharIndex)
    {
        return (CustomCharacter) compositeRowStack.get(currentRow).childrenArrayList
                .get(currentCharIndex);
    }

    public int getRowsNumber()
    {
        return compositeRowStack.size();
    }

    public RowComposite getRow(int currentRow)
    {
        return compositeRowStack.get(currentRow);
    }
}