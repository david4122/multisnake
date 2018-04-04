package multisnake;

import javafx.scene.paint.Color;

public class BlueSkinGenerator extends BasicSkinGeneratorBase {

	@Override
	public Color next() {
		Color c = Color.rgb(0, 0, level);
		updateLevel();
		return c;
	}
}
