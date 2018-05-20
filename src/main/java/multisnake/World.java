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
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import multisnake.food.Food;
import multisnake.food.FoodFactory;

public class World extends Canvas {
	class WorldAnimationTimer extends AnimationTimer {
		private boolean paused;

		@Override
		public void handle(long currentTime){
			getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
			try {
				int totalLength = 0;
				for(Snake s: snakes){
					totalLength+=s.size();
					if(!paused)
						s.update(currentTime);
					s.draw(getGraphicsContext2D(), fieldSize);
				}
				getGraphicsContext2D().fillText("Total length: " + totalLength, 20, 20);
				for(Food f: food){
					if(f instanceof Animatable && !paused)
						((Animatable)f).update(currentTime);
					f.draw(getGraphicsContext2D(), fieldSize);
				}
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
	private LinkedList<Food>food;
	private int fieldSize;
	private final int worldWidth;
	private final int worldHeight;

	private WorldAnimationTimer timer;

	public World(int width, int height, int fsize) {
		super(width*fsize, height*fsize);
		this.worldWidth = width;
		this.worldHeight = height;
		this.fieldSize = fsize;

		food = new LinkedList<>();
		for(int i=0; i<10; i++)
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

		focusedProperty().addListener((ObservableValue<? extends Boolean>val,
					Boolean oldVal, Boolean newVal) -> {
			if(!newVal && !timer.isPaused())
				timer.pause();
		});

		this.timer = new WorldAnimationTimer();
		timer.start();

		setFocusTraversable(true);
	}

	public World(int width, int height, int fsize, FoodFactory ff) {
		this(width, height, fsize);
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
		s.getDriver().install(this);
		snakes.add(s);
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
