package jplag.solidity;

import jplag.solidity.grammar.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Arrays;
import java.util.List;


public class JplagSolidityListener implements SolidityListener, SolidityTokenConstants {

    private jplag.solidity.Parser jplagParser;

    public JplagSolidityListener(jplag.solidity.Parser jplag) {
        jplagParser = jplag;
    }

    @Override
    public void enterAssemblyExpression(SolidityParser.AssemblyExpressionContext ctx) {

    }

    @Override
    public void exitAssemblyExpression(SolidityParser.AssemblyExpressionContext ctx) {

    }

    @Override
    public void enterAssemblyIf(SolidityParser.AssemblyIfContext ctx) {

    }

    @Override
    public void exitAssemblyIf(SolidityParser.AssemblyIfContext ctx) {

    }

    @Override
    public void enterStorageLocation(SolidityParser.StorageLocationContext ctx) {

    }

    @Override
    public void exitStorageLocation(SolidityParser.StorageLocationContext ctx) {

    }

    @Override
    public void enterStateMutability(SolidityParser.StateMutabilityContext ctx) {

    }

    @Override
    public void exitStateMutability(SolidityParser.StateMutabilityContext ctx) {

    }

    @Override
    public void enterEnumDefinition(SolidityParser.EnumDefinitionContext ctx) {
        jplagParser.add(ENUM_DECLARE, ctx.getStart());
    }

    @Override
    public void exitEnumDefinition(SolidityParser.EnumDefinitionContext ctx) {

    }

    @Override
    public void enterUsingForDeclaration(SolidityParser.UsingForDeclarationContext ctx) {
        jplagParser.add(USING_FOR, ctx.getStart());
    }

    @Override
    public void exitUsingForDeclaration(SolidityParser.UsingForDeclarationContext ctx) {

    }

    @Override
    public void enterEnumValue(SolidityParser.EnumValueContext ctx) {

    }

    @Override
    public void exitEnumValue(SolidityParser.EnumValueContext ctx) {

    }

    @Override
    public void enterTupleExpression(SolidityParser.TupleExpressionContext ctx) {

    }

    @Override
    public void exitTupleExpression(SolidityParser.TupleExpressionContext ctx) {

    }

    @Override
    public void enterElementaryTypeNameExpression(SolidityParser.ElementaryTypeNameExpressionContext ctx) {

    }

    @Override
    public void exitElementaryTypeNameExpression(SolidityParser.ElementaryTypeNameExpressionContext ctx) {

    }

    @Override
    public void enterAssemblyCase(SolidityParser.AssemblyCaseContext ctx) {

    }

    @Override
    public void exitAssemblyCase(SolidityParser.AssemblyCaseContext ctx) {

    }

    @Override
    public void enterAssemblyAssignment(SolidityParser.AssemblyAssignmentContext ctx) {

    }

    @Override
    public void exitAssemblyAssignment(SolidityParser.AssemblyAssignmentContext ctx) {

    }

    @Override
    public void enterBlock(SolidityParser.BlockContext ctx) {

    }

    @Override
    public void exitBlock(SolidityParser.BlockContext ctx) {

    }

    @Override
    public void enterExpressionStatement(SolidityParser.ExpressionStatementContext ctx) {

    }

    @Override
    public void exitExpressionStatement(SolidityParser.ExpressionStatementContext ctx) {

    }

    @Override
    public void enterDoWhileStatement(SolidityParser.DoWhileStatementContext ctx) {
        jplagParser.add(DO_WHILE_BEGIN, ctx.getStart());
    }

    @Override
    public void exitDoWhileStatement(SolidityParser.DoWhileStatementContext ctx) {
        jplagParser.addEnd(DO_WHILE_END, ctx.getStart());
    }

    @Override
    public void enterIdentifier(SolidityParser.IdentifierContext ctx) {

    }

    @Override
    public void exitIdentifier(SolidityParser.IdentifierContext ctx) {

    }

    @Override
    public void enterMapping(SolidityParser.MappingContext ctx) {

    }

    @Override
    public void exitMapping(SolidityParser.MappingContext ctx) {

    }

    @Override
    public void enterNameValue(SolidityParser.NameValueContext ctx) {

    }

