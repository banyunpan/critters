/* CRITTERS <MyClass.java> 
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Megan Peregrino
 * mnp624
 * 16465
 * Ban-Yun "Toby" Pan
 * bp9594
 * 16480
 * Slip days used: <0>
 * Fall 2016  */
package assignment4;

import java.lang.reflect.Modifier;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
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
	
	/**
	 * records the number of attempts that a Critter has attempted to move
	 * inside single worldTimeStep
	 */
	private int timesMoved = 0;
	//maximum number of moves that a Critter may attempt to make in one worldTimeStep
	private static final int MAX_MOVES = 1;

	/**
	 * given a direction, Critter will move one unit in that direction.
	 * substracts the appropriate amount of energy for the attempt.
	 * @param direction : the direction to move. 0 is east, 1 is northeast, etc
	 */
	protected final void walk(int direction) {
		//DONE
		energy -= Params.walk_energy_cost;
		if(timesMoved >= MAX_MOVES){
			return;
		}
		int oldX = x_coord;
		int oldY = y_coord;
		move(direction, 1);
		if(fighting && isSameSpot()){
			x_coord = oldX;
			y_coord = oldY;
		}
		timesMoved++;
	}
	/**
	 * given a direction, Critter will move two units in that direction.
	 * substracts the appropriate amount of energy for the attempt.
	 * @param direction : the direction to move. 0 is east, 1 is northeast, etc
	 */
	protected final void run(int direction) {
		//DONE
		energy -= Params.run_energy_cost;
		if(timesMoved >= MAX_MOVES){
			return;
		}
		int oldX = x_coord;
		int oldY = y_coord;
		move(direction, 2);
		if(fighting && isSameSpot()){
			x_coord = oldX;
			y_coord = oldY;
		}
		timesMoved++;
	}
	/**
	 * initialize an instance of the Critter, if the parent's energy is sufficient.
	 * assigns half the parent's energy to the offspring, rounded down
	 * deducts half the parent's energy, rounded up
	 * @param offspring: the new Critter
	 * @param direction: the space relative to the parent that offspring will be placed
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(energy < Params.min_reproduce_energy){
			return;
		}
		offspring.energy = energy / 2;
		energy = (energy+1) / 2;
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		offspring.move(direction, 1);
		babies.add(offspring);
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
			//throw new InvalidCritterException(critter_class_name);
			throw new InvalidCritterException("");
		}
		
		//Critter critter = population.get(population.size() - 1);
		critter.energy = Params.start_energy;
		critter.x_coord = Critter.getRandomInt(Params.world_width - 1);
		critter.y_coord = Critter.getRandomInt(Params.world_height - 1);
	//	critter.x_coord = 0;
	//	critter.y_coord = 0;
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
				//throw new InvalidCritterException(critter_class_name);
				throw new InvalidCritterException("");
			}
		}catch(ClassNotFoundException e){
			//throw new InvalidCritterException(critter_class_name);
			throw new InvalidCritterException("");
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
			//if new_energy_value <= 0, should remove that critter
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
		// DONE
		population = new java.util.ArrayList<Critter>();
	}
	
	//flag indicating if the Critter has just called its fight method
	private boolean fighting = false;
	/**
	 * each critter takes actions and interacts with other critters in its same spot.
	 * rest energy is deducted and dead critters are removed.
	 * new Algae and babies born in this worldTimeStep are added to the population.
	 */
	public static void worldTimeStep() {
		for(int i = 0; i < population.size(); i++){
			population.get(i).doTimeStep();
		}
		
		//check if critters are in the same spot
		//ie, encounter
		for(int A = 0; A < population.size(); A++){				
			Critter critA = population.get(A);
			for(int B = 0; B < population.size(); B++){
				if(A == B){
					continue;
				}
				Critter critB = population.get(B);
				if(critB.energy <= 0 || critA.energy <= 0){
					continue;
				}
				if(critA.x_coord == critB.x_coord && critA.y_coord == critB.y_coord){	//encounter
					int rolla = 0;
					int rollb = 0;
					critA.fighting = true;
					critB.fighting = true;
					if(critA.fight(critB.toString())){
						rolla = getRandomInt(critA.energy);
					}
					if(critB.fight(critA.toString())){
						rollb = getRandomInt(critB.energy);
					}
					if(critA.x_coord == critB.x_coord && critA.y_coord == critB.y_coord){
						if(critA.energy > 0 && critB.energy > 0){
							if(rolla >= rollb){
								int e = critB.energy / 2;
								critB.energy = 0;
								critA.energy = critA.energy + e;
							}
							else{
								int e = critA.energy / 2;
								critA.energy = 0;
								critB.energy = critB.energy + e;
							}
						}
					}
					
				}
			}
		}
		
		//add newly born critters to population
		for(int x = 0; x < babies.size(); x++){
			population.add(babies.get(x));
		}
		babies = new java.util.ArrayList<Critter>();
		
		//apply rest energy and
		//remove dead critters
		int count = population.size() - 1;
		while(count >= 0){
			population.get(count).energy -= Params.rest_energy_cost;
			if(population.get(count).energy <= 0){
				population.remove(count);
			}
			count--;
		}
		
		//add new algae
    		for(int i = Params.refresh_algae_count; i > 0; i--){
    			try{ Critter.makeCritter("Algae"); }
    			catch(InvalidCritterException e){}
    		}
    		
    	// reset movement quota and fighting status on all critters
    		for(int i = 0; i < population.size(); i++){
    			population.get(i).timesMoved = 0;
    			population.get(i).fighting = false;
    		}
		
	}
	
	/**
	 * ASCII representation of the world.
	 * Only one Critter is displayed per location, even if multiple Critters occupy the same spot.
	 */
	private static String[][] world;
	
	/**
	 * prints the state of the current world onto the console
	 */
	public static void displayWorld() {
		//DONE
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
	/**
	 * moves the Critter a number of spaces in the specified direction.
	 * will wrap around if moving through border of world.
	 * @param dir: direction to move the Critter (as defined in walk/run methods)
	 * @param mag: number of spaces to move.
	 */
	private final void move(int dir, int mag){
		if(dir == 1 || dir == 0 || dir == 7){
			// move in x positive direction
			x_coord = (x_coord + mag) % Params.world_width;
		}
		if(dir == 3 || dir == 4 || dir == 5){
			// move in x negative direction
			x_coord = (x_coord - mag);
			while(x_coord < 0){ // wrap around
				x_coord += Params.world_width;
			}
		}
		
		if(dir == 5 || dir == 6 || dir == 7){
			// move in y positive direction
			y_coord = (y_coord + mag) % Params.world_height;
		}
		if(dir == 1 || dir == 2 || dir == 3){
			// move in y negative direction
			y_coord = (y_coord - mag);
			while(y_coord < 0){ // wrap around
				y_coord += Params.world_height;
			}
		}
	}
	
	private boolean isSameSpot(){
		for(int i = 0; i < population.size(); i++){
			Critter crit = population.get(i);
			if(this != crit && crit.energy > 0){
				if(crit.x_coord == x_coord && crit.y_coord == y_coord){
					return true;
				}
			}
		}
		return false;
	}
	
}
