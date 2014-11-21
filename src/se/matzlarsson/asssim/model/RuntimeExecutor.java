package se.matzlarsson.asssim.model;

import se.matzlarsson.asssim.model.data.AssembleException;
import se.matzlarsson.asssim.model.data.Machine;

public class RuntimeExecutor {

	private static final int RUN_SLOW_DELAY = 200;
	private static final int RUN_FAST_DELAY = 10;
	
	private boolean running = false;

	private Machine machine;
	private Program program;
	
	public RuntimeExecutor(Machine machine, Program program) throws AssembleException{
		this.machine = machine;
		this.program = program;
		
		this.program.applyToMachine(this.machine);
	}
	
	private void run(int delay){
		running = true;
		while(isRunning()){
			step();
			sleep(delay);
		}
	}
	private void sleep(int ms){
		try{
			Thread.sleep(ms);
		}catch(InterruptedException ie){}
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void step(){
		program.next(machine);
	}
	
	public void runSlow(){
		run(RUN_SLOW_DELAY);
	}
	
	public void runFast(){
		run(RUN_FAST_DELAY);
	}
	
}
