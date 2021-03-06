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

public class Critter1 extends Critter{
	
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public boolean fight(String not_used) {
			return dir < 4;
	}
	
	public Critter1() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}

	@Override
	public void doTimeStep() {
		if(dir > 4){
			Critter1 baby = new Critter1();
			reproduce(baby, dir);
		}
		run(dir);
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		dir = (dir + turn) % 8;
	}
	
	@Override
	public String toString(){ return "1"; }
}
