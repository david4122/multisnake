package multisnake.skin;

import javafx.scene.paint.Color;

public class GraySkinGenerator extends BasicSkinGeneratorBase {

	@Override
	public Color next() {
		Color c = Color.rgb(level, level, level);
		updateLevel();
		return c;
	}
}
