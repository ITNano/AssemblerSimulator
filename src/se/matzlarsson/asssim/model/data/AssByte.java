package se.matzlarsson.asssim.model.data;

import se.matzlarsson.asssim.util.Converter;

public class AssByte {

	private AssBit[] bits;
	
	public AssByte(){
		bits = new AssBit[8];
		for(int i = 0; i<bits.length; i++){
			bits[i] = new AssBit();
		}
	}
	
	public AssByte(NumericalType type, String value){
		this();
		switch(type){
			case BINARY:		this.setBinValue(value);break;
			case DECIMAL:		this.setValue(Integer.parseInt(value));break;
			case HEXADECIMAL:	this.setHexValue(value);break;
		}
	}
	
	public int getValue(){
		int value = 0;
		for(int i = 0; i<8; i++){
			value = 2*value+bits[i].getValue();
		}
		
		return value;
	}
	
	public int getBitValue(int bitIndex){
		if(bitIndex>=0 && bitIndex<8){
			return bits[bitIndex].getValue();
		}else{
			return 0;
		}
	}
	
	public boolean increase(){
		int num = 7;
		while(num>=0 && bits[num].toggle()){
			num--;
		}
		
		return num<0;
	}
	
	public boolean add(AssByte b){
		int tmp = 0;
		int c = 0;
		for(int i = 7; i>=0; i++){
			tmp = bits[i].getValue()+b.bits[i].getValue()+c;
			c = tmp/2;
			bits[i].setValue(tmp%2);
		}
		
		return c>0;
	}
	
	public boolean add(int value){
		return setValue(getValue()+value);
	}
	
	public boolean subtract(AssByte b){
		AssByte tmp = b.toNegative();
		return this.add(tmp);
	}
	
	public boolean multiply(int factor){
		boolean overflow = false;
		for(int i = 1; i<factor; i++){
			overflow = overflow || this.add(this);
		}
		
		return overflow;
	}
	
	public boolean shiftRight(){
		boolean c = bits[7].getValue()==1;
		for(int i = 7; i>0; i--){
			bits[i].setValue(bits[i-1].getValue());
		}
		bits[0].setValue(0);
		
		return c;
	}
	
	public boolean shiftLeft(){
		boolean c = bits[0].getValue()==1;
		for(int i = 1; i<8; i++){
			bits[i-1].setValue(bits[i].getValue());
		}
		bits[7].setValue(0);
		
		return c;
	}
	
	public AssByte toNegative(){
		AssByte b = new AssByte();
		for(int i = 0; i<8; i++){
			b.bits[i].setValue(bits[i].getValue()==0?1:0);
		}
		b.add(1);
		
		return b;
	}
	
	public boolean setValue(int decValue){
		boolean overflow = decValue>255;
		while(decValue<0){
			decValue += 255;
			overflow = true;
		}
		
		for(int i = 7; i>=0; i--){
			bits[i].setValue(decValue%2);
			decValue /= 2;
		}
		
		return overflow;
	}
	
	public void setBitValue(int bitIndex, int value){
		if(bitIndex>=0 && bitIndex<8){
			bits[bitIndex].setValue(value);
		}
	}
	
	public void setBinValue(String bin){
		for(int i = 0; i<bin.length(); i++){
			bits[i].setValue(bin.charAt(i)=='1'?1:0);
		}
	}
	
	public void setHexValue(String hex){
		setBinValue(Converter.hexToBinary(hex.substring(0, 1))+Converter.hexToBinary(hex.substring(1, 2)));
	}
	
	public String toString(NumericalType type){
		String s = "";
		switch(type){
			case BINARY:	
					for(int i = 0; i<bits.length; i++){
						s += bits[i].getValue();
					}
					break;
			case DECIMAL:
					s += getValue();
					break;
			case HEXADECIMAL:
					s = Converter.decToHex(getValue(), 2);
					break;
		}
		
		return s;
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i<8; i++){
			s += bits[i].getValue();
		}
		
		return s;
	}
	
	
	
	public static AssByte[] getAssBytes(NumericalType type, String value){
		switch(type){
			case DECIMAL:
				return getAssBytes(Integer.parseInt(value));
			case HEXADECIMAL:
				return getAssBytes(Converter.hexToDecimal(value));
			case BINARY:
				throw new IllegalArgumentException("Binary is boring");
			default:
				return null;
		}
	}
	
	private static AssByte[] getAssBytes(int decNum){
		int size = 1, tmp = decNum;
		while(tmp>255){
			size++;
			tmp /= 256;
		}
		AssByte[] b = new AssByte[size];
		for(int i = 0; i<size; i++){
			b[i] = new AssByte();
			b[i].setValue(decNum%256);
			decNum /= 256;
		}
		return b;
	}
}
