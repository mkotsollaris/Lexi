package com.example.mkotsollaris.lexi;

/**
 * Class Explanation: Implements the Iterator pattern
 */

public class CharacterIterator
{
    private CompositeBuilder compositeBuilder;
    private int currentRow;
    private int currentCharIndex;

    CharacterIterator(CompositeBuilder compositeBuilder)
    {
        this.compositeBuilder = compositeBuilder;
        currentRow = 0;
        currentCharIndex = 0;
    }

    void first()
    {
        currentRow = 0;
    }

    CustomCharacter getCurrent()
    {
        return compositeBuilder.getCustomCharacter(currentRow, currentCharIndex);
    }

    boolean isDone()
    {
        return (currentRow == compositeBuilder.getRowsNumber());
    }

    void next()
    {
        //case of row with no CustomCharacters
        while(true)
        {
            if(currentRow == compositeBuilder.getRowsNumber()) return;
            //row has no children or all children have been iterated
            if(compositeBuilder.getRow(currentRow).getChildrenSize() == 0 ||
                    currentCharIndex == compositeBuilder.getRow(currentRow).getChildrenSize() - 1)
            {
                currentCharIndex = -1;
                currentRow++;
            }
            else
            {
                if(currentCharIndex <= compositeBuilder.getRow(currentRow).getChildrenSize() - 1)
                {
                    break;
                }
            }
        }
        currentCharIndex++;
    }
}