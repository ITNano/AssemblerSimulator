package se.matzlarsson.asssim.model;

public class AssembleException extends Exception{

	private static final long serialVersionUID = 1L;

	public AssembleException(){
		super("Could not assemble!");
	}
	
	public AssembleException(String msg){
		super(msg);
	}
	
	public AssembleException(String error, String rowContent){
		super(error+":\n\t"+rowContent);
	}
}
