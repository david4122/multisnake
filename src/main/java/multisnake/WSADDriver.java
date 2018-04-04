package multisnake;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class WSADDriver extends Driver implements EventHandler<KeyEvent>{
	private final Driveable subject;

	public WSADDriver(Driveable d) {
		this.subject = d;
	}

	@Override
	public void handle(KeyEvent event) {
		switch(event.getCode()){
			case W:
				subject.move(Direction.NORTH);
				break;
			case S:
				subject.move(Direction.SOUTH);
				break;
			case A:
				subject.move(Direction.WEST);
				break;
			case D:
				subject.move(Direction.EAST);
				break;
			default:
				//
		}
	}

	@Override
	public void install(World w) {
		w.addEventHandler(KeyEvent.KEY_PRESSED, this);
	}
}
