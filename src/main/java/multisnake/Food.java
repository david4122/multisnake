package multisnake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Food {
	protected Point location;
	protected Color color;

	public Food(Point p) {
		this.location = p;
	}

	public Point getLocation() {
		return this.location;
	}

	public void draw(GraphicsContext gc, int fsize) {
		gc.setFill(color);
		gc.fillOval(location.x*fsize, location.y*fsize, fsize, fsize);
	}
}
