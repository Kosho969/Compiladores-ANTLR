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
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class MyVisitor extends DECAFBaseVisitor<String> 
{
	// We will need here the current environment
	Environment currentEnvironment = null;
	ArrayList<String> containedOperations = new ArrayList<String>();
	List<String> x = new ArrayList<String>(Arrays.asList("+"
			, "-", "*", "/", "<", ">", "<=", ">=", "=="
			, "&&", "||", "%", "!="
			));

	Stack<Environment> environmentsStack = new Stack<Environment>(); 

	public StringBuffer errors = new StringBuffer("\n Semantic Errors: \n");
	
	IntermidiateCodeGenerator generator = new IntermidiateCodeGenerator();

	int n = 0;

	String methodType ="";
	
	ArrayList<String> operadoresPrimarios = new ArrayList<String>();
	ArrayList<String> operandos = new ArrayList<String>();
	ArrayList<String> params = new ArrayList<String>();
	String operador = "";
	
	ArrayList<String> codigoIntermedioArrayList = new ArrayList<String>();
	
	
	boolean ifExpression = false; 
	boolean whileExpression = false; 
	boolean arrayOperando = false;
	boolean assignation = false; 
	boolean isExpression = false; 
	boolean moreChilds = false; 
	boolean operandIsArray = false; 
	boolean operandIsMethod = false; 
	boolean location = false; 
	String leftLocation = "";
	String currentTemporarie = ""; 
	String arrayID = "";
	String newLeftSide = ""; 

	@Override
	public String visitProgramProduction(DECAFParser.ProgramProductionContext ctx) {
		String id = ctx.ID().getText();
		containedOperations.addAll(x);
		//System.out.println("Creating new current environment");
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

		VariableSymbol currentSymbol = new VariableSymbol(
			symbolType,
			identifier,
			false,
			false
		);
		
		if (currentEnvironment.hasSymbol2(identifier, "variable")) {
			handleSemanticError(
				"Line: "
					+ ctx.getStart().getLine() 
					+ " Identificador '"
					+ identifier
					+ "' ya utilizado en entorno actual"
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
			//System.out.println("VarType");
			System.out.println(ctx.ID().getText());
			return ctx.ID().getText();
		}
		return ctx.getText();
	}

	@Override
	public String visitMethodDeclarationProduction(DECAFParser.MethodDeclarationProductionContext ctx)
	{
		String symbolType = ctx.methodType().getText();
		methodType = symbolType;
		String identifier = ctx.ID().getText();
		
		createStartFunctionLabel(identifier);

		// Verificar que el nombre del método no haya sido usado
		// en una declaración previa del entorno actual
		if (currentEnvironment.hasSymbol(identifier, "method")) {
			handleSemanticError(
				"Line: "
					+ ctx.getStart().getLine() 
					+ " Identificador '"
					+ identifier
					+ "' para método ya utilizado en entorno actual"
			);
			
			return "";
		}

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
        	visit(parameter);
        	
        	DECAFParser.ParameterRegularDeclarationContext castedParameter =
        		(DECAFParser.ParameterRegularDeclarationContext) parameter;
        }

        for (int i = 0; i < currentEnvironment.getSymbolTable().size();i++){
        	VariableSymbol currentVariableSymbol= (VariableSymbol) currentEnvironment.getSymbolTable().get(i).getValue();
        	firm.add(currentVariableSymbol);
        }

        visit(ctx.RPARENT());

		String result = visit(ctx.block());

		currentEnvironment = environmentsStack.pop();
		
		MethodSymbol currentSymbol = new MethodSymbol(
			symbolType,
			identifier,
			false,
			firm
		);
		
		currentEnvironment.putSymbol("method", identifier, currentSymbol);
		
		createEndFunctionLabel(identifier);
		
		return result;
	}

	@Override
	public String visitMethodType(DECAFParser.MethodTypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMethodType(ctx);
	}

	@Override
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
		operandos.removeAll(operandos);
		//System.out.println("Operands: "+operandos);
		assignation = true; 
		String rightSide = ctx.expression().getText();
		String ledtSide = ctx.location().getText();
		System.out.println("Assign: "+ledtSide+" = "+rightSide);
		String leftType = visit(ctx.location());
		operandos.removeAll(operandos);
		location = false; 
		assignation = true;  
		newLeftSide = "";
		System.out.println("OperandsBefore right side: "+operandos);
		
		
		for (String operatorChild:containedOperations){
			if (rightSide.contains(operatorChild)){
				moreChilds = true; 
				System.out.println("RightSide: "+rightSide);
			}
		}
		if (!leftLocation.equals("")){
			ledtSide = leftLocation;
		}
		String rightType = visit(ctx.expression());
		if(moreChilds == false){
			if (operandIsArray == true){
				String right = operandos.get(0);
				appendToCodigoIntermedio("\t"+ledtSide+" = "+right);
			}
			else if (operandIsMethod == true){
				String right = operandos.get(0);
				appendToCodigoIntermedio("\t"+ledtSide+" = "+right);
			}
			else{
				appendToCodigoIntermedio("\t"+ledtSide+" = "+rightSide);
			}
		}
		else{
			appendToCodigoIntermedio("\t"+ledtSide+" = "+currentTemporarie);
		}
		operadoresPrimarios.removeAll(operadoresPrimarios);
		operador = "";
		operandos.removeAll(operandos);
		operandIsArray = false;
		operandIsMethod = false; 
		currentTemporarie = "";
		moreChilds = false;
		isExpression = false;
		leftLocation = "";
		currentTemporarie = "";
		
		if(leftType.equals(rightType)){
			assignation = false; 
			
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
		String startWhileLabel = generator.newWhileLabel();
		String endWhile = generator.endWhileLabel();
		appendToCodigoIntermedio(startWhileLabel);
		whileExpression = true; 
		String bool = visit(ctx.expression());
		int temporalesHechas = generator.getTemporarieCount();
		int lineasNuevas = operadoresPrimarios.size()+1;
		if(lineasNuevas == 0)
			lineasNuevas=1;
		int temporalesAValidar = temporalesHechas - lineasNuevas;
		String labelTrue = generator.newTrueLabel();
		String labelFals = "";
		if (operadoresPrimarios.contains("&&"))
			 labelFals = generator.newFalseLabel();
		int contadorOperandos = 0;
		String prev = "";
		String next = "";
		boolean appendNow = false; 
		for (int i = 0; i<lineasNuevas;i++){
			String operator = "";
			if (operadoresPrimarios.size()!= 0){
				operator = operadoresPrimarios.get(0);
				prev = operator;
			}
			if(contadorOperandos == 2){
				
				contadorOperandos = 0;
				operadoresPrimarios.remove(0);
			}
			if (operadoresPrimarios.size()!= 0){
				operator = operadoresPrimarios.get(0);
			}
			next = operator;
			if (!prev.equals(next)){
				appendNow = true;
				if (prev.equals("||")){
					appendToCodigoIntermedio("\t GOTO "+endWhile);
				}
				else if(prev.equals("&&")){
					appendToCodigoIntermedio("\t GOTO "+labelTrue);
				}
				
			}
			if(operator.equals("||")|| (operator.equals(""))){
				appendToCodigoIntermedio("\t IF t"+temporalesAValidar+" > 0 GOTO "+labelTrue);
				temporalesAValidar++;
			}
			if(operator.equals("&&")){
				appendToCodigoIntermedio("\t IF t"+temporalesAValidar+" = 0 GOTO "+endWhile);
				temporalesAValidar++;
			}
			contadorOperandos++;
		}
		if (appendNow == false){
			if (prev.equals("||")|| (prev.equals(""))){
				appendToCodigoIntermedio("\t GOTO "+endWhile);
			}
			else if(prev.equals("&&")){
				appendToCodigoIntermedio("\t GOTO "+labelTrue);
			}
		}
		
		whileExpression = false;
		
		operadoresPrimarios.removeAll(operadoresPrimarios);
		operador = "";
		operandos.removeAll(operandos);
				
		appendToCodigoIntermedio(labelTrue+": \n");
		//Visitar el bloque del while
		visit(ctx.getChild(4));
		appendToCodigoIntermedio(endWhile+": \n");
		
		if(bool.equals("boolean")){
			whileExpression = false; 
			return bool;
		}
		handleSemanticError("Line: "+ctx.getStart().getLine() 
				+" While parameter must be a  boolean Expression'" + bool + "'"
			);
		whileExpression = false;
		
		return "Error";
	}

	@Override
	// [THIS WORDS]
	public String visitReturnBlockProduction(DECAFParser.ReturnBlockProductionContext ctx, String test) {
		//System.out.println("Visitando return con: " + test);
		
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
		ifExpression = true; 
		String bool = visit(ctx.getChild(2));
		String ifLabel = generator.newIfLabel();
		int temporalesHechas = generator.getTemporarieCount();
		int lineasNuevas = operadoresPrimarios.size()+1;
		if(lineasNuevas == 0)
			lineasNuevas=1;
		int temporalesAValidar = temporalesHechas - lineasNuevas;
		String labelTrue = generator.newTrueLabel();
		String labelFals = "";
		if (operadoresPrimarios.contains("&&"))
			 labelFals = generator.newFalseLabel();
		int contadorOperandos = 0;
		String prev = "";
		String next = "";
		boolean appendNow = false; 
		for (int i = 0; i<lineasNuevas;i++){
			String operator = "";
			if (operadoresPrimarios.size()!= 0){
				operator = operadoresPrimarios.get(0);
				prev = operator;
			}
			if(contadorOperandos == 2){
				
				contadorOperandos = 0;
				operadoresPrimarios.remove(0);
			}
			if (operadoresPrimarios.size()!= 0){
				operator = operadoresPrimarios.get(0);
			}
			next = operator;
			if (!prev.equals(next)){
				appendNow = true;
				if (prev.equals("||")){
					appendToCodigoIntermedio("\t GOTO "+ifLabel);
				}
				else if(prev.equals("&&")){
					appendToCodigoIntermedio("\t GOTO "+labelTrue);
				}
				
			}
			if(operator.equals("||")|| (operator.equals(""))){
				appendToCodigoIntermedio("\t IF t"+temporalesAValidar+" > 0 GOTO "+labelTrue);
				temporalesAValidar++;
			}
			if(operator.equals("&&")){
				appendToCodigoIntermedio("\t IF t"+temporalesAValidar+" = 0 GOTO "+ifLabel);
				temporalesAValidar++;
			}
			contadorOperandos++;
		}
		if (appendNow == false){
			if (prev.equals("||")|| (prev.equals(""))){
				appendToCodigoIntermedio("\t GOTO "+ifLabel);
			}
			else if(prev.equals("&&")){
				appendToCodigoIntermedio("\t GOTO "+labelTrue);
			}
		}
		ifExpression = false;
		operadoresPrimarios.removeAll(operadoresPrimarios);
		operador = "";
		operandos.removeAll(operandos);
		//System.out.println(bool);
		appendToCodigoIntermedio(labelTrue+": \n");
		String block = visit(ctx.getChild(4));
		if(ctx.getChild(5)!= null){
			appendToCodigoIntermedio(ifLabel+": \n");
			String elseBlock = visit(ctx.getChild(5));
		}
		if(bool.equals("boolean")){
			return bool;
		}
		handleSemanticError("Line: "+ctx.getStart().getLine() 
				+" If parameter must be a  boolean Expression'" + bool + "'"
			);
		return "Error";
	}

	@Override
	public String visitLocation(DECAFParser.LocationContext ctx) {
		// TODO Auto-generated method stub
		if (assignation == true){
		location = true; 
		}
		return super.visitLocation(ctx);
	}

	@Override
	public String visitDotLocation(DECAFParser.DotLocationContext ctx) {
		// TODO Auto-generated method stub
		currentEnvironment.print();
		//System.out.println("dotLocation");
		String typeLeft = visit(ctx.variable());
		String typeRight = visit(ctx.location());
		if(currentEnvironment.hasSymbol(typeLeft, "struct")){
			//System.out.println("STRUCTS");
			//System.out.println(typeLeft);
			//System.out.println(currentEnvironment.getSymbol(typeLeft,"struct" ));
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
		if((ifExpression == true)|| (whileExpression == true)){
				
			operandos.add(variableName);
			
			
		}
		else if(isExpression == true ){
			//System.out.println("In");
			if (currentTemporarie.equals("")){
				operandos.add(variableName);
			}
			else{
				operandos.add(0,currentTemporarie);
			}
			System.out.println("Operands inside expr: "+operandos);
		}
		else if(location == true && arrayOperando == true && isExpression == false){
			//System.out.println("In");
			newLeftSide = variableName;
		}
		if(((ifExpression == true)|| (whileExpression == true)) && (arrayOperando == true)){
			//System.out.println("In4");
			
			String temporary = operandos.get(0);
			operandos.remove(temporary);
			//temporary = arrayID+"["+temporary+"]";
			appendToCodigoIntermedio("\t"+generator.newTemporary()+temporary+" * "+arrayID+"[]");
			temporary = "T"+(generator.getTemporarieCount()-1);
			operandos.add(0, temporary);
		}
		else if(isExpression == true && moreChilds == true && (arrayOperando == true)){
			//System.out.println("In3");
			String temporary = operandos.get(0);
			operandos.remove(temporary);
			//temporary = arrayID+"["+temporary+"]";
			appendToCodigoIntermedio("\t"+generator.newTemporary()+temporary+" * "+arrayID+"[]");
			temporary = "T"+(generator.getTemporarieCount()-1);
			operandos.add(0, temporary);
		}
		else if(isExpression == true && moreChilds == false && (arrayOperando == true)){
			System.out.println("In3");
			String temporary = operandos.get(0);
			operandos.remove(temporary);
			//temporary = arrayID+"["+temporary+"]";
			appendToCodigoIntermedio("\t"+generator.newTemporary()+temporary+" * "+arrayID+"[]");
			temporary = "T"+(generator.getTemporarieCount()-1);
			operandos.add(0, temporary);
			leftLocation = temporary;
			
		}
		else if(location == true && isExpression == true && assignation == true && arrayOperando == true){
			System.out.println("LocationArray");
			String temporary = newLeftSide;
			//operandos.remove(temporary);
			//temporary = arrayID+"["+temporary+"]";
			appendToCodigoIntermedio("\t"+generator.newTemporary()+temporary+" * "+arrayID+"[]");
			temporary = "T"+(generator.getTemporarieCount()-1);
			leftLocation = temporary;
		}
		String type = currentEnvironment.getSymbol(variableName, "variable").getType();
		return type ;
	}

	@Override
	// [THIS WORDS]
	public String visitDeclaredArrayProduction(DECAFParser.DeclaredArrayProductionContext ctx) {
		// TODO Auto-generated method stub
		String variableName = ctx.ID().getText();
		System.out.println("Declared Array");
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
		if((ifExpression == true)|| (whileExpression == true)){
			arrayOperando = true;
			arrayID = variableName;
		}
		if (isExpression == true || location == true){
			arrayOperando = true;
			operandIsArray = true;
			arrayID = variableName;
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
		arrayOperando = false;
		arrayID = "";
		String type = currentEnvironment.getSymbol(variableName, "variable").getType();
		return type ;
	}
	
	@Override
	public String visitExpressionInP(DECAFParser.ExpressionInPContext ctx) {
		// TODO Auto-generated method stub
		return visit(ctx.expression());
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
		String or = (ctx.getChild(1).getText());
		String andExpression = visit(ctx.getChild(2));
		if (or != null){	
			operadoresPrimarios.add(or);
		}
		if(expression.equals(andExpression) &&
				((expression.equals("boolean"))||(andExpression.equals("boolean")))
				){
			//String operador1 = operadorPr
			//System.out.println("T[N] = ")
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
		
		if (assignation == true){
			isExpression = true; 
		}
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
	public String visitAndProduction(DECAFParser.AndProductionContext ctx) {
		// TODO Auto-generated method stub
		
		String andExpression = visit(ctx.getChild(0));
		String and = (ctx.getChild(1).getText());
		String equalsExpression = visit(ctx.getChild(2));
		if (and != null){	
			operadoresPrimarios.add(and);
		}
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
		String relation = (ctx.getChild(1).getText());
		String additionSubsExpression = visit(ctx.getChild(2));
		if(relationExpression.equals(additionSubsExpression) &&
				((relationExpression.equals("int"))||(additionSubsExpression.equals("int")))
				){
			//System.out.println(operandos);
			if (whileExpression == true || ifExpression == true){
				operador = relation;
				String operando1 = operandos.get(0);
				operandos.remove(operando1);
				String operando2= operandos.get(0);
				operandos.remove(operando2);
				appendToCodigoIntermedio("\t"+generator.newTemporary()+operando1+" "+relation+" "+operando2);
				operador = "";
			}
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
		String basicLeft = ctx.getChild(0).getText();
		String op = ctx.getChild(1).getText();
		String basicRight = ctx.getChild(2).getText();
		if(additionSubsExpression.equals(multDivExpression)&&
				((multDivExpression.equals("int"))||(additionSubsExpression.equals("int")))
				){
			if(currentTemporarie.equals("")){
				if (isExpression == true){
					System.out.println("Operands inside add: "+operandos);
					operador = op;
					String operando1 = operandos.get(0);
					operandos.remove(operando1);
					String operando2= operandos.get(0);
					operandos.remove(operando2);
					appendToCodigoIntermedio("\t"+generator.newTemporary()+operando1+" "+op+" "+operando2);
					currentTemporarie = "T"+(generator.getTemporarieCount()-1);
					operador = "";
				}
			}
			else{
				if (isExpression == true){
					System.out.println("Operands inside add_subs: "+operandos);
					operador = op;
					String operando1 = operandos.get(0);
					operandos.remove(operando1);
					String operando2= operandos.get(0);
					operandos.remove(operando2);
					appendToCodigoIntermedio("\t"+generator.newTemporary()+currentTemporarie+" "+op+" "+operando2);
					currentTemporarie = "T"+(generator.getTemporarieCount()-1);
					operador = "";
				}
			}
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
		if(isExpression ==true){
			operandIsMethod = true;
			appendToCodigoIntermedio("\tCALL "+variableName);
			appendToCodigoIntermedio("\t"+generator.newTemporary()+"R");
			operandos.add(0,"T"+(generator.getTemporarieCount()-1));
			System.out.println(operandos);
		}
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
		String operador = ctx.getChild(0).getText();
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
		if(!currentTemporarie.equals("")){
			operandos.add(currentTemporarie);
		}
		if((ifExpression == true)|| (whileExpression == true)|| (isExpression == true)){
			operandos.add(ctx.getChild(0).getChild(0).getText());
		}
		if(((ifExpression == true)|| (whileExpression == true)) && (arrayOperando == true)){
			//System.out.println("In4");
			String temporary = operandos.get(0);
			operandos.remove(temporary);
			//temporary = arrayID+"["+temporary+"]";
			appendToCodigoIntermedio("\t"+generator.newTemporary()+temporary+" * "+arrayID+"[]");
			temporary = "T"+(generator.getTemporarieCount()-1);
			operandos.add(0, temporary);
		}
		if(isExpression == true && moreChilds == true && (arrayOperando == true)){
			//System.out.println("In3");
			String temporary = operandos.get(0);
			operandos.remove(temporary);
			//temporary = arrayID+"["+temporary+"]";
			appendToCodigoIntermedio("\t"+generator.newTemporary()+temporary+" * "+arrayID+"[]");
			temporary = "T"+(generator.getTemporarieCount()-1);
			operandos.add(0, temporary);
		}
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

		n++;
	}
	
	private void printLineToBuffer(String message)
	{
		errors.append(message+"\n");
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
	
	public ArrayList<String> getCodigoIntermedioArrayList()
	{	
		return codigoIntermedioArrayList;
	}
	
	public void appendToCodigoIntermedio(String lineaCodigo)
	{
		codigoIntermedioArrayList.add(lineaCodigo);
	}

	public void createStartFunctionLabel(String name){
		String label = "FUNCTION_" + name + ":";

		appendToCodigoIntermedio(label);
	}
	
	public void createEndFunctionLabel(String name) {
		String label = "END_FUNCTION_" + name + ":";

		appendToCodigoIntermedio(label);		
	}
}
