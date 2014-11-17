package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;

public abstract class BasicParameter implements Parameter{

	private String storeName;
	
	public BasicParameter(String storeName){
		this.storeName = storeName;
	}
	
	public void perform(Machine machine, AssByte[] input){
		machine.setTempValue(storeName, input);
	}
	
	@Override
	public String toString(){
		return "Parameter by name "+storeName;
	}
}
