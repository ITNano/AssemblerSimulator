package se.matzlarsson.asssim.model.data;

public class AssBit {

	private int value = 0;
	
	public AssBit(){
		this(0);
	}
	
	public AssBit(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void set(){
		this.value = 1;
	}
	
	public void reset(){
		this.value = 0;
	}
	
	public void setValue(int value){
		this.value = value%2;
	}
	
	public boolean toggle(){
		this.value = (this.value+1)%2;
		return this.value==0;
	}
	
	@Override
	public String toString(){
		return value+"";
	}
}
