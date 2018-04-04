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
		if(this.world == null)
			throw new RuntimeException("World not initialized");
		Point p;
		do {
			p = new Point(rand.nextInt(world.getWorldWidth()),
					rand.nextInt(world.getWorldHeight()));
		} while(world.get(p) != null);

		return new Food(p);
	}
}
