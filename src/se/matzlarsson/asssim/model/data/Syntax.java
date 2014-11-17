package se.matzlarsson.asssim.model.data;

import java.util.ArrayList;
import java.util.List;

public class Syntax {

	public List<SyntaxRule> rules;
	
	public Syntax(){
		rules = new ArrayList<SyntaxRule>();
	}
	
	public void addRule(String name, String value, boolean usesQuotes){
		rules.add(new SyntaxRule(name, value, usesQuotes));
	}
	
	public SyntaxRule getRule(String name){
		for(int i = 0; i<rules.size(); i++){
			if(rules.get(i).getName().equals(name)){
				return rules.get(i);
			}
		}
		
		return null;
	}
	
	public int getRulesCount(){
		return rules.size();
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i<rules.size(); i++){
			if(i>0){
				s.append(", ");
			}
			s.append(rules.get(i).getName()).append(":'").append(rules.get(i).getValue()).append("'");
		}
		
		return s.toString();
	}
	
}
