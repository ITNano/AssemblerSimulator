package se.matzlarsson.asssim.model.data.instruction;

import java.util.ArrayList;
import java.util.List;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
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
	
	public AssByte[] getParameterBytes(Machine m, String input){
		if(input.length()<=0){
			return new AssByte[0];
		}
		
		String[] parts = input.split(",");
		List<AssByte> bytes = new ArrayList<AssByte>();
		AssByte[] tmp;
		for(int i = 0; i<parts.length; i++){
			tmp = parameters.get(i).prepareBytes(m, parts[i]);
			int emptyBytes = parameters.get(i).getSize()-tmp.length;
			for(int j = 0; j<emptyBytes; j++){
				bytes.add(new AssByte());
			}
			for(int j = 0; j<tmp.length; j++){
				bytes.add(tmp[j]);
			}
		}
		
		AssByte[] b = new AssByte[bytes.size()];
		for(int i = 0; i<bytes.size(); i++){
			b[i] = bytes.get(i);
		}
		
		return b;
	}
	
	public String getParameterData(Machine m, int startAddress){
		String indata = "";
		int num = 0;
		for(int i = 0; i<parameters.size(); i++){
			num = 0;
			for(int j = 0; j<parameters.get(i).getSize(); j++){
				num = num*256+m.readMemoryData(startAddress).getValue();
				startAddress++;
			}
			indata += (i>0?",":"")+num;
		}
				
		return indata;
	}
	
	public boolean takesParameters(Machine m, String params){
		if(parameters.size()==0 && params.equals("")){
			return true;
		}
		
		String[] parts = params.split(",");
		if(parts.length != this.parameters.size()){
			return false;
		}
		
		for(int i = 0; i<parameters.size(); i++){
			if(!parameters.get(i).isValid(m, parts[i])){
				return false;
			}
		}
		
		return true;
	}
	
	public void run(Machine m, String indata){
		String[] params = indata.split(",");
		if(params.length != this.parameters.size() && !(parameters.size()==0 && indata.length()==0)){
			throw new IllegalArgumentException("Invalid parameters");
		}
		
		for(int i = 0; i<parameters.size(); i++){
			parameters.get(i).perform(m, params[i]);
		}
		
		m.increaseRegister(m.getCounterRegName(), getParameterSize());
		
		for(int i = 0; i<functions.size(); i++){
			functions.get(i).perform(m);
		}
	}
	
	private int getParameterSize(){
		int sum = 0;
		for(int i = 0; i<parameters.size(); i++){
			sum += parameters.get(i).getSize();
		}
		
		return sum;
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
