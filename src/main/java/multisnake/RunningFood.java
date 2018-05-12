package multisnake;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.scene.paint.Color;

public class RunningFood extends Food implements Animatable {
	private long lastUpdate;
	private long delay;

	private Random rand;
	private World world;

	public RunningFood(Point start, World w) {
		super(start);
		this.delay = TimeUnit.NANOSECONDS.convert(500, TimeUnit.MILLISECONDS);
		this.world = w;
		this.rand = new Random();
		this.color = Color.rgb(100, 100, 100);
	}

	@Override
	public void update(long time) throws GameOver {
		if(time - lastUpdate > delay){
			Point p;
			do {
				p = location.translate(Direction.values()[rand.nextInt(4)], 1);
			} while(world.get(p) != null);
			this.location = p;
			this.lastUpdate = time;
		}
	}
}
