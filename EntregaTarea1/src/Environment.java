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

	public void setSymbolTable(ArrayList<TableEntry> symbolTable) {
		this.symbolTable = symbolTable;
	}
	
	public void putSymbol(String type,String lexema, Symbol s){
		this.symbolTable.add(new TableEntry(type, lexema, s));
	}
	
	public Symbol getSymbol(String name,String type){
		for (int i = 0; i < symbolTable.size(); i++){
			TableEntry symbolLooking = symbolTable.get(i);

			if (symbolLooking.getLexem().equals(name) && (symbolLooking.getType().equals(type))){
				return symbolLooking.getValue();
			}
		}

		return null;
	}
}
