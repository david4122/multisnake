package multisnake.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import multisnake.Direction;
import multisnake.Snake;
import multisnake.WorldView;
import multisnake.driver.Driveable;
import multisnake.driver.Driver;

public class WebDriver extends Driver implements Runnable {
	private Socket remote;
	private Driveable subject;
	private WorldView world;

	public WebDriver(Socket remote) {
		this.remote = remote;
	}

	@Override
	public void install(WorldView w) {
		this.world = w;
	}

	@Override
	public void uninstall(WorldView w) {
		//
	}

	@Override
	public void run() {
		try (Socket s = remote;
				BufferedReader in = new BufferedReader(
					new InputStreamReader(s.getInputStream()))
				){
			String dir;
			while(!Thread.currentThread().isInterrupted() &&
					(dir = in.readLine()) != null){
				subject.move(Direction.valueOf(dir));
			}
			world.removeSnake((Snake)subject);
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setSubject(Driveable d){
		this.subject = d;
	}
}
