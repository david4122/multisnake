package multisnake;

import multisnake.driver.ArrowsDriver;
import multisnake.skin.BlueSkinGenerator;
import multisnake.skin.GraySkinGenerator;
import multisnake.skin.GreenSkinGenerator;
import multisnake.skin.RedSkinGenerator;

public class MultisnakeGameBuilder extends GameBuilder {

	@Override
	public World build(int width, int height, int fsize) {
		World w = new World(width, height, fsize);
		Snake s1 = new Snake(w, new Point(10, 10), 5, Direction.SOUTH,
				new GraySkinGenerator());
		Snake s2 = new Snake(w, new Point(10, 30), 5, Direction.SOUTH,
				new BlueSkinGenerator());
		Snake s4 = new Snake(w, new Point(30, 10), 5, Direction.SOUTH,
				new GreenSkinGenerator());
		Snake s3 = new Snake(w, new Point(30, 30), 5, Direction.SOUTH,
				new RedSkinGenerator());

		w.addSnake(s1);
		w.addSnake(s2);
		w.addSnake(s3);
		w.addSnake(s4);

		ArrowsDriver d1 = new ArrowsDriver(s1);
		d1.install(w);
		ArrowsDriver d2 = new ArrowsDriver(s2);
		d2.install(w);
		ArrowsDriver d3 = new ArrowsDriver(s3);
		d3.install(w);
		ArrowsDriver d4 = new ArrowsDriver(s4);
		d4.install(w);

		return w;
	}
}
