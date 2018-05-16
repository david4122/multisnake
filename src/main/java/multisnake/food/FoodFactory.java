package multisnake.food;

import java.util.Random;

import multisnake.World;
import multisnake.Point;
import multisnake.RandomPointGenerator;

public class FoodFactory {
	private Random rand;
	private World world;
	private RandomPointGenerator pg;

	public FoodFactory(World w) {
		this.rand = new Random();
		this.world = w;
		this.pg = new RandomPointGenerator(w);
	}

	public Food next() {
		Point p = pg.next();

		int choice = rand.nextInt(100);
		if(choice > 95)
			return new SpeedUpFood(p);
		else if(choice > 85)
			return new TeleportingFood(p, pg.next(), world);
		else if(choice > 65)
			return new JumpingFood(p, world);
		else if(choice > 50)
			return new RunningFood(p, world);
		else
			return new Food(p);
	}
}
