package multisnake.food;

import java.util.concurrent.TimeUnit;

import javafx.scene.paint.Color;

import multisnake.Point;
import multisnake.Snake;
import multisnake.World;

public class SpeedUpFood extends Food {
	public SpeedUpFood(World w, Point start) {
		super(w, start, Color.rgb(175, 95, 0));
	}

	@Override
	public void applyEffect(Snake s) {
		long currentDelay = s.getDelay();
		s.setDelay(currentDelay*4/5);
		new Thread(){
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(15);
					s.setDelay(currentDelay);
				} catch (InterruptedException e) {
					//
				}
			}
		}.start();
	}
}
