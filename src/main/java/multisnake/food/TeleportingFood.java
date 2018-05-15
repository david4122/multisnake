package multisnake.food;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import multisnake.Point;
import multisnake.Animatable;
import multisnake.World;
import multisnake.GameOver;

public class TeleportingFood extends Food implements Animatable {
	private World world;
	private long lastUpdate;
	private long delay;
	private double percent;
	private Random rand;

	public TeleportingFood(Point start, World w) {
		super(start);
		this.delay = TimeUnit.NANOSECONDS.convert(5000, TimeUnit.MILLISECONDS);
		this.world = w;
		this.color = Color.rgb(0, 255, 0);
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
		this.percent = ((double)(time - lastUpdate))/delay;
	}

	@Override
	public void draw(GraphicsContext gc, int fsize) {
		gc.setFill(color);
		gc.fillOval(location.x*fsize, location.y*fsize, fsize, fsize);
		Point center = new Point(location.x*fsize + fsize/2, location.y*fsize + fsize/2);
		double r = fsize*percent;
		gc.setFill(Color.WHITE);
		gc.fillOval(center.x-r/2, center.y-r/2, r, r);
	}
}
