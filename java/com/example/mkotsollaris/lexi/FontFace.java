package com.example.mkotsollaris.lexi;

/**
 * Class Explanation: Used to store extrinsic state. Implements Flyweight itself
 */

public class FontFace implements FontListener
{
    private FontFaceType fontFaceType;

    public FontFace(FontFaceType fontFaceType)
    {
        //todo update to flyweight pool
        this.fontFaceType = fontFaceType;
    }

    @Override public String toString()
    {
        return fontFaceType.toString();
    }

    public String getHTMLName()
    {
        return fontFaceType.getHtmlName();
    }

    @Override public void updateState(String str)
    {
        if(str.equals(FontFaceType.CURSIVE.getTitle()))
        {
            this.fontFaceType = FontFaceType.CURSIVE;
        }
        if(str.equals(FontFaceType.MONOSPACE.getTitle()))
        {
            this.fontFaceType = FontFaceType.MONOSPACE;
        }
        if(str.equals(FontFaceType.SERIF.getTitle()))
        {
            this.fontFaceType = FontFaceType.SERIF;
        }
    }

    public FontFaceType getFontFaceType()
    {
        return fontFaceType;
    }

    public static FontFace computeFontFace(int id)
    {
        if(id == 1) return new FontFace(FontFaceType.CURSIVE);
        if(id == 2) return new FontFace(FontFaceType.MONOSPACE);
        if(id == 3) return new FontFace(FontFaceType.SERIF);
        return null;
    }

    public static FontFace computeFontFace(String strName)
    {
        if(strName.equals(FontFaceType.CURSIVE.getTitle()))
            return new FontFace(FontFaceType.CURSIVE);
        if(strName.equals(FontFaceType.MONOSPACE.getTitle()))
            return new FontFace(FontFaceType.MONOSPACE);
        if(strName.equals(FontFaceType.SERIF.getTitle())) return new FontFace(FontFaceType.SERIF);
        return null;
    }

    /**
     * Created by mkotsollaris on 2017-03-19.
     * Class Explanation: TODO
     */
    enum FontFaceType
    {
        CURSIVE("Cursive", "cursive", 1), MONOSPACE("Monospace", "monospace", 2),
        SERIF("Serif", "serif", 3);

        private String title;
        private String htmlName;
        private int id;

        FontFaceType(String title, String htmlName, int number)
        {
            this.title = title;
            this.htmlName = htmlName;
            this.id = number;
        }

        public static String[] getAllFontFaces()
        {
            FontFaceType[] fontSizeValues = FontFaceType.values();
            String[] names = new String[fontSizeValues.length];

            for(int i = 0; i < fontSizeValues.length; i++)
            {
                names[i] = fontSizeValues[i].getTitle();
            }
            return names;
        }

        public String getTitle()
        {
            return title;
        }

        public String getHtmlName()
        {
            return htmlName;
        }

        public void setFontFace(FontFaceType fontFace)
        {
            this.htmlName = fontFace.htmlName;
            this.title = fontFace.title;
        }

        /**
         * Html.fromHtml("<font face=\"monospace\"><small>" +
         * "description"
         * + "</small>" + "<br /></font>" +
         * "<font <font face=\"cursive\"><small>" +
         * "DateAdded" +
         * "</small></font><font face=\"Comic sans MS\"> Test</font>"));
         */
        public String getStartHTMLTag()
        {
            return "<font face=\"" + htmlName + "\">";
        }

        public String getEndHTMLTag()
        {
            return "</font>";
        }

        public int getID()
        {
            return id;
        }

        @Override public String toString()
        {
            return htmlName + ", id: " + id;
        }
    }
}
