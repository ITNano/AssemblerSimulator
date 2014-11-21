package se.matzlarsson.asssim.model;

public class ProgramException extends Exception{

	private static final long serialVersionUID = 1L;

	public ProgramException(){
		super("An error has occurred");
	}
	
	public ProgramException(String msg){
		super(msg);
	}
	
}
