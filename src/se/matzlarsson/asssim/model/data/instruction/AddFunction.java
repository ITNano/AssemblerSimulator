package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;

public class AddFunction implements Function {
	
	private String[] inputs;
	private String output;
	
	public AddFunction(String inputs, String output){
		this.inputs = inputs.split(",");
		this.output = output;
	}

	@Override
	public void perform(Machine machine) {
		AssByte[] answer = new AssByte[machine.getMemoryAddressSize()];
		for(int i = 0; i<answer.length; i++){
			answer[i] = new AssByte();
		}
		
		int overflow = 0;
		for(int i = inputs.length-1; i>=0; i--){
			overflow = AssByte.add(answer, overflow)?1:0;
			overflow = AssByte.add(answer, machine.getTempValue(inputs[i]))?1:overflow;
		}
		
		machine.setTempValue(output, answer);
	}

}
