/*
 * Visitor a implementarse para las reglas semánticas 
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MyVisitor2 extends DECAFBaseVisitor<String> 
{
	// We will need here the current environment
	Environment currentEnvironment = null;
	Stack<Environment> environmentsStack = new Stack<Environment>(); 
	public StringBuffer errors = new StringBuffer("\n Semantic Errors: \n");
	int n = 0;
	String methodType ="";

	// ADD [THIS WORDS] to the ones I think I'm going to use

	@Override
	public String visitProgramProduction(DECAFParser.ProgramProductionContext ctx) {
		String id = ctx.ID().getText();

		System.out.println("Creating new current environment");
		currentEnvironment = new Environment(null);
		String result = visitChildren(ctx);
		MethodSymbol mainOne = (MethodSymbol) currentEnvironment.getSymbol("main", "method");
		//System.out.println(mainOne.getFirm());
		currentEnvironment.print();
		if(currentEnvironment.hasSymbol("main", "method")){
			printLine("Ejecucion correcta! ");
			printLineToBuffer("Correct");
			return "void";
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
		String symbolType = visit(ctx.varType());
		String identifier = ctx.ID().getText();
		/*if(symbolType.contains("struct")){
			symbolType = "struct";
		}*/
		VariableSymbol currentSymbol = new VariableSymbol(
			symbolType,
			identifier,
			false,
			false
		);
		
		if (currentEnvironment.hasSymbol2(identifier, "variable")) {
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
		String retorno = super.visitArrayVariableProduction(ctx);
		String symbolType = ctx.varType().getText();
		String identifier = ctx.ID().getText();
		String number = "1";
		if(ctx.NUM() == null){
			handleSemanticError("Line: "+ctx.getStart().getLine() 
					+
				" Invalid array declaration"
			);
			return "Error";
		}
		else{
			
			number = ctx.NUM().getText();
		}
		int num= Integer.parseInt(number);
		VariableSymbol currentSymbol = new VariableSymbol(
			symbolType,
			identifier,
			false,
			true
		);
		
		if (currentEnvironment.hasSymbol(identifier, "variable")) {
			handleSemanticError("Line: "+ctx.getStart().getLine() 
					+
				" Identificador '" + identifier + "' ya utilizado en entorno actual"
			);
			
			return "Error";
		}
			if (num<= 0) {
				handleSemanticError("Line: "+ctx.getStart().getLine() 
						+
					" Current size'" + num + "' must be greater than 0"
				);
				
				return "Error";
			}
		// Paso número tres, agregar validación para determinar si variable existe
		currentEnvironment.putSymbol("variable", identifier, currentSymbol);
		
		return retorno ;
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
			
			return "Error";
		}

		// Instanciar simbolo y guardarlo en entorno actual
		Symbol currentSymbol = new Symbol(identifier, identifier, true);
		currentEnvironment.putSymbol("struct", identifier, currentSymbol);
		
		// Mandar al stack el entorno actual, y crear uno nuevo para representar el cuerpo del struct
		environmentsStack.push(currentEnvironment);
		currentEnvironment = new Environment(currentEnvironment);

		// Visitar el cuerpo del struct
		String result = super.visitStructDeclaration(ctx);

		currentEnvironment.print();
		// Regresar el entorno pusheado al entorno actual
		currentEnvironment = environmentsStack.pop();
		
		return result;
	}

	@Override
	public String visitVarType(DECAFParser.VarTypeContext ctx) {
		// TODO Auto-generated method stub
		if(ctx.getText().contains("struct")){
			System.out.println("VarType");
			System.out.println(ctx.ID().getText());
			return ctx.ID().getText();
		}
		return ctx.getText();
	}

	@Override
	// [THIS WORDS]
	public String visitMethodDeclarationProduction(DECAFParser.MethodDeclarationProductionContext ctx)
	{
		String symbolType = ctx.methodType().getText();
		methodType = symbolType;
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
        MethodSymbol currentSymbol = new MethodSymbol(
    			symbolType,
    			identifier,
    			false,
    			firm
    		);
    		//System.out.println(currentSymbol.getFirm());
    		currentEnvironment.putSymbol("method", identifier, currentSymbol);
		String result = visit(ctx.block());
		

		currentEnvironment = environmentsStack.pop();
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
		
		if (currentEnvironment.hasSymbol2(parameterIdentifier, "variable")) {
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
		String parameterType = ctx.parameterType().getText();
		String parameterIdentifier = ctx.ID().getText();
		VariableSymbol currentSymbol = new VariableSymbol(
			parameterType,
			parameterIdentifier,
			false,
			true
		);
		
		if (currentEnvironment.hasSymbol(parameterIdentifier, "variable")) {
			handleSemanticError("Line: "+ctx.getStart().getLine() 
				+" Identificador '" + parameterIdentifier + "' ya utilizado en entorno actual"
			);
			
			return "Error";
		}
		currentEnvironment.putSymbol("variable", parameterIdentifier, currentSymbol);
		//currentEnvironment.print();
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
		environmentsStack.push(currentEnvironment);
		currentEnvironment = new Environment(currentEnvironment);
		String block = visitChildren(ctx);
		currentEnvironment = environmentsStack.pop();
		return block;
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
		String leftType = visit(ctx.location());
		String rightType = visit(ctx.expression());
		if(leftType.equals(rightType)){
			return leftType;
		}
		else{
			handleSemanticError("Line: "+ctx.getStart().getLine() 
					+" Assignation types not compatible'" + leftType + " = "+rightType
				);
			return "Error";
		}
	}

	@Override
	// [THIS WORDS]
	public String visitWhileBlockProduction(DECAFParser.WhileBlockProductionContext ctx) {
		// TODO Auto-generated method stub
		String bool = visit(ctx.expression());
		visit(ctx.getChild(4));
		
		if(bool.equals("boolean")){
			return bool;
		}
		handleSemanticError("Line: "+ctx.getStart().getLine() 
				+" While parameter must be a  boolean Expression'" + bool + "'"
			);
		return "Error";
	}

	@Override
	// [THIS WORDS]
	public String visitReturnBlockProduction(DECAFParser.ReturnBlockProductionContext ctx, String test) {
		System.out.println("Visitando return con: " + test);
		
		// TODO: Comparar el resultado de super.visitReturnBlockProduction(ctx, test);
		// contra methodSymbol.type.
		String returnType = visit(ctx.nExpression());
		if(!methodType.equals(returnType)){
			handleSemanticError("Line: "+ctx.getStart().getLine() 
					+" Method type: " 
					+ methodType 
					+" Return Type: "
					+ returnType
					+"\n They must be the same"
				);
			return "Error";
		}
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
		String bool = visit(ctx.getChild(2));
		System.out.println(bool);
		String block = visit(ctx.getChild(4));
		if (ctx.getChild(5)!= null){

            String elseBlock = visit(ctx.getChild(6));
        }
		if(bool != null){
		if(bool.equals("boolean")){
			return bool;
		}
		handleSemanticError("Line: "+ctx.getStart().getLine() 
				+" If parameter must be a  boolean Expression'" + bool + "'"
			);
		return "Error";
		}
		handleSemanticError("Line: "+ctx.getStart().getLine() 
				+" If parameter must be a  boolean Expression'" + bool + "'"
			);
		return "Error";
	}

	@Override
	public String visitLocation(DECAFParser.LocationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLocation(ctx);
	}

	@Override
	public String visitDotLocation(DECAFParser.DotLocationContext ctx) {
		// TODO Auto-generated method stub
		currentEnvironment.print();
		System.out.println("dotLocation");
		String typeLeft = visit(ctx.variable());
		String typeRight = visit(ctx.location());
		if(currentEnvironment.hasSymbol(typeLeft, "struct")){
			System.out.println("STRUCTS");
			System.out.println(typeLeft);
			System.out.println(currentEnvironment.getSymbol(typeLeft,"struct" ));
			if(typeLeft.equals(typeRight)){
				System.out.println("STRUCTS");
				System.out.println(typeRight);
				return typeRight;
			}
			else{
				return "Error";
			}
		}
		else{
			return "Error";
		}
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
		//currentEnvironment.print();

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
		String variableName = ctx.ID().getText();
		//currentEnvironment.print();

		if (!currentEnvironment.hasSymbol(variableName, "variable"))
		{
			handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "La variable: " + variableName + " no ha sido declarada"
			);
			return "Error";
		}
		VariableSymbol theArray = (VariableSymbol)currentEnvironment.getSymbol(variableName, "variable");
		if(theArray.isArray()== false){
			handleSemanticError("Error en la linea: "
					+ ctx.getStart().getLine()
					+ "\n "
					+ "La variable: " + variableName + " Debe de ser de tipo array"
				);
			return "Error";
		}
		String typeOfArray = visit(ctx.expression());
		if(!typeOfArray.equals("int")){
			handleSemanticError("Error en la linea: "
					+ ctx.getStart().getLine()
					+ "\n "
					+ "Expression inside '[' ']' of the variable" + variableName + "must be type int "
				);
			return "Error";
		}
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		//return super.visitDeclaredVariableProduction(ctx);
		String type = currentEnvironment.getSymbol(variableName, "variable").getType();
		return type ;
	}

	
	@Override
	public String visitExpressionInP(DECAFParser.ExpressionInPContext ctx) {
		// TODO Auto-generated method stub
		String type = visit(ctx.getChild(1));
		return type;
	}

	@Override
	public String visitNExpression(DECAFParser.NExpressionContext ctx) {
		// TODO Auto-generated method stub
		if(ctx.expression() == null){
			return "void";
		}
		return super.visitNExpression(ctx);
	}

	@Override
	// [THIS WORDS]
	public String visitOrProduction(DECAFParser.OrProductionContext ctx) {
		// TODO Auto-generated method stub
		String expression = visit(ctx.getChild(0));
		String or = visit(ctx.getChild(1));
		String andExpression = visit(ctx.getChild(2));
		if(expression.equals(andExpression) &&
				((expression.equals("boolean"))||(andExpression.equals("boolean")))
				){
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
		if(andExpression.equals(equalsExpression)&&
				((andExpression.equals("boolean"))||(equalsExpression.equals("boolean")))
				){
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
		if((equalsExpression.equals(relationExpression))&&
				((equalsExpression.equals("boolean"))||(relationExpression.equals("boolean"))
						||(relationExpression.equals("int"))
						||(relationExpression.equals("char"))
						||(equalsExpression.equals("int"))
						||(relationExpression.equals("char"))
						)
				){
			return "boolean";
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
		if(relationExpression.equals(additionSubsExpression) &&
				((relationExpression.equals("int"))||(additionSubsExpression.equals("int")))
				){
			return "boolean";	
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
		System.out.println("Left: "+additionSubsExpression);
		System.out.println("Right: "+multDivExpression);
		if(additionSubsExpression.equals(multDivExpression)&&
				((multDivExpression.equals("int"))||(additionSubsExpression.equals("int")))
				){
			System.out.println(additionSubsExpression);
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
		if(multDivExpression.equals(percentageExpression)&&
				((multDivExpression.equals("int"))||(percentageExpression.equals("int")))
				){	
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
			if(percentageExpression.equals(basicExpression)&&
					((percentageExpression.equals("int"))||(basicExpression.equals("int")))
					){
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
		String type = visitChildren(ctx);
		return type;
	}

	@Override
	public String visitMethodCallProduction(DECAFParser.MethodCallProductionContext ctx) {
		// TODO Auto-generated method stub
		String variableName = ctx.ID().getText();
		//currentEnvironment.print();

		if (!currentEnvironment.hasSymbol(variableName, "method"))
		{
			handleSemanticError("Error en la linea: "
				+ ctx.getStart().getLine()
				+ "\n "
				+ "El metodo: " + variableName + " no ha sido declarado"
			);
			return "Error";
		}
		MethodSymbol currentMethod = (MethodSymbol)currentEnvironment.getSymbol(variableName, "method");
		ArrayList<String> typesInArguments = new ArrayList<String>();
		for(DECAFParser.ArgContext argument:ctx.arg()){
			typesInArguments.add(visit(argument));
		}
		if(typesInArguments.size() != currentMethod.getFirm().size()){
			handleSemanticError("Error en la linea: "
					+ ctx.getStart().getLine()
					+ "\n "
					+ "Parameter size not the same " + variableName 
				);
			return "Error";
		}
		ArrayList<String> parameterTypes = new ArrayList<String>();
		for(int i=0; i<  currentMethod.getFirm().size();i++){
			VariableSymbol symbol = (VariableSymbol)currentMethod.getFirm().get(i);
			String parameterType = symbol.getType();
			parameterTypes.add(parameterType);
		}
		Collections.sort(typesInArguments);
		Collections.sort(parameterTypes);
		if(!typesInArguments.equals(parameterTypes)){
				handleSemanticError("Error en la linea: "
						+ ctx.getStart().getLine()
						+ "\n "
						+ "Argument types: \n" 
						+ typesInArguments.toString()
						+ "\n Parameter Types: \n"
						+ parameterTypes.toString()
					);
				return "Error";
			
		}
		// TODO: Paso número tres, agregar validación para determinar si variable existe
		//return super.visitDeclaredVariableProduction(ctx);
		String type = currentEnvironment.getSymbol(variableName, "method").getType();
		return type ;
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
		
		errors.append("["+n+"]: "+message+"\n");
		//Path file = Paths.get("ErrorLog_Syntax.log");
		//String newLine = System.getProperty("line.separator");
		/*
		try {
            //Files.deleteIfExists(file);
            Files.write(
        		file,
        		Arrays.asList("Semantic error: " + message+newLine),
        		Charset.forName("UTF-8")
    		);
        } catch (IOException e) {
            System.err.println("Something is wrong.");
        }*/
		n++;
	}
	
	private void printLine(String message)
	{
		System.out.println(message);
	}
	public static boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	private void printLineToBuffer(String message)
	{
		errors.append(message+"\n");
	}
}
