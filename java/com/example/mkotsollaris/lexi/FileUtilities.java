package com.example.mkotsollaris.lexi;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implements the Singleton pattern. Provides functionalities for Internal file loading and saving.
 */

public class FileUtilities
{
    private static FileUtilities fileUtilities;

    private FileUtilities()
    {
    }

    public static FileUtilities getInstance()
    {
        if(fileUtilities == null)
        {
            fileUtilities = new FileUtilities();
        }
        return fileUtilities;
    }


    ArrayList<String> getInternalFileNames()
    {
        ArrayList<String> arrayList = new ArrayList<>();
        File dirFiles = Utilities.getAppContext().getFilesDir();
        Collections.addAll(arrayList, dirFiles.list());
        return arrayList;
    }

    void createInternalFile(String fileName, String text)
    {
        FileOutputStream outputStream;
        try
        {
            outputStream = Utilities.getAppContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    String parseInternalFile(String fileName)
            throws IOException
    {
        FileInputStream fis = Utilities.getAppContext().openFileInput(fileName);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null)
        {
            sb.append(line);
        }
        return sb.toString();
    }

    public void deleteInternalFiles()
    {
        ArrayList<String> fileNames = getInternalFileNames();
        Context context = Utilities.getAppContext();
        for(String fileName : fileNames)
        {
            context.deleteFile(fileName);
        }
    }
}
