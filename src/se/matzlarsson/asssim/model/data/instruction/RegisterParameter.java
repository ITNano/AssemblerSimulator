package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.NumericalType;
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
	public AssByte[] prepareBytes(Machine m, String input) {
		AssByte[] data = new AssByte[getSize()];
		int i = 0;
		for(; i<input.length() && i<getSize(); i++){
			data[i] = new AssByte((int)input.charAt(i));
		}
		for(; i<getSize(); i++){
			data[i] = new AssByte();
		}
		
		return data;
	}

	@Override
	public void perform(Machine m, String input) {
		AssByte[] data = AssByte.getAssBytes(NumericalType.DECIMAL, input);
		String name = "";
		for(int i = 0; i<data.length; i++){
			if(data[i].getValue()>0){
				name += (char)data[i].getValue();
			}
		}
		
		super.perform(m, m.getRegisterValue(name));
	}

	@Override
	public String toString(){
		return "Register "+super.toString();
	}
	
}
