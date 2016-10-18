package assignment4;
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
public class Critter4 extends Critter{

	//how many times this critter has been instantiated
	private static int howMany = 0;
	
	public Critter4(){
		howMany++;
	}
	
	//moves faster when not too many of itself have been born
	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		if(howMany > 5){
			walk(Critter.getRandomInt(7));
		}
		else{
			run(Critter.getRandomInt(7));
		}
	}

	//will fight if enough of this critter has been born
	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return howMany >= 10;
	}
	
	//4 will represent Critter4
	@Override
	public String toString(){
		return "4";
	}

}
