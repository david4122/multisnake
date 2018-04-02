package multisnake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	private static World world;

	@Override
	public void start(Stage stage) {
		stage.setTitle("Multisnake");
		world = new World(50, 50, 10);
		Snake s = new Snake(world, new Point(10, 10), 5, Direction.EAST);
		Snake s2 = new Snake(world, new Point(20, 10), 5, Direction.EAST);
		world.addSnake(s);
		world.addSnake(s2);

		Driver wsaddriver = new WSADDriver(s);
		wsaddriver.install(world);

		Driver arrowsDriver = new ArrowsDriver(s);
		Driver arrowsDriver2 = new ArrowsDriver(s2);
		arrowsDriver.install(world);
		arrowsDriver2.install(world);

		StackPane root = new StackPane(world);
		Scene sc = new Scene(root, 500, 500);
		stage.setScene(sc);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
