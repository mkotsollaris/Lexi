package com.example.mkotsollaris.lexi;

/**
 * Implements the Visitor pattern.
 */

public interface VisitorAccepter
{
    void accept(CharacterVisitor characterVisitor);
}