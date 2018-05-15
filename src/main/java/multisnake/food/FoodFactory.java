package multisnake.food;

import java.util.Random;

import multisnake.World;
import multisnake.Point;

public class FoodFactory {
	private Random rand;
	private World world;

	public FoodFactory(World w) {
		this.rand = new Random();
		this.world = w;
	}

	public Food next() {
		Point p;
		while(true) {
			p = new Point(rand.nextInt(world.getWorldWidth()),
					rand.nextInt(world.getWorldHeight()));
			try {
				if(world.get(p) == null)
					break;
			} catch(Exception e){
				//
			}
		}

		int choice = rand.nextInt(100);
		if(choice > 95)
			return new SpeedUpFood(p);
		else if(choice > 70)
			return new TeleportingFood(p, world);
		else if(choice > 60)
			return new JumpingFood(p, world);
		else if(choice > 40)
			return new RunningFood(p, world);
		else
			return new Food(p);
	}
}
