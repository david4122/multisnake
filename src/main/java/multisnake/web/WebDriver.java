package multisnake.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import multisnake.Direction;
import multisnake.World;
import multisnake.driver.Driveable;
import multisnake.driver.Driver;

public class WebDriver extends Driver implements Runnable {
	private Socket remote;
	private Driveable subject;

	public WebDriver(Socket remote) {
		this.remote = remote;
	}

	@Override
	public void install(World w) {
		//
	}

	@Override
	public void run() {
		try (Socket s = remote;
				BufferedReader in = new BufferedReader(
					new InputStreamReader(s.getInputStream()))
				){
			while(!Thread.currentThread().isInterrupted()){
				String dir = in.readLine();
				subject.move(Direction.valueOf(dir));
			}
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setSubject(Driveable d){
		this.subject = d;
	}
}
