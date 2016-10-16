package assignment4;

public class Critter4 extends Critter{

	private static int howMany = 0;
	
	public Critter4(){
		howMany++;
	}
	
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

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return howMany >= 10;
	}
	
	@Override
	public String toString(){
		return "" + 4;
	}

}
