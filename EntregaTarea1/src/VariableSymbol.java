
public class VariableSymbol extends Symbol {
	boolean isArray;

	public VariableSymbol(String type,String name, boolean isStruct, boolean isArray) {
		super(type,name,isStruct);
		this.isArray = isArray;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
}