    @Override
    public void exitNameValue(SolidityParser.NameValueContext ctx) {

    }

    @Override
    public void enterIdentifierList(SolidityParser.IdentifierListContext ctx) {

    }

    @Override
    public void exitIdentifierList(SolidityParser.IdentifierListContext ctx) {

    }

    @Override
    public void enterFunctionTypeParameter(SolidityParser.FunctionTypeParameterContext ctx) {

    }

    @Override
    public void exitFunctionTypeParameter(SolidityParser.FunctionTypeParameterContext ctx) {

    }

    @Override
    public void enterNameValueList(SolidityParser.NameValueListContext ctx) {

    }

    @Override
    public void exitNameValueList(SolidityParser.NameValueListContext ctx) {

    }

    @Override
    public void enterAssemblyIdentifierOrList(SolidityParser.AssemblyIdentifierOrListContext ctx) {

    }

    @Override
    public void exitAssemblyIdentifierOrList(SolidityParser.AssemblyIdentifierOrListContext ctx) {

    }

    @Override
    public void enterAssemblyFor(SolidityParser.AssemblyForContext ctx) {

    }

    @Override
    public void exitAssemblyFor(SolidityParser.AssemblyForContext ctx) {

    }

    @Override
    public void enterModifierDefinition(SolidityParser.ModifierDefinitionContext ctx) {
        jplagParser.add(MODIFIER_BEGIN, ctx.getStart());
    }

    @Override
    public void exitModifierDefinition(SolidityParser.ModifierDefinitionContext ctx) {
        jplagParser.addEnd(MODIFIER_END, ctx.getStart());
    }

    @Override
    public void enterAssemblyBlock(SolidityParser.AssemblyBlockContext ctx) {

    }

    @Override
    public void exitAssemblyBlock(SolidityParser.AssemblyBlockContext ctx) {

    }

    @Override
    public void enterFunctionTypeName(SolidityParser.FunctionTypeNameContext ctx) {

    }

    @Override
    public void exitFunctionTypeName(SolidityParser.FunctionTypeNameContext ctx) {

    }

    @Override
    public void enterAssemblySwitch(SolidityParser.AssemblySwitchContext ctx) {

    }

    @Override
    public void exitAssemblySwitch(SolidityParser.AssemblySwitchContext ctx) {

    }

    @Override
    public void enterAssemblyFunctionReturns(SolidityParser.AssemblyFunctionReturnsContext ctx) {

    }

    @Override
    public void exitAssemblyFunctionReturns(SolidityParser.AssemblyFunctionReturnsContext ctx) {

    }

    @Override
    public void enterVersion(SolidityParser.VersionContext ctx) {

    }

    @Override
    public void exitVersion(SolidityParser.VersionContext ctx) {

    }

    @Override
    public void enterFunctionTypeParameterList(SolidityParser.FunctionTypeParameterListContext ctx) {

    }

    @Override
    public void exitFunctionTypeParameterList(SolidityParser.FunctionTypeParameterListContext ctx) {

    }

    @Override
    public void enterExpressionList(SolidityParser.ExpressionListContext ctx) {

    }

    @Override
    public void exitExpressionList(SolidityParser.ExpressionListContext ctx) {

    }

    @Override
    public void enterInheritanceSpecifier(SolidityParser.InheritanceSpecifierContext ctx) {

    }

    @Override
    public void exitInheritanceSpecifier(SolidityParser.InheritanceSpecifierContext ctx) {

    }

    @Override
    public void enterFunctionCall(SolidityParser.FunctionCallContext ctx) {
        jplagParser.add(FUNC_CALL, ctx.getStart());
    }

    @Override
    public void exitFunctionCall(SolidityParser.FunctionCallContext ctx) {

    }

    @Override
    public void enterParameterList(SolidityParser.ParameterListContext ctx) {

    }

    @Override
    public void exitParameterList(SolidityParser.ParameterListContext ctx) {

    }

    @Override
    public void enterAssemblyItem(SolidityParser.AssemblyItemContext ctx) {

    }

    @Override
    public void exitAssemblyItem(SolidityParser.AssemblyItemContext ctx) {

    }

    @Override
    public void enterEventParameterList(SolidityParser.EventParameterListContext ctx) {

    }

