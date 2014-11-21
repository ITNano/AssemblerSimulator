package se.matzlarsson.asssim;

import se.matzlarsson.asssim.model.AssembleException;
import se.matzlarsson.asssim.model.Program;
import se.matzlarsson.asssim.model.ProgramException;
import se.matzlarsson.asssim.model.RuntimeExecutor;
import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.util.Converter;

public class Main {

	public static void main(String[] args){
		System.out.println("Simulator is loading...");
		Machine m = new Machine("res/hc12.xml");
		Program p = new Program("res/simple.s12");
		
		RuntimeExecutor executor = null;
		try{
			executor = new RuntimeExecutor(m, p);
		}catch(AssembleException ae){
			System.out.println(ae.getMessage());
			System.exit(0);
		}
		
		System.out.println();
		for(int i = 0; i<7; i++){
			try{
				executor.step();
				System.out.println("D: $"+Converter.decToHex(AssByte.getNumericalValue(m.getRegisterValue("D")), 4));
			}catch(ProgramException pe){
				System.out.println(pe.getMessage());
			}
		}
	}
	
}
