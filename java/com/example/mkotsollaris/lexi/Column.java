package com.example.mkotsollaris.lexi;

class Column extends Composite
{
    private final int columnNumber;

    public Column(int column)
    {
        this.columnNumber = column;
    }

    public int getColumnNumber()
    {
        return columnNumber;
    }
}