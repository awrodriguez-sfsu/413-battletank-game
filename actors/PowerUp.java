package actors;

import enums.GameImageType;
import enums.GameObjectType;

public class PowerUp extends Actor {

	private long life = 2500;

	public PowerUp(GameImageType gameImage, double posX, double posY) {
		super(gameImage, GameObjectType.BACKGROUND, posX, posY);
	}

	@Override
	public void update() {
		life -= 16;
	}

	@Override
	public void moveForward() {}

	@Override
	public void moveBackward() {}

	@Override
	public void rotateLeft() {}

	@Override
	public void rotateRight() {}

	@Override
	public void moveForwardRotateLeft() {}

	@Override
	public void moveForwardRotateRight() {}

	@Override
	public void moveBackwardRotateLeft() {}

	@Override
	public void moveBackwardRotateRight() {}

	@Override
	public void fire() {}

	@Override
	public void explode() {}

	@Override
	public boolean isVisible() {
		return life > 0;
	}

	@Override
	public boolean inPlay() {
		return isVisible();
	}

	public void upgrade(MainActor actor) {
		GameImageType thisType = getGameImageType();
		if (thisType == GameImageType.LIFE_PICKUP) {
			actor.lives++;
		} else if (thisType == GameImageType.BASIC_UPGRADE) {
			if (actor.getGameObjectType() == GameObjectType.PLAYER1) {
				actor.setGameImageType(GameImageType.TANK_BLUE_BASIC);
			} else {
				actor.setGameImageType(GameImageType.TANK_RED_BASIC);
			}
		} else if (thisType == GameImageType.HEAVY_UPGRADE) {
			if (actor.getGameObjectType() == GameObjectType.PLAYER1) {
				actor.setGameImageType(GameImageType.TANK_BLUE_HEAVY);
			} else {
				actor.setGameImageType(GameImageType.TANK_RED_HEAVY);
			}
		} else if (thisType == GameImageType.LIGHT_UPGRADE) {
			if (actor.getGameObjectType() == GameObjectType.PLAYER1) {
				actor.setGameImageType(GameImageType.TANK_BLUE_LIGHT);
			} else {
				actor.setGameImageType(GameImageType.TANK_RED_LIGHT);
			}
		}
	}

}
