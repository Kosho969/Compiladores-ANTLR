/*
 * Visitor a implementarse para las reglas semánticas 
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MyVisitor extends DECAFBaseVisitor<String> 
{
	// We will need here the current environment
	Environment currentEnvironment = null;
	Stack<Environment> environmentsStack = new Stack<Environment>(); 
	public StringBuffer errors = new StringBuffer("Semantic Errors: \n");

	// ADD [THIS WORDS] to the ones I think I'm going to use

	@Override
	public String visitProgramProduction(DECAFParser.ProgramProductionContext ctx) {
		String id = ctx.ID().getText();

		System.out.println("Creating new current environment");
		currentEnvironment = new Environment(null);
		String result = visitChildren(ctx);
		MethodSymbol mainOne = (MethodSymbol) currentEnvironment.getSymbol("main", "method");
		//System.out.println(mainOne.getFirm());
		if(currentEnvironment.hasSymbol("main", "method") && mainOne.getFirm().isEmpty()){
			printLine("Ejecucion correcta! ");
		}
		handleSemanticError("Expected main method without parameters");
		return result;
	}

	@Override
	public String visitDeclaration(DECAFParser.DeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDeclaration(ctx);
	}

	@Override
	public String visitRegularVariableProduction(DECAFParser.RegularVariableProductionContext ctx)
	{
		String retorno = super.visitRegularVariableProduction(ctx); 
		String symbolType = ctx.varType().getText();
		String identifier = ctx.ID().getText();
		VariableSymbol currentSymbol = new VariableSymbol(
			symbolType,
			identifier,
			false,
			false
		);
		
		if (currentEnvironment.hasSymbol(identifier, "variable")) {
			handleSemanticError("Line: "+ctx.getStart().getLine() 
					+
				" Identificador '" + identifier + "' ya utilizado en entorno actual"
			);
			
			return "";
		}
		
		// Paso número tres, agregar validación para determinar si variable existe
		currentEnvironment.putSymbol("variable", identifier, currentSymbol);

		return retorno;
	}

	@Override
	// [THIS WORDS]
	public String visitArrayVariableProduction(DECAFParser.ArrayVariableProductionContext ctx) {
		// TODO Auto-generated method stub
		return super.visitArrayVariableProduction(ctx);
	}

	@Override
	public String visitStructDeclaration(DECAFParser.StructDeclarationContext ctx)
	{
		// Procesar identificador
		String identifier = ctx.ID().getText();

		// Si el identificador existe ya en el entorno actual usado como 'struct', error
		if (currentEnvironment.hasSymbol(identifier, "struct")){
			handleSemanticError("Line: "+ctx.getStart().getLine() 
					+
				" Identificador '" + identifier + "' de estructura ya utilizado en entorno actual"
			);
			
			return "";
		}

		// Instanciar simbolo y guardarlo en entorno actual
		Symbol currentSymbol = new Symbol(identifier, identifier, true);
		currentEnvironment.putSymbol("struct", identifier, currentSymbol);
		
		// Mandar al stack el entorno actual, y crear uno nuevo para representar el cuerpo del struct
		environmentsStack.push(currentEnvironment);
		currentEnvironment = new Environment(currentEnvironment);

		// Visitar el cuerpo del struct
		String result = super.visitStructDeclaration(ctx);

		// Regresar el entorno pusheado al entorno actual
		currentEnvironment = environmentsStack.pop();
		
		return result;
	}

	@Override
	public String visitVarType(DECAFParser.VarTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitVarType(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitMethodDeclarationProduction(DECAFParser.MethodDeclarationProductionContext ctx)
	{
		String symbolType = ctx.methodType().getText();
		String identifier = ctx.ID().getText();

		// Verificar que el nombre del método no haya sido usado
		// en una declaración previa del entorno actual
		if (currentEnvironment.hasSymbol(identifier, "method")) {
			handleSemanticError("Line: "+ctx.getStart().getLine() 
					+
				" Identificador '" + identifier + "' para método ya utilizado en entorno actual"
			);
			
			return "";
		}
		printLine("Antes del metodo: ");
		//currentEnvironment.print();
		// Mandar entorno actual al stack y crear nuevo entorno
		environmentsStack.push(currentEnvironment);
		currentEnvironment = new Environment(currentEnvironment);

		// Visitar methodType
        visit(ctx.methodType());
        
        // Visitar ID
        visit(ctx.ID());

        // Visitar LPARENT
        visit(ctx.LPARENT());

        // Visitar (parameter(COMA parameter)*)?
        // Armar firma del método: listado de VariableSymbol
        ArrayList<VariableSymbol> firm = new ArrayList<VariableSymbol>();
        
        for (DECAFParser.ParameterContext parameter : ctx.parameter()) {
        	printLine("Pre");
        	visit(parameter);
        	
        	DECAFParser.ParameterRegularDeclarationContext castedParameter =
        		(DECAFParser.ParameterRegularDeclarationContext) parameter;
        	
        	printLine("Dude!");
        	printLine(castedParameter.parameterType().getText());
        	printLine(castedParameter.ID().getText());
        	printLine("End dude!");
        	

        }
        printLine("Post");
        //currentEnvironment.print();
        for (int i = 0; i<currentEnvironment.getSymbolTable().size();i++){
        	VariableSymbol currentVariableSymbol= (VariableSymbol) currentEnvironment.getSymbolTable().get(i).getValue();
        	firm.add(currentVariableSymbol);
        	printLine("adding");
        	System.out.println(firm);
        }
        printLine("Post");
        // Visitar RPARENT
        visit(ctx.RPARENT());

		// Ejecutar cosas locas
        // Instanciar MethodSymbol

		// Visitar block
		String result = visit(ctx.block());

		currentEnvironment = environmentsStack.pop();
		
		MethodSymbol currentSymbol = new MethodSymbol(
			symbolType,
			identifier,
			false,
			firm
		);
		//System.out.println(currentSymbol.getFirm());
		currentEnvironment.putSymbol("method", identifier, currentSymbol);
		
		return result;
	}

	@Override
	public String visitMethodType(DECAFParser.MethodTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMethodType(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitParameterRegularDeclaration(DECAFParser.ParameterRegularDeclarationContext ctx) {
		
		//currentEnvironment.print();
		String parameterType = ctx.parameterType().getText();
		String parameterIdentifier = ctx.ID().getText();
		VariableSymbol currentSymbol = new VariableSymbol(
			parameterType,
			parameterIdentifier,
			false,
			false
		);
		
		if (currentEnvironment.hasSymbol(parameterIdentifier, "variable")) {
			handleSemanticError("Line: "+ctx.getStart().getLine() 
				+" Identificador '" + parameterIdentifier + "' ya utilizado en entorno actual"
			);
			
			return "";
		}
		currentEnvironment.putSymbol("variable", parameterIdentifier, currentSymbol);
		//currentEnvironment.print();
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
	public String visitDeclaredVariableProduction(DECAFParser.DeclaredVariableProductionContext ctx)
	{
		String variableName = ctx.ID().getText();
		currentEnvironment.print();

		if (!currentEnvironment.hasSymbol(variableName, "variable"))
		{
			handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "La variable: " + variableName + " no ha sido declarada"
			);
			return "Error";
		}
		
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		//return super.visitDeclaredVariableProduction(ctx);
		String type = currentEnvironment.getSymbol(variableName, "variable").getType();
		return type ;
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
		if(expression.equals(andExpression)){
			return expression;
		}
		handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "Expecter type boolean"
			);
		System.out.println("Error Expected Type Boolean");
		return "Error";
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
		handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "Expecter type boolean"
			);
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
		printLine(equalsExpression);
		printLine(equal);
		printLine(relationExpression);
		if((equalsExpression.equals(relationExpression))){
			return equalsExpression;
		}
		handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "Expected same type of operands "
			);
		//System.out.println("Error Expected Type Boolean");
		return ("Error");
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
		if(relationExpression.equals(additionSubsExpression)){
			return relationExpression;	
		}
		handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "Expecter type integer"
			);
		System.out.println("Error Expected Type Boolean at line: "+ctx.getStart().getLine());
		return "Error";
	}

	@Override
	// [THIS WORDS]
	public String visitAddSubProduction(DECAFParser.AddSubProductionContext ctx) {
		// TODO Auto-generated method stub
		String additionSubsExpression = visit(ctx.getChild(0));
		String arithmetic = visit(ctx.getChild(1));
		String multDivExpression = visit(ctx.getChild(2));
		if(additionSubsExpression.equals(multDivExpression)){
			
			return additionSubsExpression;
		}
		handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "Expecter type integer"
			);
		System.out.println("Error Expected Type INTEGER at line: "+ctx.getStart().getLine());
		return "Error";
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
		if(multDivExpression.equals(percentageExpression)){	
			return multDivExpression;
		}
		handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "Expecter type integer"
			);
		System.out.println("Error Expected Type Boolean at line: "+ctx.getStart().getLine());
		return "Error";
	}

	@Override
	public String visitPrExpression(DECAFParser.PrExpressionContext ctx) {
		// TODO Auto-generated method stub
		if(ctx.getChildCount()==3){
			String percentageExpression = visit(ctx.getChild(0));
			String percent = visit(ctx.getChild(1));
			String basicExpression = visit(ctx.getChild(2));
			if(percentageExpression.equals(basicExpression)){
				return percentageExpression;
				
			}
			handleSemanticError("Error en la linea: "
					+ ctx.getStart().getLine()
					+ "\n "
					+ "Expecter type integer"
				);
			System.out.println("Error Expected Type int at line: "+ctx.getStart().getLine());
			return "Error";
		}
		else{
			String basicExpression = visitChildren(ctx);
			return basicExpression;
		}
	}

	@Override
	public String visitBasicExpression(DECAFParser.BasicExpressionContext ctx) {
		// TODO Auto-generated method stu
		if (ctx.getChildCount()==2){
			printLine(ctx.getChild(0).getText());
			if(ctx.getChild(0).getText().equals("-")){
				String integer=visit(ctx.basic());
				printLine("En integer basic");
				printLine(integer);
				if(integer.equals("int")){
					return integer;
				}
				else{
					handleSemanticError("Error en la linea: "
							+ ctx.getStart().getLine()
							+ "\n "
							+ "Expecter type integer"
						);
					return "Error";
				}
			}
			if(ctx.getChild(0).getText().equals("!")){
				String bool= visit(ctx.basic());
				printLine("En boolean basic");
				printLine(bool);
				if(bool.equals("boolean")){
					return bool;
				}
				else{
					handleSemanticError("Error en la linea: "
							+ ctx.getStart().getLine()
							+ "\n "
							+ "Expecter type boolean"
						);
					return "Error";
				}
			}
		}
		else{
			String type = visit(ctx.basic());
			return type;
		}
		return "Error";
	}

	@Override
	public String visitBasic(DECAFParser.BasicContext ctx) {
		// TODO Auto-generated method stub
		String type=visitChildren(ctx);
		return type;
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
		return ctx.getText();
	}

	@Override
	public String visitCond_op(DECAFParser.Cond_opContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCond_op(ctx);
	}

	@Override
	public String visitLiteral(DECAFParser.LiteralContext ctx) {
		// TODO Auto-generated method stub
		String type = visitChildren(ctx);
		return type;
	}

	@Override
	public String visitInt_literal(DECAFParser.Int_literalContext ctx) {
		// TODO Auto-generated method stub
		String type = "int";
		return type;
	}

	@Override
	public String visitChar_literal(DECAFParser.Char_literalContext ctx) {
		String type = "char";
		return type;
	}

	@Override
	public String visitBool_literal(DECAFParser.Bool_literalContext ctx) {
		// TODO Auto-generated method stub
		return "boolean";
	}

	private void handleSemanticError(String message)
	{
		errors.append(message+"\n");
		Path file = Paths.get("ErrorLog_Syntax.log");
		String newLine = System.getProperty("line.separator");
		
		try {
            //Files.deleteIfExists(file);
            Files.write(
        		file,
        		Arrays.asList("Semantic error: " + message+newLine),
        		Charset.forName("UTF-8")
    		);
        } catch (IOException e) {
            System.err.println("Something is wrong.");
        }
	}
	
	private void printLine(String message)
	{
		System.out.println(message);
	}
}
