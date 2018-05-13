package multisnake;

import javafx.scene.paint.Color;

public class SpeedUpFood extends Food {
	public SpeedUpFood(Point start) {
		super(start);
		this.color = Color.rgb(233, 84, 32);
	}

	@Override
	public void applyEffect(Snake s) {
		s.setDelay(s.getDelay()*4/5);
	}
}
