package multisnake;

import javafx.scene.canvas.GraphicsContext;

interface Animatable {
	public void update(long time) throws GameOver ;
	public void draw(GraphicsContext gc);
}
