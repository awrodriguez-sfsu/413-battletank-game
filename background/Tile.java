package background;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import actors.GameObject;
import collision.GameObjectBounds;
import enums.GameImageType;
import enums.GameObjectType;
import enums.TileType;
import gamebase.GameBase;
import gamebase.Resources;

public class Tile extends GameObject {

	public Image tileImage;

	public Tile(TileType type, double posX, double posY) {
		super(GameImageType.getType(type.getResourceName()), GameObjectType.BACKGROUND, posX, posY);
		this.tileImage = Resources.getImage(type.getResourceName());
	}

	@Override
	public void update() {}

	@Override
	public void updateCollision() {

		for (Iterator<GameObjectBounds> iterator = getGameObjectBounds().iterator(); iterator.hasNext();) {
			GameObjectBounds bounds = (GameObjectBounds) iterator.next();
			bounds.update(posX + GameBase.getGameScreenDifference().width, posY + GameBase.getGameScreenDifference().height);
		}
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

		return false;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public boolean inPlay() {
		return true;
	}
}
