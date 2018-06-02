package multisnake.food;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
		if(choice > 90)
			return new SpeedUpFood(world, p, TimeUnit.NANOSECONDS.convert(20000, TimeUnit.MILLISECONDS));
		else if(choice >  85)
			return new PersistentFood(world, p, 5);
		else if(choice > 70)
			return new TeleportingFood(world, p, pg.next());
		else if(choice > 55)
			return new JumpingFood(world, p);
		else if(choice > 40)
			return new RunningFood(world, p);
		else
			return new BasicFood(world, p);
	}
}
