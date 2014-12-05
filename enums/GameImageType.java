package enums;

public enum GameImageType {
	LIFE(false, "life"),
	BACKGROUND(false, "background"),
	BACKGROUND_LARGE(false, "background_large"),
	BORDER(true, "border"),
	WALL1(true, "wall1"),
	WALL2(true, "wall2"),
	SHELL_BASIC(true, "shell_basic"),
	SHELL_HEAVY(true, "shell_heavy"),
	SHELL_LIGHT(true, "shell_light"),
	TANK_BLUE_BASIC(true, "tank_blue_basic"),
	TANK_BLUE_HEAVY(true, "tank_blue_heavy"),
	TANK_BLUE_LIGHT(true, "tank_blue_light"),
	TANK_RED_BASIC(true, "tank_red_basic"),
	TANK_RED_HEAVY(true, "tank_red_heavy"),
	TANK_RED_LIGHT(true, "tank_red_light"),
	BASIC_UPGRADE(true, "basic_upgrade"),
	HEAVY_UPGRADE(true, "heavy_upgrade"),
	LIGHT_UPGRADE(true, "light_upgrade"),
	LIFE_PICKUP(true, "life_pickup");

	private final boolean SOLID;
	private final String RESOURCE_NAME;

	private GameImageType(boolean solid, String name) {
		this.SOLID = solid;
		this.RESOURCE_NAME = name;
	}

	public boolean isSolid() {
		return SOLID;
	}

	public String getResourceName() {
		return RESOURCE_NAME;
	}

	public static GameImageType getType(String name) {
		for (GameImageType type : values()) {
			if (type.getResourceName().equals(name)) {
				return type;
			}
		}
		return null;
	}
}
