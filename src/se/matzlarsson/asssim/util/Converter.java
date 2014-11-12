package se.matzlarsson.asssim.util;

public class Converter {
	
	public static String hexToBinary(String hex){
		String binary = "";
		for(int i = 0; i<hex.length(); i++){
			binary += hexDigitToBinary(hex.charAt(i));
		}
		
		return binary;
	}
	
	public static int hexToDecimal(String hex){
		int dec = 0;
		int tmp = 0;
		for(int i = 0; i<hex.length(); i++){
			tmp = Character.isDigit(hex.charAt(i))?hex.charAt(i)-48:hex.charAt(i)-55;
			dec = 16*dec+tmp;
		}
		
		return tmp;
	}
	
	public static String decToHex(int dec){
		String hex = "";
		int tmp = 0;
		while(hex.length()<2 || dec>0){
			tmp = dec%16;
			hex = (char)(tmp<10?tmp+48:tmp+55)+hex;
			dec /= 16;
		}
		
		return hex;
	}
	
	
	private static String hexDigitToBinary(char c){
		int num = 0;
		if(Character.isDigit(c)){
			num = Character.getNumericValue(c);
		}else{
			Character.toUpperCase(c);
			num = (int)c-55;
		}

		String binary = "";
		while(binary.length()<4){
			binary = (num%2)+binary;
			num /= 2;
		}
		
		return binary;
	}
	
}
