package multisnake;

import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage stage;
	private static World world;
	private static List<Procedure>shutdownActions;

	@Override
	public void start(Stage stage) {
		Main.stage = stage;
		stage.setTitle("Multisnake");

		shutdownActions = new LinkedList<>();

		// world = new WebGameBuilder().build(50, 50, 10);
		// world = new ClassicGameBuilder().build(50, 50, 10);
		world = new MultisnakeGameBuilder().build(50, 50, 10);
		// world = new World(50, 50, 10);

		StackPane root = new StackPane(world);
		Scene sc = new Scene(root, 500, 500);
		stage.setScene(sc);
		stage.show();
	}

	@Override
	public void stop(){
		for(Procedure p: shutdownActions)
			p.execute();
	}

	public static void addShutdownAction(Procedure p){
		shutdownActions.add(p);
	}

	public void removeShutdownAction(Procedure p){
		shutdownActions.remove(p);
	}

	public static Stage getStage(){
		return Main.stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
