package multisnake.web;

import java.io.IOException;
import multisnake.GameBuilder;
import multisnake.Main;
import multisnake.World;
import multisnake.WorldView;

public class WebGameBuilder extends GameBuilder {

	@Override
	public WorldView build(int width, int height, int fsize) {
		WorldView wv;
		try {
			World w = new World(width, height);
			wv = new WorldView(w, fsize);
			WebServer server = new WebServer(wv);
			Thread serverThread = new Thread(server);
			serverThread.start();

			Main.addShutdownAction(() -> {
				server.shutdown();
			});
		} catch(IOException e){
			throw new RuntimeException(e);
		}
		return wv;
	}
}
