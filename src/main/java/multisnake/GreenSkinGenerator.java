package multisnake;

import javafx.scene.paint.Color;

public class GreenSkinGenerator extends BasicSkinGeneratorBase {

	@Override
	public Color next() {
		Color c = Color.rgb(0, level, 0);
		updateLevel();
		return c;
	}
}
