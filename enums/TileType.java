package enums;

public enum TileType {
	BORDER(0, "border"),
	WALL1(3, "wall1"),
	WALL2(4, "wall2"),
	BACKGROUND(5, "background");

	private final int MAP_KEY;
	private final String RESOURCE_NAME;

	private TileType(int type, String name) {
		this.MAP_KEY = type;
		this.RESOURCE_NAME = name;
	}

	public String getResourceName() {
		return RESOURCE_NAME;
	}

	public int getMapKey() {
		return MAP_KEY;
	}

	public static TileType getTypeByName(String name) {
		for (TileType type : values()) {
			if (type.getResourceName().equals(name)) {
				return type;
			}
		}
		return null;
	}

	public static TileType getTypeByMapKey(int mapKey) {
		for (TileType type : values()) {
			if (type.getMapKey() == mapKey) {
				return type;
			}
		}
		return null;
	}
}
