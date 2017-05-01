package com.example.mkotsollaris.lexi;

/**
 * Implements the Adapter pattern.
 */

interface GOFList
{
    int size();  //return the current number of elements in the list

    Object get(int index);  //return the object at the index in the list

    void add(Object object);

    void add(int index, Object object); //add the object to the end of the list

    void delete(int index);  //remove the object from the list

    void deleteAll();  //remove all elements of the list

    void printAll();
}