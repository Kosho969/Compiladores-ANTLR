import java.util.ArrayList;

public class MethodSymbol extends Symbol {

	// Parametros de un método 
	ArrayList<VariableSymbol> firm = new ArrayList<VariableSymbol>();
	
	public MethodSymbol(
		String type,
		String name,
		boolean isStruct,
		ArrayList<VariableSymbol> firm
	) {
		super(type, name, isStruct);

		this.firm = firm;
	}

	public ArrayList<VariableSymbol> getFirm() {
		return firm;
	}

	public void setFirm(ArrayList<VariableSymbol> firm) {
		this.firm = firm;
	}
}
