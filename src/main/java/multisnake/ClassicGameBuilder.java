package multisnake;

import multisnake.driver.ArrowsDriver;
import multisnake.skin.GraySkinGenerator;

public class ClassicGameBuilder extends GameBuilder {

	@Override
	public World build(int width, int height, int fsize) {
		World w = new World(width, height, fsize);
		Snake s = new Snake(w, new Point(0, 0), 5, Direction.SOUTH, new GraySkinGenerator());
		w.addSnake(s);
		ArrowsDriver driver = new ArrowsDriver(s);
		driver.install(w);
		return w;
	}
}
