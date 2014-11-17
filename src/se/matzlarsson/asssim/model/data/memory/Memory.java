package se.matzlarsson.asssim.model.data.memory;

import java.util.ArrayList;
import java.util.List;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.util.Converter;

public class Memory {

	private List<MemoryComponent> components;
	private int addressSize;
	
	public Memory(int addressSize){
		this.addressSize = addressSize;
		components = new ArrayList<MemoryComponent>();
	}
	
	public void addMemoryComponent(MemoryComponent component){
		if(component != null){
			for(int i = 0; i<components.size(); i++){
				if(components.get(i).compareTo(component)>0){
					this.components.add(i, component);
					return;
				}
			}
			
			this.components.add(component);
		}
	}
	
	public int getAddressSize(){
		return this.addressSize;
	}
	
	public AssByte readMemoryData(int memoryAddress){
		MemoryComponent comp = getComponent(memoryAddress);
		if(comp != null){
			return comp.readMemoryData(memoryAddress);
		}else{
			throw new IllegalArgumentException("Could not find memory cell");
		}
	}
	
	public void writeMemoryData(int memoryAddress, AssByte data){
		MemoryComponent comp = getComponent(memoryAddress);
		if(comp != null){
			comp.writeMemoryData(memoryAddress, data);
		}else{
			throw new IllegalArgumentException("Could not find memory cell");
		}
	}
	
	public boolean isReadable(int memoryAddress){
		MemoryComponent comp = getComponent(memoryAddress);
		return comp!=null && comp.isReadable();
	}
	
	public boolean isWriteable(int memoryAddress){
		MemoryComponent comp = getComponent(memoryAddress);
		return comp!=null && comp.isWriteable();
	}
	
	private MemoryComponent getComponent(int memoryAddress){
		for(int i = 0; i<components.size(); i++){
			if(components.get(i).containsMemoryCell(memoryAddress)){
				return components.get(i);
			}
		}
		
		return null;
	}
	
	@Override
	public String toString(){
		int hexNumbers = 2;
		if(components.size()>0){
			MemoryComponent last = components.get(components.size()-1);
			int max = last.getStartAddress()+last.getSize();
			hexNumbers = 2;
			max /= 256;
			while((max/=16)>0){
				hexNumbers++;
			}
		}
		
		
		StringBuilder s = new StringBuilder();
		MemoryComponent c;
		for(int i = 0; i<components.size(); i++){
			c = components.get(i);
			
			if(i>0){
				s.append(", ");
			}
			s.append(c.getName()).append(" (").append(Converter.decToHex(c.getStartAddress(), hexNumbers)).append("-");
			s.append(Converter.decToHex(c.getStartAddress()+c.getSize(), hexNumbers)).append(")");
		}
		
		return s.toString();
	}
}
