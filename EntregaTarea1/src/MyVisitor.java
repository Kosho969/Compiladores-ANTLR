/*
 * Visitor a implementarse para las reglas semánticas 
 */
import java.util.HashMap;
import java.util.Map;


public class MyVisitor extends DECAFBaseVisitor<Integer> 
{
	// We will need here the current environment
	Environment currentEnvironment;

	// ADD [THIS WORDS] to the ones I think I'm going to use

	@Override
	public Integer visitProgramProduction(DECAFParser.ProgramProductionContext ctx) {
		String id = ctx.ID().getText();

		currentEnvironment = new Environment(null);

		return super.visitProgramProduction(ctx);
	}

	@Override
	public Integer visitDeclaration(DECAFParser.DeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeclaration(ctx);
	}

	@Override
	public Integer visitRegularVariableProduction(DECAFParser.RegularVariableProductionContext ctx) throws SemanticErrorException {
		Integer retorno = super.visitRegularVariableProduction(ctx);
		
		boolean symbolAlreadyExists = false; 
		
		String symbolType = ctx.varType().getText();
		
		String identifier = ctx.ID().getText();
		
		
		VariableSymbol currentSymbol = new VariableSymbol(symbolType,identifier,false,false);
		TableEntry entry = new TableEntry(symbolType, identifier, currentSymbol);
		for (int i = 0; i< currentEnvironment.getSymbolTable().size(); i++){
			String typeInTable = currentEnvironment.getSymbolTable().get(i).getType();
			String nameInTable = currentEnvironment.getSymbolTable().get(i).getLexem();
			if(typeInTable.equals(symbolType) && nameInTable.equals(identifier)){
				symbolAlreadyExists = true;
			}
			
		}
		if(!symbolAlreadyExists)
			throw new SemanticErrorException("No se permite eso que está intentando hacer");
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		
		
		// TODO: Paso número uno, implementar excepciones
		
		//ctx.getStart().getLine()
		//ctx.getStart().getCharPositionInLine()
		
		// TODO: Paso número dos, obtener valores, e insertar en tábla de símbolos
		

		// VariableSymbol symbol = new VariableSymbol()
		
		
		
		return retorno;
	}

	@Override
	// [THIS WORDS]
	public Integer visitArrayVariableProduction(DECAFParser.ArrayVariableProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitArrayVariableProduction(ctx);
	}

	@Override
	public Integer visitStructDeclaration(DECAFParser.StructDeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStructDeclaration(ctx);
	}

