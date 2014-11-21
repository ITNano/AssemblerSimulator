package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.SyntaxUtil;

public class MemoryReferenceParameter extends BasicParameter {
	
	private String addressStoreName;
	private int validSize = 2;
	private int byteSize = 1;

	public MemoryReferenceParameter(String storeName, String addressStoreName, int validSize, int byteSize) {
		super(storeName);
		
		this.addressStoreName = addressStoreName;
		this.validSize = validSize;
		this.byteSize = byteSize;
	}
	
	@Override
	public boolean isValid(Machine m, String input) {
		int memory = SyntaxUtil.getIntValue(m.getSyntax(), input);
		return (!SyntaxUtil.isConstant(m.getSyntax(), input) &&
				memory<Math.pow(256, validSize) &&
				memory>=0 && m.hasReadableMemory(memory));
	}
	
	@Override
	public int getSize(){
		return validSize;
	}

	@Override
	public AssByte[] prepareBytes(Machine m, String input) {
		String concreteValue = SyntaxUtil.getConcreteValue(m.getSyntax(), input);
		return AssByte.getAssBytes(SyntaxUtil.getNumericalType(m.getSyntax(), input), concreteValue);
	}

	/**
	 * NOTE:
	 * input should contain the decimal memory address to load
	 */
	@Override
	public void perform(Machine m, String input) {
		int address = Integer.parseInt(input);
		m.setTempValue(addressStoreName, AssByte.getAssBytes(address));
		
		AssByte[] data = new AssByte[byteSize];
		for(int i = 0; i<byteSize; i++){
			data[i] = m.readMemoryData(address+i);
		}
		super.perform(m, data);
	}

}
