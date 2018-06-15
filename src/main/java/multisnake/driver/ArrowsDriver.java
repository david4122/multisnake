package multisnake.driver;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import multisnake.Direction;
import multisnake.WorldView;

public class ArrowsDriver extends Driver implements EventHandler<KeyEvent> {
	private Driveable subject;

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
			default:
				//
		}
	}

	@Override
	public void install(WorldView w) {
		w.addEventHandler(KeyEvent.KEY_PRESSED, this);
	}

	@Override
	public void setSubject(Driveable d) {
		this.subject = d;
	}

	@Override
	public void uninstall(WorldView w) {
		w.removeEventHandler(KeyEvent.KEY_PRESSED, this);
	}
}
