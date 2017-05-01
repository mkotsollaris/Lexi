package com.example.mkotsollaris.lexi;

import android.app.Application;
import android.content.Context;

/**
 * Contains useful functionalities for the application (Bundle, context etc.)
 */

public class Utilities extends Application
{
    private static Context context;

    public void onCreate()
    {
        super.onCreate();
        Utilities.context = getApplicationContext();
    }

    public static Context getAppContext()
    {
        return Utilities.context;
    }

    enum StartActivityBundle
    {
        FILENAME("FileName"), DECORATOR_TYPE("Decorator");

        String name;

        StartActivityBundle(String name)
        {
            this.name = name;
        }
    }

    enum DecoratorBundle
    {
        NORMAL("Normal Decorator"), BORDER("Border Decorator");

        private String type;

        DecoratorBundle(String type)
        {
            this.type = type;
        }

        static DecoratorBundle[] getDecoratorTypes()
        {
            return DecoratorBundle.values();
        }

        public String getType()
        {
            return type;
        }

        public static DecoratorBundle setDecoratorType(String strName)
        {
            if(strName.equals(NORMAL.getType())) return DecoratorBundle.NORMAL;
            else if(strName.equals(BORDER.getType())) return DecoratorBundle.BORDER;
            return null;
        }
    }
}
