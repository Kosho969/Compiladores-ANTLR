// Generated from DECAF.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DECAFParser}.
 */
public interface DECAFListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code programProduction}
	 * labeled alternative in {@link DECAFParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgramProduction(DECAFParser.ProgramProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code programProduction}
	 * labeled alternative in {@link DECAFParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgramProduction(DECAFParser.ProgramProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(DECAFParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(DECAFParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code regularVariableProduction}
	 * labeled alternative in {@link DECAFParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterRegularVariableProduction(DECAFParser.RegularVariableProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code regularVariableProduction}
	 * labeled alternative in {@link DECAFParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitRegularVariableProduction(DECAFParser.RegularVariableProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayVariableProduction}
	 * labeled alternative in {@link DECAFParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArrayVariableProduction(DECAFParser.ArrayVariableProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayVariableProduction}
	 * labeled alternative in {@link DECAFParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArrayVariableProduction(DECAFParser.ArrayVariableProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(DECAFParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(DECAFParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#varType}.
	 * @param ctx the parse tree
	 */
	void enterVarType(DECAFParser.VarTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#varType}.
	 * @param ctx the parse tree
	 */
	void exitVarType(DECAFParser.VarTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodDeclarationProduction}
	 * labeled alternative in {@link DECAFParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclarationProduction(DECAFParser.MethodDeclarationProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodDeclarationProduction}
	 * labeled alternative in {@link DECAFParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclarationProduction(DECAFParser.MethodDeclarationProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#methodType}.
	 * @param ctx the parse tree
	 */
	void enterMethodType(DECAFParser.MethodTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#methodType}.
	 * @param ctx the parse tree
	 */
	void exitMethodType(DECAFParser.MethodTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parameterRegularDeclaration}
	 * labeled alternative in {@link DECAFParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameterRegularDeclaration(DECAFParser.ParameterRegularDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parameterRegularDeclaration}
	 * labeled alternative in {@link DECAFParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameterRegularDeclaration(DECAFParser.ParameterRegularDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parameterArrayDeclaration}
	 * labeled alternative in {@link DECAFParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameterArrayDeclaration(DECAFParser.ParameterArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parameterArrayDeclaration}
	 * labeled alternative in {@link DECAFParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameterArrayDeclaration(DECAFParser.ParameterArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#parameterType}.
	 * @param ctx the parse tree
	 */
	void enterParameterType(DECAFParser.ParameterTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#parameterType}.
	 * @param ctx the parse tree
	 */
	void exitParameterType(DECAFParser.ParameterTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(DECAFParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(DECAFParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(DECAFParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(DECAFParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignationProduction}
	 * labeled alternative in {@link DECAFParser#assignation}.
	 * @param ctx the parse tree
	 */
	void enterAssignationProduction(DECAFParser.AssignationProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignationProduction}
	 * labeled alternative in {@link DECAFParser#assignation}.
	 * @param ctx the parse tree
	 */
	void exitAssignationProduction(DECAFParser.AssignationProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileBlockProduction}
	 * labeled alternative in {@link DECAFParser#whileBlock}.
	 * @param ctx the parse tree
	 */
	void enterWhileBlockProduction(DECAFParser.WhileBlockProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileBlockProduction}
	 * labeled alternative in {@link DECAFParser#whileBlock}.
	 * @param ctx the parse tree
	 */
	void exitWhileBlockProduction(DECAFParser.WhileBlockProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnBlockProduction}
	 * labeled alternative in {@link DECAFParser#returnBlock}.
	 * @param ctx the parse tree
	 */
	void enterReturnBlockProduction(DECAFParser.ReturnBlockProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnBlockProduction}
	 * labeled alternative in {@link DECAFParser#returnBlock}.
	 * @param ctx the parse tree
	 */
	void exitReturnBlockProduction(DECAFParser.ReturnBlockProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(DECAFParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(DECAFParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifProduction}
	 * labeled alternative in {@link DECAFParser#myIf}.
	 * @param ctx the parse tree
	 */
	void enterIfProduction(DECAFParser.IfProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifProduction}
	 * labeled alternative in {@link DECAFParser#myIf}.
	 * @param ctx the parse tree
	 */
	void exitIfProduction(DECAFParser.IfProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#location}.
	 * @param ctx the parse tree
	 */
	void enterLocation(DECAFParser.LocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#location}.
	 * @param ctx the parse tree
	 */
	void exitLocation(DECAFParser.LocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#dotLocation}.
	 * @param ctx the parse tree
	 */
	void enterDotLocation(DECAFParser.DotLocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#dotLocation}.
	 * @param ctx the parse tree
	 */
	void exitDotLocation(DECAFParser.DotLocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#declaredVariable}.
	 * @param ctx the parse tree
	 */
	void enterDeclaredVariable(DECAFParser.DeclaredVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#declaredVariable}.
	 * @param ctx the parse tree
	 */
	void exitDeclaredVariable(DECAFParser.DeclaredVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code declaredVariableProduction}
	 * labeled alternative in {@link DECAFParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterDeclaredVariableProduction(DECAFParser.DeclaredVariableProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code declaredVariableProduction}
	 * labeled alternative in {@link DECAFParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitDeclaredVariableProduction(DECAFParser.DeclaredVariableProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code declaredArrayProduction}
	 * labeled alternative in {@link DECAFParser#arrayVariable}.
	 * @param ctx the parse tree
	 */
	void enterDeclaredArrayProduction(DECAFParser.DeclaredArrayProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code declaredArrayProduction}
	 * labeled alternative in {@link DECAFParser#arrayVariable}.
	 * @param ctx the parse tree
	 */
	void exitDeclaredArrayProduction(DECAFParser.DeclaredArrayProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#expressionInP}.
	 * @param ctx the parse tree
	 */
	void enterExpressionInP(DECAFParser.ExpressionInPContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#expressionInP}.
	 * @param ctx the parse tree
	 */
	void exitExpressionInP(DECAFParser.ExpressionInPContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#nExpression}.
	 * @param ctx the parse tree
	 */
	void enterNExpression(DECAFParser.NExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#nExpression}.
	 * @param ctx the parse tree
	 */
	void exitNExpression(DECAFParser.NExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orProduction}
	 * labeled alternative in {@link DECAFParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrProduction(DECAFParser.OrProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orProduction}
	 * labeled alternative in {@link DECAFParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrProduction(DECAFParser.OrProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link DECAFParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(DECAFParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link DECAFParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(DECAFParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalsExpr}
	 * labeled alternative in {@link DECAFParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualsExpr(DECAFParser.EqualsExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalsExpr}
	 * labeled alternative in {@link DECAFParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualsExpr(DECAFParser.EqualsExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andProduction}
	 * labeled alternative in {@link DECAFParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndProduction(DECAFParser.AndProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andProduction}
	 * labeled alternative in {@link DECAFParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndProduction(DECAFParser.AndProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalsProduction}
	 * labeled alternative in {@link DECAFParser#equalsExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualsProduction(DECAFParser.EqualsProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalsProduction}
	 * labeled alternative in {@link DECAFParser#equalsExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualsProduction(DECAFParser.EqualsProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationExpr}
	 * labeled alternative in {@link DECAFParser#equalsExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationExpr(DECAFParser.RelationExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationExpr}
	 * labeled alternative in {@link DECAFParser#equalsExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationExpr(DECAFParser.RelationExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addSubsExpr}
	 * labeled alternative in {@link DECAFParser#relationExpression}.
	 * @param ctx the parse tree
	 */
	void enterAddSubsExpr(DECAFParser.AddSubsExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addSubsExpr}
	 * labeled alternative in {@link DECAFParser#relationExpression}.
	 * @param ctx the parse tree
	 */
	void exitAddSubsExpr(DECAFParser.AddSubsExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationProduction}
	 * labeled alternative in {@link DECAFParser#relationExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationProduction(DECAFParser.RelationProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationProduction}
	 * labeled alternative in {@link DECAFParser#relationExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationProduction(DECAFParser.RelationProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addSubProduction}
	 * labeled alternative in {@link DECAFParser#addSubsExpression}.
	 * @param ctx the parse tree
	 */
	void enterAddSubProduction(DECAFParser.AddSubProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addSubProduction}
	 * labeled alternative in {@link DECAFParser#addSubsExpression}.
	 * @param ctx the parse tree
	 */
	void exitAddSubProduction(DECAFParser.AddSubProductionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link DECAFParser#addSubsExpression}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpr(DECAFParser.MulDivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link DECAFParser#addSubsExpression}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpr(DECAFParser.MulDivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prExpr}
	 * labeled alternative in {@link DECAFParser#mulDivExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrExpr(DECAFParser.PrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prExpr}
	 * labeled alternative in {@link DECAFParser#mulDivExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrExpr(DECAFParser.PrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulDivProduction}
	 * labeled alternative in {@link DECAFParser#mulDivExpression}.
	 * @param ctx the parse tree
	 */
	void enterMulDivProduction(DECAFParser.MulDivProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulDivProduction}
	 * labeled alternative in {@link DECAFParser#mulDivExpression}.
	 * @param ctx the parse tree
	 */
	void exitMulDivProduction(DECAFParser.MulDivProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#prExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrExpression(DECAFParser.PrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#prExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrExpression(DECAFParser.PrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#basicExpression}.
	 * @param ctx the parse tree
	 */
	void enterBasicExpression(DECAFParser.BasicExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#basicExpression}.
	 * @param ctx the parse tree
	 */
	void exitBasicExpression(DECAFParser.BasicExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#basic}.
	 * @param ctx the parse tree
	 */
	void enterBasic(DECAFParser.BasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#basic}.
	 * @param ctx the parse tree
	 */
	void exitBasic(DECAFParser.BasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(DECAFParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(DECAFParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodCallProduction}
	 * labeled alternative in {@link DECAFParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCallProduction(DECAFParser.MethodCallProductionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodCallProduction}
	 * labeled alternative in {@link DECAFParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCallProduction(DECAFParser.MethodCallProductionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#arith_op}.
	 * @param ctx the parse tree
	 */
	void enterArith_op(DECAFParser.Arith_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#arith_op}.
	 * @param ctx the parse tree
	 */
	void exitArith_op(DECAFParser.Arith_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#md_op}.
	 * @param ctx the parse tree
	 */
	void enterMd_op(DECAFParser.Md_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#md_op}.
	 * @param ctx the parse tree
	 */
	void exitMd_op(DECAFParser.Md_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#pr_op}.
	 * @param ctx the parse tree
	 */
	void enterPr_op(DECAFParser.Pr_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#pr_op}.
	 * @param ctx the parse tree
	 */
	void exitPr_op(DECAFParser.Pr_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#rel_op}.
	 * @param ctx the parse tree
	 */
	void enterRel_op(DECAFParser.Rel_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#rel_op}.
	 * @param ctx the parse tree
	 */
	void exitRel_op(DECAFParser.Rel_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#eq_op}.
	 * @param ctx the parse tree
	 */
	void enterEq_op(DECAFParser.Eq_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#eq_op}.
	 * @param ctx the parse tree
	 */
	void exitEq_op(DECAFParser.Eq_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#cond_op}.
	 * @param ctx the parse tree
	 */
	void enterCond_op(DECAFParser.Cond_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#cond_op}.
	 * @param ctx the parse tree
	 */
	void exitCond_op(DECAFParser.Cond_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(DECAFParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(DECAFParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void enterInt_literal(DECAFParser.Int_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void exitInt_literal(DECAFParser.Int_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void enterChar_literal(DECAFParser.Char_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void exitChar_literal(DECAFParser.Char_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link DECAFParser#bool_literal}.
	 * @param ctx the parse tree
	 */
	void enterBool_literal(DECAFParser.Bool_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link DECAFParser#bool_literal}.
	 * @param ctx the parse tree
	 */
	void exitBool_literal(DECAFParser.Bool_literalContext ctx);
}