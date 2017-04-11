import java.util.*;
import java.util.ArrayList;

public class Environment {

	Environment parent;

	ArrayList<TableEntry> symbolTable = new ArrayList<TableEntry>();

	public Environment(Environment parent) {
		this.parent = parent;

		this.symbolTable = new ArrayList<TableEntry>();
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
		this.symbolTable.add(new TableEntry(type, lexema, s));
	}
	public void putSymbol2(String type, String lexema, Symbol s,int size)
	{
		this.symbolTable.add(new TableEntry(type, lexema, s,size));
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
