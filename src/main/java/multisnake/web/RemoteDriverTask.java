package multisnake.web;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import multisnake.Direction;
import multisnake.Point;
import multisnake.Snake;
import multisnake.WorldView;
import multisnake.skin.BlueSkinGenerator;

public class RemoteDriverTask implements Runnable {
	private Socket client;
	private WorldView worldview;
	private ExecutorService executor;

	public RemoteDriverTask(ExecutorService exe, Socket soc, WorldView wview) {
		this.executor = exe;
		this.client = soc;
		this.worldview = wview;
	}

	@Override
	public void run() {
		Snake s = new Snake(worldview.getWorld(), new Point(10, 10), 10, Direction.SOUTH,
				new BlueSkinGenerator());
		WebDriver driver = new WebDriver(client);
		s.setDriver(driver);
		worldview.addSnake(s);
		executor.execute(driver);
	}
}
