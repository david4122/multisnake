package multisnake;

import javafx.scene.paint.Color;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TeleportingFood extends Food implements Animatable {
	private World world;
	private long lastUpdate;
	private long delay;
	private Random rand;

	public TeleportingFood(Point start, World w) {
		super(start);
		this.delay = TimeUnit.NANOSECONDS.convert(5000, TimeUnit.MILLISECONDS);
		this.world = w;
		this.color = Color.rgb(255, 10, 10);
		this.rand = new Random();
	}

	@Override
	public void update(long time) throws GameOver {
		if(time - lastUpdate > delay){
			Point p;
			do {
				p = new Point(rand.nextInt(world.getWorldWidth()),
						rand.nextInt(world.getWorldHeight()));
			} while(world.get(p) != null);
			this.location = p;
			lastUpdate = time;
		}
	}
}
