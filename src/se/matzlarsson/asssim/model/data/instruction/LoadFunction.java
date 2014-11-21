package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.SyntaxUtil;

public class LoadFunction implements Function {
	
	private RequestType type;
	private String input;
	private String output;
	private boolean inputIsVariable;
	private int byteSize = 1;
	
	public LoadFunction(RequestType type, String input, String output, boolean inputIsVariable, int byteSize){
		this.type = type;
		this.input = input;
		this.output = output;
		this.inputIsVariable = inputIsVariable;
		this.byteSize = byteSize;
	}

	@Override
	public void perform(Machine m) {
		AssByte[] data = null;
		switch(type){
			case MEMORY:
				int address = 0;
				if(inputIsVariable){
					address = AssByte.getNumericalValue(m.getTempValue(input));
				}else{
					address = SyntaxUtil.getIntValue(m.getSyntax(), input);
				}
				data = new AssByte[byteSize];
				for(int i = 0; i<data.length; i++){
					data[i] = m.readMemoryData(address+i);
				}
				break;
			case CONSTANT:
				if(inputIsVariable){
					data = m.getTempValue(input);
				}else{
					data = AssByte.getAssBytes(SyntaxUtil.getNumericalType(m.getSyntax(), input), input);
				}
				break;
			case REGISTER:
				if(inputIsVariable){
					data = m.getRegisterValue(AssByte.getStringFromAssbytes(m.getTempValue(input)));
				}else{
					data = m.getRegisterValue(input);
				}
				break;
			default:
				data = null;
				break;
		}
		
		m.setTempValue(output, data);
	}

	@Override
	public String toString(){
		return "LOAD from "+input+" to "+output+" ("+type.toString()+")";
	}
}
