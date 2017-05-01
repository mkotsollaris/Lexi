package com.example.mkotsollaris.lexi;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

/**
 * Core Activity which is implemented by the MVC Pattern.
 */
public class EditorActivity extends AppCompatActivity implements EditorObserver
{
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback()
    {
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            Log.d(TAG, "onCreateActionMode");
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.on_select_text, menu);
            menu.removeItem(android.R.id.selectAll);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
            Log.d(TAG, String.format("onActionItemClicked item=%s/%d", item.toString(),
                                     item.getItemId()));
            int start = editText.getSelectionStart();
            int end = editText.getSelectionEnd();
            System.out.println("start: " + start + ", end: " + end);

            switch(item.getItemId())
            {
                case R.id.action_font_face_on_select:
                    popupChangeFontFaceDialog(start, end);
                    return true;

                case R.id.action_font_size_on_select:
                    System.out.println("action_font_size_on_select");
                    popupChangeFontSizeDialog(start, end);
                    return true;
            }
            return false;
        }

        public void onDestroyActionMode(ActionMode mode)
        {
        }
    };
    //current fonts selected by the user
    private FontSize currFontSize = new FontSize(FontSize.FontSizeType.NORMAL);
    private FontFace currFontFace = new FontFace(FontFace.FontFaceType.SERIF);

    /**
     * Pops up the FontSizeDialog and changes the characters between the start and end within the
     * EditText's text.
     *
     * @param start the star position within the EditText text
     * @param end   the ending position within the EditText text
     */
    private void popupChangeFontSizeDialog(final int start, final int end)
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(EditorActivity.this);
        builderSingle.setTitle("Change Font Size");
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(EditorActivity.this, android.R.layout.select_dialog_item);
        for(String item : FontSize.FontSizeType.getAllFontSizes())
        {
            arrayAdapter.add(item);
        }
        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int index)
            {
                String strName = arrayAdapter.getItem(index);
                System.out.println(strName);
                FontSize fontSize = FontSize.computeFontSize(strName);
                for(int i = start; i < end; i++)
                {
                    if(editText.getText().charAt(i) == '\n') continue;
                    int currLine = computeCurrLineNumber(editText.getText().toString(), i);
                    int charPosAtRow = computeCharAtRowPos(editText.getText().toString(), i);
                    FontFace fontFace =
                            new FontFace(systemFacade.getFontFaceType(currLine, charPosAtRow));
                    //font size stays the same
                    systemFacade.updateCustomCharacter(currLine, charPosAtRow, fontFace, fontSize);
                }
            }
        });
        builderSingle.show();
    }

    /**
     * Pops up the FontFaceDialog and changes the characters between the start and end within the
     * EditText's text.
     *
     * @param start the star position within the EditText text
     * @param end   the ending position within the EditText text
     */
    private void popupChangeFontFaceDialog(final int start, final int end)
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(EditorActivity.this);
        builderSingle.setTitle("Change FontType Face");
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(EditorActivity.this, android.R.layout.select_dialog_item);
        for(String item : FontFace.FontFaceType.getAllFontFaces())
        {
            arrayAdapter.add(item);
        }
        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int index)
            {
                String strName = arrayAdapter.getItem(index);
                System.out.println(strName);
                FontFace fontFace = FontFace.computeFontFace(strName);
                for(int i = start; i < end; i++)
                {
                    if(editText.getText().charAt(i) == '\n') continue;
                    int currLine = computeCurrLineNumber(editText.getText().toString(), i);
                    int charPosAtRow = computeCharAtRowPos(editText.getText().toString(), i);
                    System.out.println("char: " + editText.getText().charAt(i) + "charPosAtRow: " +
                                               charPosAtRow + ", currLine: " + currLine);
                    FontSize fontSize =
                            new FontSize(systemFacade.getFontSizeType(currLine, charPosAtRow));
                    //font size stays the same
                    systemFacade.updateCustomCharacter(currLine, charPosAtRow, fontFace, fontSize);
                }
            }
        });
        builderSingle.show();
    }

    private EditText editText;
    private SystemFacade systemFacade;
    private EditTextStatus editTextStatus;
    private final CharSequence[] previousText = {""};

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        initializeComponents();

        editText.addTextChangedListener(new TextWatcher()
        {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            /**
             * Updates only if EditTextStatus in USER_EDIT.
             * */
            @Override public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(editTextStatus.getStatusEnum() == EditTextStatus.StatusEnum.COMMAND_EDIT)
                {
                    previousText[0] = s;
                    editTextStatus.setStatusEnum(EditTextStatus.StatusEnum.USER_EDIT);
                    return;
                }
                boolean flag = true;
                if(before == count)
                {
                    flag = false;
                }
                if(before > 0 && flag)
                {
                    for(int index = before - 1; index >= 0; index--)
                    {
                        int charPos = start + index;
                        int currRowPosition =
                                computeCurrLineNumber(previousText[0].toString(), charPos);
                        int charAtRowPos = computeCharAtRowPos(previousText[0].toString(), charPos);
                        if(previousText[0].toString().charAt(charPos) == '\n')
                        {
                            currRowPosition++;
                            systemFacade.removeRow(currRowPosition);
                            continue;
                        }
                        systemFacade.removeCustomCharacter(currRowPosition, charAtRowPos);
                    }
                }
                if(count > 0 && flag)
                {
                    for(int index = 0; index < count; index++)
                    {
                        int currRowPosition = computeCurrLineNumber(s.toString(), start);
                        int charPos = start + index;
                        int charAtRowPos = computeCharAtRowPos(s.toString(), charPos);
                        CustomCharacter customCharacter = new CustomCharacter(s.charAt(charPos));
                        systemFacade
                                .insertCustomCharacter(customCharacter, currFontFace, currFontSize,
                                                       currRowPosition, charAtRowPos);
                    }
                }
                previousText[0] = s.toString();
            }

            @Override public void afterTextChanged(Editable editable)
            {}
        });
        loadMessage();
    }

    /**
     * Initializes all the UI and facade components.
     */
    private void initializeComponents()
    {
        systemFacade = new SystemFacade();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_editorActivity);
        setSupportActionBar(myToolbar);
        systemFacade.getEditorLogic().attach(this);
        editTextStatus = EditTextStatus.getInstance();
        editTextStatus.setStatusEnum(EditTextStatus.StatusEnum.USER_EDIT);
        editText = (EditText) findViewById(R.id.editText_activityEditor);
        editText.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                displayKeyboard();
            }
        });
        editText.setOnLongClickListener(new View.OnLongClickListener()
        {
            // Called when the user long-clicks on someView
            public boolean onLongClick(View view)
            {
                // Start the CAB using the ActionMode.Callback defined above
                view.setSelected(true);
                return true;
            }
        });
        editText.setCustomSelectionActionModeCallback(mActionModeCallback);
        String decoratorString = getIntent().getExtras()
                .getString(Utilities.StartActivityBundle.DECORATOR_TYPE.name);
        if(decoratorString != null &&
                decoratorString.equals(Utilities.DecoratorBundle.BORDER.getType()))
            editText.setBackgroundResource(R.drawable.edittext_border);
    }

    /**
     * Computes the position of the character (in the specific row) based on the endPos and on the
     * number of new lines ("\n").
     * <p>
     * If the substring is equals with "\n"
     *
     * @param s      the string
     * @param endPos the ending position within the String
     */
    private int computeCharAtRowPos(String s, int endPos)
    {
        int charCount = 0;
        String subString = s.substring(0, endPos);
        for(int i = 0; i < subString.length(); i++)
        {
            char c = subString.charAt(i);
            charCount++;
            if(c == '\n')
            {
                charCount = 0;
            }
        }
        return charCount;
    }

    /**
     * Assuming that each new line "\n" takes 1 character.
     *
     * @param s      the string
     * @param endPos the ending position within the String
     */
    private int computeCurrLineNumber(String s, int endPos)
    {
        int rowCount = 0;
        String subString = s.substring(0, endPos);
        for(int i = 0; i < subString.length(); i++)
        {
            char c = subString.charAt(i);
            if(c == '\n')
            {
                rowCount++;
            }
        }
        return rowCount;
    }

    /**
     * Fix does not open properly.
     * <p>
     * FIXME breakdown REFACTOR
     */
    private void loadMessage()
    {
        if(getIntent().getExtras() == null ||
                getIntent().getExtras().getString(Utilities.StartActivityBundle.FILENAME.name) ==
                        null) return;
        String fileName =
                getIntent().getExtras().getString(Utilities.StartActivityBundle.FILENAME.name);
        try
        {
            FileInputStream fis = Utilities.getAppContext().openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            int currLine = 0;
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                int charPos = 0;
                if(line.equals("")) continue;
                if(currLine != 0)
                {
                    CustomCharacter newLineCustomChar =
                            CustomCharacterFlyweightFactory.getInstance().getCustomCharacter('\n');

                    systemFacade
                            .insertCustomCharacter(newLineCustomChar, currFontFace, currFontSize,
                                                   currLine, charPos);
                }
                parseValues(line, currLine, charPos);
                currLine++;
            }
            previousText[0] = editText.getText().toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Parses the line and updates the CompositeBuilder.
     *
     * @param line     the line String
     * @param currLine the line's current index
     * @param charPos  the character's position in the line
     */
    private void parseValues(String line, int currLine, int charPos)
    {
        for(int position = 0; position < line.length(); position += 3)
        {
            CustomCharacter customCharacter = CustomCharacterFlyweightFactory.getInstance().
                    getCustomCharacter(line.charAt(position));
            int fontFaceID = Integer.parseInt(String.valueOf(line.charAt(position + 1)));
            int fontSizeID = Integer.parseInt(String.valueOf(line.charAt(position + 2)));
            FontFace fontFace = FontFace.computeFontFace(fontFaceID);
            FontSize fontSize = FontSize.computeFontSize(fontSizeID);
            editTextStatus.setStatusEnum(EditTextStatus.StatusEnum.COMMAND_EDIT);
            systemFacade
                    .insertCustomCharacter(customCharacter, fontFace, fontSize, currLine, charPos);
            charPos++;
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_font_face_on_select:
                popupCurrFontFaceDialog("Change FontType Face",
                                        FontFace.FontFaceType.getAllFontFaces());
                return true;
            case R.id.action_font_size_on_select:
                popupCurrFontFaceDialog("Change FontType Size",
                                        FontSize.FontSizeType.getAllFontSizes());
                return true;
            case R.id.action_undo:
                String s = editText.getText().toString();
                previousText[0] = s;
                editTextStatus.setStatusEnum(EditTextStatus.StatusEnum.COMMAND_EDIT);
                systemFacade.undo();
                s = editText.getText().toString();
                previousText[0] = s;
                return true;
            case R.id.action_redo:
                s = editText.getText().toString();
                previousText[0] = s;
                editTextStatus.setStatusEnum(EditTextStatus.StatusEnum.COMMAND_EDIT);
                systemFacade.redo();
                s = editText.getText().toString();
                previousText[0] = s;
                return true;
            case R.id.action_save_as:
                popUpSaveAsDialog();
                return true;
            case R.id.action_count_characters:
                Toast.makeText(getApplicationContext(),
                               "Characters: " + systemFacade.countCharacters(), Toast.LENGTH_LONG)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Pops up the Save as Dialog.
     */
    protected void popUpSaveAsDialog()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(EditorActivity.this);
        View promptView = layoutInflater.inflate(R.layout.custom_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditorActivity.this);
        alertDialogBuilder.setView(promptView);
        final EditText editText =
                (EditText) promptView.findViewById(R.id.custom_input_dialog_edittext);
        final String[] result = new String[1];
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        result[0] = editText.getText().toString();
                        systemFacade.save(result[0]);
                        Toast.makeText(getApplicationContext(), "Content Saved Successfully.",
                                       Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * Pops up an Alert Dialog.
     *
     * @param title        the tile of the Dialog.
     * @param stringValues the options for selection.
     */
    private void popupCurrFontFaceDialog(String title, String[] stringValues)
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(EditorActivity.this);
        builderSingle.setTitle(title);
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(EditorActivity.this, android.R.layout.select_dialog_item);
        for(String item : stringValues)
        {
            arrayAdapter.add(item);
        }
        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int index)
            {
                String strName = arrayAdapter.getItem(index);
                System.out.println(strName);
                currFontFace.updateState(strName);
                updateCurrentFonts(strName);
            }
        });
        builderSingle.show();
    }

    /**
     * Updates the current fonts (FontFace and FontSize) selected by the user.
     */
    private void updateCurrentFonts(String s)
    {
        currFontFace.updateState(s);
        currFontSize.updateState(s);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override public void update()
    {
        int cursorPosition = editText.getSelectionStart();
        editTextStatus.setStatusEnum(EditTextStatus.StatusEnum.COMMAND_EDIT);
        editText.setText(Html.fromHtml(systemFacade.getHTML()));
        editTextStatus.setStatusEnum(EditTextStatus.StatusEnum.USER_EDIT);
        updateCursorPointer(cursorPosition);
    }

    /**
     * Updates the position of the Cursor pointer
     */
    private void updateCursorPointer(int cursorPosition)
    {
        if(cursorPosition > editText.getText().length())
        {
            editText.setSelection(editText.getText().length());
        }
        else
        {
            editText.setSelection(cursorPosition);
        }
    }

    /**
     * Displays the Keyboard which is focused on the EditText.
     */
    private void displayKeyboard()
    {
        if(editText != null)
        {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInputFromWindow(editText.getApplicationWindowToken(),
                                          InputMethodManager.SHOW_FORCED, 0);
        }
    }

    /**
     * Removes the keyboard.
     */
    @Override public void onPause()
    {
        super.onPause();
    }
}