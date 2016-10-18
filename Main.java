/* CRITTERS Main.java
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
package assignment4; // cannot be in default package
import java.util.Scanner;
import java.io.*;
import java.util.List;
import java.lang.reflect.*; // added


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     * @throws InvalidCritterException 
     */
    public static void main(String[] args) throws InvalidCritterException { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
       // Critter.makeCritter("craig");
        
        userInterface(kb);
        
        /* Write your code above */
        System.out.flush();
        
    }
    
    /**
     * controller for Critter simulation. accepts the following commands:
     * quit – terminates the program
     * 
     * show – invoke the Critter.displayWorld() method
     * 
     * step [ <count> ] – The <count> is optional (count is [STAGE2]). If 
     * <count> is included, then <count> will be an integer. There are no square brackets
     * in this command, this notation is used simply to indicate that the <count> is optional.
     * For example, “step 10000” is a legal command, as is “step”. In response to
     * this command, the program must perform the specified number of world time
     * steps. If no count is provided, then only one world time step is performed.
     * 
     * seed <number> - invoke the Critter.setSeed method using
     * the number provided as the new random number seed. This method is provided so
     * that you can force your simulation to repeat the same sequence of random numbers
     * during testing.
     * 
     * make <class_name> [ <count> ] – the <count> argument is optional. The command “make” must be provided
     * verbatim. The <class_name> argument will be a string and must be the name
     * of a concrete subclass of Critter. When this command is executed, the controller
     * will invoke the Critter.makeCritter static method. The <class_name>
     * string will be provided as an argument to makeCritter. If no count is provided,
     * then makeCritter will be called exactly once. If a count is provided, then
     * 
     * stats <class_name> -- Similar to make, <class_name> must be a
     * string and will be the name of a concrete subclass of Critter. In response to this
     * command, the controller will
     * 1. Invoke the Critter.getInstances(<class_name>) which must
     * return a java.util.List<Critter> of all the instances of the specified
     * class (including instances of subclasses) currently in the critter collection
     * – you must write Crittter.getInstances, by the way, we
     * didn’t provide that for you.
     * 2. Invoke the static runStats() method for the specified class. For example,
     * if <class_name> were Craig, then your controller will invoke
     * Craig.runStats() and will invoke this function with a list of all of
     * the Craig critters currently in the critter list. See the note about converting
     * unqualified names to qualified.
     * 
     * @param kb: input stream to the controller
     */
    private static void userInterface(Scanner kb){
    	System.out.print("critters> ");
        while(kb.hasNextLine()){
	        String s = kb.nextLine();
	        String original = s;
	        String[] words = s.split("\\s+"); // \\s+ takes away all spaces and tabs
	        /*for(int i = 0; i < words.length; i++){ // test for s.split
	        	System.out.println(words[i] + " " + "is the " + i + "word");
	        }*/
	        try{
		        if(words[0].equals("quit")){
		        	if(words.length > 1){
		        		throw new Exception();
		        	}
		        	else{
		        		break;
		        	}
		        }
		        else if(words[0].equals("show")){
		        	if(words.length > 1){
		        		throw new Exception();
		        	}
		        	else{
		        		Critter.displayWorld();
		        	}
		        }
		        else if(words[0].equals("step")){
		        	int num = 0;
		        	if(words.length > 2){
		        		throw new Exception();
		        	}
		        	else if(words.length > 1){
		        		num = Integer.parseInt(words[1]); // turns the string in words[1] to an int
		        		if(num < 0){
		        			throw new Exception();
		        		}
		        	}
		        	else{
		        		num = 1; // user types in "step"
		        	}
	        		for(int i = num; i > 0; i--){
	        			Critter.worldTimeStep();
	        		}
		        }
		        else if(words[0].equals("seed")){
		        	if(words.length != 2){
		        		throw new Exception();
		        	}
		        	Critter.setSeed(Integer.parseInt(words[1]));
		        }
		        else if(words[0].equals("make")){
		        	String next = words[1];
		        	int num;
		        	if(words.length > 3){
		        		throw new Exception();
		        	}
		        	if(words.length > 2){
		        		num = Integer.parseInt(words[2]); // turns the string in words[2] to an int
		        		if(num < 0){
		        			throw new Exception();
		        		}
		        	}
		        	else{
		        		num = 1; // user types in only make and class_name
		        	}
	        		for(int i = num; i > 0; i--){
	        			Critter.makeCritter(next);
	        		}
		        }
		        else if(words[0].equals("stats")){
		        	if(words.length != 2){
		        		throw new Exception();
		        	}
		        	Class<?> crit = Class.forName(myPackage + "." + words[1]);
		        	Method m = crit.getMethod("runStats", List.class);
		        	m.invoke(null, Critter.getInstances(words[1]));
		        }
		        else{
		        	System.out.println("invalid command: " + original);
		        }
	        }
	        catch(Exception e){
	        	//e.printStackTrace();
	        	System.out.println("error processing: " + original);
	        }
	        System.out.print("critters> ");
        }
    }
    
    
}
