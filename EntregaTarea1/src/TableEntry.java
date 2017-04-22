
public class TableEntry {

	Environment environment;

	// Identificadores 
	String lexem;

	// ("method", "variable", "structure")
	String type;

	// Symbol that goes into the Symbol Table 
	Symbol value;

	int size = 0;
	
	public TableEntry(String type, String lexem, Symbol value, Environment environment) {
		this.lexem = lexem;
		this.type = type;
		this.value = value;
		this.environment = environment;
		value.tableEntry = this;
	}
	
	public TableEntry(String type, String lexem, Symbol value, int size) {
		this.lexem = lexem;
		this.type = type;
		this.value = value;
		this.size = size; 
	}

	public TableEntry() {
		this.lexem = "";
		this.type = "";
		this.value = new Symbol();
	}

	public String getLexem() {
		return lexem;
	}

	public void setLexem(String lexem) {
		this.lexem = lexem;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Symbol getValue() {
		return value;
	}

	public void setValue(Symbol value) {
		this.value = value;
	}
}