package se.matzlarsson.asssim.model.data;

public class SyntaxRule {

	private String name;
	private String value;
	private boolean quotes;
	
	public SyntaxRule(String name, String value, boolean quotes){
		this.name = name;
		this.value = value;
		this.quotes = quotes;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public boolean usesQuotes(){
		return this.quotes;
	}
	
}
