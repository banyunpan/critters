package assignment4;

public class Critter3 extends Critter{
	//will always try to reproduce this many times during doTimeStep
	private int babies;

	//random number of babymaking capability
	public Critter3() {
		babies = super.getRandomInt(5);
	}
	//makes babies, runs randomly
	public void doTimeStep() {
		makeBabies();
		run(super.getRandomInt(7));
	}
	//spawns more babies if stressed by fight
	public boolean fight(String critter) {
		makeBabies();

		return super.getRandomInt(10) > babies;
	}

	//make babies
	private void makeBabies() {
		for (int i = 0; i < babies; i++) {
			reproduce(new Critter3(), super.getRandomInt(babies));
		}
	}
	
	//3 will represent this Critter3
	@Override
	public String toString(){
		return "3";
	}


}
