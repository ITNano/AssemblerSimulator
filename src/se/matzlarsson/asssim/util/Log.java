package se.matzlarsson.asssim.util;

public class Log {
	
	public static void i(String msg){
		log(Level.INFO, msg);
	}
	
	public static void d(String msg){
		log(Level.DEBUG, msg);
	}
	
	public static void w(String msg){
		log(Level.WARNING, msg);
	}
	
	public static void e(String msg){
		log(Level.ERROR, msg);
	}

	public static void log(Level level, String msg){
		System.out.println(level.getName()+": "+msg);
	}
	
	public enum Level{
		INFO("Info"),
		DEBUG("Debug"),
		WARNING("Warning"),
		ERROR("Error");
		
		private String name;
		
		private Level(String name){
			this.name = name;
		}
		
		public String getName(){
			return this.name;
		}
	}
}
