package se.matzlarsson.asssim.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.instruction.Instruction;
import se.matzlarsson.asssim.util.Converter;

public class Program {
	
	private Map<Integer, AssByte> data;
	private String sourceFile;
	
	public Program(String sourceFile){
		this.data = new TreeMap<Integer, AssByte>();
		this.sourceFile = sourceFile;
	}
	
	public void parse(Machine m) throws AssembleException{
		Map<String, Integer> labels = new HashMap<String, Integer>();
		int counter = 4096;			//FIXME not use static
		
		try {
			data.clear();
			Scanner sc = new Scanner(new File(sourceFile));
			String line;
			String[] parts;
			boolean hasLabel = true;
			AssByte[] params;
			while(sc.hasNextLine()){
				line = sc.nextLine().trim();
				if(line.length()>0){
					hasLabel = line.substring(0, 1).contains("\\s+");
					parts = line.split("\\s+");
					
					if(hasLabel){
						//Fix label
						labels.put(parts[0], counter);
					}
						
					//Fix instruction stuff
					Instruction instr = m.findInstruction(parts[0+(hasLabel?1:0)], (parts.length>1+(hasLabel?1:0)?(parts[1+(hasLabel?1:0)]):""));
					data.put(counter, new AssByte(instr.getID()));
					counter++;
					
					//Fix parameters
					params = instr.getParameterBytes(m, (parts.length>1+(hasLabel?1:0)?(parts[1+(hasLabel?1:0)]):""));
					for(int i = 0; i<params.length; i++){
						data.put(counter, params[i]);
						counter++;
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e){
			throw new IllegalArgumentException("The source file '"+sourceFile+"' could not be found");
		}
	}
	
	public void applyToMachine(Machine m) throws AssembleException{
		parse(m);
		
		System.out.println("Printing program...");
		for(Entry<Integer, AssByte> entry : data.entrySet()){
			System.out.println(entry.getKey()+" "+Converter.decToHex(entry.getValue().getValue(), 2));
			m.writeMemoryData((int)entry.getKey(), (AssByte)entry.getValue());
		}
	}
	
	public void next(Machine m) throws ProgramException{
		//Load
		int address = Math.max(0, AssByte.getNumericalValue(m.getRegisterValue(m.getCounterRegName())));
		Instruction instr = m.findInstruction(m.readMemoryData(address).getValue());
		if(instr==null){
			throw new ProgramException("Could not find the current instruction");
		}
		
		//Run
		System.out.println("Running "+instr.getName());
		instr.run(m, instr.getParameterData(m, address+1));
		
		//Prepare for next instruction
		m.increaseRegister(m.getCounterRegName(), 1);
	}
}
