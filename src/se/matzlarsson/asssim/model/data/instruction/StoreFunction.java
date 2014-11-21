package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;

public class StoreFunction implements Function {
	
	private RequestType type;
	private String input;
	private String output;
	
	public StoreFunction(RequestType type, String input, String output){
		this.type = type;
		this.input = input;
		this.output = output;
	}

	@Override
	public void perform(Machine machine) {
		AssByte[] data = machine.getTempValue(input);
		
		switch(type){
			case MEMORY:
				int address = AssByte.getNumericalValue(machine.getTempValue(output));
				machine.writeMemoryData(address, data);
				break;
			case CONSTANT:
				machine.setTempValue(output, data);
				break;
			case REGISTER:
				machine.setRegisterValue(output, AssByte.getNumericalValue(data));
				break;
			default:
				break;
		}
	}

	@Override
	public String toString(){
		return "STORE from "+input+" to "+output+" ("+type.toString()+")";
	}
}
