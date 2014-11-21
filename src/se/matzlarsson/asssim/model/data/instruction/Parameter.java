package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;

public interface Parameter {
	
	public boolean isValid(Machine m, String input);
	public int getSize();
	public AssByte[] prepareBytes(Machine m, String input);
	public void perform(Machine m, String input);
	
}
