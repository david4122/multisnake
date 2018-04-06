package multisnake.skin;

import javafx.scene.paint.Color;

public abstract class BasicSkinGeneratorBase extends SkinGenerator {
	protected int level;
	protected int step;
	protected int upperBoundary;
	protected int lowerBoundary;

	protected BasicSkinGeneratorBase() {
		this(0, 20, 200, 50);
	}

	protected BasicSkinGeneratorBase(int init, int step, int upperBoundary, int lowerBoundary) {
		this.level = init;
		this.step = step;
		this.upperBoundary = upperBoundary;
		this.lowerBoundary = lowerBoundary;
	}

	protected void updateLevel() {
		level += step;
		if((step > 0 && level+step > upperBoundary) || (step < 0 && level+step < lowerBoundary))
			step = -step;
	}

	@Override
	public abstract Color next();
}
