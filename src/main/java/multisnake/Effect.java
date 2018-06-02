package multisnake;

import java.util.function.Consumer;

public class Effect {
	private long duration;
	private long applied;
	private Consumer<Snake>effect;
	private Consumer<Snake>revert;

	public Effect(long duration, Consumer<Snake>effect, Consumer<Snake>revert) {
		this.duration = duration;
		this.effect = effect;
		this.revert = revert;
	}

	public void apply(Snake s, long time){
		effect.accept(s);
		this.applied = time;
	}

	public void remove(Snake s) {
		revert.accept(s);
	}

	public long activeSince() {
		return this.applied;
	}

	public long getDuration() {
		return duration;
	}

}