	@Override
	public Integer visitVarType(DECAFParser.VarTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitVarType(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitMethodDeclarationProduction(DECAFParser.MethodDeclarationProductionContext ctx) {
		// return super.visitMethodDeclarationProduction(ctx);
		
		// Visitar methodType
        visit(ctx.methodType());
        
        // Visitar ID
        visit(ctx.ID());

        // Visitar LPARENT
        visit(ctx.LPARENT());

        // Visitar (parameter(COMA parameter)*)?
        // Armar firma del método: listado de VariableSymbol
        for (DECAFParser.ParameterContext element : ctx.parameter()) {
        	visit(element);
        }

        // Visitar RPARENT
        visit(ctx.RPARENT());

		// Ejecutar cosas locas
        // Instanciar MethodSymbol
		System.out.println("Haciendo cosas locasl :-D");

		// Visitar block
		return visit(ctx.block());
	}

	@Override
	public Integer visitMethodType(DECAFParser.MethodTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMethodType(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitParameterRegularDeclaration(DECAFParser.ParameterRegularDeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitParameterRegularDeclaration(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitParameterArrayDeclaration(DECAFParser.ParameterArrayDeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitParameterArrayDeclaration(ctx);
	}

	@Override
	public Integer visitParameterType(DECAFParser.ParameterTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitParameterType(ctx);
	}

	@Override
	public Integer visitBlock(DECAFParser.BlockContext ctx) {
		// TODO Auto-generated method stub
		return super.visitBlock(ctx);
	}

	@Override
	public Integer visitStatement(DECAFParser.StatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStatement(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitAssignationProduction(DECAFParser.AssignationProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAssignationProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitWhileBlockProduction(DECAFParser.WhileBlockProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitWhileBlockProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitReturnBlockProduction(DECAFParser.ReturnBlockProductionContext ctx, String test) {
		System.out.println("Visitando return con: " + test);
		
		// TODO: Comparar el resultado de super.visitReturnBlockProduction(ctx, test);
		// contra methodSymbol.type.

		return super.visitReturnBlockProduction(ctx, test);
	}

	@Override
	public Integer visitPrint(DECAFParser.PrintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPrint(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitIfProduction(DECAFParser.IfProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitIfProduction(ctx);
	}

	@Override
	public Integer visitLocation(DECAFParser.LocationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLocation(ctx);
	}

	@Override
	public Integer visitDotLocation(DECAFParser.DotLocationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDotLocation(ctx);
	}

	@Override
	public Integer visitDeclaredVariable(DECAFParser.DeclaredVariableContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeclaredVariable(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitDeclaredVariableProduction(DECAFParser.DeclaredVariableProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeclaredVariableProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitDeclaredArrayProduction(DECAFParser.DeclaredArrayProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeclaredArrayProduction(ctx);
	}

	@Override
	public Integer visitExpressionInP(DECAFParser.ExpressionInPContext ctx) {
		// TODO Auto-generated method stub
		return super.visitExpressionInP(ctx);
	}

	@Override
	public Integer visitNExpression(DECAFParser.NExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitNExpression(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitOrProduction(DECAFParser.OrProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitOrProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitAndExpr(DECAFParser.AndExprContext ctx) {
		// TODO Auto-generated method stub
		ctx.andExpression();
		return super.visitAndExpr(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitEqualsExpr(DECAFParser.EqualsExprContext ctx) {
		// TODO Auto-generated method stub
		ctx.equalsExpression();
		return super.visitEqualsExpr(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitAndProduction(DECAFParser.AndProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAndProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitEqualsProduction(DECAFParser.EqualsProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEqualsProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitRelationExpr(DECAFParser.RelationExprContext ctx) {
		// TODO Auto-generated method stub
		ctx.relationExpression();
		return super.visitRelationExpr(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitAddSubsExpr(DECAFParser.AddSubsExprContext ctx) {
		// TODO Auto-generated method stub
		ctx.addSubsExpression();
		return super.visitAddSubsExpr(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitRelationProduction(DECAFParser.RelationProductionContext ctx) {
		// TODO Auto-generated method stub
		ctx.relationExpression();
		ctx.rel_op().getText();
		ctx.addSubsExpression();
		return super.visitRelationProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitAddSubProduction(DECAFParser.AddSubProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAddSubProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitMulDivExpr(DECAFParser.MulDivExprContext ctx) {
		// TODO Auto-generated method stub
		ctx.mulDivExpression();
		return super.visitMulDivExpr(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitPrExpr(DECAFParser.PrExprContext ctx) {
		// TODO Auto-generated method stub
		ctx.prExpression();
		return super.visitPrExpr(ctx);
	}

	@Override
	// [THIS WORDS]
	public Integer visitMulDivProduction(DECAFParser.MulDivProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMulDivProduction(ctx);
	}

	@Override
	public Integer visitPrExpression(DECAFParser.PrExpressionContext ctx) {
		// TODO Auto-generated method stub
		ctx.basicExpression();
		return super.visitPrExpression(ctx);
	}

	@Override
	public Integer visitBasicExpression(DECAFParser.BasicExpressionContext ctx) {
		// TODO Auto-generated method stub
		ctx.basic();
		return super.visitBasicExpression(ctx);
	}

	@Override
	public Integer visitBasic(DECAFParser.BasicContext ctx) {
		// TODO Auto-generated method stub
		ctx.literal();
		return super.visitBasic(ctx);
	}

	@Override
	public Integer visitArg(DECAFParser.ArgContext ctx) {
		// TODO Auto-generated method stub
		return super.visitArg(ctx);
	}

	@Override
	public Integer visitMethodCallProduction(DECAFParser.MethodCallProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMethodCallProduction(ctx);
	}

	@Override
	public Integer visitArith_op(DECAFParser.Arith_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitArith_op(ctx);
	}

	@Override
	public Integer visitMd_op(DECAFParser.Md_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMd_op(ctx);
	}

	@Override
	public Integer visitPr_op(DECAFParser.Pr_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPr_op(ctx);
	}

	@Override
	public Integer visitRel_op(DECAFParser.Rel_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRel_op(ctx);
	}

	@Override
	public Integer visitEq_op(DECAFParser.Eq_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEq_op(ctx);
	}

	@Override
	public Integer visitCond_op(DECAFParser.Cond_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCond_op(ctx);
	}

	@Override
	public Integer visitLiteral(DECAFParser.LiteralContext ctx) {
		// TODO Auto-generated method stub
		ctx.int_literal();
		return super.visitLiteral(ctx);
	}

	@Override
	public Integer visitInt_literal(DECAFParser.Int_literalContext ctx) {
		// TODO Auto-generated method stub
		ctx.getText();
		System.out.println("Es integer "+ctx.getText());
		return super.visitInt_literal(ctx);
	}

	@Override
	public Integer visitChar_literal(DECAFParser.Char_literalContext ctx) {
		// TODO Auto-generated method stub
		return super.visitChar_literal(ctx);
	}

	@Override
	public Integer visitBool_literal(DECAFParser.Bool_literalContext ctx) {
		// TODO Auto-generated method stub
		return super.visitBool_literal(ctx);
	}
}
