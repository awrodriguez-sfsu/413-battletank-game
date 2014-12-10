package actors;

import enums.GameImageType;
import enums.GameObjectType;
import gamebase.Resources;

public class Projectile extends Actor {

	private final int MOVEMENT_SPEED = 25;
	private int deflection = 3;

	public Projectile(GameImageType gameImage, GameImageType shooter, double posX, double posY, double direction) {
		super(gameImage, GameObjectType.PROJECTILE, getX(posX, shooter, gameImage, direction), getY(posY, shooter, gameImage, direction));
		this.direction = direction;

		moveForward();
	}

	private static double getX(double posX, GameImageType shooter, GameImageType gameImage, double direction) {
		return posX + Resources.getSolidSpec(shooter.getResourceName()).centerX + 40 * Math.cos(Math.toRadians(-direction));
	}

	private static double getY(double posY, GameImageType shooter, GameImageType gameImage, double direction) {
		return posY + Resources.getSolidSpec(shooter.getResourceName()).centerY + 40 * Math.sin(Math.toRadians(-direction));
	}

	@Override
	public void update() {
		if (direction >= 360 || direction <= -360) {
			direction = 0;
		}

		if (isMovingForward || isMovingBackward) {
			posX += speedX;
			posY += speedY;
		}

		if (isRotatingLeft || isRotatingRight) {
			direction += deflection;
		}
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public boolean inPlay() {
		return true;
	}

	@Override
	public void moveForward() {
		speedX = ( MOVEMENT_SPEED * ( Math.cos(Math.toRadians(-direction)) ) );
		speedY = ( MOVEMENT_SPEED * ( Math.sin(Math.toRadians(-direction)) ) );

		isMovingForward = true;
		isMovingBackward = false;
	}

	@Override
	public void moveBackward() {
		speedX = -( MOVEMENT_SPEED * ( Math.cos(Math.toRadians(-direction)) ) );
		speedY = -( MOVEMENT_SPEED * ( Math.sin(Math.toRadians(-direction)) ) );

		isMovingForward = false;
		isMovingBackward = true;
	}

	@Override
	public void rotateLeft() {
		deflection = -Math.abs(deflection);

		isRotatingLeft = true;
		isRotatingRight = false;
	}

	@Override
	public void rotateRight() {
		deflection = Math.abs(deflection);

		isRotatingLeft = false;
		isRotatingRight = true;
	}

	@Override
	public void moveForwardRotateLeft() {
		rotateLeft();
		moveForward();
	}

	@Override
	public void moveForwardRotateRight() {
		rotateRight();
		moveForward();
	}

	@Override
	public void moveBackwardRotateLeft() {
		rotateLeft();
		moveBackward();
	}

	@Override
	public void moveBackwardRotateRight() {
		rotateRight();
		moveBackward();
	}

	@Override
	public void fire() {
		if (canFire()) {
			Projectile pShot = new Projectile(GameImageType.SHELL_HEAVY, GameImageType.TANK_BLUE_HEAVY, posX, posY, direction);
			shots.add(pShot);
		}
	}

	@Override
	public void explode() {
		System.out.println("explode");
	}
}
