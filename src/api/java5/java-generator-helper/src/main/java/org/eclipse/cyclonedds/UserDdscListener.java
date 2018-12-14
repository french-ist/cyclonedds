package org.eclipse.cyclonedds;

import org.eclipse.cyclonedds.DdscParser.CloseBraceContext;
import org.eclipse.cyclonedds.DdscParser.CloseBracketContext;
import org.eclipse.cyclonedds.DdscParser.CommaSeparatorContext;
import org.eclipse.cyclonedds.DdscParser.DeclarationSpecifierContext;
import org.eclipse.cyclonedds.DdscParser.DeclaratorContext;
import org.eclipse.cyclonedds.DdscParser.DirectDeclaratorContext;
import org.eclipse.cyclonedds.DdscParser.InitializerContext;
import org.eclipse.cyclonedds.DdscParser.OpenBraceContext;
import org.eclipse.cyclonedds.DdscParser.OpenBracketContext;
import org.eclipse.cyclonedds.DdscParser.OrContext;
import org.eclipse.cyclonedds.DdscParser.PrimaryExpressionContext;
import org.eclipse.cyclonedds.builders.interfaces.BuildingState;
import org.eclipse.cyclonedds.builders.interfaces.JavaCodeBuilder;


public class UserDdscListener extends DdscBaseListener {

    private JavaCodeBuilder[] codeBuilders;

    public UserDdscListener(JavaCodeBuilder... codeBuilders){
        this.codeBuilders = codeBuilders;
    }

    public String lastFunc = "";
    static int order = 0;
    private void transition(String funcName, String txt) {
        order++;
        String thisFunc = funcName;
        if (lastFunc != thisFunc) {
            if (lastFunc == "") {
                lastFunc = "[*]";
            }
            //System.out.println(lastFunc + " --> " + thisFunc);
            lastFunc = thisFunc;
        }
        //System.out.println(thisFunc + ": " + txt + " //order: " + order);
    }

    private void setState(BuildingState state, String text){
        for(JavaCodeBuilder builder: codeBuilders){
            builder.setState(state, text);
        }
    }

    @Override
    public void enterDeclarationSpecifier(DeclarationSpecifierContext ctx) {
        transition("enterDeclarationSpecifier", ctx.getText());
        setState(BuildingState.DECLARATION_SPECIFIER, ctx.getText());
        
    }

    @Override
    public void enterDeclarator(DeclaratorContext ctx) {
        transition("enterDeclarator", ctx.getText());
        setState(BuildingState.DECLARATOR, ctx.getText());
    }

    @Override
    public void enterDirectDeclarator(DirectDeclaratorContext ctx) {
        transition("enterDirectDeclarator", ctx.getText());
        setState(BuildingState.DIRECT_DECLARATOR, ctx.getText());
    }

    @Override
    public void enterOpenBrace(OpenBraceContext ctx) {
        transition("enterOpenBrace", ctx.getText());
        setState(BuildingState.OPEN_BRACE, ctx.getText());
    }

    @Override
    public void enterCloseBrace(CloseBraceContext ctx) {
        transition("enterCloseBrace", ctx.getText()); 
        setState(BuildingState.CLOSE_BRACE, ctx.getText()); 
    }

    @Override
    public void enterOpenBracket(OpenBracketContext ctx) {
        transition("enterOpenBracket", ctx.getText());
        setState(BuildingState.OPEN_BRACKET, ctx.getText()); 
    }

    @Override
    public void enterCloseBracket(CloseBracketContext ctx) {
        transition("enterCloseBracket", ctx.getText());
        setState(BuildingState.CLOSE_BRACKET, ctx.getText()); 
    }

    @Override
    public void enterOr(OrContext ctx) {
        transition("enterOr", ctx.getText());
        setState(BuildingState.OR, ctx.getText()); 
    }

    @Override
    public void enterPrimaryExpression(PrimaryExpressionContext ctx) {
        transition("enterPrimaryExpression", ctx.getText());
        setState(BuildingState.PRIMARY_EXPRESSION, ctx.getText()); 
    }

    @Override
    public void enterCommaSeparator(CommaSeparatorContext ctx) {
        transition("enterCommaSeparator", ctx.getText());
        setState(BuildingState.COMMA, ctx.getText()); 
    }

    @Override
    public void enterInitializer(InitializerContext ctx) {
        transition("enterInitializer", ctx.getText());
        setState(BuildingState.INITIALIZER, ctx.getText()); 
    }

}