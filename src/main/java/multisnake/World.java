package multisnake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import java.util.concurrent.ConcurrentLinkedQueue;

public class World extends Canvas {
	class WorldAnimationTimer extends AnimationTimer {
		private boolean paused;

		@Override
		public void handle(long currentTime){
			getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
			try {
				for(Snake s: snakes){
					if(!paused)
						s.update(currentTime);
					s.draw(getGraphicsContext2D());
				}
				food.update(getGraphicsContext2D(), fieldSize);
				drawBorder();
			} catch(GameOver e){
				this.pause();
				Alert alert = new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK);
				alert.setOnHidden(evt -> Platform.exit());
				alert.setHeaderText("");
				alert.initOwner(Main.getStage());
				alert.show();
			}
		}

		public void pause() {
			this.paused = true;
		}

		public void resume() {
			this.paused = false;
		}

		public boolean isPaused() {
			return paused;
		}
	}

	private ConcurrentLinkedQueue<Snake>snakes = new ConcurrentLinkedQueue<>();
	private FoodFactory foodFactory = new FoodFactory(this);
	private Food food;
	private int fieldSize;
	private final int worldWidth;
	private final int worldHeight;

	private WorldAnimationTimer timer;

	public World(int width, int height, int fsize) {
		super(width*fsize, height*fsize);
		this.worldWidth = width;
		this.worldHeight = height;
		this.fieldSize = fsize;

		createFood();

		addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e) {
				switch(e.getCode()){
					case SPACE:
					case P:
						if(World.this.timer.isPaused())
							World.this.timer.resume();
						else
							World.this.timer.pause();
						break;
					default:
						//
				}
			}
		});

		this.timer = new WorldAnimationTimer();
		timer.start();

		setFocusTraversable(true);
	}

	public World(int width, int height, int fsize, FoodFactory ff) {
		this(width, height, fsize);
		this.foodFactory = ff;
	}

	public synchronized Object get(Point p) {
		if(p.x >= worldWidth || p.x < 0 || p.y >= worldHeight || p.y < 0)
			throw new PointOutOfBoundariesException();
		if(food !=null && food.getLocation().equals(p))
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

	void drawBorder() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(5);
		gc.strokeRect(0, 0, worldWidth*fieldSize, worldHeight*fieldSize);
	}

	public synchronized int getFieldSize() {
		return this.fieldSize;
	}

	public synchronized void setFieldSize(int fsize) {
		this.fieldSize = fsize;
	}

	public void addSnake(Snake s) {
		snakes.add(s);
		s.getDriver().install(this);
	}

	public void removeSnake(Snake s) {
		s.getDriver().uninstall(this);
		snakes.remove(s);
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}
}
