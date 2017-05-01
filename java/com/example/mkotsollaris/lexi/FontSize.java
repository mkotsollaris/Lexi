package com.example.mkotsollaris.lexi;

class FontSize implements FontListener
{
    private FontSizeType fontSizeType;

    public String getHTMLName()
    {
        return fontSizeType.getHtmlName();
    }

    public FontSize(FontSizeType fontSizeType)
    {
        this.fontSizeType = fontSizeType;
    }

    @Override public void updateState(String str)
    {
        if(str.equals(FontSizeType.SMALL.getTitle()))
        {
            this.fontSizeType = FontSizeType.SMALL;
        }
        else if(str.equals(FontSizeType.NORMAL.getTitle()))
        {
            this.fontSizeType = FontSizeType.NORMAL;
        }
        else if(str.equals(FontSizeType.LARGE.getTitle()))
        {
            this.fontSizeType = FontSizeType.LARGE;
        }
    }

    @Override public String toString()
    {
        return fontSizeType.toString();
    }

    public FontSizeType getFontSizeType()
    {
        return fontSizeType;
    }

    public static FontSize computeFontSize(int id)
    {
        if(id == 1) return new FontSize(FontSizeType.SMALL);
        if(id == 2) return new FontSize(FontSizeType.NORMAL);
        if(id == 3) return new FontSize(FontSizeType.LARGE);
        return null;
    }

    public static FontSize computeFontSize(String strName)
    {
        if(strName.equals(FontSizeType.SMALL.getTitle()))
            return new FontSize(FontSizeType.SMALL);
        if(strName.equals(FontSizeType.NORMAL.getTitle()))
            return new FontSize(FontSizeType.NORMAL);
        if(strName.equals(FontSizeType.LARGE.getTitle()))
            return new FontSize(FontSizeType.LARGE);
        return null;
    }

    enum FontSizeType
    {
        SMALL("Small", "small", 1), NORMAL("Normal", "", 2), LARGE("Large", "big", 3);

        private String title;
        private String htmlName;
        private int id;

        public static String[] getAllFontSizes()
        {
            FontSizeType[] fontSizeValues = FontSizeType.values();
            String[] names = new String[fontSizeValues.length];

            for(int i = 0; i < fontSizeValues.length; i++)
            {
                names[i] = fontSizeValues[i].getTitle();
            }
            return names;
        }

        FontSizeType(String title, String htmlName, int id)
        {
            this.title = title;
            this.htmlName = htmlName;
            this.id = id;
        }

        public String getTitle()
        {
            return title;
        }

        public String getHtmlName()
        {
            return htmlName;
        }

        public void setFontSize(FontSizeType fontSize)
        {
            this.htmlName = fontSize.htmlName;
            this.title = fontSize.title;
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
            if(this.id == 2)
            { return ""; }
            return "<" + htmlName + ">";
        }

        /**TODO Test*/
        public String getEndHTMLTag()
        {
            return "</" + htmlName + ">";
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