package multisnake;

public enum Direction {
	NORTH, SOUTH, WEST, EAST;

	public Direction opposite() {
		switch(this) {
			case NORTH:
				return SOUTH;
			case SOUTH:
				return NORTH;
			case WEST:
				return EAST;
			case EAST:
				return WEST;
			default:
				return null;
		}
	}
}
