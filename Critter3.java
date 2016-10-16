package assignment4;

public class Critter3 extends Critter{

	private int babies;

	public Critter3() {
		babies = super.getRandomInt(5);
	}

	public void doTimeStep() {
		makeBabies();
		run(super.getRandomInt(7));
	}

	public boolean fight(String critter) {
		makeBabies();

		return super.getRandomInt(10) > babies;
	}

	private void makeBabies() {
		for (int i = 0; i < babies; i++) {
			reproduce(new Critter3(), super.getRandomInt(babies));
		}
	}
	
	@Override
	public String toString(){
		return "3";
	}


}
