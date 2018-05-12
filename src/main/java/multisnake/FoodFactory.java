package multisnake;

import java.util.Random;

public class FoodFactory {
	private Random rand;
	private World world;

	public FoodFactory(World w) {
		this.rand = new Random();
		this.world = w;
	}

	public Food next() {
		Point p;
		do {
			p = new Point(rand.nextInt(world.getWorldWidth()),
					rand.nextInt(world.getWorldHeight()));
		} while(world.get(p) != null);

		int choice = rand.nextInt(100);
		if(choice > 60)
			return new RunningFood(p, world);
		else
			return new Food(p);
	}
}
