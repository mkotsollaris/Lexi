package com.example.mkotsollaris.lexi;

/**
 * Implements the Proxy pattern (Copy-On-Write).
 */
public class ColumnCOWProxy
{
    private Column column;

    public ColumnCOWProxy(Column column)
    {
        this.column = column;
    }


    public Column getColumn()
    {
        return column;
    }

    /**
     * Copy-on-write (COW), whenever there's a new column assigned
     */
    public void setColumn(int number)
    {
        column = new Column(number);
    }
}