package gamebase;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JApplet;
import javax.swing.JFrame;

import actors.MainActor;
import actors.PowerUp;
import actors.Projectile;
import background.Tile;
import controller.Controls;
import enums.GameImageType;
import enums.GameObjectType;
import enums.TileType;

public class GameBase extends JApplet implements Runnable {

	private static final long serialVersionUID = 5279573925307200299L;

	private BufferedImage bufferedGame;
	private BufferedImage bufferedHud;

	private static Dimension dimension;
	private static Dimension mapDimension;

	private static int deltaX;
	private static int deltaY;

	private static ArrayList<Tile> tiles = new ArrayList<Tile>();
	private static ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();

	public static MainActor player1;
	public static MainActor player2;

	private static Controls controls;

	private AudioClip backgroundMusic;

	private FontMetrics metrics;

	@Override
	public void init() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dimension);
		setBackground(Color.BLACK);
		setFocusable(true);

		// Set Font
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		metrics = getFontMetrics(getFont());

		Resources.getInstance();

		deltaX = dimension.width / 2;
		deltaY = dimension.height / 2;

		mapDimension = loadMap();

		player1 = new MainActor(GameImageType.TANK_BLUE_BASIC, GameObjectType.PLAYER1, 100, 100);
		player2 = new MainActor(GameImageType.TANK_RED_BASIC, GameObjectType.PLAYER2, 1032, 1032);

		controls = new Controls();
		addKeyListener(controls);

		backgroundMusic = Resources.sound_background;
	}

	@Override
	public void start() {
		Thread thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();

		backgroundMusic.loop();
	}

	@Override
	public void run() {
		while (true) {
			repaint();
			controls.update();

			try {
				Thread.sleep(16);
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
		}
	}

	private Dimension loadMap() {
		Dimension dimension = Resources.mapDimension;
		for (int j = 0; j < dimension.height; j++) {
			String line = (String) Resources.lines.get(j);
			for (int i = 0; i < dimension.width; i++) {
				if (i < line.length()) {
					char ch = line.charAt(i);
					if (ch != ' ') {
						Tile tile = new Tile(TileType.getTypeByMapKey(Character.getNumericValue(ch)), i * 32, j * 32);
						tiles.add(tile);
					}
				}

			}
		}

		return new Dimension(dimension.width * 32, dimension.width * 32);
	}

	@Override
	public void paint(Graphics graphics) {
		Graphics2D graphicsGame = createGraphicsGame(mapDimension.width + dimension.width, mapDimension.height + dimension.height);
		drawGame(graphicsGame);

		Graphics2D graphicsHud = createGraphicsHud(dimension.width, (int) ( dimension.height * .3 ));
		drawHud(graphicsHud);

		graphicsHud.dispose();
		graphicsGame.dispose();

		BufferedImage player1View;
		BufferedImage player2View;
		try {
			player1View = bufferedGame.getSubimage(player1.view.x, player1.view.y, player1.view.width, player1.view.height);
			player2View = bufferedGame.getSubimage(player2.view.x, player2.view.y, player2.view.width, player2.view.height);
		} catch (RasterFormatException exception) {
			player1View = null;
			player2View = null;
			exception.printStackTrace();
		}

		// Draw each player view
		graphics.drawImage(bufferedHud, 0, (int) ( dimension.height * .7 ), this);
		graphics.drawImage(player1View, ( dimension.width / 2 ) + 10, 0, ( dimension.width / 2 ) - 10, (int) ( dimension.height * .7 ), this);
		graphics.drawImage(player2View, 0, 0, ( dimension.width / 2 ) - 10, (int) ( dimension.height * .7 ), this);
	}

	private Graphics2D createGraphicsGame(int width, int height) {
		Graphics2D graphics2d = null;

		if (bufferedGame == null) {
			bufferedGame = (BufferedImage) createImage(width + 128, height + 128);
		}

		graphics2d = bufferedGame.createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
		graphics2d.clearRect(0, 0, width, height);

		return graphics2d;
	}

	private Graphics2D createGraphicsHud(int width, int height) {
		Graphics2D graphics2d = null;

		if (bufferedHud == null) {
			bufferedHud = (BufferedImage) createImage(width, height);
		}

		graphics2d = bufferedHud.createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
		graphics2d.clearRect(0, 0, width, height);

		return graphics2d;
	}

	private void drawGame(Graphics2D graphics2d) {
		drawBackground(graphics2d);

		AffineTransform transform1 = new AffineTransform();
		transform1.translate(player1.getPosX() + deltaX, player1.getPosY() + deltaY);
		transform1.rotate(Math.toRadians(-player1.getDirection()), 32, 32);

		AffineTransform transform2 = new AffineTransform();
		transform2.translate(player2.getPosX() + deltaX, player2.getPosY() + deltaY);
		transform2.rotate(Math.toRadians(-player2.getDirection()), 32, 32);

		player1.update();
		player1.draw(graphics2d, transform1, this);

		player2.update();
		player2.draw(graphics2d, transform2, this);

		ArrayList<Projectile> player1Shots = player1.getShots();
		for (int i = 0; i < player1Shots.size(); i++) {
			Projectile b = (Projectile) player1Shots.get(i);
			if (b.isVisible()) {
				AffineTransform transformShot1 = new AffineTransform();
				transformShot1.translate(b.getPosX() + deltaX, b.getPosY() + deltaY);
				transformShot1.rotate(Math.toRadians(-b.getDirection()), 16, 16);

				b.update();
				b.draw(graphics2d, transformShot1, this);

				if (b.isColliding(player2)) {
					if (b.getGameImageType() == GameImageType.SHELL_BASIC) {
						player2.adjustHealth(-2);
						player1Shots.remove(i);
					} else if (b.getGameImageType() == GameImageType.SHELL_HEAVY) {
						player2.adjustHealth(-4);
						player1Shots.remove(i);
					} else {
						player2.adjustHealth(-1);
						player1Shots.remove(i);
					}
				}

			} else {
				player1Shots.remove(i);
			}
		}

		ArrayList<Projectile> player2Shots = player2.getShots();
		for (int i = 0; i < player2Shots.size(); i++) {
			Projectile b = (Projectile) player2Shots.get(i);
			if (b.isVisible()) {
				AffineTransform transformShot1 = new AffineTransform();
				transformShot1.translate(b.getPosX() + deltaX, b.getPosY() + deltaY);
				transformShot1.rotate(Math.toRadians(-b.getDirection()), 16, 16);

				b.update();
				b.draw(graphics2d, transformShot1, this);

				if (b.isColliding(player1)) {
					if (b.getGameImageType() == GameImageType.SHELL_BASIC) {
						player1.adjustHealth(-2);
						player2Shots.remove(i);
					} else if (b.getGameImageType() == GameImageType.SHELL_HEAVY) {
						player1.adjustHealth(-4);
						player2Shots.remove(i);
					} else {
						player1.adjustHealth(-1);
						player2Shots.remove(i);
					}
				}

			} else {
				player2Shots.remove(i);
			}
		}
	}

	private void drawHud(Graphics2D graphics2d) {
		// Mini Map
		BufferedImage miniMapBuf = bufferedGame.getSubimage(dimension.width / 2 - 16, dimension.height / 2, mapDimension.width, mapDimension.height);
		Image miniMap = miniMapBuf.getScaledInstance(( mapDimension.width + dimension.width ) / 10, ( mapDimension.height + dimension.height ) / 10, Image.SCALE_FAST);
		int miniMapCenterX = miniMap.getWidth(this) / 2;

		// Draw Hud
		graphics2d.drawImage(miniMap, ( dimension.width / 2 ) - miniMapCenterX, 0, this);
		graphics2d.drawImage(player1.getHealthBar(), dimension.width - 128, 0, this);
		graphics2d.drawImage(player2.getHealthBar(), 0, 0, this);

		// Draw Lives
		for (int i = 0; i < player1.getLives(); i++) {
			graphics2d.drawImage(Resources.getImage(GameImageType.LIFE.getResourceName()), dimension.width - 128 + 32 * i, 42, this);
		}

		for (int i = 0; i < player2.getLives(); i++) {
			graphics2d.drawImage(Resources.getImage(GameImageType.LIFE.getResourceName()), 0 + 32 * i, 42, this);
		}

		// Draw Tank Type
		if (player1.getGameImageType() == GameImageType.TANK_BLUE_BASIC) {
			graphics2d.drawImage(Resources.getImage(GameImageType.TANK_BASIC.getResourceName()), dimension.width - 128 - 8, 74, this);
		} else if (player1.getGameImageType() == GameImageType.TANK_BLUE_HEAVY) {
			graphics2d.drawImage(Resources.getImage(GameImageType.TANK_HEAVY.getResourceName()), dimension.width - 128 - 8, 74, this);
		} else if (player1.getGameImageType() == GameImageType.TANK_BLUE_LIGHT) {
			graphics2d.drawImage(Resources.getImage(GameImageType.TANK_LIGHT.getResourceName()), dimension.width - 128 - 8, 74, this);
		}

		if (player2.getGameImageType() == GameImageType.TANK_RED_BASIC) {
			graphics2d.drawImage(Resources.getImage(GameImageType.TANK_BASIC.getResourceName()), 0, 74, this);
		} else if (player2.getGameImageType() == GameImageType.TANK_RED_HEAVY) {
			graphics2d.drawImage(Resources.getImage(GameImageType.TANK_HEAVY.getResourceName()), 0, 74, this);
		} else if (player2.getGameImageType() == GameImageType.TANK_RED_LIGHT) {
			graphics2d.drawImage(Resources.getImage(GameImageType.TANK_LIGHT.getResourceName()), 0, 74, this);
		}

		// Draw Score
		graphics2d.setColor(Color.WHITE);
		graphics2d.drawString(player1.score + "", (int) ( dimension.width - 10 - ( metrics.stringWidth(player1.score + "") ) ) + 2, 150);
		graphics2d.drawString(player2.score + "", 0, 150);
	}

	private void drawBackground(Graphics2D graphics2d) {
		graphics2d.drawImage(Resources.getImage(GameImageType.BACKGROUND_LARGE.getResourceName()), 0, 0, mapDimension.width + dimension.width, mapDimension.height + dimension.height, this);

		for (int j = 0; j < tiles.size(); j++) {
			Tile tile = (Tile) tiles.get(j);
			tile.update();
			tile.draw(graphics2d, this);

			if (tile.getGameImageType() == GameImageType.WALL1 || tile.getGameImageType() == GameImageType.WALL2) {
				if (tile.isColliding(player1)) {
					player1.stop();
				}
				if (tile.isColliding(player2)) {
					player2.stop();
				}
			}

			ArrayList<Projectile> player1Shots = player1.getShots();
			for (int i = 0; i < player1Shots.size(); i++) {
				Projectile b = (Projectile) player1Shots.get(i);

				if (b.isColliding(tile)) {
					player1Shots.remove(i);
					if (tile.getGameImageType() == GameImageType.WALL1) {
						tile.setGameImageType(GameImageType.WALL2);
					} else if (tile.getGameImageType() == GameImageType.WALL2) {
						tiles.remove(j);
					}
				}
			}

			ArrayList<Projectile> player2Shots = player2.getShots();
			for (int i = 0; i < player2Shots.size(); i++) {
				Projectile b = (Projectile) player2Shots.get(i);

				if (b.isColliding(tile)) {
					player2Shots.remove(i);
					if (tile.getGameImageType() == GameImageType.WALL1) {
						tile.setGameImageType(GameImageType.WALL2);
					} else if (tile.getGameImageType() == GameImageType.WALL2) {
						tiles.remove(j);
					}
				}
			}
		}

		Random random = new Random();
		int action = random.nextInt(500) + 1;
		if (action == 1) {
			PowerUp powerUp = new PowerUp(GameImageType.LIFE_PICKUP, ( random.nextInt(40) + 1 ) * 32, ( random.nextInt(40) + 1 ) * 32);
			powerUps.add(powerUp);
		} else if (action == 2) {
			PowerUp powerUp = new PowerUp(GameImageType.HEAVY_UPGRADE, ( random.nextInt(40) + 1 ) * 32, ( random.nextInt(40) + 1 ) * 32);
			powerUps.add(powerUp);
		} else if (action == 3) {
			PowerUp powerUp = new PowerUp(GameImageType.LIGHT_UPGRADE, ( random.nextInt(40) + 1 ) * 32, ( random.nextInt(40) + 1 ) * 32);
			powerUps.add(powerUp);
		}

		for (int i = 0; i < powerUps.size(); i++) {
			PowerUp powerUp = (PowerUp) powerUps.get(i);

			if (powerUp.isVisible()) {
				powerUp.update();
				powerUp.draw(graphics2d, this);
				if (powerUp.isColliding(player1)) {
					powerUp.upgrade(player1);
					powerUps.remove(i);
				} else if (powerUp.isColliding(player2)) {
					powerUp.upgrade(player2);
					powerUps.remove(i);
				}
			} else {
				powerUps.remove(i);
			}
		}
	}

	public static void reset() {

		if (player1.isAlive()) {
			player1.score++;
		} else if (player2.isAlive()) {
			player1.score++;
		}

		player1.reset(100, 100);
		player2.reset(1032, 1032);
	}

	public static Dimension getDimension() {
		return dimension;
	}

	public static Dimension getGameScreenDifference() {
		return new Dimension(deltaX, deltaY);
	}

	public static void main(String[] args) {
		final GameBase game = new GameBase();
		game.init();

		JFrame jFrame = new JFrame("BattleTanks");
		jFrame.addWindowListener(new WindowAdapter() {});
		jFrame.getContentPane().add("Center", game);
		jFrame.pack();
		jFrame.setSize(new Dimension(GameBase.getDimension()));
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.start();
	}
}