    @Override
    public void exitEventParameterList(SolidityParser.EventParameterListContext ctx) {

    }

    @Override
    public void enterEventDefinition(SolidityParser.EventDefinitionContext ctx) {
        jplagParser.add(EVENT_DECLARE, ctx.getStart());
    }

    @Override
    public void exitEventDefinition(SolidityParser.EventDefinitionContext ctx) {

    }

    @Override
    public void enterAssemblyCall(SolidityParser.AssemblyCallContext ctx) {

    }

    @Override
    public void exitAssemblyCall(SolidityParser.AssemblyCallContext ctx) {

    }

    @Override
    public void enterContractDefinition(SolidityParser.ContractDefinitionContext ctx) {
        jplagParser.add(CONTRACT_BEGIN, ctx.getStart());
    }

    @Override
    public void exitContractDefinition(SolidityParser.ContractDefinitionContext ctx) {
        jplagParser.addEnd(CONTRACT_END, ctx.getStart());
    }

    @Override
    public void enterVersionOperator(SolidityParser.VersionOperatorContext ctx) {

    }

    @Override
    public void exitVersionOperator(SolidityParser.VersionOperatorContext ctx) {

    }

    @Override
    public void enterTypeName(SolidityParser.TypeNameContext ctx) {

    }

    @Override
    public void exitTypeName(SolidityParser.TypeNameContext ctx) {

    }

    @Override
    public void enterElementaryTypeName(SolidityParser.ElementaryTypeNameContext ctx) {

    }

    @Override
    public void exitElementaryTypeName(SolidityParser.ElementaryTypeNameContext ctx) {

    }

    @Override
    public void enterLabelDefinition(SolidityParser.LabelDefinitionContext ctx) {

    }

    @Override
    public void exitLabelDefinition(SolidityParser.LabelDefinitionContext ctx) {

    }

    @Override
    public void enterInlineAssemblyStatement(SolidityParser.InlineAssemblyStatementContext ctx) {

    }

    @Override
    public void exitInlineAssemblyStatement(SolidityParser.InlineAssemblyStatementContext ctx) {

    }

    @Override
    public void enterReturnParameters(SolidityParser.ReturnParametersContext ctx) {

    }

    @Override
    public void exitReturnParameters(SolidityParser.ReturnParametersContext ctx) {

    }

    @Override
    public void enterReturnStatement(SolidityParser.ReturnStatementContext ctx) {
        jplagParser.add(RETURN, ctx.getStart());
    }

    @Override
    public void exitReturnStatement(SolidityParser.ReturnStatementContext ctx) {

    }

    @Override
    public void enterContractPart(SolidityParser.ContractPartContext ctx) {

    }

    @Override
    public void exitContractPart(SolidityParser.ContractPartContext ctx) {

    }

    @Override
    public void enterConstructorDefinition(SolidityParser.ConstructorDefinitionContext ctx) {
        jplagParser.add(CONSTRUCTOR_BEGIN, ctx.getStart());
    }

    @Override
    public void exitConstructorDefinition(SolidityParser.ConstructorDefinitionContext ctx) {
        jplagParser.addEnd(CONSTRUCTOR_END, ctx.getStart());
    }

    @Override
    public void enterEmitStatement(SolidityParser.EmitStatementContext ctx) {
        jplagParser.add(EMIT, ctx.getStart());
    }

    @Override
    public void exitEmitStatement(SolidityParser.EmitStatementContext ctx) {

    }

    @Override
    public void enterStructDefinition(SolidityParser.StructDefinitionContext ctx) {
        jplagParser.add(STRUCT_BEGIN, ctx.getStart());
    }

    @Override
    public void exitStructDefinition(SolidityParser.StructDefinitionContext ctx) {
        jplagParser.addEnd(STRUCT_END, ctx.getStart());
    }

    @Override
    public void enterAssemblyIdentifierList(SolidityParser.AssemblyIdentifierListContext ctx) {

    }

    @Override
    public void exitAssemblyIdentifierList(SolidityParser.AssemblyIdentifierListContext ctx) {

    }

    @Override
    public void enterAssemblyStackAssignment(SolidityParser.AssemblyStackAssignmentContext ctx) {

    }

