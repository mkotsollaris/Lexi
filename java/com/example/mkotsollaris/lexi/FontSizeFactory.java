package com.example.mkotsollaris.lexi;

public class FontSizeFactory extends FontAbstractFactory
{
    @Override FontFace createFontFace()
    {
        return null;
    }

    @Override FontSize createFontSize()
    {
        return new FontSize(FontSize.FontSizeType.NORMAL);
    }
}
