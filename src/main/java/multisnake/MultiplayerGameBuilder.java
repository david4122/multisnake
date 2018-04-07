package multisnake;

import multisnake.driver.ArrowsDriver;
import multisnake.driver.WSADDriver;
import multisnake.skin.BlueSkinGenerator;
import multisnake.skin.RedSkinGenerator;

public class MultiplayerGameBuilder extends GameBuilder {

	@Override
	public World build(int width, int height, int fsize) {
		World w = new World(width, height, fsize);

		Snake s1 = new Snake(w, new Point(0, 0), 5, Direction.EAST, new BlueSkinGenerator());
		Snake s2 = new Snake(w, new Point(width-1, height-1), 5, Direction.WEST, new RedSkinGenerator());

		w.addSnake(s1);
		w.addSnake(s2);
		WSADDriver d1 = new WSADDriver(s1);
		d1.install(w);
		ArrowsDriver d2 = new ArrowsDriver(s2);
		d2.install(w);

		return w;
	}
}
