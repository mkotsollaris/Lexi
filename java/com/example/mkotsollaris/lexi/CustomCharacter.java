package com.example.mkotsollaris.lexi;

/**
 * A character within the text editor.
 * Implements the Composite, Flyweight and abstract factory pattern.
 */

class CustomCharacter extends Component
{
    private final char aChar;

    CustomCharacter(char c)
    {
        aChar = c;
    }

    char getChar()
    {
        return aChar;
    }

    @Override Component createComponentObject()
    {
        return null;
    }

    public void printValue()
    {
        System.out.print(aChar);
    }

    @Override public String toString()
    {
        return String.valueOf(aChar);
    }

    @Override public void accept(CharacterVisitor characterVisitor)
    {
        characterVisitor.visitCharacter(this);
    }
}