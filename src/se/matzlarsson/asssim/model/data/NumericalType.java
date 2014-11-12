package se.matzlarsson.asssim.model.data;

public enum NumericalType {
	BINARY,
	DECIMAL,
	HEXADECIMAL;
	
	public static NumericalType find(String name){
		switch(name){
			case "BIN": case "2": case "BINARY":		return BINARY;
			case "DEC": case "10": case "DECIMAL":		return DECIMAL;
			case "HEX": case "16": case "HEXADECIMAL":	return HEXADECIMAL;
			default:									return HEXADECIMAL;
		}
	}
}
