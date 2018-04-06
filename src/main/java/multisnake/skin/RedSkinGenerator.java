package multisnake.skin;

import javafx.scene.paint.Color;

public class RedSkinGenerator extends BasicSkinGeneratorBase {

	@Override
	public Color next() {
		Color c = Color.rgb(level, 0, 0);
		updateLevel();
		return c;
	}
}