    @Override
    public void exitAssemblyStackAssignment(SolidityParser.AssemblyStackAssignmentContext ctx) {

    }

    @Override
    public void enterPragmaDirective(SolidityParser.PragmaDirectiveContext ctx) {
        jplagParser.add(PRAGMA, ctx.getStart());
    }

    @Override
    public void exitPragmaDirective(SolidityParser.PragmaDirectiveContext ctx) {

    }

    @Override
    public void enterPragmaValue(SolidityParser.PragmaValueContext ctx) {

    }

    @Override
    public void exitPragmaValue(SolidityParser.PragmaValueContext ctx) {
        jplagParser.add(ctx.getText(), ctx.getStart());
    }

    @Override
    public void enterFunctionCallArguments(SolidityParser.FunctionCallArgumentsContext ctx) {

    }

    @Override
    public void exitFunctionCallArguments(SolidityParser.FunctionCallArgumentsContext ctx) {

    }

    @Override
    public void enterAssemblyLocalDefinition(SolidityParser.AssemblyLocalDefinitionContext ctx) {

    }

    @Override
    public void exitAssemblyLocalDefinition(SolidityParser.AssemblyLocalDefinitionContext ctx) {

    }

    @Override
    public void enterParameter(SolidityParser.ParameterContext ctx) {

    }

    @Override
    public void exitParameter(SolidityParser.ParameterContext ctx) {

    }

    @Override
    public void enterEventParameter(SolidityParser.EventParameterContext ctx) {

    }

    @Override
    public void exitEventParameter(SolidityParser.EventParameterContext ctx) {

    }

    @Override
    public void enterStatement(SolidityParser.StatementContext ctx) {

    }

    @Override
    public void exitStatement(SolidityParser.StatementContext ctx) {

    }

    @Override
    public void enterVariableDeclarationStatement(SolidityParser.VariableDeclarationStatementContext ctx) {
        jplagParser.add(VAR_DECLARE, ctx.getStart());
    }

    @Override
    public void exitVariableDeclarationStatement(SolidityParser.VariableDeclarationStatementContext ctx) {

    }

    @Override
    public void enterImportDeclaration(SolidityParser.ImportDeclarationContext ctx) {

    }

    @Override
    public void exitImportDeclaration(SolidityParser.ImportDeclarationContext ctx) {

    }

    @Override
    public void enterImportDirective(SolidityParser.ImportDirectiveContext ctx) {

    }

    @Override
    public void exitImportDirective(SolidityParser.ImportDirectiveContext ctx) {

    }

    @Override
    public void enterSubAssembly(SolidityParser.SubAssemblyContext ctx) {

    }

    @Override
    public void exitSubAssembly(SolidityParser.SubAssemblyContext ctx) {

    }

    @Override
    public void enterAssemblyFunctionDefinition(SolidityParser.AssemblyFunctionDefinitionContext ctx) {

    }

    @Override
    public void exitAssemblyFunctionDefinition(SolidityParser.AssemblyFunctionDefinitionContext ctx) {

    }

    @Override
    public void enterPrimaryExpression(SolidityParser.PrimaryExpressionContext ctx) {

    }

    @Override
    public void exitPrimaryExpression(SolidityParser.PrimaryExpressionContext ctx) {

    }

    @Override
    public void enterExpression(SolidityParser.ExpressionContext ctx) {

    }

