package multisnake;

public interface Animatable extends Drawable {
	public void update(long time) throws GameOver;
}
