/*
 [The "BSD licence"]
 Copyright (c) 2013 Sam Harwell
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/** C 2011 grammar built from the C11 Spec */
grammar Ddsc;

import C;

main
   :  include* translationUnit 
   ;
   

include
   :  '#include ' StringLiteral+ Newline*  
   ;


initializer
   :   assignmentExpression
   |   openBrace initializerList closeBrace
   |   openBrace initializerList commaSeparator closeBrace
   ;

initializerList
   :   designation? initializer
   |   initializerList commaSeparator designation? initializer
   ;

directDeclarator
   :   Identifier
   |   '(' declarator ')'
   |   directDeclarator openBracket typeQualifierList? assignmentExpression? closeBracket
   |   directDeclarator openBracket 'static' typeQualifierList? assignmentExpression closeBracket
   |   directDeclarator openBracket typeQualifierList 'static' assignmentExpression closeBracket
   |   directDeclarator openBracket typeQualifierList? '*' closeBracket
   |   directDeclarator '(' parameterTypeList ')'
   |   directDeclarator '(' identifierList? ')'
   |   Identifier ':' DigitSequence  // bit field
   |   '(' typeSpecifier? pointer directDeclarator ')' // function pointer like: (__cdecl *f)
   ;

inclusiveOrExpression
   :   exclusiveOrExpression
   |   inclusiveOrExpression or exclusiveOrExpression
   ;

primaryExpression
   :   Identifier
   |   Constant
   |   StringLiteral+
   |   '(' expression ')'
   |   genericSelection
   |   '__extension__'? '(' compoundStatement ')' // Blocks (GCC extension)
   |   '__builtin_va_arg' '(' unaryExpression ',' typeName ')'
   |   '__builtin_offsetof' '(' typeName ',' unaryExpression ')'
   ;

commaSeparator
   :  Comma
   ;

leftParen
   :  LeftParen
   ;

rightParen
   :  RightParen
   ;

or
   :  Or
   ;

openBracket   
   :  '['
   ;

closeBracket   
   :  ']'
   ;

openBrace   
   :  '{'
   ;

closeBrace   
   :  '}'
   ;

