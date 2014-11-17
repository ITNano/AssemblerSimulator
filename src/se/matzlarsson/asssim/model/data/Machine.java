package se.matzlarsson.asssim.model.data;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.matzlarsson.asssim.model.data.instruction.Instruction;
import se.matzlarsson.asssim.model.data.instruction.InstructionFactory;
import se.matzlarsson.asssim.model.data.memory.Memory;
import se.matzlarsson.asssim.model.data.memory.MemoryFactory;
import se.matzlarsson.asssim.model.data.register.Register;
import se.matzlarsson.asssim.model.data.register.RegisterFactory;
import se.matzlarsson.asssim.util.XMLReader;

public class Machine {
	
	private Map<String, AssByte[]> tempValues;
	
	private String name;
	private Register[] registers;
	private Instruction[] instructions;
	private Memory memory;
	private Syntax syntax;

	public Machine(String xmlPath){
		tempValues = new HashMap<String, AssByte[]>();
		
		Node computer = XMLReader.getDocument(xmlPath, false).getElementsByTagName("computer").item(0);
		this.name = computer.getAttributes().getNamedItem("name").getNodeValue();
		initRegisters(((Element)computer).getElementsByTagName("registers").item(0));
		initInstructions(((Element)computer).getElementsByTagName("instructions").item(0));
		memory = MemoryFactory.createMemory(((Element)computer).getElementsByTagName("memory").item(0));
		syntax = SyntaxFactory.createSyntax(((Element)computer).getElementsByTagName("syntax").item(0));
	}
	
	private void initRegisters(Node rootNode){
		NodeList list = ((Element)rootNode).getElementsByTagName("register");
		registers = new Register[list.getLength()];
		for(int i = 0; i<list.getLength(); i++){
			registers[i] = RegisterFactory.createRegisterFromXML(list.item(i));
		}
	}
	
	private void initInstructions(Node rootNode){
		NodeList list = ((Element)rootNode).getElementsByTagName("instruction");
		instructions = new Instruction[list.getLength()];
		for(int i = 0; i<list.getLength(); i++){
			instructions[i] = InstructionFactory.createInstruction(list.item(i));
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public Syntax getSyntax(){
		return this.syntax;
	}
	
	public int getMemoryAddressSize(){
		return memory.getAddressSize();
	}
	
	public boolean hasReadableMemory(int memoryAddress){
		return memory.isReadable(memoryAddress);
	}
	
	public boolean hasWriteableMemory(int memoryAddress){
		return memory.isWriteable(memoryAddress);
	}
	
	public boolean hasRegister(String name){
		for(int i = 0; i<registers.length; i++){
			if(registers[i].getName().equals(name)){
				return true;
			}
		}
		
		return false;
	}
	
	public AssByte readMemoryData(int memoryAddress){
		return memory.readMemoryData(memoryAddress);
	}
	
	public void writeMemoryData(int memoryAddress, AssByte data){
		memory.writeMemoryData(memoryAddress, data);
	}
	
	public void setTempValue(String key, AssByte[] data){
		tempValues.put(key, data);
	}
	
	public AssByte[] getTempValue(String key){
		return tempValues.get(key);
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Machine ").append(getName()).append("{\n");
		s.append("\tRegisters:{");
		for(int i = 0; i<registers.length; i++){
			if(i>0){
				s.append(", ");
			}
			s.append(registers[i].getName()).append(" (").append(registers[i].getByteSize()).append("byte)");
		}
		s.append("}\n");
		
		s.append("\tMemory:{").append(memory).append("}\n");
		
		s.append("\tSyntax:{").append(syntax).append("}\n");

		s.append("\n\tInstructions:{\n");
		for(int i = 0; i<instructions.length; i++){
			s.append("\t   ").append(instructions[i].toString().replace("\n", "\n\t   ")).append("\n");
		}
		s.append("\t}\n");
		
		s.append("}");
		return s.toString();
	}
	
}
