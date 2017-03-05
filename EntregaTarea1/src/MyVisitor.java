/*
 * Visitor a implementarse para las reglas semánticas 
 */
import java.util.HashMap;
import java.util.Map;


public class MyVisitor extends DECAFBaseVisitor<String> 
{
	// We will need here the current environment
	Environment currentEnvironment;
	Environment saved = new Environment(null); 

	// ADD [THIS WORDS] to the ones I think I'm going to use

	@Override
	public String visitProgramProduction(DECAFParser.ProgramProductionContext ctx) {
		String id = ctx.ID().getText();

		currentEnvironment = new Environment(null);

		return super.visitProgramProduction(ctx);
	}

	@Override
	public String visitDeclaration(DECAFParser.DeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeclaration(ctx);
	}

	@Override
	public String visitRegularVariableProduction(DECAFParser.RegularVariableProductionContext ctx){
		String retorno = super.visitRegularVariableProduction(ctx);
		
		boolean symbolAlreadyExists = false; 
		
		String symbolType = ctx.varType().getText();
		System.out.print("Type: "+symbolType+"\n");
		
		String identifier = ctx.ID().getText();
		System.out.print("ID: "+identifier+"\n");
		
		VariableSymbol currentSymbol = new VariableSymbol(symbolType,identifier,false,false);
		TableEntry entry = new TableEntry(symbolType, identifier, currentSymbol);
		System.out.println("Entrando al for");
		for (int i = 0; i< currentEnvironment.getSymbolTable().size(); i++){
			String typeInTable = currentEnvironment.getSymbolTable().get(i).getType();
			System.out.print("Type in Table: "+typeInTable+"\n");
			String nameInTable = currentEnvironment.getSymbolTable().get(i).getLexem();
			System.out.print("Name in Table: "+nameInTable+"\n");
			if(typeInTable.equals(symbolType) && nameInTable.equals(identifier)){
				symbolAlreadyExists = true;
			}
			
		}
		if(symbolAlreadyExists){
			System.out.print(symbolType+" "+identifier+"\n");
			System.out.print("No se permite eso que está intentando hacer \n");
		}
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		else{
			currentEnvironment.putSymbol(symbolType,identifier, currentSymbol);
			System.out.print("Added \n");
		}
		
		// TODO: Paso número uno, implementar excepciones
		
		//ctx.getStart().getLine()
		//ctx.getStart().getCharPositionInLine()
		
		// TODO: Paso número dos, obtener valores, e insertar en tábla de símbolos
		

		// VariableSymbol symbol = new VariableSymbol()
		return retorno;
	}

	@Override
	// [THIS WORDS]
	public String visitArrayVariableProduction(DECAFParser.ArrayVariableProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitArrayVariableProduction(ctx);
	}

	@Override
	public String visitStructDeclaration(DECAFParser.StructDeclarationContext ctx) {
		// TODO Auto-generated method stub
		boolean symbolAlreadyExists = false; 		
		currentEnvironment = saved;
		String identifier = ctx.ID().getText();
		System.out.print("ID: "+identifier+"\n");
		
		Symbol currentSymbol = new Symbol("void",identifier,true);
		TableEntry entry = new TableEntry("void", identifier, currentSymbol);
		System.out.println("Entrando al for");
		for (int i = 0; i< currentEnvironment.getSymbolTable().size(); i++){
			String typeInTable = currentEnvironment.getSymbolTable().get(i).getType();
			String nameInTable = currentEnvironment.getSymbolTable().get(i).getLexem();
			System.out.print("Name in Table: "+nameInTable+"\n");
			if( nameInTable.equals(identifier)){
				symbolAlreadyExists = true;
			}
			
		}
		if(symbolAlreadyExists){
			System.out.print(identifier+"\n");
			System.out.print("No se permite eso que está intentando hacer \n");
		}
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		else{
			currentEnvironment.putSymbol("void",identifier, currentSymbol);
			System.out.print("Added \n");
		}
		saved = currentEnvironment;
		currentEnvironment = new Environment(currentEnvironment);
		return super.visitStructDeclaration(ctx);
	}

