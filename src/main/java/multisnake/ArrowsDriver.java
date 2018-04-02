package multisnake;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class ArrowsDriver extends Driver implements EventHandler<KeyEvent> {
	private Driveable subject;

	public ArrowsDriver(Driveable d){
		this.subject = d;
	}

	@Override
	public void handle(KeyEvent event) {
		switch(event.getCode()){
			case UP:
				subject.move(Direction.NORTH);
				break;
			case DOWN:
				subject.move(Direction.SOUTH);
				break;
			case LEFT:
				subject.move(Direction.WEST);
				break;
			case RIGHT:
				subject.move(Direction.EAST);
				break;
		}
	}

	@Override
	public void install(World w) {
		w.addEventHandler(KeyEvent.KEY_PRESSED, this);
	}

}
