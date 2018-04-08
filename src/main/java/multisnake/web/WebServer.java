package multisnake.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import multisnake.World;

public class WebServer implements Runnable {
	private ExecutorService executor;
	private ServerSocket ssocket;
	private World world;

	private static final int PORT = 8866;

	public WebServer(World w) throws IOException {
		this.executor = Executors.newCachedThreadPool();
		this.ssocket = new ServerSocket(PORT);
		this.world = w;
	}

	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()){
				Socket client = ssocket.accept();
				executor.execute(new RemoteDriverTask(executor, client, world));
			}
		} catch(IOException e){
			// Shutting down server
		}
	}

	public void shutdown() {
		try {
			ssocket.close();
			executor.shutdown();
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}
}
