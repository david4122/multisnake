package multisnake;

import java.util.Random;

public class FoodFactory {
	private Random rand;

	public FoodFactory() {
		this.rand = new Random();
	}

	public Food next() {
		//TODO
		return new Food(new Point(rand.nextInt(50), rand.nextInt(50)));
	}
}
