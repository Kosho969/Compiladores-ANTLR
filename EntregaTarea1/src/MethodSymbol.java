import java.util.ArrayList;

public class MethodSymbol extends Symbol {

	// Parametros de un m�todo. 
	ArrayList<VariableSymbol> firm = new ArrayList<VariableSymbol>();
	
	public MethodSymbol(ArrayList<VariableSymbol> firm) {
		this.firm = firm;
	}

	public ArrayList<VariableSymbol> getFirm() {
		return firm;
	}

	public void setFirm(ArrayList<VariableSymbol> firm) {
		this.firm = firm;
	}
}
