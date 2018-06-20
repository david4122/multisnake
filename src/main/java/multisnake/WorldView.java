package multisnake;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import multisnake.food.Food;

public class WorldView extends Canvas {
	class WorldAnimationTimer extends AnimationTimer {
		private volatile boolean paused;
		private volatile long pauseLength;

		private volatile long pauseStart;

		@Override
		public void handle(long currentTime) {
			long gameTime = currentTime - pauseLength;
			WorldView.this.getGraphicsContext2D().clearRect(0, 0, world.getWorldWidth()*fieldSize,
					world.getWorldHeight()*fieldSize);
			try {
				int totalLength = 0;
				for(Snake s: world.getSnakes()){
					totalLength+=s.size();
					if(!isPaused())
						s.update(gameTime);
					s.draw(getGraphicsContext2D(), fieldSize);
				}
				getGraphicsContext2D().fillText("Total length: " + totalLength, 20, 20);
				for(Food f: world.getFood()){
					if(f instanceof Animatable && !isPaused())
						((Animatable)f).update(gameTime);
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

		public synchronized void pause() {
			if(isPaused())
				return;
			this.pauseStart = System.nanoTime();
			this.paused = true;
		}

		public synchronized void resume() {
			if(!isPaused())
				return;
			this.paused = false;
			this.pauseLength+= System.nanoTime() - pauseStart;
		}

		public synchronized boolean isPaused() {
			return paused;
		}
	}

	private int fieldSize;
	private World world;
	private WorldAnimationTimer timer;

	public WorldView(World w, int fsize) {
		super(w.getWorldWidth()*fsize, w.getWorldHeight()*fsize);
		this.world = w;
		this.fieldSize = fsize;
		setFocusTraversable(true);

		addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
			switch(e.getCode()){
				case SPACE:
				case P:
					if(timer.isPaused())
						timer.resume();
					else
						timer.pause();
					break;
				default:
					//
			}
		});

		focusedProperty().addListener((ObservableValue<? extends Boolean>val,
					Boolean oldVal, Boolean newVal) -> {
			if(!newVal && !timer.isPaused())
				timer.pause();
		});

		this.timer = new WorldAnimationTimer();
		timer.start();
	}

	protected void drawBorder() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(5);
		gc.strokeRect(0, 0, world.getWorldWidth()*fieldSize, world.getWorldHeight()*fieldSize);
	}

	public World getWorld() {
		return world;
	}

	public void addSnake(Snake s) {
		s.getDriver().install(this);
		world.addSnake(s);
	}

	public void removeSnake(Snake s) {
		s.getDriver().uninstall(this);
		world.removeSnake(s);
	}
}
