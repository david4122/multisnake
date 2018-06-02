package multisnake.food;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import multisnake.Point;
import multisnake.Snake;
import multisnake.World;

public class PersistentFood extends BasicFood {
	private int persistence;
	private int current;

	public PersistentFood(World w, Point p, int persistence) {
		super(w, p, Color.rgb(255, 0, 0));
		this.persistence = current = persistence;
	}

	@Override
	public void draw(GraphicsContext gc, int fsize) {
		gc.setFill(color);
		gc.fillOval(location.x*fsize, location.y*fsize, fsize, fsize);
		Point center = new Point(location.x*fsize + fsize/2, location.y*fsize + fsize/2);
		double r = fsize - ((double)current/persistence)*fsize;
		gc.setFill(Color.WHITE);
		gc.fillOval(center.x-r/2, center.y-r/2, r, r);
	}

	@Override
	public void eaten(Snake s, long time) {
		applyEffect(s, time);
		if(0 == --current)
			world.foodEaten(this);
	}
}
