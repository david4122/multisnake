package multisnake.food;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import multisnake.Point;
import multisnake.Drawable;
import multisnake.Edible;
import multisnake.Snake;

public class Food implements Drawable, Edible {
	protected Point location;
	protected Color color;

	public Food(Point p) {
		this.location = p;
		this.color = Color.rgb(0, 0, 0);
	}

	public Point getLocation() {
		return this.location;
	}

	@Override
	public void draw(GraphicsContext gc, int fsize) {
		gc.setFill(color);
		gc.fillOval(location.x*fsize, location.y*fsize, fsize, fsize);
	}

	@Override
	public void applyEffect(Snake s) {
		s.addSegment();
	}
}
