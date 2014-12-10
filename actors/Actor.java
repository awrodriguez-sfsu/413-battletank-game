package actors;

import enums.GameImageType;
import enums.GameObjectType;
import gamebase.GameBase;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import collision.GameObjectBounds;

public abstract class Actor extends GameObject {

	protected static Dimension dimension = GameBase.getDimension();

	protected double speedX = 0;
	protected double speedY = 0;

	protected double stopX = -100;
	protected double stopY = -100;

	protected double direction = 0;

	protected boolean isMovingForward = false;
	protected boolean isMovingBackward = false;
	protected boolean isRotatingLeft = false;
	protected boolean isRotatingRight = false;

	protected boolean canFire = true;
	protected boolean isAlive = true;
	protected boolean isExploding = false;

	protected ArrayList<Projectile> shots = new ArrayList<Projectile>();

	public Actor(GameImageType gameImage, GameObjectType type, double posX, double posY) {
		super(gameImage, type, posX, posY);
	}

	public boolean isMovingForward() {
		return isMovingForward && !isExploding;
	}

	public boolean isMovingBackward() {
		return isMovingBackward && !isExploding;
	}

	public boolean isRotatingLeft() {
		return isRotatingLeft && !isExploding;
	}

	public boolean isRotatingRight() {
		return isRotatingRight && !isExploding;
	}

	public boolean canFire() {
		return canFire && !isExploding;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isExploding() {
		return isExploding;
	}

	public double getDirection() {
		return direction;
	}

	public void setMovingForward(boolean isMovingForward) {
		this.isMovingForward = isMovingForward;
	}

	public void setMovingBackward(boolean isMovingBackward) {
		this.isMovingBackward = isMovingBackward;
	}

	public void setRotatingLeft(boolean isRotatingLeft) {
		this.isRotatingLeft = isRotatingLeft;
	}

	public void setRotatingRight(boolean isRotatingRight) {
		this.isRotatingRight = isRotatingRight;
	}

	public void setCanFire(boolean canFire) {
		this.canFire = canFire;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setExploding(boolean isExploding) {
		this.isExploding = isExploding;
	}

	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public ArrayList<Projectile> getShots() {
		return shots;
	}

	public void stop(double posX, double posY) {
		speedX = 0;
		speedY = 0;

		isMovingForward = false;
		isMovingBackward = false;

		stopX = posX;
		stopY = posY;
	}

	public boolean isColliding(GameObject object) {
		ArrayList<GameObjectBounds> thisBounds = getGameObjectBounds();
		ArrayList<GameObjectBounds> objectBounds = object.getGameObjectBounds();

		for (Iterator<GameObjectBounds> thisIterator = thisBounds.iterator(); thisIterator.hasNext();) {
			for (Iterator<GameObjectBounds> objectIterator = objectBounds.iterator(); objectIterator.hasNext();) {
				GameObjectBounds thisObjectBounds = (GameObjectBounds) objectIterator.next();
				GameObjectBounds objectObjectBounds = (GameObjectBounds) thisIterator.next();
				if (thisObjectBounds.intersects(objectObjectBounds)) {
					return true;
				}
			}
		}

		stopX = -100;
		stopY = -100;

		return false;
	}

	@Override
	public void updateCollision() {
		for (Iterator<GameObjectBounds> iterator = getGameObjectBounds().iterator(); iterator.hasNext();) {
			GameObjectBounds bounds = (GameObjectBounds) iterator.next();
			bounds.update(posX + GameBase.getGameScreenDifference().width, posY + GameBase.getGameScreenDifference().height);
		}
	}

	public abstract void moveForward();

	public abstract void moveBackward();

	public abstract void rotateLeft();

	public abstract void rotateRight();

	public abstract void moveForwardRotateLeft();

	public abstract void moveForwardRotateRight();

	public abstract void moveBackwardRotateLeft();

	public abstract void moveBackwardRotateRight();

	public abstract void fire();

	public abstract void explode();
}
