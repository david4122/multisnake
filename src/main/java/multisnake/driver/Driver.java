package multisnake.driver;

import multisnake.World;

public abstract class Driver {
	public abstract void install(World w);
	public abstract void setSubject(Driveable d);
}
