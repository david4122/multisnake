package multisnake.food;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import multisnake.Point;
import multisnake.Snake;
import multisnake.World;

public class TeleportingFood extends BasicFood {
	private Point target;

	public TeleportingFood(World world, Point start, Point target) {
		super(world, start, Color.rgb(0, 0, 255));
		this.target = target;
	}

	@Override
	public void applyEffect(Snake s, long time) {
		s.setNextHeadPos(target);
	}

	@Override
	public void draw(GraphicsContext gc, int fsize) {
		gc.setLineWidth(1);
		gc.setStroke(Color.GRAY);
		gc.strokeLine(location.x*fsize+fsize/2, location.y*fsize+fsize/2,
				target.x*fsize+fsize/2, target.y*fsize+fsize/2);
		super.draw(gc, fsize);
		gc.setFill(Color.GRAY);
		gc.strokeOval(target.x*fsize, target.y*fsize, fsize, fsize);
	}
}
