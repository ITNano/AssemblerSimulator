package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.NumericalType;

public class RegisterParameter extends BasicParameter {

	public RegisterParameter(String storeName) {
		super(storeName);
	}
	
	@Override
	public boolean isValid(Machine m, String input) {
		return m.hasRegister(input);
	}

	@Override
	public void perform(Machine m, String input) {
		AssByte[] data = new AssByte[input.length()];
		for(int i = 0; i<input.length(); i++){
			data[i] = new AssByte(NumericalType.DECIMAL, Integer.toString((int)input.charAt(i)));
		}
		
		super.perform(m, data);
	}

}
