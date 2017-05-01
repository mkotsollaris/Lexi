package com.example.mkotsollaris.lexi;

public class InsertCharacterCommand extends Command
{
    private CustomCharacter customCharacter;
    private FontFace fontFace;
    private FontSize fontSize;
    private int rowNumber;
    private int charPosition;

    public InsertCharacterCommand(CompositeBuilder compositeBuilder,
            CustomCharacter customCharacter, FontFace fontFace, FontSize fontSize, int rowPosition,
            int charPosition)
    {
        this.compositeBuilder = compositeBuilder;
        this.customCharacter = customCharacter;
        this.fontFace = fontFace;
        this.fontSize = fontSize;
        this.rowNumber = rowPosition;
        this.charPosition = charPosition;
    }

    @Override public void execute()
    {
        memento = compositeBuilder.createMemento();
        compositeBuilder
                .buildCharacter(customCharacter, fontFace, fontSize, rowNumber, charPosition);
    }

    @Override public void unExecute()
    {
        this.compositeBuilder.setMemento(memento);
    }
}
