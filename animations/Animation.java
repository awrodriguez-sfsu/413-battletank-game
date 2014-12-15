package animations;

import java.awt.Image;
import java.util.ArrayList;

import actors.GameObject;

/**
 * @author Anthony Rodriguez
 *
 */
public class Animation {

	private ArrayList<AnimationFrame> frames;
	private int currentFrame;
	private long animationTime;
	private long totalDuration;

	private boolean once;

	public Animation() {
		frames = new ArrayList<AnimationFrame>();
		totalDuration = 0;
		this.once = false;

		synchronized (this) {
			animationTime = 0;
			currentFrame = 0;
		}
	}

	public Animation(boolean once) {
		frames = new ArrayList<AnimationFrame>();
		totalDuration = 0;
		this.once = once;

		synchronized (this) {
			animationTime = 0;
			currentFrame = 0;
		}
	}

	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimationFrame(image, totalDuration));
	}

	public synchronized void update(long elapsedTime, GameObject object) {
		if (once) {
			if (frames.size() > 1) {
				animationTime += elapsedTime;
				if (animationTime >= totalDuration) {
					// if (object.getType() == GameObjectType.ENEMY) {
					// ( (Enemy) object ).setAlive(false);
					// ( (Enemy) object ).checkDrop();
					// } else if (object.getType() == GameObjectType.PLAYER) {
					// ( (MainActor) object ).setAlive(false);
					// }
					frames.clear();
					animationTime = animationTime % totalDuration;
					currentFrame = 0;
				}

				while (!frames.isEmpty() && animationTime > getFrame(currentFrame).endTime) {
					currentFrame++;
				}
			}
		} else {
			if (frames.size() > 1) {
				animationTime += elapsedTime;
				if (animationTime >= totalDuration) {
					animationTime = animationTime % totalDuration;
					currentFrame = 0;
				}

				while (animationTime > getFrame(currentFrame).endTime) {
					currentFrame++;
				}
			}
		}
	}

	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

	private AnimationFrame getFrame(int i) {
		return (AnimationFrame) frames.get(i);
	}

	private class AnimationFrame {

		Image image;
		long endTime;

		public AnimationFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}
