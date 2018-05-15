package multisnake;

public class Point {
	public final int x;
	public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point translate(Direction d, int distance) {
		switch(d){
			case NORTH:
				return new Point(x, y-distance);
			case SOUTH:
				return new Point(x, y+distance);
			case WEST:
				return new Point(x-distance, y);
			case EAST:
				return new Point(x+distance, y);
			default:
				throw new RuntimeException("Unsopported direction");
		}
	}

	@Override
	public boolean equals(Object o){
		return o instanceof Point && ((Point)o).x == x && ((Point)o).y == y;
	}
}
