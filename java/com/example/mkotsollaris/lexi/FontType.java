package com.example.mkotsollaris.lexi;

public enum FontType
{
    FONT_STYLE("FontStyle"),
    FONT_SIZE("FontSize"),
    FONT_FACE("FontFace");

    private String name;

    FontType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}