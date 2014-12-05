package collision;

import java.awt.Rectangle;

import enums.CollisionShape;

public class GameObjectBounds {

	private CollisionShape shape;
	private double x, y, width, height, posX, posY;

	public GameObjectBounds(CollisionShape shape, double x, double y, double width, double height) {
		this.shape = shape;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean intersects(GameObjectBounds bounds) {
		return intersects(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public boolean intersects(Rectangle rectangle) {
		return intersects(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public boolean intersects(double x, double y, double width, double height) {
		if (shape == CollisionShape.CIRCLE) {
			if (width <= 0.0 || height <= 0.0) {
				return false;
			}
			// Normalize the rectangular coordinates compared to the ellipse
			// having a center at 0,0 and a radius of 0.5.
			double ellw = getWidth();
			if (ellw <= 0.0) {
				return false;
			}
			double normx0 = ( x - getX() ) / ellw - 0.5;
			double normx1 = normx0 + width / ellw;
			double ellh = getHeight();
			if (ellh <= 0.0) {
				return false;
			}
			double normy0 = ( y - getY() ) / ellh - 0.5;
			double normy1 = normy0 + height / ellh;
			// find nearest x (left edge, right edge, 0.0)
			// find nearest y (top edge, bottom edge, 0.0)
			// if nearest x,y is inside circle of radius 0.5, then intersects
			double nearx, neary;
			if (normx0 > 0.0) {
				// center to left of X extents
				nearx = normx0;
			} else if (normx1 < 0.0) {
				// center to right of X extents
				nearx = normx1;
			} else {
				nearx = 0.0;
			}
			if (normy0 > 0.0) {
				// center above Y extents
				neary = normy0;
			} else if (normy1 < 0.0) {
				// center below Y extents
				neary = normy1;
			} else {
				neary = 0.0;
			}
			return ( nearx * nearx + neary * neary ) < 0.25;
		} else {
			double x0 = getX();
			double y0 = getY();
			return ( x + width > x0 && y + height > y0 && x < x0 + getWidth() && y < y0 + getHeight() );
		}
	}

	public CollisionShape getShape() {
		return shape;
	}

	public double getX() {
		return x + posX;
	}

	public double getY() {
		return y + posY;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void update(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}
}
