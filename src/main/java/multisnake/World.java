package multisnake;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

import java.util.concurrent.ConcurrentLinkedQueue;

public class World extends Canvas {
	private ConcurrentLinkedQueue<Snake>snakes;
	private FoodFactory foodFactory;
	private Food food;
	private int fieldSize;
	private int worldWidth;
	private int worldHeight;

	private AnimationTimer timer;

	public World(int width, int height, int fsize, FoodFactory ff) {
		super(width*fsize, height*fsize);
		this.snakes = new ConcurrentLinkedQueue<>();
		this.worldWidth = width;
		this.worldHeight = height;
		this.fieldSize = fsize;
		this.foodFactory = ff;

		this.timer = new AnimationTimer(){
			@Override
			public void handle(long currentTime){
				getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
				try {
					for(Snake s: snakes)
						s.update(getGraphicsContext2D(), currentTime);
					food.update(getGraphicsContext2D(), fieldSize);
				} catch(GameOver e){
					this.stop();
					Alert alert = new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK);
					alert.setOnHidden(evt -> Platform.exit());
					alert.show();
				}
			}
		};
		timer.start();

		setFocusTraversable(true);
		createFood();
	}

	public World(int width, int height, int fsize) {
		this(width, height, fsize, new FoodFactory());
	}

	public synchronized Object get(Point p) throws GameOver {
		if(p.x > worldWidth || p.x < 0 || p.y > worldHeight || p.y < 0)
			throw new GameOver("You've fallen off the world!");
		if(food.getLocation().equals(p))
			return food;
		for(Snake s: snakes){
			if(s.occupies(p))
				return s;
		}
		return null;
	}

	public synchronized void createFood() {
		food = foodFactory.next();
	}

	public synchronized int getFieldSize() {
		return this.fieldSize;
	}

	public synchronized void setFieldSize(int fsize) {
		this.fieldSize = fsize;
	}

	public void addSnake(Snake s) {
		snakes.add(s);
	}

	public void removeSnake(Snake s) {
		snakes.remove(s);
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}
}
