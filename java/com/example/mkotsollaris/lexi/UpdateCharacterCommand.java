package com.example.mkotsollaris.lexi;

class UpdateCharacterCommand extends Command
{
    private int rowNumber;
    private int charPosition;
    private FontFace fontFace;
    private FontSize fontSize;

    public UpdateCharacterCommand(CompositeBuilder compositeBuilder,
            int rowNumber,
            int charPosition,
            FontFace fontFace,
            FontSize fontSize)
    {
        this.compositeBuilder = compositeBuilder;
        this.fontFace = fontFace;
        this.fontSize = fontSize;
        this.rowNumber = rowNumber;
        this.charPosition = charPosition;
    }

    @Override public void execute()
    {
        memento = compositeBuilder.createMemento();
        compositeBuilder.setFonts(rowNumber, fontFace, fontSize, charPosition);
    }

    @Override public void unExecute()
    {
        this.compositeBuilder.setMemento(memento);
    }
}
