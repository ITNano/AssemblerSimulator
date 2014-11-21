package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.SyntaxUtil;

public class MemoryReferenceParameter extends BasicParameter {
	
	private String addressStoreName;
	private int size = 2;

	public MemoryReferenceParameter(String storeName, String addressStoreName) {
		super(storeName);
		
		this.addressStoreName = addressStoreName;
	}
	
	@Override
	public boolean isValid(Machine m, String input) {
		int memory = SyntaxUtil.getIntValue(m.getSyntax(), input);
		return !SyntaxUtil.isConstant(m.getSyntax(), input) && memory>=0 && m.hasReadableMemory(memory);
	}
	
	@Override
	public int getSize(){
		return size;
	}

	/**
	 * NOTE:
	 * input should contain the decimal memory address to load
	 */
	@Override
	public void perform(Machine m, String input) {
		//Sync size
		this.size = m.getMemoryAddressSize();
		
		int address = Integer.parseInt(input);
		m.setTempValue(addressStoreName, AssByte.getAssBytes(address));
		
		AssByte data = m.readMemoryData(address);
		super.perform(m, data);
	}

}
