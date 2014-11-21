package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.Machine;

public interface Parameter {
	
	public boolean isValid(Machine m, String input);
	public int getSize();
	public void perform(Machine m, String input);
	
}
