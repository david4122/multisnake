package multisnake;

import javafx.scene.canvas.GraphicsContext;

public class Food {
	protected Point location;

	public Food(Point p) {
		this.location = p;
	}

	public Point getLocation() {
		return this.location;
	}

	public void draw(GraphicsContext gc, int fsize) {
		gc.fillOval(location.x*fsize, location.y*fsize, fsize, fsize);
	}
}
