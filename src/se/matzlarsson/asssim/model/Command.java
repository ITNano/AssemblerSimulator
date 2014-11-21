package se.matzlarsson.asssim.model;

import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.instruction.Instruction;

public class Command {

	private Instruction instruction;
	private String indata;
	private int line = 0;
	
	public Command(Instruction instruction, String indata, int line){
		this.instruction = instruction;
		this.indata = indata;
		this.line = line;
	}
	
	public int getLine(){
		return this.line;
	}
	
	public void run(Machine m){
		instruction.run(m, this.indata);
	}
	
}
