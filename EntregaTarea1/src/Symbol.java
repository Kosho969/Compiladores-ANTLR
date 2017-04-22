
public class Symbol
{
	/**
	 * Cuando el simbolo es de una variable, aquí guardamos el tipo de la variable.
	 * 
	 * Cuando el simbolo es de un método, aquí guardamos el tipo de retorno del método.
	 * 
	 * Cuando el símbolo es de un struct, aquí guardamos el nombre del struct,
	 * que de ahora en adelante será un tipo de simbolo válido.
	 */
	String type;

	String name;

	boolean isStruct;
	
	public TableEntry tableEntry;
	
	/**
	 * TODO: Revisar si necestiamos
	 */
	public Symbol(){
		this.type = "";
		this.name = "";
		this.isStruct = false;
	}
	
	public Symbol(String type, String name, boolean isStruct){
		this.type = type; 
		this.name = name; 
		this.isStruct= isStruct;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStruct() {
		return isStruct;
	}

	public void setStruct(boolean isStruct) {
		this.isStruct = isStruct;
	}
	
	public String toString()
	{
		return this.name + " (" + this.type + ")";
	}
}