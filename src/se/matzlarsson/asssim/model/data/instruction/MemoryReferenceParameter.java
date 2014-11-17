package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.SyntaxUtil;

public class MemoryReferenceParameter extends BasicParameter {

	public MemoryReferenceParameter(String storeName) {
		super(storeName);
	}
	
	@Override
	public boolean isValid(Machine m, String input) {
		int memory = SyntaxUtil.getIntValue(m.getSyntax(), input);
		return memory>=0 && m.hasReadableMemory(memory);
	}

	@Override
	public void perform(Machine m, String input) {
		int maxByteValue = (int)Math.pow(256, m.getMemoryAddressSize());
		
		int address = SyntaxUtil.getIntValue(m.getSyntax(), input);
		int tmp = address, size = 1;
		while(tmp>maxByteValue){
			size++;
			tmp/= maxByteValue;
		}
		
		AssByte[] data = new AssByte[size];
		for(int i = 0; i<size; i++){
			data[i] = m.readMemoryData(address%maxByteValue);
			address /= maxByteValue;
		}
		
		super.perform(m, data);
	}

}
