package com.example.mkotsollaris.lexi;

public class FontFaceFactory extends FontAbstractFactory
{
    @Override FontFace createFontFace()
    {
        return new FontFace(FontFace.FontFaceType.SERIF);
    }

    @Override FontSize createFontSize()
    {
        return null;
    }
}
