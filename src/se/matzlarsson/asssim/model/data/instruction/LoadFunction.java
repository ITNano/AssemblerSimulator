package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.Machine;

public class LoadFunction implements Function {
	
	private RequestType type;
	private String input;
	private String output;
	
	public LoadFunction(RequestType type, String input, String output){
		this.type = type;
		this.input = input;
		this.output = output;
	}

	@Override
	public void perform(Machine machine) {
		String tmp = "";
	}

	@Override
	public String toString(){
		return "LOAD from "+input+" to "+output+" ("+type.toString()+")";
	}
}
