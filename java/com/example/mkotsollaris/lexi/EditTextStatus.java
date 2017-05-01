package com.example.mkotsollaris.lexi;

/**
 * Helper class for representing an EditText edit from the UI or a command.
 * Needed for the implementation of the MVC (Observer) pattern.
 * <p>
 * Implements the Singleton pattern.
 */

class EditTextStatus
{
    private StatusEnum statusEnum;

    private EditTextStatus()
    {

    }

    private static EditTextStatus editTextStatus = null;

    public static EditTextStatus getInstance()
    {
        if(editTextStatus == null)
        {
            editTextStatus = new EditTextStatus();
        }
        return editTextStatus;
    }

    public void setStatusEnum(StatusEnum statusEnum)
    {
        this.statusEnum = statusEnum;
    }

    public StatusEnum getStatusEnum()
    {
        return statusEnum;
    }

    /**
     * USER_EDIT: A user updated the EditText via GUI
     * COMMAND_EDIT: system run command (e.g., undo)
     */
    enum StatusEnum
    {
        USER_EDIT, COMMAND_EDIT
    }
}
