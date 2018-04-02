package multisnake;

import javafx.scene.canvas.GraphicsContext;

interface Animatable {
	public void update(GraphicsContext gc, long time) throws GameOver ;
}
