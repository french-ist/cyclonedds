package org.eclipse.cyclonedds.builders.interfaces;

public enum BuildingState {
    DECLARATION,
    DECLARATION_SPECIFIER,
    INIT_DECLARATOR,
    DECLARATOR,
    DIRECT_DECLARATOR,
    OPEN_BRACKET, //[
    PRIMARY_EXPRESSION,
    CLOSE_BRACKET,  //]
    INITIALIZER,
    OPEN_BRACE, //{
    COMMA,
    OR,    
    CLOSE_BRACE //}
}