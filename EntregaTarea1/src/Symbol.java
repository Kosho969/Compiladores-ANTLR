
public class Symbol {
	String type;

	String name;

	boolean isStruct;
	
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
}