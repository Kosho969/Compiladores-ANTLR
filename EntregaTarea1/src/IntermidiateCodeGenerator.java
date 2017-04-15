
public class IntermidiateCodeGenerator {
	int temporarieCount = 0;
	int whileLabelCount = 0; 
	int trueLabel = 0; 
	int falseLabel = 0;
	int ifLabel = 0;
	
	public void ifStatements(String variable, String operador){
	}
	
	public String newTemporary(){
		String temporarie = "T"+temporarieCount+" = ";
		temporarieCount++;
		return temporarie;
	}
	
	public String newWhileLabel(){
		String whileLabel = "Start_While"+whileLabelCount+":\n";
		return whileLabel;
	}
	
	public String endWhileLabel(){
		String whileLabel = "End_While"+whileLabelCount;
		whileLabelCount++;
		return whileLabel;
	}
	
	public String newTrueLabel(){
		String label = "Label_True"+trueLabel+"";
		trueLabel++;
		return label;
	}
	
	public String newIfLabel(){
		String label = "End_If_"+trueLabel+"";
		return label;
	}
	
	public String newFalseLabel(){
		String label = "Label_False"+falseLabel+"";
		falseLabel++;
		return label;
	}
	
	public int getTemporarieCount() {
		return temporarieCount;
	}
	public void setTemporarieCount(int temporarieCount) {
		this.temporarieCount = temporarieCount;
	}
	public int getWhileLabelCount() {
		return whileLabelCount;
	}
	public void setWhileLabelCount(int whileLabelCount) {
		this.whileLabelCount = whileLabelCount;
	}
	

}
