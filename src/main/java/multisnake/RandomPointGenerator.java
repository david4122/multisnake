package multisnake;

import java.util.Random;

public class RandomPointGenerator implements Generator<Point> {
	private World world;
	private Random rand;

	public RandomPointGenerator(World w) {
		this.world = w;
		this.rand = new Random();
	}

	public Point next() {
		Point p;
		while(true) {
			p = new Point(rand.nextInt(world.getWorldWidth()),
					rand.nextInt(world.getWorldHeight()));
			try {
				if(world.get(p) == null)
					break;
			} catch(PointOutOfBoundariesException e){
				//
			}
		}
		return p;
	}
}
