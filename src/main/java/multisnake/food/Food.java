package multisnake.food;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import multisnake.Point;
import multisnake.Drawable;
import multisnake.Edible;
import multisnake.Effect;
import multisnake.Snake;
import multisnake.World;

public class Food implements Drawable, Edible {
	protected Point location;
	protected Color color;
	protected World world;
	protected Effect effect;

	protected Food(World w, Point p, Effect e, Color c) {
		this.location = p;
		this.world = w;
		this.effect = e;
		this.color = c;
	}

	public Point getLocation() {
		return this.location;
	}

	protected void applyEffect(Snake s, long time) {
		effect.apply(s, time);
	}

	@Override
	public void draw(GraphicsContext gc, int fsize) {
		gc.setFill(color);
		gc.fillOval(location.x*fsize, location.y*fsize, fsize, fsize);
	}

	@Override
	public void eaten(Snake s, long time) {
		this.applyEffect(s, time);
		world.foodEaten(this);
	}
}
