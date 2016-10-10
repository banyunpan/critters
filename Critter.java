/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.lang.reflect.Modifier;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	// private	static List<Critter> population = new java.util.ArrayList<Critter>();
	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
	}
	
	protected final void run(int direction) {
		
	}
	
	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Critter critter;
		try{/*
			Class<?> attemptCritter = Class.forName(critter_class_name);
			if(Modifier.isAbstract(attemptCritter.getModifiers())){
				// not a concrete class
				throw new InvalidCritterException(critter_class_name);
			}
	
			Class<?> critterClass = Class.forName("assignment4.Critter");
			if(!critterClass.isAssignableFrom(attemptCritter)){
				// not a subclass
				throw new InvalidCritterException(critter_class_name);
			}
			*/
			//this will throw exceptions if any of the following are true:
			// 1. critter_class does not exist in the critter package
			Class<?> attemptCritter = Class.forName(Critter.myPackage + "." + critter_class_name);
			//this will throw exceptions if any of the following are true:
			// 1. critter_class is an abstract class
			// 2. critter_class does not extend Critter
			critter = (Critter)(attemptCritter.newInstance());	
		} catch(Exception e){
			throw new InvalidCritterException(critter_class_name);
		}
		
		//Critter critter = population.get(population.size() - 1);
		critter.energy = Params.start_energy;
		critter.x_coord = Critter.getRandomInt(Params.world_width - 1);
		critter.y_coord = Critter.getRandomInt(Params.world_height - 1);
		population.add(critter);
	
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		Class<?> critter, critterClass;
		try{
			critterClass = Class.forName(myPackage + ".Critter");
			critter = Class.forName(myPackage + "." + critter_class_name);
			/*if(Modifier.isAbstract(critter.getModifiers())){
				//critter_class is abstract
				throw new InvalidCritterException(critter_class_name);
			}*/
			if(!critterClass.isAssignableFrom(critter)){
				//critter_class is not subclass of Critter
				throw new InvalidCritterException(critter_class_name);
			}
		}catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
		
		for(int i = 0; i < population.size(); i++){
			if(critter.isInstance(population.get(i))){
				result.add(population.get(i));
			}
		}
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
	}
	
	public static void worldTimeStep() {
		for(int i = 0; i < population.size(); i++){
			population.get(i).doTimeStep();
		}
		//check if critters are in the same spot
		//ie, encounter
		
		//add newly born critters to population
		
		//apply rest energy and
		//remove dead critters
		
		//add new algae
		
	}
	private static String[][] world;
	public static void displayWorld() {
		world = new String[Params.world_width][Params.world_height];
		for(int x = 0; x < Params.world_width; x ++){
			for(int y = 0; y < Params.world_height; y ++){
				world[x][y] = " ";
			}
		}

		for(int i = 0; i < population.size(); i ++){
			int x = population.get(i).x_coord;
			int y = population.get(i).y_coord;
			world[x][y] = population.get(i).toString();
		}
		System.out.print("+");
		for(int x = 0; x < Params.world_width; x++){
			System.out.print("-");
		}
		System.out.println("+");
		for(int y = 0; y < Params.world_height; y ++){
			for(int x = 0; x < Params.world_width; x ++){
				if(x == 0){
					System.out.print("|");
				}
				System.out.print(world[x][y]);
			}
			System.out.println("|");
		}
		System.out.print("+");
		for(int x = 0; x < Params.world_width; x++){
			System.out.print("-");
		}
		System.out.println("+");

		
	}
}
