package multisnake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage stage;
	private static World world;

	@Override
	public void start(Stage stage) {
		Main.stage = stage;
		stage.setTitle("Multisnake");

		world = new MultiplayerGameBuilder().build(50, 50, 10);

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
