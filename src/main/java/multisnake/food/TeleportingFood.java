package multisnake.food;

import javafx.scene.paint.Color;
import java.util.Random;

import multisnake.Point;
import multisnake.PointOutOfBoundariesException;
import multisnake.Snake;
import multisnake.FieldIsBusyException;
import multisnake.World;

public class TeleportingFood extends Food {
	private World world;
	private Random rand;

	public TeleportingFood(Point start, World w) {
		super(start);
		this.world = w;
		this.color = Color.rgb(0, 0, 255);
		this.rand = new Random();
	}

	@Override
	public void applyEffect(Snake s) {
		while(true){
			try {
				s.moveHead(new Point(
							rand.nextInt(world.getWorldWidth()), rand.nextInt(world.getWorldHeight())));
				return;
			} catch (FieldIsBusyException | PointOutOfBoundariesException e) {
				//
			}
		}
	}
}
