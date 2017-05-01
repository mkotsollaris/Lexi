package com.example.mkotsollaris.lexi;

import java.util.ArrayList;

/**
 * Represents a Row. Saves the extrinsic state of the CustomCharacter (FontFace and FontSize).
 */
class RowComposite extends Composite
{
    /**
     * The rowNumber number.
     */
    private final int rowNumber;
    /**
     * The FontFace list.
     */
    private final ArrayList<FontFace.FontFaceType> fontFaceList;
    /**
     * The FontSize list.
     */
    private final ArrayList<FontSize.FontSizeType> fontSizeList;


    public void setFontFace(FontFace.FontFaceType fontFaceType,
            FontSize.FontSizeType fontSizeType,
            int charPosition)
    {
        if(charPosition <= fontFaceList.size() - 1)
        {
            fontFaceList.remove(charPosition);
            fontSizeList.remove(charPosition);
        }
        fontFaceList.add(charPosition, fontFaceType);
        fontSizeList.add(charPosition, fontSizeType);
    }

    RowComposite(int row)
    {
        fontFaceList = new ArrayList<>();
        fontSizeList = new ArrayList<>();
        this.rowNumber = row;
    }

    /**
     * Replaces the character if it's getting added in the same place
     */
    void addChild(CustomCharacter customCharacter,
            FontFace fontFace,
            FontSize fontSize,
            int characterPos)
    {
        this.childrenArrayList.add(characterPos, customCharacter);
        fontFaceList.add(characterPos, fontFace.getFontFaceType());
        fontSizeList.add(characterPos, fontSize.getFontSizeType());
    }

    public void removeChild(int index)
    {
        System.out.println("@removeChild ChildrenSize: " + childrenArrayList.size());
        if(index >= childrenArrayList.size()) return;
        childrenArrayList.remove(index);
        fontFaceList.remove(index);
        fontSizeList.remove(index);
    }

    /**
     * Implements the Prototype Pattern; uses Deep cloning.
     */
    RowComposite cloneMe()
    {

        ArrayList<FontFace.FontFaceType> fontFaceListCopy = new ArrayList<>();
        ArrayList<FontSize.FontSizeType> fontSizeListCopy = new ArrayList<>();
        for(int i = 0; i < fontFaceList.size(); i++)
        {
            fontFaceListCopy.add(fontFaceList.get(i));
            fontSizeListCopy.add(fontSizeList.get(i));
        }
        ArrayList<Component> childrenArrayListCopied = new ArrayList<>();
        for(int i = 0; i < childrenArrayList.size(); i++)
        {
            childrenArrayListCopied.add(i, childrenArrayList.get(i));
        }
        return new RowComposite(rowNumber, childrenArrayListCopied, fontFaceListCopy,
                                fontSizeListCopy);
    }

    private RowComposite(int rowNumber,
            ArrayList<Component> arrayList,
            ArrayList<FontFace.FontFaceType> fontFaceMapCopy,
            ArrayList<FontSize.FontSizeType> fontSizeMapCopy)
    {
        this.rowNumber = rowNumber;
        this.fontFaceList = fontFaceMapCopy;
        this.fontSizeList = fontSizeMapCopy;
        this.childrenArrayList = arrayList;
    }

    @Override public String toString()
    {
        String s = "";
        for(int i = 0; i < childrenArrayList.size(); i++)
        {
            s += childrenArrayList.get(i).toString();
            if(i != childrenArrayList.size() - 1)
            {
                s += ", ";
            }
        }
        s += "\nFontFace Map: \n";
        for(int i = 0; i < fontFaceList.size(); i++)
        {
            s += "Pos = " + i + ", fontFace: " + fontFaceList.get(i) + ", FontSize: " +
                    fontSizeList.get(i);
        }
        return s;
    }

    /**
     * Forms an HTML of the RowComposite.
     */
    String getHTML()
    {
        System.out.println("@RowComposite getHTML childrensize: " + childrenArrayList.size());
        String s = "";
        for(int i = 0; i < childrenArrayList.size(); i++)
        {
            s += fontFaceList.get(i).getStartHTMLTag();
            s += fontSizeList.get(i).getStartHTMLTag();
            s += childrenArrayList.get(i).toString();
            s += fontSizeList.get(i).getEndHTMLTag();
            s += fontFaceList.get(i).getEndHTMLTag();
        }
        return s;
    }

    String getFontFaceIDs()
    {
        String s = "";
        for(int i = 0; i < fontFaceList.size(); i++)
        {
            s += fontFaceList.get(i).getID();
        }
        return s;
    }

    String getFontSizeIDs()
    {
        String s = "";
        for(int i = 0; i < fontSizeList.size(); i++)
        {
            s += fontSizeList.get(i).getID();
        }
        return s;
    }

    /**
     * TODO test
     */
    String getCustomCharacters()
    {
        String s = "";
        for(int i = 0; i < childrenArrayList.size(); i++)
        {
            s += childrenArrayList.get(i);
        }
        return s;
    }

    public String getSavedString()
    {
        String s = "";
        for(int i = 0; i < childrenArrayList.size(); i++)
        {
            s += fontFaceList.get(i).getID();
            s += fontSizeList.get(i).getID();
            s += childrenArrayList.get(i);
        }
        return s;
    }

    public FontSize.FontSizeType getFontSize(int charPosAtRow)
    {
        return fontSizeList.get(charPosAtRow);
    }

    public FontFace.FontFaceType getFontFace(int charPosAtRow)
    {
        return fontFaceList.get(charPosAtRow);
    }

    public int getChildrenSize()
    {
        return childrenArrayList.size();
    }
}