package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.SyntaxUtil;

public class RegisterParameter extends BasicParameter {

	public RegisterParameter(String storeName) {
		super(storeName);
	}
	
	@Override
	public boolean isValid(Machine m, String input) {
		return SyntaxUtil.getConcreteValue(m.getSyntax(), input).equals(input) && m.hasRegister(input);
	}
	
	@Override
	public int getSize(){
		return 3;
	}

	@Override
	public void perform(Machine m, String input) {
		AssByte[] data = new AssByte[input.length()];
		for(int i = 0; i<input.length(); i++){
			data[i] = new AssByte((int)input.charAt(i));
		}
		
		while(input.length()<getSize()){
			input += " ";
		}
		
		super.perform(m, data);
	}

}
