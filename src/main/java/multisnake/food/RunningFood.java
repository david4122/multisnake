package multisnake.food;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.scene.paint.Color;

import multisnake.Point;
import multisnake.Animatable;
import multisnake.World;
import multisnake.GameOver;
import multisnake.Direction;
import multisnake.PointOutOfBoundariesException;

public class RunningFood extends Food implements Animatable {
	private long lastUpdate;
	private long delay;

	private Random rand;
	private World world;

	public RunningFood(World w, Point start) {
		super(w, start, Color.rgb(100, 100, 100));
		this.delay = TimeUnit.NANOSECONDS.convert(500, TimeUnit.MILLISECONDS);
		this.world = w;
		this.rand = new Random();
	}

	@Override
	public void update(long time) throws GameOver {
		if(time - lastUpdate > delay){
			Point p;
			while(true){
				p = location.translate(Direction.values()[rand.nextInt(4)], 1);
				try {
					if(world.get(p) == null)
						break;
				} catch(PointOutOfBoundariesException e) {
					//
				}
			}
			this.location = p;
			this.lastUpdate = time;
		}
	}
}
