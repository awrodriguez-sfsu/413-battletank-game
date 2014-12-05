package gamebase;

import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Resources {

	private static Resources instance;

	public static HashMap<String, SolidObjectSpec> specs = new HashMap<String, SolidObjectSpec>();
	public static HashMap<String, Image> images = new HashMap<String, Image>();
	public static HashMap<Integer, Image> health = new HashMap<Integer, Image>();

	public static Dimension mapDimension;

	public static ArrayList<String> lines = new ArrayList<String>();
	private Image background, background_large, basic_upgrade, boder_horizontal, boder_vertical, border, healthbar0, healthbar1, healthbar2, healthbar3, healthbar4, healthbar5, healthbar6, healthbar7, healthbar8, heavy_upgrade, life, life_pickup, light_upgrade, shell_basic, shell_heavy, shell_light, tank_blue_basic, tank_blue_heavy, tank_blue_light, tank_gray_basic, tank_gray_heavy, tank_gray_light, tank_red_basic, tank_red_heavy, tank_red_light, wall1, wall2;

	private SolidObjectSpec border_spec, wall1_spec, wall2_spec, shell_basic_spec, shell_heavy_spec, shell_light_spec, tank_blue_basic_spec, tank_blue_heavy_spec, tank_blue_light_spec, tank_red_basic_spec, tank_red_heavy_spec, tank_red_light_spec, basic_upgrade_spec, heavy_upgrade_spec, light_upgrade_spec, life_pickup_spec;

	public AudioClip large_explosion_sound, shot;

	private Resources() {
		try {
			background = ImageIO.read(new File("../resources/background.png"));
			background_large = ImageIO.read(new File("../resources/background_large.png"));
			basic_upgrade = ImageIO.read(new File("../resources/basic_upgrade.png"));
			boder_horizontal = ImageIO.read(new File("../resources/boder_horizontal.png"));
			boder_vertical = ImageIO.read(new File("../resources/boder_vertical.png"));
			border = ImageIO.read(new File("../resources/border.png"));
			healthbar0 = ImageIO.read(new File("../resources/healthbar0.png"));
			healthbar1 = ImageIO.read(new File("../resources/healthbar1.png"));
			healthbar2 = ImageIO.read(new File("../resources/healthbar2.png"));
			healthbar3 = ImageIO.read(new File("../resources/healthbar3.png"));
			healthbar4 = ImageIO.read(new File("../resources/healthbar4.png"));
			healthbar5 = ImageIO.read(new File("../resources/healthbar5.png"));
			healthbar6 = ImageIO.read(new File("../resources/healthbar6.png"));
			healthbar7 = ImageIO.read(new File("../resources/healthbar7.png"));
			healthbar8 = ImageIO.read(new File("../resources/healthbar8.png"));
			heavy_upgrade = ImageIO.read(new File("../resources/heavy_upgrade.png"));
			life = ImageIO.read(new File("../resources/life.png"));
			life_pickup = ImageIO.read(new File("../resources/life_pickup.png"));
			light_upgrade = ImageIO.read(new File("../resources/light_upgrade.png"));
			shell_basic = ImageIO.read(new File("../resources/shell_basic.png"));
			shell_heavy = ImageIO.read(new File("../resources/shell_heavy.png"));
			shell_light = ImageIO.read(new File("../resources/shell_light.png"));
			tank_blue_basic = ImageIO.read(new File("../resources/tank_blue_basic.png"));
			tank_blue_heavy = ImageIO.read(new File("../resources/tank_blue_heavy.png"));
			tank_blue_light = ImageIO.read(new File("../resources/tank_blue_light.png"));
			tank_gray_basic = ImageIO.read(new File("../resources/tank_gray_basic.png"));
			tank_gray_heavy = ImageIO.read(new File("../resources/tank_gray_heavy.png"));
			tank_gray_light = ImageIO.read(new File("../resources/tank_gray_light.png"));
			tank_red_basic = ImageIO.read(new File("../resources/tank_red_basic.png"));
			tank_red_heavy = ImageIO.read(new File("../resources/tank_red_heavy.png"));
			tank_red_light = ImageIO.read(new File("../resources/tank_red_light.png"));
			wall1 = ImageIO.read(new File("../resources/wall1.png"));
			wall2 = ImageIO.read(new File("../resources/wall2.png"));

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		images.put("background", background);
		images.put("background_large", background_large);
		images.put("basic_upgrade", basic_upgrade);
		images.put("boder_horizontal", boder_horizontal);
		images.put("boder_vertical", boder_vertical);
		images.put("border", border);
		images.put("healthbar0", healthbar0);
		images.put("healthbar1", healthbar1);
		images.put("healthbar2", healthbar2);
		images.put("healthbar3", healthbar3);
		images.put("healthbar4", healthbar4);
		images.put("healthbar5", healthbar5);
		images.put("healthbar6", healthbar6);
		images.put("healthbar7", healthbar7);
		images.put("healthbar8", healthbar8);
		images.put("heavy_upgrade", heavy_upgrade);
		images.put("life", life);
		images.put("life_pickup", life_pickup);
		images.put("light_upgrade", light_upgrade);
		images.put("shell_basic", shell_basic);
		images.put("shell_heavy", shell_heavy);
		images.put("shell_light", shell_light);
		images.put("tank_blue_basic", tank_blue_basic);
		images.put("tank_blue_heavy", tank_blue_heavy);
		images.put("tank_blue_light", tank_blue_light);
		images.put("tank_gray_basic", tank_gray_basic);
		images.put("tank_gray_heavy", tank_gray_heavy);
		images.put("tank_gray_light", tank_gray_light);
		images.put("tank_red_basic", tank_red_basic);
		images.put("tank_red_heavy", tank_red_heavy);
		images.put("tank_red_light", tank_red_light);
		images.put("wall1", wall1);
		images.put("wall2", wall2);

		health.put(0, healthbar0);
		health.put(1, healthbar1);
		health.put(2, healthbar2);
		health.put(3, healthbar3);
		health.put(4, healthbar4);
		health.put(5, healthbar5);
		health.put(6, healthbar6);
		health.put(7, healthbar7);
		health.put(8, healthbar8);

		try {
			large_explosion_sound = JApplet.newAudioClip(( new File("../resources/large_explosion_sound.wav").toURI().toURL() ));
			shot = JApplet.newAudioClip(( new File("../resources/shot.wav").toURI().toURL() ));

		} catch (MalformedURLException exception) {
			exception.printStackTrace();
		}

		border_spec = new SolidObjectSpec("border");
		wall1_spec = new SolidObjectSpec("wall1");
		wall2_spec = new SolidObjectSpec("wall2");
		shell_basic_spec = new SolidObjectSpec("shell_basic");
		shell_heavy_spec = new SolidObjectSpec("shell_heavy");
		shell_light_spec = new SolidObjectSpec("shell_light");
		tank_blue_basic_spec = new SolidObjectSpec("tank_blue_basic");
		tank_blue_heavy_spec = new SolidObjectSpec("tank_blue_heavy");
		tank_blue_light_spec = new SolidObjectSpec("tank_blue_light");
		tank_red_basic_spec = new SolidObjectSpec("tank_red_basic");
		tank_red_heavy_spec = new SolidObjectSpec("tank_red_heavy");
		tank_red_light_spec = new SolidObjectSpec("tank_red_light");
		basic_upgrade_spec = new SolidObjectSpec("basic_upgrade");
		heavy_upgrade_spec = new SolidObjectSpec("heavy_upgrade");
		light_upgrade_spec = new SolidObjectSpec("light_upgrade");
		life_pickup_spec = new SolidObjectSpec("life_pickup");

		specs.put("border", border_spec);
		specs.put("wall1", wall1_spec);
		specs.put("wall2", wall2_spec);
		specs.put("shell_basic", shell_basic_spec);
		specs.put("shell_heavy", shell_heavy_spec);
		specs.put("shell_light", shell_light_spec);
		specs.put("tank_blue_basic", tank_blue_basic_spec);
		specs.put("tank_blue_heavy", tank_blue_heavy_spec);
		specs.put("tank_blue_light", tank_blue_light_spec);
		specs.put("tank_red_basic", tank_red_basic_spec);
		specs.put("tank_red_heavy", tank_red_heavy_spec);
		specs.put("tank_red_light", tank_red_light_spec);
		specs.put("basic_upgrade", basic_upgrade_spec);
		specs.put("heavy_upgrade", heavy_upgrade_spec);
		specs.put("light_upgrade", light_upgrade_spec);
		specs.put("life_pickup", life_pickup_spec);
		try {
			int width = 0;
			int height = 0;

			BufferedReader reader = new BufferedReader(new FileReader("../map1.txt"));
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					reader.close();
					break;
				}

				if (!line.startsWith("!")) {
					lines.add(line);
					width = Math.max(width, line.length());
				}
			}
			height = lines.size();

			mapDimension = new Dimension(width, height);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public static Resources getInstance() {
		if (instance == null) {
			instance = new Resources();
		}

		return instance;
	}

	public static Image getImage(String name) {

		if (instance == null) {
			instance = new Resources();
		}

		return images.get(name);
	}

	public static SolidObjectSpec getSolidSpec(String name) {

		if (instance == null) {
			instance = new Resources();
		}

		return specs.get(name);
	}

	public class SolidObjectSpec {

		// Bounds
		public double centerX, centerY, top, bottom, left, right, front;
		public long number_of_shapes;
		public String name;

		private JSONParser parser;

		public ArrayList<Shape> shapes = new ArrayList<Shape>();

		public SolidObjectSpec(String name) {
			parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("../resources.json"));
				JSONObject objectSpecifications = (JSONObject) jsonObject.get(name);
				JSONObject bounds = (JSONObject) objectSpecifications.get("bounds");

				this.centerX = (double) Double.parseDouble(bounds.get("center_x").toString());
				this.centerY = (double) Double.parseDouble(bounds.get("center_y").toString());
				this.top = (double) Double.parseDouble(bounds.get("top").toString());
				this.bottom = (double) Double.parseDouble(bounds.get("bottom").toString());
				this.left = (double) Double.parseDouble(bounds.get("left").toString());
				this.right = (double) Double.parseDouble(bounds.get("right").toString());
				this.front = (double) Double.parseDouble(bounds.get("front").toString());
				this.front = (double) Double.parseDouble(bounds.get("front").toString());

				JSONObject collision = (JSONObject) objectSpecifications.get("collision");
				this.number_of_shapes = (long) Long.parseLong(collision.get("number_of_shapes").toString());

				JSONObject JSONshapes = (JSONObject) collision.get("shapes");
				for (int i = 1; i <= number_of_shapes; i++) {
					JSONObject shape = (JSONObject) JSONshapes.get("shape" + i);
					JSONArray params = (JSONArray) shape.get("params");
					shapes.add(new Shape((String) shape.get("type"), params));
				}

				this.name = name;

			} catch (Exception exception) {
				exception.printStackTrace();
			}

			parser = null;
		}

		public class Shape {

			public double x, y, width, height;
			public String type;

			public Shape(String type, JSONArray array) {
				this.type = type;
				this.x = (double) Double.parseDouble(array.get(0).toString());
				this.y = (double) Double.parseDouble(array.get(1).toString());
				this.width = (double) Double.parseDouble(array.get(2).toString());
				this.height = (double) Double.parseDouble(array.get(3).toString());
			}
		}
	}

}