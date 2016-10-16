package assignment4;

public class Longhorn extends Critter{
	
	
	
	public boolean fight(String not_used) {
			return true;
	}

	@Override
	public void doTimeStep() {
		Longhorn baby = new Longhorn();
		reproduce(baby, 6);
		
	}
	
	@Override
	public String toString(){
		return "L";
	}
}
