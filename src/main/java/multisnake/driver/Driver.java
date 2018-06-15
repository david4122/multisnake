package multisnake.driver;

import multisnake.WorldView;

public abstract class Driver {
	public abstract void install(WorldView w);
	public abstract void uninstall(WorldView w);
	public abstract void setSubject(Driveable d);
}
