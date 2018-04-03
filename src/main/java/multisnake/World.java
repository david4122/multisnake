package multisnake;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import java.util.concurrent.ConcurrentLinkedQueue;

public class World extends Canvas {
	class WorldAnimationTimer extends AnimationTimer {
		private boolean stopped;

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
				alert.setHeaderText("");
				alert.initOwner(Main.getStage());
				alert.show();
			}
		}

		@Override
		public void stop(){
			super.stop();
			this.stopped = true;
		}

		@Override
		public void start(){
			super.start();
			this.stopped = false;
		}

		public boolean isStopped(){
			return stopped;
		}
	}

	private ConcurrentLinkedQueue<Snake>snakes;
	private FoodFactory foodFactory;
	private Food food;
	private int fieldSize;
	private final int worldWidth;
	private final int worldHeight;

	private WorldAnimationTimer timer;

	public World(int width, int height, int fsize, FoodFactory ff) {
		super(width*fsize, height*fsize);
		this.snakes = new ConcurrentLinkedQueue<>();
		this.worldWidth = width;
		this.worldHeight = height;
		this.fieldSize = fsize;
		this.foodFactory = ff;

		addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e){
				switch(e.getCode()){
					case P:
						if(World.this.timer.isStopped())
							World.this.timer.start();
						else
							World.this.timer.stop();
						break;
				}
			}
		});

		this.timer = new WorldAnimationTimer();
		timer.start();

		setFocusTraversable(true);
		createFood();
	}

	public World(int width, int height, int fsize) {
		this(width, height, fsize, new FoodFactory());
	}

	public synchronized Object get(Point p) throws PointOutOfBoundariesException {
		if(p.x > worldWidth || p.x < 0 || p.y > worldHeight || p.y < 0)
			throw new PointOutOfBoundariesException();
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
