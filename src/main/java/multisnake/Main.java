package multisnake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import multisnake.skin.*;
import multisnake.driver.*;

public class Main extends Application {
	private static Stage stage;
	private static World world;

	@Override
	public void start(Stage stage) {
		Main.stage = stage;
		stage.setTitle("Multisnake");
		world = new World(50, 50, 10);
		Snake s = new Snake(world, new Point(10, 10), 5, Direction.EAST, new RedSkinGenerator());
		Snake s2 = new Snake(world, new Point(30, 10), 5, Direction.EAST, new BlueSkinGenerator());
		Snake s3 = new Snake(world, new Point(10, 30), 5, Direction.EAST, new GreenSkinGenerator());
		Snake s4 = new Snake(world, new Point(30, 30), 5, Direction.EAST, new GraySkinGenerator());
		world.addSnake(s);
		world.addSnake(s2);
		world.addSnake(s3);
		world.addSnake(s4);

		Driver wsaddriver = new WSADDriver(s);
		wsaddriver.install(world);

		Driver arrowsDriver = new ArrowsDriver(s);
		Driver arrowsDriver2 = new ArrowsDriver(s2);
		Driver arrowsDriver3 = new ArrowsDriver(s3);
		Driver arrowsDriver4 = new ArrowsDriver(s4);
		arrowsDriver.install(world);
		arrowsDriver2.install(world);
		arrowsDriver3.install(world);
		arrowsDriver4.install(world);

		StackPane root = new StackPane(world);
		Scene sc = new Scene(root, 500, 500);
		stage.setScene(sc);
		stage.show();
	}

	public static Stage getStage(){
		return Main.stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
