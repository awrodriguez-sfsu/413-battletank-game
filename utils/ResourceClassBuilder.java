package utils;

/*
 * Name: Anderson Tao Description: Singleton Resource Class Builder
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

public class ResourceClassBuilder {

	private static String newline = "\n";
	private static String tab = "\t";

	private static ArrayList<String> images = new ArrayList<String>();
	private static ArrayList<String> solidSpecs = new ArrayList<String>();
	private static ArrayList<String> sounds = new ArrayList<String>();

	public static void main(String[] args) {
		// Linux
		String basePath = "/home/arod/Desktop/TankResources/";

		// Windows
		// String basePath = "C:\\Users\\arodr101\\Desktop\\TankResources\\";

		String resourcePath = basePath + "Resources";
		String solidResourcesText = basePath + "solid_objects.txt";
		String resourcesJSON = basePath + "resources.json";
		String maps = basePath + "map1.txt";

		String jarRun = "../"; // Applet Configuration
		// String jarRun = ""; // Jar Configuration

		System.out.println("Getting solid_resources.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(solidResourcesText))) {
			for (String line; ( line = br.readLine() ) != null;) {
				solidSpecs.add(line);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		System.out.println("Found " + solidSpecs.size() + " resources with specifications");

		System.out.println("Getting resources");
		File folder = new File(resourcePath);
		File[] listOfFiles = folder.listFiles();

		Arrays.sort(listOfFiles);

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().endsWith("png")) {
					images.add(listOfFiles[i].getName());
				} else if (listOfFiles[i].getName().endsWith("wav")) {
					sounds.add(listOfFiles[i].getName());
				}
			} else if (listOfFiles[i].isDirectory()) {
				// Multiple levels of directories
			}
		}
		System.out.println("Found " + images.size() + " resources");

		System.out.println("Moving resources");
		File directory = new File("resources");

		FileUtilities.copyDirectory(resourcePath, directory.getAbsolutePath());

		FileUtilities.copyFile(new File(resourcesJSON), new File("resources.json"));

		FileUtilities.copyFile(new File(maps), new File("map1.txt"));

		System.out.println("Writing Resources.java");
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src//gamebase//Resources.java"), "utf-8"));
			// Import statements
			writer.write("package gamebase;" + newline);
			writer.write(newline);
			writer.write("import java.applet.AudioClip;" + newline);
			writer.write("import java.awt.Dimension;" + newline);
			writer.write("import java.awt.Image;" + newline);
			writer.write("import java.io.BufferedReader;" + newline);
			writer.write("import java.io.File;" + newline);
			writer.write("import java.io.FileReader;" + newline);
			writer.write("import java.io.IOException;" + newline);
			writer.write("import java.net.MalformedURLException;" + newline);
			writer.write("import java.util.ArrayList;" + newline);
			writer.write("import java.util.HashMap;" + newline);
			writer.write(newline);
			writer.write("import javax.imageio.ImageIO;" + newline);
			writer.write("import javax.swing.JApplet;" + newline);
			writer.write(newline);
			writer.write("import org.json.simple.JSONArray;" + newline);
			writer.write("import org.json.simple.JSONObject;" + newline);
			writer.write("import org.json.simple.parser.JSONParser;" + newline);
			writer.write(newline);

			// Resource class
			writer.write("public class Resources {" + newline);
			writer.write(newline);

			// Member variables
			writer.write(tab + "private static Resources instance;" + newline);
			writer.write(newline);
			writer.write(tab + "public static HashMap<String, SolidObjectSpec> specs = new HashMap<String, SolidObjectSpec>();" + newline);
			writer.write(tab + "public static HashMap<String, Image> images = new HashMap<String, Image>();" + newline);
			writer.write(tab + "public static HashMap<Integer, Image> health = new HashMap<Integer, Image>();" + newline);
			writer.write(newline);
			writer.write(tab + "public static Dimension mapDimension;" + newline);
			writer.write(newline);
			writer.write(tab + "public static ArrayList<String> lines = new ArrayList<String>();" + newline);

			// Images
			writer.write(tab + "private Image ");
			String imageVariables = "";
			for (int i = 0; i < images.size(); i++) {
				String prefix = images.get(i);
				prefix = prefix.substring(0, prefix.length() - 4);
				imageVariables += prefix + ", ";
			}
			writer.write(imageVariables.substring(0, imageVariables.length() - 2));
			writer.write(";" + newline);
			writer.write(newline);

			// SolidObjectSpec
			writer.write(tab + "private SolidObjectSpec ");
			String specsVariable = "";
			for (int i = 0; i < solidSpecs.size(); i++) {
				String prefix = solidSpecs.get(i) + "_spec";
				specsVariable += prefix + ", ";
			}
			writer.write(specsVariable.substring(0, specsVariable.length() - 2));
			writer.write(";" + newline);
			writer.write(newline);

			// AudioClips
			String audioVariable = "";
			writer.write(tab + "public AudioClip ");
			for (int i = 0; i < sounds.size(); i++) {
				String prefix = sounds.get(i);
				prefix = prefix.substring(0, prefix.length() - 4);
				audioVariable += prefix + ", ";

				if (i % 5 == 0 && i > 0) {
					audioVariable += newline;
					audioVariable += tab + tab + tab + tab;
				}
			}
			writer.write(audioVariable.substring(0, audioVariable.length() - 2));
			writer.write(";" + newline);
			writer.write(newline);

			// Constructor
			writer.write(tab + "private Resources() {" + newline);
			writer.write(tab + tab + "try {" + newline);

			for (int i = 0; i < images.size(); i++) {
				String prefix = images.get(i);
				prefix = prefix.substring(0, prefix.length() - 4);
				writer.write(tab + tab + tab + prefix + " = ImageIO.read(new File(\"" + jarRun + "resources/" + images.get(i) + "\"));" + newline);
			}
			writer.write(newline);
			writer.write(tab + tab + "} catch (IOException ioException) {" + newline);
			writer.write(tab + tab + tab + "ioException.printStackTrace();" + newline);
			writer.write(tab + tab + "}" + newline);
			writer.write(newline);

			// Fill images HashMap
			for (int i = 0; i < images.size(); i++) {
				writer.write(tab + tab + "images.put(\"" + images.get(i).substring(0, images.get(i).length() - 4) + "\", " + images.get(i).substring(0, images.get(i).length() - 4) + ");" + newline);
			}
			writer.write(newline);

			// Fill health images
			for (int i = 0; i < images.size(); i++) {
				if (images.get(i).contains("healthbar")) {
					writer.write(tab + tab + "health.put(" + images.get(i).substring(9, images.get(i).length() - 4) + ", " + images.get(i).substring(0, images.get(i).length() - 4) + ");" + newline);
				}
			}
			writer.write(newline);

			// Sounds
			writer.write(tab + tab + "try {" + newline);
			for (int i = 0; i < sounds.size(); i++) {
				String prefix = sounds.get(i);
				prefix = prefix.substring(0, prefix.length() - 4);
				writer.write(tab + tab + tab + prefix + " = JApplet.newAudioClip(( new File(\"" + jarRun + "resources/" + sounds.get(i) + "\").toURI().toURL() ));" + newline);
			}
			writer.write(newline);
			writer.write(tab + tab + "} catch (MalformedURLException exception) {" + newline);
			writer.write(tab + tab + tab + "exception.printStackTrace();" + newline);
			writer.write(tab + tab + "}" + newline);
			writer.write(newline);

			// SolidObjectSpec
			for (int i = 0; i < solidSpecs.size(); i++) {
				writer.write(tab + tab + solidSpecs.get(i) + "_spec" + " = new SolidObjectSpec(\"" + solidSpecs.get(i) + "\");" + newline);
			}
			writer.write(newline);

			// Fill specs HashMap
			for (int i = 0; i < solidSpecs.size(); i++) {
				writer.write(tab + tab + "specs.put(\"" + solidSpecs.get(i) + "\", " + solidSpecs.get(i) + "_spec" + ");" + newline);
			}

			// Read Maps
			writer.write(tab + tab + "try {" + newline);
			writer.write(tab + tab + tab + "int width = 0;" + newline);
			writer.write(tab + tab + tab + "int height = 0;" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + "BufferedReader reader = new BufferedReader(new FileReader(\"" + jarRun + "map1.txt\"));" + newline);
			writer.write(tab + tab + tab + "while (true) {" + newline);
			writer.write(tab + tab + tab + tab + "String line = reader.readLine();" + newline);
			writer.write(tab + tab + tab + tab + "if (line == null) {" + newline);
			writer.write(tab + tab + tab + tab + tab + "reader.close();" + newline);
			writer.write(tab + tab + tab + tab + tab + "break;" + newline);
			writer.write(tab + tab + tab + tab + "}" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + tab + "if (!line.startsWith(\"!\")) {" + newline);
			writer.write(tab + tab + tab + tab + tab + "lines.add(line);" + newline);
			writer.write(tab + tab + tab + tab + tab + "width = Math.max(width, line.length());" + newline);
			writer.write(tab + tab + tab + tab + "}" + newline);
			writer.write(tab + tab + tab + "}" + newline);
			writer.write(tab + tab + tab + "height = lines.size();" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + "mapDimension = new Dimension(width, height);" + newline);
			writer.write(tab + tab + "} catch (Exception exception) {" + newline);
			writer.write(tab + tab + tab + "exception.printStackTrace();" + newline);
			writer.write(tab + tab + "}" + newline);
			writer.write(newline);

			writer.write(tab + "}" + newline);
			writer.write(newline);

			// getInstance() member method
			writer.write(tab + "public static Resources getInstance() {" + newline);
			writer.write(tab + tab + "if (instance == null) {" + newline);
			writer.write(tab + tab + tab + "instance = new Resources();" + newline);
			writer.write(tab + tab + "}" + newline);
			writer.write(newline);
			writer.write(tab + tab + "return instance;" + newline);
			writer.write(tab + "}" + newline);
			writer.write(newline);

			// getImage() member method
			writer.write(tab + "public static Image getImage(String name) {" + newline);
			writer.write(newline);
			writer.write(tab + tab + "if (instance == null) {" + newline);
			writer.write(tab + tab + tab + "instance = new Resources();" + newline);
			writer.write(tab + tab + "}" + newline);
			writer.write(newline);
			writer.write(tab + tab + "return images.get(name);" + newline);
			writer.write(tab + "}" + newline);
			writer.write(newline);

			// getSolidSpec() member method
			writer.write(tab + "public static SolidObjectSpec getSolidSpec(String name) {" + newline);
			writer.write(newline);
			writer.write(tab + tab + "if (instance == null) {" + newline);
			writer.write(tab + tab + tab + "instance = new Resources();" + newline);
			writer.write(tab + tab + "}" + newline);
			writer.write(newline);
			writer.write(tab + tab + "return specs.get(name);" + newline);
			writer.write(tab + "}" + newline);
			writer.write(newline);

			// SolidObjectSpec class
			writer.write(tab + "public class SolidObjectSpec {" + newline);
			writer.write(newline);

			// member variables
			writer.write(tab + tab + "// Bounds" + newline);
			writer.write(tab + tab + "public double centerX, centerY, top, bottom, left, right, front;" + newline);
			writer.write(tab + tab + "public long number_of_shapes;" + newline);
			writer.write(tab + tab + "public String name;" + newline);
			writer.write(newline);
			writer.write(tab + tab + "private JSONParser parser;" + newline);
			writer.write(newline);
			writer.write(tab + tab + "public ArrayList<Shape> shapes = new ArrayList<Shape>();" + newline);
			writer.write(newline);

			// Constructor
			writer.write(tab + tab + "public SolidObjectSpec(String name) {" + newline);
			writer.write(tab + tab + tab + "parser = new JSONParser();" + newline);
			writer.write(tab + tab + tab + "try {" + newline);
			writer.write(tab + tab + tab + tab + "JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(\"" + jarRun + "resources.json\"));" + newline);
			writer.write(tab + tab + tab + tab + "JSONObject objectSpecifications = (JSONObject) jsonObject.get(name);" + newline);
			writer.write(tab + tab + tab + tab + "JSONObject bounds = (JSONObject) objectSpecifications.get(\"bounds\");" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + tab + "this.centerX = (double) Double.parseDouble(bounds.get(\"center_x\").toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.centerY = (double) Double.parseDouble(bounds.get(\"center_y\").toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.top = (double) Double.parseDouble(bounds.get(\"top\").toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.bottom = (double) Double.parseDouble(bounds.get(\"bottom\").toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.left = (double) Double.parseDouble(bounds.get(\"left\").toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.right = (double) Double.parseDouble(bounds.get(\"right\").toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.front = (double) Double.parseDouble(bounds.get(\"front\").toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.front = (double) Double.parseDouble(bounds.get(\"front\").toString());" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + tab + "JSONObject collision = (JSONObject) objectSpecifications.get(\"collision\");" + newline);
			writer.write(tab + tab + tab + tab + "this.number_of_shapes = (long) Long.parseLong(collision.get(\"number_of_shapes\").toString());" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + tab + "JSONObject JSONshapes = (JSONObject) collision.get(\"shapes\");" + newline);
			writer.write(tab + tab + tab + tab + "for (int i = 1; i <= number_of_shapes; i++) {" + newline);
			writer.write(tab + tab + tab + tab + tab + "JSONObject shape = (JSONObject) JSONshapes.get(\"shape\" + i);" + newline);
			writer.write(tab + tab + tab + tab + tab + "JSONArray params = (JSONArray) shape.get(\"params\");" + newline);
			writer.write(tab + tab + tab + tab + tab + "shapes.add(new Shape((String) shape.get(\"type\"), params));" + newline);
			writer.write(tab + tab + tab + tab + "}" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + tab + "this.name = name;" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + "} catch (Exception exception) {" + newline);
			writer.write(tab + tab + tab + tab + "exception.printStackTrace();" + newline);
			writer.write(tab + tab + tab + "}" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + "parser = null;" + newline);
			writer.write(tab + tab + "}" + newline);
			writer.write(newline);

			// Shape class
			writer.write(tab + tab + "public class Shape {" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + "public double x, y, width, height;" + newline);
			writer.write(tab + tab + tab + "public String type;" + newline);
			writer.write(newline);
			writer.write(tab + tab + tab + "public Shape(String type, JSONArray array) {" + newline);
			writer.write(tab + tab + tab + tab + "this.type = type;" + newline);
			writer.write(tab + tab + tab + tab + "this.x = (double) Double.parseDouble(array.get(0).toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.y = (double) Double.parseDouble(array.get(1).toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.width = (double) Double.parseDouble(array.get(2).toString());" + newline);
			writer.write(tab + tab + tab + tab + "this.height = (double) Double.parseDouble(array.get(3).toString());" + newline);
			writer.write(tab + tab + tab + "}" + newline);
			writer.write(tab + tab + "}" + newline);
			writer.write(tab + "}" + newline);
			writer.write(newline);

			// writer.write(tab + tab + tab + tab + "" + newline);

			writer.write("}");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		System.out.println("Done writing Resources.java");
	}
}
