package com.example.mkotsollaris.lexi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartActivity extends Activity
{
    private String decoratorBundleString = Utilities.DecoratorBundle.BORDER.getType();

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button newButton = (Button) findViewById(R.id.newDocumment_startActivity);
        Button openButton = (Button) findViewById(R.id.openButton_startActivity);
        openButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                popUpAlertDialog();
            }
        });
        newButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                Intent myIntent = new Intent(StartActivity.this, EditorActivity.class);
                myIntent.putExtra(Utilities.StartActivityBundle.DECORATOR_TYPE.name,
                                decoratorBundleString);
                startActivity(myIntent);
            }
        });

        ImageView settingsImageView = (ImageView) findViewById(R.id.settings_imageView);
        settingsImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                //TODO Update Enum
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(StartActivity.this);
                builderSingle.setTitle("Select Decorator");

                final ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<>(StartActivity.this, android.R.layout.select_dialog_item);

                Utilities.DecoratorBundle[] decoratorBundles =
                        Utilities.DecoratorBundle.getDecoratorTypes();
                for(Utilities.DecoratorBundle decoratorBundle : decoratorBundles)
                    arrayAdapter.add(decoratorBundle.getType());


                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
                {
                    @Override public void onClick(DialogInterface dialog, int index)
                    {
                        String strName = arrayAdapter.getItem(index);
                        System.out.println(strName);
                        decoratorBundleString = strName;

                    }
                });

                builderSingle.setPositiveButton("cancel", new DialogInterface.OnClickListener()
                {
                    @Override public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });
    }

    private void popUpAlertDialog()
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(StartActivity.this);
        builderSingle.setTitle("Select the file to load:");

        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(StartActivity.this, android.R.layout.select_dialog_item);
        final FileUtilities fileUtilities = FileUtilities.getInstance();
        ArrayList<String> fileNamesArrayList = fileUtilities.getInternalFileNames();
        for(String fileName : fileNamesArrayList)
        {
            arrayAdapter.add(fileName);
        }

        builderSingle.setPositiveButton("cancel", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        builderSingle.setNegativeButton("Clear Memory", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                fileUtilities.deleteInternalFiles();
                Toast.makeText(getApplicationContext(), "Files Deleted Successfully.",
                               Toast.LENGTH_SHORT).show();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int index)
            {
                String strName = arrayAdapter.getItem(index);
                Intent intent = new Intent(StartActivity.this, EditorActivity.class);
                intent.putExtra(Utilities.StartActivityBundle.FILENAME.name, strName);
                intent.putExtra(Utilities.StartActivityBundle.DECORATOR_TYPE.name,
                                decoratorBundleString);
                startActivity(intent);
            }
        });
        builderSingle.show();
    }
}