	@Override
	public String visitVarType(DECAFParser.VarTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitVarType(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitMethodDeclarationProduction(DECAFParser.MethodDeclarationProductionContext ctx) {
		// return super.visitMethodDeclarationProduction(ctx);
		
		boolean symbolAlreadyExists = false; 		
		currentEnvironment = saved;
		String symbolType = ctx.methodType().getText();
		System.out.print("Type: "+symbolType+"\n");
		
		String identifier = ctx.ID().getText();
		System.out.print("ID: "+identifier+"\n");
		
		VariableSymbol currentSymbol = new VariableSymbol(symbolType,identifier,false,false);
		TableEntry entry = new TableEntry("void", identifier, currentSymbol);
		System.out.println("Entrando al for");
		for (int i = 0; i< currentEnvironment.getSymbolTable().size(); i++){
			String typeInTable = currentEnvironment.getSymbolTable().get(i).getType();
			String nameInTable = currentEnvironment.getSymbolTable().get(i).getLexem();
			System.out.print("Name in Table: "+nameInTable+"\n");
			if( nameInTable.equals(identifier)){
				symbolAlreadyExists = true;
			}
			
		}
		if(symbolAlreadyExists){
			System.out.print(identifier+"\n");
			System.out.print("No se permite eso que está intentando hacer \n");
		}
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		else{
			currentEnvironment.putSymbol("void",identifier, currentSymbol);
			System.out.print("Added \n");
		}
		saved = currentEnvironment;
		currentEnvironment = new Environment(currentEnvironment);
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
	public String visitMethodType(DECAFParser.MethodTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMethodType(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitParameterRegularDeclaration(DECAFParser.ParameterRegularDeclarationContext ctx) {
		// TODO Auto-generated method stub
		
		boolean symbolAlreadyExists = false; 
		String symbolType = ctx.parameterType().getText();
		System.out.print("Type: "+symbolType+"\n");
		
		String identifier = ctx.ID().getText();
		System.out.print("ID: "+identifier+"\n");
		
		VariableSymbol currentSymbol = new VariableSymbol(symbolType,identifier,false,false);
		TableEntry entry = new TableEntry(symbolType, identifier, currentSymbol);
		System.out.println("Entrando al for");
		for (int i = 0; i< currentEnvironment.getSymbolTable().size(); i++){
			String typeInTable = currentEnvironment.getSymbolTable().get(i).getType();
			System.out.print("Type in Table: "+typeInTable+"\n");
			String nameInTable = currentEnvironment.getSymbolTable().get(i).getLexem();
			System.out.print("Name in Table: "+nameInTable+"\n");
			if(typeInTable.equals(symbolType) && nameInTable.equals(identifier)){
				symbolAlreadyExists = true;
			}
			
		}
		if(symbolAlreadyExists){
			System.out.print(symbolType+" "+identifier+"\n");
			System.out.print("No se permite eso que está intentando hacer \n");
		}
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		else{
			currentEnvironment.putSymbol(symbolType,identifier, currentSymbol);
			System.out.print("Added \n");
		}
		return super.visitParameterRegularDeclaration(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitParameterArrayDeclaration(DECAFParser.ParameterArrayDeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitParameterArrayDeclaration(ctx);
	}

	@Override
	public String visitParameterType(DECAFParser.ParameterTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitParameterType(ctx);
	}

	@Override
	public String visitBlock(DECAFParser.BlockContext ctx) {
		// TODO Auto-generated method stub
		return super.visitBlock(ctx);
	}

	@Override
	public String visitStatement(DECAFParser.StatementContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStatement(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitAssignationProduction(DECAFParser.AssignationProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAssignationProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitWhileBlockProduction(DECAFParser.WhileBlockProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitWhileBlockProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitReturnBlockProduction(DECAFParser.ReturnBlockProductionContext ctx, String test) {
		System.out.println("Visitando return con: " + test);
		
		// TODO: Comparar el resultado de super.visitReturnBlockProduction(ctx, test);
		// contra methodSymbol.type.

		return super.visitReturnBlockProduction(ctx, test);
	}

	@Override
	public String visitPrint(DECAFParser.PrintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPrint(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitIfProduction(DECAFParser.IfProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitIfProduction(ctx);
	}

	@Override
	public String visitLocation(DECAFParser.LocationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLocation(ctx);
	}

	@Override
	public String visitDotLocation(DECAFParser.DotLocationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDotLocation(ctx);
	}

	@Override
	public String visitDeclaredVariable(DECAFParser.DeclaredVariableContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeclaredVariable(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitDeclaredVariableProduction(DECAFParser.DeclaredVariableProductionContext ctx) {
		// TODO Auto-generated method stub
		boolean symbolAlreadyExists = false; 
		String variableName = ctx.ID().getText();
		for (int i = 0; i< currentEnvironment.getSymbolTable().size(); i++){
			String nameInTable = currentEnvironment.getSymbolTable().get(i).getLexem();
			System.out.print("Name in Table: "+nameInTable+"\n");
			if(nameInTable.equals(variableName)){
				symbolAlreadyExists = true;
			}
			
		}
		if(!symbolAlreadyExists){
			System.out.print("Error en la linea: "
				+ctx.getStart().getLine()
				+"\n "
				+ "La variable: "+variableName+" no ha sido declarada");
		}
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		return super.visitDeclaredVariableProduction(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitDeclaredArrayProduction(DECAFParser.DeclaredArrayProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeclaredArrayProduction(ctx);
	}

	@Override
	public String visitExpressionInP(DECAFParser.ExpressionInPContext ctx) {
		// TODO Auto-generated method stub
		return super.visitExpressionInP(ctx);
	}

	@Override
	public String visitNExpression(DECAFParser.NExpressionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitNExpression(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitOrProduction(DECAFParser.OrProductionContext ctx) {
		// TODO Auto-generated method stub
		String expression = visit(ctx.getChild(0));
		String or = visit(ctx.getChild(1));
		String andExpression = visit(ctx.getChild(2));
		if(!expression.equals(andExpression)){
			System.out.println("Error Expected Type Boolean");
		}
		return expression;
	}

	@Override
	// [THIS WORDS]
	public String visitAndExpr(DECAFParser.AndExprContext ctx) {
		// TODO Auto-generated method stub
		String andExpression = visitChildren(ctx);
		return andExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitEqualsExpr(DECAFParser.EqualsExprContext ctx) {
		// TODO Auto-generated method stub
		String equalsExpression = visitChildren(ctx);
		return equalsExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitAndProduction(DECAFParser.AndProductionContext ctx) {
		// TODO Auto-generated method stub
		
		String andExpression = visit(ctx.getChild(0));
		String and = visit(ctx.getChild(1));
		String equalsExpression = visit(ctx.getChild(2));
		if(andExpression.equals(equalsExpression)){
			return andExpression;
			
		}
		System.out.println("Error Expected Type Boolean");
		return "Error";
	}

	@Override
	// [THIS WORDS]
	public String visitEqualsProduction(DECAFParser.EqualsProductionContext ctx) {
		// TODO Auto-generated method stub
		String equalsExpression = visit(ctx.getChild(0));
		String equal = visit(ctx.getChild(1));
		String relationExpression = visit(ctx.getChild(2));
		if(!(equalsExpression.equals(relationExpression))){
			
			System.out.println("Error Expected Type Boolean");
			return ("Error");
		}
		return equalsExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitRelationExpr(DECAFParser.RelationExprContext ctx) {
		// TODO Auto-generated method stub
		String relationExpression = visitChildren(ctx);
		return relationExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitAddSubsExpr(DECAFParser.AddSubsExprContext ctx) {
		// TODO Auto-generated method stub
		String additionSubsExpression = visitChildren(ctx);
		return additionSubsExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitRelationProduction(DECAFParser.RelationProductionContext ctx) {
		// TODO Auto-generated method stub
		String relationExpression = visit(ctx.getChild(0));
		String relation = visit(ctx.getChild(1));
		String additionSubsExpression = visit(ctx.getChild(2));
		if(!relationExpression.equals(additionSubsExpression)){
			System.out.println("Error Expected Type Boolean at line: "+ctx.getStart().getLine());
		}
		return relationExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitAddSubProduction(DECAFParser.AddSubProductionContext ctx) {
		// TODO Auto-generated method stub
		String additionSubsExpression = visit(ctx.getChild(0));
		String arithmetic = visit(ctx.getChild(1));
		String multDivExpression = visit(ctx.getChild(2));
		if(!additionSubsExpression.equals(multDivExpression)){
			System.out.println("Error Expected Type INTEGER at line: "+ctx.getStart().getLine());
		}
		return additionSubsExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitMulDivExpr(DECAFParser.MulDivExprContext ctx) {
		// TODO Auto-generated method stub
		String multDivExpression = visitChildren(ctx);
		return multDivExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitPrExpr(DECAFParser.PrExprContext ctx) {
		// TODO Auto-generated method stub
		String percentageExpression = visitChildren(ctx);
		return percentageExpression;
	}

	@Override
	// [THIS WORDS]
	public String visitMulDivProduction(DECAFParser.MulDivProductionContext ctx) {
		// TODO Auto-generated method stub
		String multDivExpression = visit(ctx.getChild(0));
		String mdOperator = visit(ctx.getChild(1));
		String percentageExpression = visit(ctx.getChild(2));
		if(!multDivExpression.equals(percentageExpression)){
			System.out.println("Error Expected Type Boolean at line: "+ctx.getStart().getLine());
		}
		return multDivExpression;
	}

	@Override
	public String visitPrExpression(DECAFParser.PrExpressionContext ctx) {
		// TODO Auto-generated method stub
		if(ctx.getChildCount()==3){
			String percentageExpression = visit(ctx.getChild(0));
			String percent = visit(ctx.getChild(1));
			String basicExpression = visit(ctx.getChild(2));
			if(!percentageExpression.equals(basicExpression)){
				System.out.println("Error Expected Type Boolean at line: "+ctx.getStart().getLine());
			}
			return percentageExpression;
		}
		else{
			String basicExpression = visitChildren(ctx);
			return basicExpression;
		}
	}

	@Override
	public String visitBasicExpression(DECAFParser.BasicExpressionContext ctx) {
		// TODO Auto-generated method stu
		String basic = visitChildren(ctx);
		return basic;
	}

	@Override
	public String visitBasic(DECAFParser.BasicContext ctx) {
		// TODO Auto-generated method stub
		ctx.literal();
		return super.visitBasic(ctx);
	}

	@Override
	public String visitArg(DECAFParser.ArgContext ctx) {
		// TODO Auto-generated method stub
		return super.visitArg(ctx);
	}

	@Override
	public String visitMethodCallProduction(DECAFParser.MethodCallProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMethodCallProduction(ctx);
	}

	@Override
	public String visitArith_op(DECAFParser.Arith_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitArith_op(ctx);
	}

	@Override
	public String visitMd_op(DECAFParser.Md_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMd_op(ctx);
	}

	@Override
	public String visitPr_op(DECAFParser.Pr_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPr_op(ctx);
	}

	@Override
	public String visitRel_op(DECAFParser.Rel_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitRel_op(ctx);
	}

	@Override
	public String visitEq_op(DECAFParser.Eq_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitEq_op(ctx);
	}

	@Override
	public String visitCond_op(DECAFParser.Cond_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCond_op(ctx);
	}

	@Override
	public String visitLiteral(DECAFParser.LiteralContext ctx) {
		// TODO Auto-generated method stub
		String bool = "boolean";
		//ctx.int_literal();
		return bool;
	}

	@Override
	public String visitInt_literal(DECAFParser.Int_literalContext ctx) {
		// TODO Auto-generated method stub
		ctx.getText();
		System.out.println("Es String "+ctx.getText());
		return super.visitInt_literal(ctx);
	}

	@Override
	public String visitChar_literal(DECAFParser.Char_literalContext ctx) {
		// TODO Auto-generated method stub
		return super.visitChar_literal(ctx);
	}

	@Override
	public String visitBool_literal(DECAFParser.Bool_literalContext ctx) {
		// TODO Auto-generated method stub
		return super.visitBool_literal(ctx);
	}
}
