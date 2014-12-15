package actors;

import enums.GameImageType;
import enums.GameObjectType;
import gamebase.GameBase;
import gamebase.Resources;

import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Rectangle;

public class MainActor extends Actor {

	protected int health = 8;
	protected int lives = 2;
	public int score = 0;

	private final double MOVEMENT_SPEED = 15;
	private int deflection = 3;

	private int viewWidth = ( dimension.width / 2 ) - 10 + 128;
	private int viewHeight = dimension.height;

	private Image healthBar = Resources.health.get(8 - health);

	public Rectangle view;

	private AudioClip shot = (AudioClip) Resources.sound_shot;

	public MainActor(GameImageType gameImage, GameObjectType type, double posX, double posY) {
		super(gameImage, type, posX, posY);
		view = new Rectangle(0, 0, (int) ( viewWidth / 1.5 ), (int) ( viewHeight / 1.5 ));
	}

	@Override
	public void update() {

		view.setLocation((int) posX + GameBase.getGameScreenDifference().width - ( viewWidth / 3 ), (int) posY + GameBase.getGameScreenDifference().height - ( viewHeight / 3 ));

		if (direction >= 360 || direction <= -360) {
			direction = 0;
		}

		if (isMovingForward || isMovingBackward) {
			posX += speedX;
			posY += speedY;
		}

		if (isRotatingLeft || isRotatingRight) {
			direction -= deflection;
		}

		// Left Edge of Map
		if (posX <= 32) {
			posX = 32;
		}

		// Top Edge of Map
		if (posY <= 32) {
			posY = 32;
		}

		// Right Edge of Map
		if (posX >= 1600 - 160) {
			posX = 1600 - 160;
		}

		// Bottom Edge of Map
		if (posY >= 1600 - 128) {
			posY = 1600 - 128;
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
		GameImageType tank = getGameImageType();
		GameImageType shell = null;

		if (tank == GameImageType.TANK_BLUE_BASIC || tank == GameImageType.TANK_RED_BASIC) {
			shell = GameImageType.SHELL_BASIC;
		} else if (tank == GameImageType.TANK_BLUE_HEAVY || tank == GameImageType.TANK_RED_HEAVY) {
			shell = GameImageType.SHELL_HEAVY;
		} else if (tank == GameImageType.TANK_BLUE_LIGHT || tank == GameImageType.TANK_RED_LIGHT) {
			shell = GameImageType.SHELL_LIGHT;
		}

		if (canFire()) {
			Projectile pShot = new Projectile(shell, tank, posX, posY, direction);
			shots.add(pShot);
			shot.play();
		}

		setCanFire(false);
	}

	public void adjustHealth(int amount) {
		this.health += amount;

		if (this.health == 0 && lives > 0) {
			lives--;
			this.health = 8;
		}

		healthBar = (Image) Resources.health.get(8 - this.health);

		if (this.health == 0) {
			explode();
		}
	}

	public int getLives() {
		return lives;
	}

	public Image getHealthBar() {
		return healthBar;
	}

	@Override
	public void explode() {
		isAlive = false;
		GameBase.reset();
	}

	public void reset(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.health = 7;
		adjustHealth(1);
		this.lives = 2;
	}
}
