package multisnake.food;

import javafx.scene.paint.Color;
import multisnake.Effect;
import multisnake.Point;
import multisnake.Snake;
import multisnake.World;

public class BasicFood extends Food {
	public BasicFood(World w, Point start) {
		this(w, start, Color.rgb(0, 0, 0));
	}

	protected BasicFood(World w, Point start, Color c) {
		super(w, start, new Effect(0, (Snake s) -> {
			s.addSegment();
		}, (Snake s) -> {}), c);
	}
}
