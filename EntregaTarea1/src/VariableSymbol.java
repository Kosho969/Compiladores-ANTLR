public class VariableSymbol extends Symbol
{
	boolean isArray;

	private int entryVariableOffset = 0;

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
	
	public void setEntryVariableOffset(int offset) {
		this.entryVariableOffset = offset;
	}
	
	public int getEntryVariableOffset() {
		return this.entryVariableOffset;
	}
	
	public String getIntermediateName() {
		// Esto debe determinar si estoy en contexto de clase
		// o en contexto de método.
		
		// Si estoy en un método, devolver el FP
		
		// Si estoy en una clase, devolver G
		if (this.tableEntry.environment.isClassEnvironment) {
			return "G[" + this.entryVariableOffset + "]";
		}
		
		return "FP[" + this.entryVariableOffset + "]";
	}
}
