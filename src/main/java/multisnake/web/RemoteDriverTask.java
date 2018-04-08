package multisnake.web;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import multisnake.Direction;
import multisnake.Point;
import multisnake.Snake;
import multisnake.World;
import multisnake.skin.BlueSkinGenerator;

public class RemoteDriverTask implements Runnable {
	private Socket client;
	private World world;
	private ExecutorService executor;

	public RemoteDriverTask(ExecutorService exe, Socket soc, World w) {
		this.executor = exe;
		this.client = soc;
		this.world = w;
	}

	@Override
	public void run() {
		Snake s = new Snake(world, new Point(10, 10), 10, Direction.SOUTH,
				new BlueSkinGenerator());
		WebDriver driver = new WebDriver(client);
		s.setDriver(driver);
		world.addSnake(s);
		executor.execute(driver);
	}
}
