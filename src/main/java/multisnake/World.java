package multisnake;

import java.util.concurrent.ConcurrentLinkedQueue;

import multisnake.food.Food;
import multisnake.food.FoodFactory;

public class World {

	private ConcurrentLinkedQueue<Snake>snakes = new ConcurrentLinkedQueue<>();
	private FoodFactory foodFactory = new FoodFactory(this);
	private ConcurrentLinkedQueue<Food>food;
	private int fieldSize;
	private final int worldWidth;
	private final int worldHeight;

	public World(int width, int height) {
		this.worldWidth = width;
		this.worldHeight = height;

		food = new ConcurrentLinkedQueue<>();
		for(int i=0; i<10; i++)
			createFood();
	}

	public World(int width, int height, FoodFactory ff) {
		this(width, height);
		this.foodFactory = ff;
	}

	public synchronized Object get(Point p) throws PointOutOfBoundariesException {
		if(p.x >= worldWidth || p.x < 0 || p.y >= worldHeight || p.y < 0)
			throw new PointOutOfBoundariesException();
		for(Snake s: snakes){
			if(s.occupies(p))
				return s;
		}
		for(Food f: food){
			if(f.getLocation().equals(p))
				return f;
		}
		return null;
	}

	public synchronized void createFood() {
		food.add(foodFactory.next());
	}

	public synchronized void foodEaten(Food f) {
		this.food.remove(f);
		createFood();
	}

	public synchronized int getFieldSize() {
		return this.fieldSize;
	}

	public synchronized void setFieldSize(int fsize) {
		this.fieldSize = fsize;
	}

	void addSnake(Snake s) {
		snakes.add(s);
	}

	public ConcurrentLinkedQueue<Snake>getSnakes() {
		return this.snakes;
	}

	void removeSnake(Snake s) {
		snakes.remove(s);
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public ConcurrentLinkedQueue<Food>getFood() {
		return this.food;
	}
}
