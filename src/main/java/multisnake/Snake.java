package multisnake;

import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.GraphicsContext;

public class Snake implements Animatable, Driveable {
	private class Segment {
		private Point loc;
		private Segment prev;

		Segment(Point loc, Segment p) {
			this.loc = loc;
			this.prev = p;
		}
	}

	private Segment head;
	private Segment tail;
	private int size = 1;
	private Direction dir;
	private World world;

	private long delay;
	private long lastUpdate;

	public Snake(World w, Point start, int initSize, Direction dir) {
		this.world = w;
		this.dir = dir;
		this.delay = TimeUnit.NANOSECONDS.convert(100, TimeUnit.MILLISECONDS);
		this.head = this.tail = new Segment(start, null);
		for(int i=0;i<initSize;i++)
			addSegment();
	}

	public synchronized boolean occupies(Point p) {
		for(Segment s=tail; s!=null; s=s.prev){
			if(s.loc.equals(p))
				return true;
		}
		return false;
	}

	public synchronized void update(GraphicsContext gc, long time) throws GameOver {
		if(time - lastUpdate > delay){
			move();
			this.lastUpdate = time;
		}
		for(Segment s=tail; s!=null; s=s.prev)
			gc.fillRect(s.loc.x*world.getFieldSize(), s.loc.y*world.getFieldSize(), world.getFieldSize(), world.getFieldSize());
	}

	private synchronized void move() throws GameOver {
		Point nextLoc = head.loc.translate(dir, 1);
		Object o = world.get(nextLoc);
		if(o instanceof Food)
			eat(((Food)o));
		else if(o instanceof Snake){
			System.out.println("BIT");
			if(o == this)
				throw new GameOver("You bit yourself!");
			else
				throw new GameOver("Someone got bitten");
		}
		for(Segment s=tail; s!=head; s=s.prev)
			s.loc = s.prev.loc;
		head.loc = head.loc.translate(dir, 1);
	}

	public synchronized void eat(Food f) {
		addSegment();
		world.createFood();
	}

	private synchronized void addSegment() {
		tail = new Segment(tail.loc, tail);
		this.size++;
	}

	public synchronized void setDirection(Direction d) {
		this.dir = d;
	}

	public synchronized Direction getDirection() {
		return this.dir;
	}

	public synchronized int getSize() {
		return this.size;
	}

	public synchronized void setDelay(int delay) {
		this.delay = delay;
	}

	public synchronized long getDelay() {
		return this.delay;
	}

	public synchronized long lastUpdate() {
		return this.lastUpdate;
	}

	@Override
	public synchronized void move(Direction d) {
		this.dir = d;
	}
}
