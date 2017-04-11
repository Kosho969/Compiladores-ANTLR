
public class TableEntry {
	// Identificadores 
	String lexem;

	// ("method", "variable", "structure")
	String type;

	// Symbol that goes into the Symbol Table 
	Symbol value;
	
	int size = 0;
	
	public TableEntry(String type, String lexem, Symbol value) {
		this.lexem = lexem;
		this.type = type;
		this.value = value;
	}
	
	public TableEntry(String type, String lexem, Symbol value, int size) {
		this.lexem = lexem;
		this.type = type;
		this.value = value;
		this.size = size; 
	}
	
	/**
	 * TODO: Evaluar si lo necesitamos
	 */
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