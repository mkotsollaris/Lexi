package com.example.mkotsollaris.lexi;

/**
 * Implements the Visitor pattern
 */

class CharacterVisitor
{
    private int count = 0;

    void visitCharacter(CustomCharacter customCharacter)
    {
        count++;
    }

    public int getResult()
    {
        return count;
    }
}