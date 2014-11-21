package se.matzlarsson.asssim;

import se.matzlarsson.asssim.model.Program;
import se.matzlarsson.asssim.model.RuntimeExecutor;
import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.AssembleException;
import se.matzlarsson.asssim.model.data.Machine;

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
		System.out.println("A: "+AssByte.getNumericalValue((m.getRegisterValue("A"))));
		executor.step();
		executor.step();
		System.out.println("A: "+AssByte.getNumericalValue(m.getRegisterValue("A")));
		System.out.println("B: "+AssByte.getNumericalValue(m.getRegisterValue("B")));
		System.out.println(AssByte.getNumericalValue(m.getRegisterValue(m.getCounterRegName())));
	}
	
}
