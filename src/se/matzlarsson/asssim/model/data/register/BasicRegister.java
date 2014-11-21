package se.matzlarsson.asssim.model.data.register;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.NumericalType;
import se.matzlarsson.asssim.util.Converter;


public class BasicRegister implements Register{
	
	private String name;
	private AssByte[] data;

	public BasicRegister(String name, int size){
		this(name, new AssByte[size]);
	}
	
	public BasicRegister(String name, AssByte... data){
		this.name = name;
		this.data = data;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getByteSize() {
		return (data==null?0:data.length);
	}

	@Override
	public AssByte getByte() {
		return (data==null?null:data[0]);
	}

	@Override
	public AssByte[] getBytes() {
		return data;
	}

	@Override
	public void setBytes(AssByte... data) {
		this.data = data;
	}
	
	@Override
	public void setValue(int value){
		for(int i = data.length-1; i>=0; i--){
			data[i].setValue(value%256);
			value /= 256;
		}
	}
	
	@Override
	public void setValue(String hexValue){
		this.setValue(Converter.hexToDecimal(hexValue));
	}
	
	@Override
	public boolean add(int num){
		return AssByte.add(data, num);
	}
	
	@Override
	public String toString(){
		String s = getName()+": ";
		for(int i = 0; i<data.length; i++){
			s += (i>0?"|":"")+data[i].toString(NumericalType.HEXADECIMAL);
		}
		
		return s;
	}

}
