package actors;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;

import collision.GameObjectBounds;
import enums.CollisionShape;
import enums.GameImageType;
import enums.GameObjectType;
import gamebase.GameBase;
import gamebase.Resources;
import gamebase.Resources.SolidObjectSpec;

public abstract class GameObject {

	protected double centerX, centerY, top, bottom, left, right, front;

	protected double posX, posY;

	private boolean isSolid;

	private Image image;

	private GameObjectType gameObjectType;

	private GameImageType gameImageType;

	private SolidObjectSpec spec;

	private ArrayList<GameObjectBounds> bounds;

	public GameObject(GameImageType gameImage, GameObjectType type, double posX, double posY) {
		this.gameObjectType = type;
		this.gameImageType = gameImage;
		this.image = Resources.getImage(gameImage.getResourceName());
		this.isSolid = gameImage.isSolid();
		this.posX = posX;
		this.posY = posY;

		if (isSolid) {
			bounds = new ArrayList<GameObjectBounds>();
			spec = Resources.getSolidSpec(gameImage.getResourceName());
			for (int i = 0; i < spec.shapes.size(); i++) {
				if (spec.shapes.get(i).type.equals("rectangle")) {
					bounds.add(new GameObjectBounds(CollisionShape.RECTANGLE, spec.shapes.get(i).x, spec.shapes.get(i).y, spec.shapes.get(i).width, spec.shapes.get(i).height));
				} else {
					bounds.add(new GameObjectBounds(CollisionShape.CIRCLE, spec.shapes.get(i).x, spec.shapes.get(i).y, spec.shapes.get(i).width, spec.shapes.get(i).height));
				}
			}

			this.centerX = spec.centerX;
			this.centerY = spec.centerY;
			this.top = spec.top;
			this.bottom = spec.bottom;
			this.left = spec.left;
			this.right = spec.right;
			this.front = spec.front;

			this.posX -= centerX;
			this.posY -= centerY;
		}
	}

	public void draw(Graphics2D graphics2d, ImageObserver observer) {
		if (gameObjectType == GameObjectType.BACKGROUND) {
			if (isVisible()) {
				graphics2d.drawImage(image, (int) posX + GameBase.getGameScreenDifference().width, (int) posY + GameBase.getGameScreenDifference().height, observer);
			}
		} else {
			graphics2d.drawImage(image, (int) posX + GameBase.getGameScreenDifference().width, (int) posY + GameBase.getGameScreenDifference().height, observer);
		}

		if (isSolid() && true) {
			for (Iterator<GameObjectBounds> iterator = bounds.iterator(); iterator.hasNext();) {
				GameObjectBounds gameObjectBounds = (GameObjectBounds) iterator.next();
				if (gameObjectBounds.getShape() == CollisionShape.RECTANGLE) {
					graphics2d.drawRect((int) gameObjectBounds.getX(), (int) gameObjectBounds.getY(), (int) gameObjectBounds.getWidth(), (int) gameObjectBounds.getHeight());
				} else {
					graphics2d.drawOval((int) gameObjectBounds.getX(), (int) gameObjectBounds.getY(), (int) gameObjectBounds.getWidth(), (int) gameObjectBounds.getHeight());
				}
			}
		}

		updateCollision();
	}

	public void draw(Graphics2D graphics2d, AffineTransform transform, ImageObserver observer) {
		graphics2d.drawImage(image, transform, observer);

		if (isSolid() && true) {
			for (Iterator<GameObjectBounds> iterator = bounds.iterator(); iterator.hasNext();) {
				GameObjectBounds gameObjectBounds = (GameObjectBounds) iterator.next();
				if (gameObjectBounds.getShape() == CollisionShape.RECTANGLE) {
					graphics2d.drawRect((int) gameObjectBounds.getX(), (int) gameObjectBounds.getY(), (int) gameObjectBounds.getWidth(), (int) gameObjectBounds.getHeight());
				} else {
					graphics2d.drawOval((int) gameObjectBounds.getX(), (int) gameObjectBounds.getY(), (int) gameObjectBounds.getWidth(), (int) gameObjectBounds.getHeight());
				}
			}
		}

		updateCollision();
	}

	public GameObjectType getGameObjectType() {
		return gameObjectType;
	}

	public GameImageType getGameImageType() {
		return gameImageType;
	}

	public boolean isSolid() {
		return isSolid;
	}

	public ArrayList<GameObjectBounds> getGameObjectBounds() {
		return bounds;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	public abstract void update();

	public abstract void updateCollision();

	public abstract boolean isVisible();

	public abstract boolean inPlay();
}
