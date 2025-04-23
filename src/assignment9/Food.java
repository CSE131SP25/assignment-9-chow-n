package assignment9;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class Food {

	public static final double FOOD_SIZE = 0.02;
	private double x, y;

	/**
	 * Creates a new Food at a random location
	 */
	public Food() {
		this.x = FOOD_SIZE + (Math.random() * (1.0 - 2 * FOOD_SIZE));
		this.y = FOOD_SIZE + (Math.random() * (1.0 - 2 * FOOD_SIZE));
	}

	/**
	 * Draws the Food
	 */
	public void draw() {
		StdDraw.setPenColor(Color.RED); // Food is typically red
		StdDraw.filledCircle(this.x, this.y, FOOD_SIZE);
	}

	// Getters
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getSize() {
		return FOOD_SIZE;
	}
}
