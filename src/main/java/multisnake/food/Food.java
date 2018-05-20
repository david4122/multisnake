package multisnake.food;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import multisnake.Point;
import multisnake.Drawable;
import multisnake.Edible;
import multisnake.Snake;
import multisnake.World;

public class Food implements Drawable, Edible {
	protected Point location;
	protected Color color;
	protected World world;

	public Food(World w, Point p) {
		this(w, p, Color.rgb(0, 0, 0));
	}

	protected Food(World w, Point p, Color c) {
		this.location = p;
		this.world = w;
		this.color = c;
	}

	public Point getLocation() {
		return this.location;
	}

	protected void applyEffect(Snake s) {
		s.addSegment();
	}

	@Override
	public void draw(GraphicsContext gc, int fsize) {
		gc.setFill(color);
		gc.fillOval(location.x*fsize, location.y*fsize, fsize, fsize);
	}

	@Override
	public void eaten(Snake s) {
		this.applyEffect(s);
		world.foodEaten(this);
	}
}
