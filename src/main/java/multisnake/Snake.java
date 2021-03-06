package multisnake;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import multisnake.skin.SkinGenerator;
import multisnake.skin.DefaultSkinGenerator;
import multisnake.driver.Driveable;
import multisnake.driver.Driver;
import multisnake.food.Food;

public class Snake implements Animatable, Driveable {
	private class Segment implements Drawable {
		private Point loc;
		private Segment prev;
		private Color color;

		Segment(Point loc, Segment p, Color c) {
			this.loc = loc;
			this.prev = p;
			this.color = c;
		}

		@Override
		public void draw(GraphicsContext gc, int fsize) {
			gc.setFill(color);
			gc.fillRect(loc.x*fsize, loc.y*fsize, fsize, fsize);
		}
	}

	private Segment head;
	private Segment tail;
	private Point nextHeadPos;
	private int size = 1;
	private Direction dir;
	private SkinGenerator sg = new DefaultSkinGenerator();
	private World world;
	private Driver driver;
	private ConcurrentLinkedQueue<Effect>effects;

	private volatile long delay;
	private long lastUpdate;

	public Snake(World w, Point start, int initSize, Direction dir, SkinGenerator sg) {
		this.world = w;
		this.dir = dir;
		this.delay = TimeUnit.NANOSECONDS.convert(100, TimeUnit.MILLISECONDS);
		this.sg = sg;
		this.head = this.tail = new Segment(start, null, sg.next());
		for(int i=0;i<initSize;i++)
			addSegment();
		this.effects = new ConcurrentLinkedQueue<>();
	}

	public Snake(World w, Point start, int initSize, Direction dir) {
		this(w, start, initSize, dir, new DefaultSkinGenerator());
	}

	public synchronized boolean occupies(Point p) {
		for(Segment s=tail; s!=null; s=s.prev){
			if(s.loc.equals(p))
				return true;
		}
		return false;
	}

	@Override
	public synchronized void update(long time) throws GameOver {
		for(Effect e: effects){
			if(time - e.activeSince() > e.getDuration()){
				removeEffect(e);
			}
		}
		if(time - lastUpdate > delay){
			move(time);
			this.lastUpdate = time;
		}
	}

	@Override
	public void draw(GraphicsContext gc, int fsize) {
		for(Segment s=tail; s!=null; s=s.prev){
			s.draw(gc, fsize);
		}
	}

	private synchronized void move(long time) throws GameOver {
		Point nextLoc = getNextHeadPos();
		Object o;
		try {
			o = world.get(nextLoc);
		} catch(PointOutOfBoundariesException e){
			throw new GameOver("You've fallen off the edge of the world!'");
		}
		if(o instanceof Food)
			eat(((Food)o), time);
		else if(o instanceof Snake){
			if(o == this)
				throw new GameOver("You bit yourself!");
			else
				throw new GameOver("Someone got bitten");
		}
		for(Segment s=tail; s!=head; s=s.prev)
			s.loc = s.prev.loc;
		head.loc = nextLoc;
	}

	public synchronized void eat(Food f, long time) {
		f.eaten(this, time);
	}

	public synchronized void applyEffect(Effect e, long time) {
		e.apply(this, time);
		effects.add(e);
	}

	public synchronized void removeEffect(Effect e) {
		e.remove(this);
		effects.remove(e);
	}

	public synchronized void setNextHeadPos(Point p) {
		this.nextHeadPos = p;
	}

	public synchronized Point getNextHeadPos() {
		Point nextLoc;
		if(nextHeadPos != null) {
			nextLoc = nextHeadPos;
			nextHeadPos = null;
		} else
			nextLoc = head.loc.translate(dir, 1);
		return nextLoc;
	}

	public synchronized void addSegment() {
		tail = new Segment(tail.loc, tail, sg.next());
		this.size++;
	}

	public synchronized Direction getDirection() {
		return this.dir;
	}

	public synchronized int size() {
		return this.size;
	}

	public synchronized void setDelay(long delay) {
		this.delay = delay;
	}

	public synchronized long getDelay() {
		return this.delay;
	}

	public synchronized long lastUpdate() {
		return this.lastUpdate;
	}

	@Override
	public Driver getDriver(){
		return this.driver;
	}

	@Override
	public void setDriver(Driver d) {
		this.driver = d;
		d.setSubject(this);
	}

	@Override
	public synchronized void move(Direction d) {
		if(d.equals(dir.opposite()))
			return;
		this.dir = d;
	}
}