    @Override
    public void exitExpression(SolidityParser.ExpressionContext ctx) {
        try {
            List<String> assignSymbols = Arrays.asList("=", "|=", "^=", "&=", "<<=", ">>=", "+=", "-=", "*=", "/=", "%=");
            if (ctx.getChildCount() > 1 && assignSymbols.contains(ctx.getChild(1).getText())) {
                jplagParser.add(ASSIGN, ctx.getStart());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enterVersionConstraint(SolidityParser.VersionConstraintContext ctx) {

    }

    @Override
    public void exitVersionConstraint(SolidityParser.VersionConstraintContext ctx) {
    }

    @Override
    public void enterThrowStatement(SolidityParser.ThrowStatementContext ctx) {
        jplagParser.add(THROW, ctx.getStart());
    }

    @Override
    public void exitThrowStatement(SolidityParser.ThrowStatementContext ctx) {

    }

    @Override
    public void enterFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) {
        jplagParser.add(FUNCTION_BEGIN, ctx.getStart());
    }

    @Override
    public void exitFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) {
        jplagParser.addEnd(FUNCTION_END, ctx.getStart());
    }

    @Override
    public void enterForStatement(SolidityParser.ForStatementContext ctx) {

    }

    @Override
    public void exitForStatement(SolidityParser.ForStatementContext ctx) {

    }

    @Override
    public void enterBreakStatement(SolidityParser.BreakStatementContext ctx) {
        jplagParser.add(BREAK, ctx.getStart());
    }

    @Override
    public void exitBreakStatement(SolidityParser.BreakStatementContext ctx) {

    }

    @Override
    public void enterStateVariableDeclaration(SolidityParser.StateVariableDeclarationContext ctx) {
        jplagParser.add(STATE_DECLARE, ctx.getStart());
    }

    @Override
    public void exitStateVariableDeclaration(SolidityParser.StateVariableDeclarationContext ctx) {

    }

    @Override
    public void enterIfStatement(SolidityParser.IfStatementContext ctx) {
        jplagParser.add(IF_BEGIN, ctx.getStart());
    }

    @Override
    public void exitIfStatement(SolidityParser.IfStatementContext ctx) {
        jplagParser.addEnd(IF_END, ctx.getStart());
    }

    @Override
    public void enterPragmaName(SolidityParser.PragmaNameContext ctx) {

    }

    @Override
    public void exitPragmaName(SolidityParser.PragmaNameContext ctx) {

    }

    @Override
    public void enterModifierList(SolidityParser.ModifierListContext ctx) {

    }

    @Override
    public void exitModifierList(SolidityParser.ModifierListContext ctx) {
    }

    @Override
    public void enterVariableDeclaration(SolidityParser.VariableDeclarationContext ctx) {

    }

    @Override
    public void exitVariableDeclaration(SolidityParser.VariableDeclarationContext ctx) {

    }

    @Override
    public void enterWhileStatement(SolidityParser.WhileStatementContext ctx) {
        jplagParser.add(WHILE_BEGIN, ctx.getStart());
    }

    @Override
    public void exitWhileStatement(SolidityParser.WhileStatementContext ctx) {
        jplagParser.addEnd(WHILE_END, ctx.getStart());
    }

    @Override
    public void enterAssemblyLiteral(SolidityParser.AssemblyLiteralContext ctx) {

    }

    @Override
    public void exitAssemblyLiteral(SolidityParser.AssemblyLiteralContext ctx) {

    }

    @Override
    public void enterSimpleStatement(SolidityParser.SimpleStatementContext ctx) {

    }

    @Override
    public void exitSimpleStatement(SolidityParser.SimpleStatementContext ctx) {

    }

    @Override
    public void enterContinueStatement(SolidityParser.ContinueStatementContext ctx) {
        jplagParser.add(CONTINUE, ctx.getStart());
    }

    @Override
    public void exitContinueStatement(SolidityParser.ContinueStatementContext ctx) {

    }

    @Override
    public void enterVariableDeclarationList(SolidityParser.VariableDeclarationListContext ctx) {

    }

    @Override
    public void exitVariableDeclarationList(SolidityParser.VariableDeclarationListContext ctx) {

    }

    @Override
    public void enterModifierInvocation(SolidityParser.ModifierInvocationContext ctx) {

    }

    @Override
    public void exitModifierInvocation(SolidityParser.ModifierInvocationContext ctx) {

    }

    @Override
    public void enterSourceUnit(SolidityParser.SourceUnitContext ctx) {

    }

    @Override
    public void exitSourceUnit(SolidityParser.SourceUnitContext ctx) {

    }

    @Override
    public void enterUserDefinedTypeName(SolidityParser.UserDefinedTypeNameContext ctx) {

    }

    @Override
    public void exitUserDefinedTypeName(SolidityParser.UserDefinedTypeNameContext ctx) {

    }

    @Override
    public void enterNumberLiteral(SolidityParser.NumberLiteralContext ctx) {

    }

    @Override
    public void exitNumberLiteral(SolidityParser.NumberLiteralContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
