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
    List<String> x = new ArrayList<String>(Arrays.asList(
        "+", "-", "*", "/", "<", ">", "<=", ">=", "==", "&&", "||", "%", "!="
    ));
    Stack<Environment> environmentsStack = new Stack<Environment>();
    public StringBuffer errors = new StringBuffer("\n Semantic Errors: \n");
    IntermidiateCodeGenerator generator = new IntermidiateCodeGenerator();
    int n = 0;
    String methodType ="";
    ArrayList<String> operadoresPrimarios = new ArrayList<String>();
    ArrayList<String> operandos = new ArrayList<String>();
    ArrayList<String> params = new ArrayList<String>();
    ArrayList<String> arguments = new ArrayList<String>();
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
    boolean argument = false;
    boolean returnBlock = false; 
    String leftLocation = "";
    String currentTemporarie = ""; 
    String arrayID = "";
    String newLeftSide = ""; 

    @Override
    public String visitProgramProduction(DECAFParser.ProgramProductionContext ctx) {
        String id = ctx.ID().getText();
        containedOperations.addAll(x);
        currentEnvironment = new Environment(null);
        currentEnvironment.isClassEnvironment = true;

        String result = visitChildren(ctx);

        MethodSymbol mainOne = (MethodSymbol) currentEnvironment.getSymbol("main", "method");

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

        // Paso número tres, agregar validación para determinar si variable existe
        currentEnvironment.putSymbol("variable", identifier, currentSymbol);

        return retorno;
    }
    
    @Override
    public String visitArrayVariableProduction(DECAFParser.ArrayVariableProductionContext ctx) {
        String retorno = super.visitArrayVariableProduction(ctx);
        String symbolType = ctx.varType().getText();
        String identifier = ctx.ID().getText();
        String number = "1";
        number = ctx.NUM().getText();
        int num = Integer.parseInt(number);
        VariableSymbol currentSymbol = new VariableSymbol(
            symbolType,
            identifier,
            false,
            true
        );

        // Paso número tres, agregar validación para determinar si variable existe
        currentEnvironment.putSymbol("variable", identifier, currentSymbol);

        return retorno ;
    }

    @Override
    public String visitStructDeclaration(DECAFParser.StructDeclarationContext ctx)
    {
        // Procesar identificador
        String identifier = ctx.ID().getText();

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
        if(ctx.getText().contains("struct")){
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

        // Mandar entorno actual al stack y crear nuevo entorno
        environmentsStack.push(currentEnvironment);
        currentEnvironment = new Environment(currentEnvironment);
        currentEnvironment.isMethodEnvironment = true;

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
            VariableSymbol currentVariableSymbol
                = (VariableSymbol) currentEnvironment.getSymbolTable().get(i).getValue();

            firm.add(currentVariableSymbol);
        }

        visit(ctx.RPARENT());

        MethodSymbol currentSymbol = new MethodSymbol(
            symbolType,
            identifier,
            false,
            firm
        );

        currentEnvironment.putSymbol("method", identifier, currentSymbol);
        String result = visit(ctx.block());

        currentEnvironment = environmentsStack.pop();
        currentEnvironment.putSymbol("method", identifier, currentSymbol);
        createEndFunctionLabel(identifier);

        return result;
    }

    @Override
    public String visitParameterRegularDeclaration(DECAFParser.ParameterRegularDeclarationContext ctx) {
        String parameterType = ctx.parameterType().getText();
        String parameterIdentifier = ctx.ID().getText();

        VariableSymbol currentSymbol = new VariableSymbol(
            parameterType,
            parameterIdentifier,
            false,
            false
        );

        currentEnvironment.putSymbol(
            "variable",
            parameterIdentifier,
            currentSymbol
        );

        return super.visitParameterRegularDeclaration(ctx);
    }

    @Override
    public String visitParameterArrayDeclaration(DECAFParser.ParameterArrayDeclarationContext ctx) {
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
    public String visitBlock(DECAFParser.BlockContext ctx) {
        environmentsStack.push(currentEnvironment);
        currentEnvironment = new Environment(currentEnvironment);
        String block = visitChildren(ctx);
        currentEnvironment = environmentsStack.pop();

        return block;
    }

    @Override
    public String visitAssignationProduction(DECAFParser.AssignationProductionContext ctx) {
        operandos.removeAll(operandos);

        assignation = true;

        String rightSide = ctx.expression().getText();

        String leftSide = ctx.location().getText();
        System.out.println("LEFT: "+leftSide);
        System.out.println("RIGHT: "+rightSide);

        String leftType = visit(ctx.location());
        
        // TODO: Considerar acceso por el lado izquierdo a variables de estructuras o de arrays
        
        leftSide = operandos.get(0);
        System.out.println("LeftSide: "+operandos);
        /*if (leftSide.contains("T")){
        	leftLocation = "M["+leftSide+"]";
        }*/
        operandos.removeAll(operandos);

        location = false; 
        assignation = true;  
        newLeftSide = "";
        
        for (String operatorChild:containedOperations){
            if (rightSide.contains(operatorChild)){
                moreChilds = true; 
                System.out.println("RightSide: "+rightSide);
            }
        }

        if (!leftLocation.equals("")){
            leftSide = leftLocation;
        }
        System.out.println("Location: "+leftLocation);
        String rightType = visit(ctx.expression());
        if (moreChilds == false){
            if (operandIsArray == true){
                String right = operandos.get(0);
                //System.out.println("RIGHT: "+right);
                appendToCodigoIntermedio(leftSide+" = "+right);
            }
            else if (operandIsMethod == true){
                String right = operandos.get(0);
                //System.out.println("RIGHT: "+right);
                appendToCodigoIntermedio(leftSide+" = "+right);
            }
            else{
            	String right = operandos.get(0);
            	//System.out.println("RIGHT: "+right);
                appendToCodigoIntermedio(leftSide+" = "+right);
            }
        } else{
            appendToCodigoIntermedio(leftSide+" = "+currentTemporarie);
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
        
        assignation = false; 
        
        return leftType;
    }

    @Override
    public String visitWhileBlockProduction(DECAFParser.WhileBlockProductionContext ctx) {
        String startWhileLabel = generator.newWhileLabel();
        String endWhile = generator.endWhileLabel();
        String labelTrue = generator.newTrueLabel();
        String labelFals = "";
        appendLabelToCodigoIntermedio(startWhileLabel+":");
        whileExpression = true; 
        String bool = visit(ctx.expression());
        appendToCodigoIntermedio("IF "+currentTemporarie+" > 0 GOTO "+labelTrue);
        appendToCodigoIntermedio("GOTO "+endWhile);
       /* int temporalesHechas = generator.getTemporarieCount();
        int lineasNuevas = operadoresPrimarios.size()+1;
        if(lineasNuevas == 0)
            lineasNuevas=1;
        int temporalesAValidar = temporalesHechas - lineasNuevas;
       
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
                    appendToCodigoIntermedio("GOTO "+endWhile);
                }
                else if(prev.equals("&&")){
                    appendToCodigoIntermedio("GOTO "+labelTrue);
                }
                
            }
            if(operator.equals("||")|| (operator.equals(""))){
                appendToCodigoIntermedio("IF t"+temporalesAValidar+" > 0 GOTO "+labelTrue);
                temporalesAValidar++;
            }
            if(operator.equals("&&")){
                appendToCodigoIntermedio("IF t"+temporalesAValidar+" = 0 GOTO "+endWhile);
                temporalesAValidar++;
            }
            contadorOperandos++;
        }
        if (appendNow == false){
            if (prev.equals("||")|| (prev.equals(""))){
                appendToCodigoIntermedio("GOTO "+endWhile);
            }
            else if(prev.equals("&&")){
                appendToCodigoIntermedio("GOTO "+labelTrue);
            }
        }
        
        whileExpression = false;
        
        operadoresPrimarios.removeAll(operadoresPrimarios);
        operador = "";
        operandos.removeAll(operandos);*/
                
        appendLabelToCodigoIntermedio(labelTrue+":");
        //Visitar el bloque del while
        currentTemporarie = "";
        visit(ctx.getChild(4));
        appendToCodigoIntermedio("GOTO "+startWhileLabel);
        appendLabelToCodigoIntermedio(endWhile+":");
        
        
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
    public String visitReturnBlockProduction(DECAFParser.ReturnBlockProductionContext ctx, String test) {
        // TODO: Comparar el resultado de super.visitReturnBlockProduction(ctx, test);
        // contra methodSymbol.type.
    	returnBlock = true;
    	System.out.println("RETURN "+ ctx.getAltNumber());
        String returnType = visit(ctx.nExpression());
        
        if(!currentTemporarie.equals("")){
            appendToCodigoIntermedio("RETURN "+currentTemporarie);
        	//arguments.add(currentTemporarie);
            currentTemporarie = "";
        }
        else{
        	appendToCodigoIntermedio("RETURN "+operandos.get(0));
        	operandos.remove(0);
        }
        returnBlock = false;
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
    public String visitIfProduction(DECAFParser.IfProductionContext ctx) {
        ifExpression = true;

        String bool = visit(ctx.getChild(2));

        String ifLabel = generator.newIfLabel();
        String labelTrue = generator.newTrueLabel();
        String labelElse = generator.newElseLabel();
        appendToCodigoIntermedio("IF " + currentTemporarie + " > 0 GOTO " + labelTrue);
        if (ctx.getChild(5)!= null){
        	appendToCodigoIntermedio("GOTO " + labelElse);
        }
        else{
        	appendToCodigoIntermedio("GOTO " + ifLabel);
        }
        
        

        /*int temporalesHechas = generator.getTemporarieCount();
        
        // Contador de 'ors' y 'ands' ocurridos dentro de la expresión
        int lineasNuevas = operadoresPrimarios.size() + 1;

        if (lineasNuevas == 0)
            lineasNuevas = 1;

        // Temporales por cada parte de la expresión
        int temporalesAValidar = temporalesHechas - lineasNuevas;
       

        int contadorOperandos = 0;
        String prev = "";
        String next = "";
        boolean appendNow = false;

        for (int i = 0; i < lineasNuevas; i++){
            String operator = "";

            if (operadoresPrimarios.size()!= 0){
                operator = operadoresPrimarios.get(0);
                prev = operator;
            }

            if (contadorOperandos == 2){
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
                    appendToCodigoIntermedio("GOTO " + ifLabel);
                } else if(prev.equals("&&")){
                    appendToCodigoIntermedio("GOTO " + labelTrue);
                }
                
            }

            if (operator.equals("||")|| (operator.equals(""))){
                appendToCodigoIntermedio("IF t" + temporalesAValidar + " > 0 GOTO " + labelTrue);

                temporalesAValidar++;
            }

            if (operator.equals("&&")){
                appendToCodigoIntermedio("IF t" + temporalesAValidar + " = 0 GOTO " + ifLabel);

                temporalesAValidar++;
            }

            contadorOperandos++;
        }

        if (appendNow == false){
            if (prev.equals("||")|| (prev.equals(""))){
                appendToCodigoIntermedio("GOTO " + ifLabel);
            }
            else if(prev.equals("&&")){
                appendToCodigoIntermedio("GOTO " + labelTrue);
            }
        }*/

        ifExpression = false;
        operadoresPrimarios.removeAll(operadoresPrimarios);
        operador = "";
        operandos.removeAll(operandos);
        currentTemporarie = "";

        appendLabelToCodigoIntermedio(labelTrue+":");
        String block = visit(ctx.getChild(4));
        appendToCodigoIntermedio("GOTO " + ifLabel);

        if (ctx.getChild(5)!= null){
            appendLabelToCodigoIntermedio(labelElse + ":");

            String elseBlock = visit(ctx.getChild(6));
            appendToCodigoIntermedio("GOTO " + ifLabel);
        }

        appendLabelToCodigoIntermedio(ifLabel + ":");
        
        if (bool.equals("boolean")){
            return bool;
        }

        handleSemanticError(
            "Line: "
                +ctx.getStart().getLine() 
                + " If parameter must be a  boolean Expression'"
                + bool
                + "'"
        );

        return "Error";
    }

    @Override
    public String visitLocation(DECAFParser.LocationContext ctx) {
        if (assignation == true){
        	location = true; 
        }

        return super.visitLocation(ctx);
    }

    @Override
    public String visitDotLocation(DECAFParser.DotLocationContext ctx) {
        currentEnvironment.print();
        String typeLeft = visit(ctx.variable());
        String typeRight = visit(ctx.location());
        if (currentEnvironment.hasSymbol(typeLeft, "struct")){
            if (typeLeft.equals(typeRight)){
                System.out.println("STRUCTS");
                System.out.println(typeRight);
                return typeRight;
            } else{
                return "Error";
            }
        }
        else{
            return "Error";
        }
    }

    @Override
    public String visitDeclaredVariableProduction(DECAFParser.DeclaredVariableProductionContext ctx)
    {
        String variableName = ctx.ID().getText();
        VariableSymbol symbol = (VariableSymbol) currentEnvironment.getSymbol(variableName, "variable");
        variableName = symbol.getIntermediateName();

        if(((ifExpression == true)|| (whileExpression == true)|| location == true || argument == true || returnBlock == true)){
            operandos.add(0, variableName);
        } else if(isExpression == true ){
        	System.out.println(currentTemporarie);
            if (currentTemporarie.equals("")){
                operandos.add(0, variableName);
            } else {
                operandos.add(0, currentTemporarie);
            }
        } else if (location == true && arrayOperando == true && isExpression == false){
        	System.out.println("Estoy Array");
        	//operandos.add(0, variableName);
            newLeftSide = variableName;
        }

        if(((ifExpression == true) || (whileExpression == true)) && (arrayOperando == true)){
            String temporary = operandos.get(0);
            operandos.remove(temporary);
            appendToCodigoIntermedio(generator.newTemporary()+temporary+" * "+arrayID+"");
            temporary = "M["+"T"+(generator.getTemporarieCount()-1)+"]";
            operandos.add(0, temporary);
        } else if(isExpression == true && moreChilds == true && (arrayOperando == true)){
            String temporary = operandos.get(0);
            operandos.remove(temporary);
            appendToCodigoIntermedio(generator.newTemporary()+temporary+" * "+arrayID+"");
            temporary = "M["+"T"+(generator.getTemporarieCount()-1)+"]";
            operandos.add(0, temporary);
        } else if(isExpression == true && moreChilds == false && (arrayOperando == true)){
            System.out.println("In3");
            String temporary = operandos.get(0);
            operandos.remove(temporary);
            appendToCodigoIntermedio(generator.newTemporary()+temporary+" * "+arrayID+"");
            temporary = "M["+"T"+(generator.getTemporarieCount()-1)+"]";
            operandos.add(0, temporary);
            leftLocation = "M["+temporary+"]";
        } else if(location == true && isExpression == true && assignation == true && arrayOperando == true){
            System.out.println("LocationArray");
            String temporary = newLeftSide;
            appendToCodigoIntermedio(generator.newTemporary()+temporary+" * "+arrayID+"");
            temporary = "M["+"T"+(generator.getTemporarieCount()-1)+"]";
            leftLocation = "M["+temporary+"]";
        }

        return symbol.getType();
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
        VariableSymbol symbol = (VariableSymbol) currentEnvironment.getSymbol(variableName, "variable");
        variableName = symbol.getIntermediateName();
        if((ifExpression == true)|| (whileExpression == true)){
            arrayOperando = true;
            arrayID = "4";
        }
        if (isExpression == true || location == true){
            arrayOperando = true;
            operandIsArray = true;
            arrayID = "4";
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
        variableName = ctx.ID().getText();
        arrayOperando = false;
        arrayID = "";
        String type = currentEnvironment.getSymbol(variableName, "variable").getType();
        return type ;
    }
    
    @Override
    public String visitExpressionInP(DECAFParser.ExpressionInPContext ctx) {
        // TODO Auto-generated method stub
    	String visita = visit(ctx.expression());
    	if (!currentTemporarie.equals("")){
    		operandos.add(0,currentTemporarie);
    	}
        return visita;
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
        	System.out.println("OR PRODUCTION: "+operandos);
        	if (whileExpression == true || ifExpression == true || isExpression == true){
                operador = or;
                String operando1 = operandos.get(0);
                operandos.remove(operando1);
                String operando2= operandos.get(0);
                operandos.remove(operando2);
                appendToCodigoIntermedio(generator.newTemporary()+operando2+" "+"OR"+" "+operando1);
                currentTemporarie = "T"+(generator.getTemporarieCount()-1);
                operador = "";
            }
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
        
        if (assignation == true || argument){
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
        	if (whileExpression == true || ifExpression == true || isExpression == true){
                operador = and;
                String operando1 = operandos.get(0);
                operandos.remove(operando1);
                String operando2= operandos.get(0);
                operandos.remove(operando2);
                appendToCodigoIntermedio(generator.newTemporary()+operando2+" "+"AND"+" "+operando1);
                currentTemporarie = "T"+(generator.getTemporarieCount()-1);
                operador = "";
            }
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
        	 if (whileExpression == true || ifExpression == true|| isExpression == true){
                 operador = equal;
                 String operando1 = operandos.get(0);
                 operandos.remove(operando1);
                 String operando2= operandos.get(0);
                 operandos.remove(operando2);
                 appendToCodigoIntermedio(generator.newTemporary()+operando2+" "+equal+" "+operando1);
                 operandos.add("T"+(generator.getTemporarieCount()-1));
                 currentTemporarie = "T"+(generator.getTemporarieCount()-1);
                 operador = "";
             }
        	
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
            if (whileExpression == true || ifExpression == true || isExpression == true){
                operador = relation;
                String operando1 = operandos.get(0);
                operandos.remove(operando1);
                String operando2= operandos.get(0);
                operandos.remove(operando2);
                appendToCodigoIntermedio(generator.newTemporary()+operando2+" "+relation+" "+operando1);
                operandos.add("T"+(generator.getTemporarieCount()-1));
                currentTemporarie = "T"+(generator.getTemporarieCount()-1);
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
    public String visitAddSubProduction(DECAFParser.AddSubProductionContext ctx) {
        // TODO Auto-generated method stub
        String additionSubsExpression = visit(ctx.getChild(0));
        String arithmetic = visit(ctx.getChild(1));
        String multDivExpression = visit(ctx.getChild(2));
        String basicLeft = ctx.getChild(0).getText();
        String op = ctx.getChild(1).getText();
        String basicRight = ctx.getChild(2).getText();
        if(currentTemporarie.equals("")){
            if (isExpression == true){
                System.out.println("Operands inside add: "+operandos);
                System.out.println("left: "+basicLeft);
                System.out.println("right: "+basicRight);
                operador = op;
                String operando1 = operandos.get(0);
                operandos.remove(operando1);
                String operando2= operandos.get(0);
                operandos.remove(operando2);
                appendToCodigoIntermedio(generator.newTemporary()+operando2+" "+op+" "+operando1);
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
                appendToCodigoIntermedio(generator.newTemporary()+currentTemporarie+" "+op+" "+operando2);
                currentTemporarie = "T"+(generator.getTemporarieCount()-1);
                operador = "";
            }
        }
        return additionSubsExpression;
    }

    @Override
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
        String basicLeft = ctx.getChild(0).getText();
        String op = ctx.getChild(1).getText();
        String basicRight = ctx.getChild(2).getText();
        if(currentTemporarie.equals("")){
            if (isExpression == true){
                System.out.println("Operands inside add: "+operandos);
                System.out.println("left: "+basicLeft);
                System.out.println("right: "+basicRight);
                operador = op;
                String operando1 = operandos.get(0);
                operandos.remove(operando1);
                String operando2= operandos.get(0);
                operandos.remove(operando2);
                appendToCodigoIntermedio(generator.newTemporary()+operando2+" "+op+" "+operando1);
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
                appendToCodigoIntermedio(generator.newTemporary()+currentTemporarie+" "+op+" "+operando2);
                currentTemporarie = "T"+(generator.getTemporarieCount()-1);
                operador = "";
            }
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
        if(operandIsMethod == true){
            argument = true;
        }
        String type = visitChildren(ctx);
        if(!currentTemporarie.equals("")){
            arguments.add(currentTemporarie);
            currentTemporarie = "";
        }
        else{
        	arguments.add(operandos.get(0));
        	operandos.remove(0);
        }
        return type;
    }

    @Override
    public String visitMethodCallProduction(DECAFParser.MethodCallProductionContext ctx) {
        // TODO Auto-generated method stub
        String variableName = ctx.ID().getText();
        System.out.println("METHOD CALLING"+isExpression);
        
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
        
        MethodSymbol method = (MethodSymbol) currentEnvironment.getSymbol(variableName, "method");
        if(//isExpression ==
        		true){
            System.out.println("METHOD CALLING");
            operandIsMethod = true;
            for(DECAFParser.ArgContext argument:ctx.arg()){
                typesInArguments.add(visit(argument));
            }
            System.out.println("Arguments: "+arguments);
            
            if (method.getFirm().isEmpty()){
                appendToCodigoIntermedio("CALL "+variableName);
                appendToCodigoIntermedio(generator.newTemporary()+"R");
            }
            else{
                String firma = "";
                for (int i = 0; i< arguments.size(); i++){
                    firma = arguments.get(i);
                    appendToCodigoIntermedio("PARAMS: "+firma);
                }
                appendToCodigoIntermedio("CALL "+variableName+", "+method.getFirm().size());
                appendToCodigoIntermedio(generator.newTemporary()+"R");
            }
            operandos.add(0,"T"+(generator.getTemporarieCount()-1));
            arguments.removeAll(arguments);
            
        }
        if(typesInArguments.size() != currentMethod.getFirm().size()){
            handleSemanticError("Error en la linea: "
                    + ctx.getStart().getLine()
                    + "\n "
                    + "Parameter size not the same " + variableName 
                );
            return "Error";
        }
        String type = currentEnvironment.getSymbol(variableName, "method").getType();
        
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
        System.out.println("Operands Literal: "+operandos);
        if(!currentTemporarie.equals("") && (isExpression == true)){
            operandos.add(0, currentTemporarie);
        }
        else if((ifExpression == true)|| (whileExpression == true)|| (isExpression == true || location == true || argument == true || returnBlock == true)){        	
            operandos.add(0, ctx.getChild(0).getChild(0).getText());
        }
        if(((ifExpression == true)|| (whileExpression == true)) && (arrayOperando == true)){
            String temporary = operandos.get(0);
            operandos.remove(temporary);
            appendToCodigoIntermedio(generator.newTemporary()+temporary+" * "+arrayID+"");
            temporary = "T"+(generator.getTemporarieCount()-1);
            operandos.add(0, temporary);
        }
        else if(isExpression == true && moreChilds == true && (arrayOperando == true)){
            String temporary = operandos.get(0);
            operandos.remove(temporary);
            appendToCodigoIntermedio(generator.newTemporary()+temporary+" * "+arrayID+"");
            temporary = "T"+(generator.getTemporarieCount()-1);
            operandos.add(0, temporary);
        }
        else if(isExpression == true && moreChilds == false && (arrayOperando == true)){
            //System.out.println("In3");
            String temporary = operandos.get(0);
            operandos.remove(temporary);
            //temporary = arrayID+"["+temporary+"]";
            appendToCodigoIntermedio(generator.newTemporary()+temporary+" * "+arrayID+"");
            temporary = "T"+(generator.getTemporarieCount()-1);
            operandos.add(0, temporary);
            leftLocation = temporary;
            
        }
        System.out.println("Operands Literal2: "+operandos);
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

    public static boolean isNumeric(String str) {
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
        codigoIntermedioArrayList.add("\t" + lineaCodigo + "\n");
    }
    
    public void appendLabelToCodigoIntermedio(String label)
    {
        codigoIntermedioArrayList.add(label + "\n");
    }

    public void createStartFunctionLabel(String name){
        String label = "FUNCTION_" + name + ":";

        appendLabelToCodigoIntermedio(label);
    }
    
    public void createEndFunctionLabel(String name) {
        String label = "END_FUNCTION_" + name + ":";

        appendLabelToCodigoIntermedio(label);
    }
}
