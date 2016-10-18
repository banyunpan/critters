package assignment4;

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
