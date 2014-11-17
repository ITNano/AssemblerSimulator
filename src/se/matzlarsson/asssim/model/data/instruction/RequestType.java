package se.matzlarsson.asssim.model.data.instruction;

public enum RequestType {
	MEMORY,
	REGISTER,
	CONSTANT;
	
	public static RequestType find(String name){
		switch(name){
			case "M":	case "MEM":		case "MEMORY":		return MEMORY;
			case "R":	case "REG": 	case "REGISTER":	return REGISTER;
			case "C":	case "CONST":	case "CONSTANT":	return CONSTANT;
			default:									return MEMORY;
		}
	}
}
