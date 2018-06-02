package multisnake.food;

import javafx.scene.paint.Color;
import multisnake.Effect;
import multisnake.Point;
import multisnake.Snake;
import multisnake.World;

public class SpeedUpFood extends BasicFood {
	private Effect effect;

	public SpeedUpFood(World w, Point start, long duration) {
		super(w, start, Color.rgb(227, 75, 22));
		this.effect = new Effect(duration, (Snake s) -> {
			s.setDelay(s.getDelay()*2/3);
		}, (Snake s) -> {
			s.setDelay(s.getDelay()*3/2);
		});
	}

	@Override
	public void applyEffect(Snake s, long time) {
		s.applyEffect(effect, time);
	}
}
