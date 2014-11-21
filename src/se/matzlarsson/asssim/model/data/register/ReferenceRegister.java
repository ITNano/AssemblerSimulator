package se.matzlarsson.asssim.model.data.register;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.NumericalType;
import se.matzlarsson.asssim.util.Converter;

public class ReferenceRegister implements Register{
	
	private String name;
	private Register[] registers;

	public ReferenceRegister(String name, Register... registers){
		this.name = name;
		this.registers = registers;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getByteSize() {
		if(registers == null){
			return 0;
		}
		
		int size = 0;
		for(int i = 0; i<registers.length; i++){
			size += registers[i].getByteSize();
		}
		
		return size;
	}

	@Override
	public AssByte getByte() {
		if(registers!=null && registers.length>0 && registers[0]!=null){
			return registers[0].getByte();
		}else{
			return null;
		}
	}

	@Override
	public AssByte[] getBytes() {
		AssByte[] bytes = new AssByte[getByteSize()];
		
		if(registers!=null){
			int num = 0;
			AssByte[] tmp = null;
			for(int i = 0; i<registers.length; i++){
				tmp = registers[i].getBytes();
				for(int j = 0; j<tmp.length; j++){
					bytes[num] = tmp[j];
					num++;
				}
			}
		}
		
		return bytes;
	}

	@Override
	public void setBytes(AssByte... data) {
		if(data.length!=this.getByteSize()){
			throw new IllegalArgumentException("The size of the new data does not fit the register size");
		}
		
		int counter = 0;
		for(int i = 0 ; i<registers.length; i++){
			AssByte[] tmp = new AssByte[registers[i].getByteSize()];
			for(int j = 0; j<tmp.length; j++){
				tmp[j] = data[counter];
				counter++;
			}
			registers[i].setBytes(tmp);
		}
	}
	
	@Override
	public void setValue(int value){
		int range = 0;
		for(int i = registers.length-1; i>=0; i--){
			range = (int)Math.pow(256, registers[i].getByteSize());
			registers[i].setValue(value%range);
			value /= range;
		}
	}
	
	@Override
	public void setValue(String hexValue){
		this.setValue(Converter.hexToDecimal(hexValue));
	}
	
	@Override
	public boolean add(int num){
		int overflow = 0;
		int tmpMax = 0;
		for(int i = registers.length-1; i>=0; i--){
			tmpMax = (int)Math.pow(256, registers[i].getByteSize());
			overflow = registers[i].add((num+overflow)%tmpMax)?1:0;
			num /= tmpMax;
			
			if(num<0){
				overflow = -overflow;
			}
		}
		
		return overflow!=0;
	}
	
	@Override
	public String toString(){
		String s = getName()+": ";
		if(registers != null){
			for(int i = 0; i<registers.length; i++){
				s += (i>0?"|":"")+registers[i].getName();
			}
			
			s += "  ";
			AssByte[] data = getBytes();
			for(int i = 0; i<data.length; i++){
				s += (i>0?"|":"")+data[i].toString(NumericalType.HEXADECIMAL);
			}
		}else{
			s += "No children";
		}
		
		return s;
	}
}
