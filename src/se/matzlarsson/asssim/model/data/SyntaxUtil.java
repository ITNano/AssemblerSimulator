package se.matzlarsson.asssim.model.data;

import se.matzlarsson.asssim.util.Converter;



public class SyntaxUtil {
	
	public static boolean isConstant(Syntax syntax, String input){
		SyntaxRule constant = syntax.getRule("constant");
		return input.startsWith(constant.getValue());
	}
	
	public static String getRealValue(Syntax syntax, String input){
		//Remove constant markup
		SyntaxRule constant = syntax.getRule("constant");
		if(input.startsWith(constant.getValue())){
			int quotes = (constant.usesQuotes()?1:0);
			return input.substring(constant.getValue().length()+quotes, input.length()-quotes);
		}else{
			return input;
		}
	}
	
	public static String getConcreteValue(Syntax syntax, String input){
		String realValue = getRealValue(syntax, input);
		NumericalType type = getNumericalType(syntax, realValue);
		int offset = 0;
		if(type != null){
			switch(type){
				case HEXADECIMAL:	offset = syntax.getRule("hexadecimal").getValue().length();
				break;
				case BINARY:		offset = syntax.getRule("binary").getValue().length();
				break;
				case DECIMAL:		offset = syntax.getRule("decimal").getValue().length();
				break;
			}
		}
		
		return realValue.substring(offset);
	}
	
	public static NumericalType getNumericalType(Syntax syntax, String input){
		input = getRealValue(syntax, input);
		
		//Check for number type
		SyntaxRule bin = syntax.getRule("binary");
		SyntaxRule hex = syntax.getRule("hexadecimal");
		SyntaxRule dec = syntax.getRule("decimal");
		if(input.startsWith(bin.getValue())){
			return NumericalType.BINARY;
		}else if(input.startsWith(hex.getValue())){
			return NumericalType.HEXADECIMAL;
		}else if(input.startsWith(dec.getValue())){
			try{
				Integer.parseInt(input);
				return NumericalType.DECIMAL;
			}catch(NumberFormatException nfe){
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static int getIntValue(Syntax syntax, String input){
		input = getRealValue(syntax, input);
		
		//Check for number type
		SyntaxRule bin = syntax.getRule("binary");
		SyntaxRule hex = syntax.getRule("hexadecimal");
		SyntaxRule dec = syntax.getRule("decimal");
		if(input.startsWith(bin.getValue())){
			int quotes = bin.usesQuotes()?1:0;
			input.substring(bin.getValue().length()+quotes, input.length()-quotes);
			throw new IllegalArgumentException("Fuck no to binary nums");
		}else if(input.startsWith(hex.getValue())){
			int quotes = hex.usesQuotes()?1:0;
			return Converter.hexToDecimal(input.substring(hex.getValue().length()+quotes, input.length()-quotes));
		}else if(input.startsWith(dec.getValue())){
			try{
				int quotes = dec.usesQuotes()?1:0;
				return Integer.parseInt(input.substring(dec.getValue().length()+quotes, input.length()-quotes));
			}catch(NumberFormatException nfe){
				return -1;
			}
		}else{
			return -1;
		}
	}
}
