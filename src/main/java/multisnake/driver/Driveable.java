package multisnake.driver;

import multisnake.Direction;

public interface Driveable {
	public void setDriver(Driver d);
	public Driver getDriver();
	public void move(Direction d);
}
