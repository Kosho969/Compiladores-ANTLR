import java.util.*;
import java.util.ArrayList;

public class Environment {

	Environment parent;

	ArrayList<TableEntry> symbolTable = new ArrayList<TableEntry>();
	
	boolean isMethodEnvironment = false;
	
	boolean isClassEnvironment = false;
	
	int currentVariablesOffset = 0;

	public Environment(Environment parent) {
		this.parent = parent;
	}

	public Environment getParent() {
		return parent;
	}

	public void setParent(Environment parent) {
		this.parent = parent;
	}

	public ArrayList<TableEntry> getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(ArrayList<TableEntry> symbolTable)
	{
		this.symbolTable = symbolTable;
	}

	public void putSymbol(String type, String lexema, Symbol s)
	{
		TableEntry entry = new TableEntry(type, lexema, s, this);

		// Si el entry que estoy agregando es de tipo variable,
		// analizar el tipo de variable, asignarle el offset y hacer
		// el corrimiento de offset actual respectivo
		if ("variable" == type) {
			String type2 = s.getType();
			((VariableSymbol) s).setEntryVariableOffset(
				this.getAndUpdateCurrentVariableOffsetForType(type2)
			);
		}
		
		this.symbolTable.add(entry);
	}
	
	public int getAndUpdateCurrentVariableOffsetForType(String type) {
		// TODO: Analizar la variable type para calcular la cantidad
		// a sumar en el offset. Si el actual es int, sumar 4. Si el tipo
		// es array, sumar el tamaño del array, si el tipo es boolean,
		// sumar uno, etc.
		int offsetToSum = 0;
		System.out.println("Type TO Offset: "+type);
		if(type.equals("int")){
			offsetToSum = 4;
		}
		else if (type.equals("char")){
			offsetToSum = 1;
		}
		else if (type.equals("boolean")){
			offsetToSum = 1;
		}
		

		if (this.isClassEnvironment) {
			int returnValue = this.currentVariablesOffset;
			this.currentVariablesOffset += offsetToSum;
			return returnValue;
		}

		Environment methodEnvironment = this.getClosestMethodEnvironment();
		int returnValue = methodEnvironment.currentVariablesOffset;
		methodEnvironment.currentVariablesOffset += offsetToSum;

		return returnValue;
	}
	
	public Environment getClosestMethodEnvironment() {
		if (this.isMethodEnvironment) {
			return this;
		}
		
		if (null != this.getParent()) {
			return this.getParent().getClosestMethodEnvironment();
		}

		return this;
	}
	
	public Symbol getSymbol(String name, String type)
	{
		for (int i = 0; i < symbolTable.size(); i++) {
			TableEntry currentTableEntry = symbolTable.get(i);

			if (
				currentTableEntry.getLexem().equals(name)
					&& currentTableEntry.getType().equals(type)
			) {
				return currentTableEntry.getValue();
			}
		}
		
		// Si estoy en este punto, es porque la tabla de simbolos
		// del entorno actual no tiene lo que yo quería, por tanto
		// buscar en la posible tabla padre
		if (null != this.parent) {
			return this.parent.getSymbol(name, type);
		}

		return null;
	}

	public Symbol getSymbol2(String name, String type)
	{
		for (int i = 0; i < symbolTable.size(); i++) {
			TableEntry currentTableEntry = symbolTable.get(i);

			if (
				currentTableEntry.getLexem().equals(name)
					&& currentTableEntry.getType().equals(type)
			) {
				return currentTableEntry.getValue();
			}
		}

		return null;
	}
	
	public boolean hasSymbol(String name, String type)
	{
		return null != getSymbol(name, type);
	}

	public boolean hasSymbol2(String name, String type)
	{
		return null != getSymbol2(name, type);
	}
	
	public void print()
	{
		System.out.println("Dumping environment");

		for (int i = 0; i < symbolTable.size(); i++){
			TableEntry currentTableEntry = symbolTable.get(i);

			System.out.println(
				"Type: '" + currentTableEntry.type +
				"', Name: '"+ currentTableEntry.lexem +
				"', Symbol: '"+ currentTableEntry.value +"'"
			);
		}
		
		if (null != this.parent) {
			System.out.println("");
			System.out.println("Dumping parent");
			
			this.parent.print();
		}
	}
}
