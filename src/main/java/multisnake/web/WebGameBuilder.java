package multisnake.web;

import java.io.IOException;
import multisnake.GameBuilder;
import multisnake.Main;
import multisnake.World;

public class WebGameBuilder extends GameBuilder {

	@Override
	public World build(int width, int height, int fsize) {
		World w;
		try {
			w = new World(width, height, fsize);
			WebServer server = new WebServer(w);
			Thread serverThread = new Thread(server);
			serverThread.start();

			Main.addShutdownAction(() -> {
				server.shutdown();
			});
		} catch(IOException e){
			throw new RuntimeException(e);
		}
		return w;
	}
}
