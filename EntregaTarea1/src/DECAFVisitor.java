// Generated from DECAF.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DECAFParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DECAFVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code programProduction}
	 * labeled alternative in {@link DECAFParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgramProduction(DECAFParser.ProgramProductionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(DECAFParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code regularVariableProduction}
	 * labeled alternative in {@link DECAFParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegularVariableProduction(DECAFParser.RegularVariableProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayVariableProduction}
	 * labeled alternative in {@link DECAFParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayVariableProduction(DECAFParser.ArrayVariableProductionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#structDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDeclaration(DECAFParser.StructDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#varType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarType(DECAFParser.VarTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodDeclarationProduction}
	 * labeled alternative in {@link DECAFParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclarationProduction(DECAFParser.MethodDeclarationProductionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#methodType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodType(DECAFParser.MethodTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parameterRegularDeclaration}
	 * labeled alternative in {@link DECAFParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterRegularDeclaration(DECAFParser.ParameterRegularDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parameterArrayDeclaration}
	 * labeled alternative in {@link DECAFParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterArrayDeclaration(DECAFParser.ParameterArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#parameterType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterType(DECAFParser.ParameterTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(DECAFParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(DECAFParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignationProduction}
	 * labeled alternative in {@link DECAFParser#assignation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignationProduction(DECAFParser.AssignationProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileBlockProduction}
	 * labeled alternative in {@link DECAFParser#whileBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileBlockProduction(DECAFParser.WhileBlockProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnBlockProduction}
	 * labeled alternative in {@link DECAFParser#returnBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnBlockProduction(DECAFParser.ReturnBlockProductionContext ctx, String test);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(DECAFParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifProduction}
	 * labeled alternative in {@link DECAFParser#myIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfProduction(DECAFParser.IfProductionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#location}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocation(DECAFParser.LocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#dotLocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotLocation(DECAFParser.DotLocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#declaredVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaredVariable(DECAFParser.DeclaredVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declaredVariableProduction}
	 * labeled alternative in {@link DECAFParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaredVariableProduction(DECAFParser.DeclaredVariableProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declaredArrayProduction}
	 * labeled alternative in {@link DECAFParser#arrayVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaredArrayProduction(DECAFParser.DeclaredArrayProductionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#expressionInP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionInP(DECAFParser.ExpressionInPContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#nExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNExpression(DECAFParser.NExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orProduction}
	 * labeled alternative in {@link DECAFParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrProduction(DECAFParser.OrProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link DECAFParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(DECAFParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalsExpr}
	 * labeled alternative in {@link DECAFParser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualsExpr(DECAFParser.EqualsExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andProduction}
	 * labeled alternative in {@link DECAFParser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndProduction(DECAFParser.AndProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalsProduction}
	 * labeled alternative in {@link DECAFParser#equalsExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualsProduction(DECAFParser.EqualsProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationExpr}
	 * labeled alternative in {@link DECAFParser#equalsExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationExpr(DECAFParser.RelationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubsExpr}
	 * labeled alternative in {@link DECAFParser#relationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubsExpr(DECAFParser.AddSubsExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationProduction}
	 * labeled alternative in {@link DECAFParser#relationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationProduction(DECAFParser.RelationProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubProduction}
	 * labeled alternative in {@link DECAFParser#addSubsExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubProduction(DECAFParser.AddSubProductionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link DECAFParser#addSubsExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpr(DECAFParser.MulDivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prExpr}
	 * labeled alternative in {@link DECAFParser#mulDivExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrExpr(DECAFParser.PrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivProduction}
	 * labeled alternative in {@link DECAFParser#mulDivExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivProduction(DECAFParser.MulDivProductionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#prExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrExpression(DECAFParser.PrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#basicExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicExpression(DECAFParser.BasicExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#basic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasic(DECAFParser.BasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(DECAFParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodCallProduction}
	 * labeled alternative in {@link DECAFParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallProduction(DECAFParser.MethodCallProductionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#arith_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArith_op(DECAFParser.Arith_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#md_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMd_op(DECAFParser.Md_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#pr_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPr_op(DECAFParser.Pr_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#rel_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel_op(DECAFParser.Rel_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#eq_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq_op(DECAFParser.Eq_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#cond_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond_op(DECAFParser.Cond_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(DECAFParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#int_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt_literal(DECAFParser.Int_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#char_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChar_literal(DECAFParser.Char_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link DECAFParser#bool_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_literal(DECAFParser.Bool_literalContext ctx);
}