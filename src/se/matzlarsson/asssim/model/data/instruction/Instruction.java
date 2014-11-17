package se.matzlarsson.asssim.model.data.instruction;

import java.util.ArrayList;
import java.util.List;

import se.matzlarsson.asssim.util.Converter;

public class Instruction {
	
	private int ID;
	private String name;
	private String help;
	
	private List<Function> functions;
	private List<Parameter> parameters;
	
	public Instruction(String id, String name, String help){
		this.ID = Converter.hexToDecimal(id);
		this.name = name;
		this.help = help;

		this.functions = new ArrayList<Function>();
		this.parameters = new ArrayList<Parameter>();
	}
	
	public int getID(){
		return this.ID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getHelp(){
		return this.help;
	}
	
	protected void addFunction(Function func){
		if(func != null){
			functions.add(func);
		}
	}
	
	protected void addParameter(Parameter param){
		if(param != null){
			parameters.add(param);
		}
	}

	
	public static Instruction find(List<Instruction> instructions, int ID){
		for(int i = 0; i<instructions.size(); i++){
			if(instructions.get(i).getID() == ID){
				return instructions.get(i);
			}
		}
		
		return null;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder(getName()).append(" (").append(getID()).append(") {\n");
		s.append("   Parameters:{\n");
		for(int i = 0; i<parameters.size(); i++){
			s.append("      ").append(parameters.get(i)).append("\n");
		}
		s.append("   }\n");
		
		s.append("   Functions:{\n");
		for(int i = 0; i<functions.size(); i++){
			s.append("      ").append(functions.get(i)).append("\n");
		}
		s.append("   }\n");
		
		s.append("}");
		return s.toString();
	}
}
