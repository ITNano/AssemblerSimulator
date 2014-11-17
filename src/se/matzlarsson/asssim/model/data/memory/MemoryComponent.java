package se.matzlarsson.asssim.model.data.memory;

import se.matzlarsson.asssim.model.data.AssByte;

public class MemoryComponent implements Comparable<MemoryComponent>{

	private String name;
	private boolean readable;
	private boolean writeable;
	private int startAddress;
	
	private AssByte[] data;
	
	public MemoryComponent(String name, int startAddress, int size, boolean readable, boolean writeable){
		this.name = name;
		this.startAddress = startAddress;
		this.readable = readable;
		this.writeable = writeable;
		
		this.data = new AssByte[size];
		for(int i = 0; i<size; i++){
			data[i] = new AssByte();
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getStartAddress(){
		return this.startAddress;
	}
	
	public int getSize(){
		return data.length;
	}
	
	public boolean isReadable(){
		return this.readable;
	}
	
	public boolean isWriteable(){
		return this.writeable;
	}
	
	public boolean containsMemoryCell(int memoryAddress){
		return memoryAddress>=startAddress && memoryAddress<startAddress+data.length;
	}
	
	public AssByte readMemoryData(int memoryAddress){
		if(containsMemoryCell(memoryAddress)){
			if(!isReadable()){
				throw new IllegalRequestException("Cannot read from unreadable cell");
			}
			return data[memoryAddress-startAddress];
		}else{
			throw new IllegalArgumentException("Could not find the address "+memoryAddress);
		}
	}
	
	public void writeMemoryData(int memoryAddress, AssByte data){
		if(containsMemoryCell(memoryAddress)){
			if(!isWriteable()){
				throw new IllegalRequestException("Cannot write to unwriteable cell");
			}
			this.data[memoryAddress-startAddress] = data;
		}
	}

	@Override
	public int compareTo(MemoryComponent comp) {
		return this.startAddress-comp.startAddress;
	}
}
