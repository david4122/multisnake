package multisnake.food;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.concurrent.TimeUnit;

import multisnake.Point;
import multisnake.RandomPointGenerator;
import multisnake.Animatable;
import multisnake.World;
import multisnake.GameOver;

public class JumpingFood extends BasicFood implements Animatable {
	private long lastUpdate;
	private long delay;
	private double percent;
	private RandomPointGenerator pg;

	public JumpingFood(World w, Point start) {
		super(w, start, Color.rgb(0, 255, 0));
		this.delay = TimeUnit.NANOSECONDS.convert(5000, TimeUnit.MILLISECONDS);
		this.pg = new RandomPointGenerator(w);
	}

	@Override
	public void update(long time) throws GameOver {
		if(time - lastUpdate > delay){
			Point p = pg.next();
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
