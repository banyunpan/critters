package assignment4;

public class Critter2 extends Critter{
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public boolean fight(String not_used) {
			return dir > 4;
	}
	
	public Critter2() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}

	@Override
	public void doTimeStep() {
		
			Critter2 baby = new Critter2();
			reproduce(baby, dir);
		
		/* take two steps forward */
		if(dir == 1){	
			run(dir);
		}
		else{
			walk(dir);
		}
		
		/* pick a new direction based on our genes */
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
	public String toString(){ return "2"; }
	
	public static void runStats(java.util.List<Critter> critters2) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : critters2) {
			Critter2 c = (Critter2) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + critters2.size() + " total Critters2    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * critters2.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * critters2.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * critters2.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * critters2.size()) + "% left   ");
		System.out.println();
	}
}
