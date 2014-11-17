package se.matzlarsson.asssim;

import se.matzlarsson.asssim.model.data.Machine;

public class Main {

	public static void main(String[] args){
		System.out.println("Simulator is loading...");
		Machine m = new Machine("res/hc12.xml");
		System.out.println(m);
	}
	
}
