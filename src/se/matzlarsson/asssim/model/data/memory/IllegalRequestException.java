package se.matzlarsson.asssim.model.data.memory;

public class IllegalRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalRequestException(){
		this("An illegal request was received");
	}
	
	public IllegalRequestException(String msg){
		super(msg);
	}
}
