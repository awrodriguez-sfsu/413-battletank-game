package background;

import java.awt.Image;
import java.util.Iterator;

import actors.GameObject;
import collision.GameObjectBounds;
import enums.GameImageType;
import enums.GameObjectType;
import enums.TileType;
import gamebase.GameBase;
import gamebase.Resources;

public class Tile extends GameObject {

	private TileType type;

	public Image tileImage;

	private int side;

	public Tile(TileType type, double posX, double posY) {
		super(GameImageType.getType(type.getResourceName()), GameObjectType.BACKGROUND, posX, posY);
		this.type = type;
		this.tileImage = Resources.getImage(type.getResourceName());
	}

	@Override
	public void update() {

		for (Iterator<GameObjectBounds> iterator = getGameObjectBounds().iterator(); iterator.hasNext();) {
			GameObjectBounds bounds = (GameObjectBounds) iterator.next();
			bounds.update(posX + GameBase.getGameScreenDifference().width, posY + GameBase.getGameScreenDifference().height);
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
}
