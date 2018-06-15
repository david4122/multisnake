package multisnake;

import multisnake.driver.ArrowsDriver;
import multisnake.skin.BlueSkinGenerator;
import multisnake.skin.GraySkinGenerator;
import multisnake.skin.GreenSkinGenerator;
import multisnake.skin.RedSkinGenerator;

public class MultisnakeGameBuilder extends GameBuilder {

	@Override
	public WorldView build(int width, int height, int fsize) {
		World w = new World(width, height);
		WorldView wv = new WorldView(w, fsize);
		Snake s1 = new Snake(w, new Point(10, 10), 5, Direction.SOUTH,
				new GraySkinGenerator());
		ArrowsDriver d1 = new ArrowsDriver();
		s1.setDriver(d1);
		Snake s2 = new Snake(w, new Point(10, 30), 5, Direction.SOUTH,
				new BlueSkinGenerator());
		ArrowsDriver d2 = new ArrowsDriver();
		s2.setDriver(d2);
		Snake s3 = new Snake(w, new Point(30, 30), 5, Direction.SOUTH,
				new RedSkinGenerator());
		ArrowsDriver d3 = new ArrowsDriver();
		s3.setDriver(d3);
		Snake s4 = new Snake(w, new Point(30, 10), 5, Direction.SOUTH,
				new GreenSkinGenerator());
		ArrowsDriver d4 = new ArrowsDriver();
		s4.setDriver(d4);

		wv.addSnake(s1);
		wv.addSnake(s2);
		wv.addSnake(s3);
		wv.addSnake(s4);

		return wv;
	}
}
