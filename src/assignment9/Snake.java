package assignment9;

import java.awt.Color; 
import java.util.LinkedList;
// Make sure StdDraw is available if you were to use it directly in Snake, 
// though it's primarily used in Game and BodySegment.
// import edu.princeton.cs.introcs.StdDraw; 

public class Snake {

	private static final double SEGMENT_SIZE = 0.02;
	// MOVEMENT_SIZE determines how far the snake moves each frame (speed)
	private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 1.5; 
	private LinkedList<BodySegment> segments;
	// deltaX/deltaY track the snake's current direction vector per frame
	private double deltaX;
	private double deltaY;
	// Flag to signal that the snake ate food and should grow on the next move
	private boolean grewThisTurn = false; 

	public Snake() {
		segments = new LinkedList<>();
		
		// Start the snake in the middle of the screen
		BodySegment head = new BodySegment(0.5, 0.5, SEGMENT_SIZE);
		// Optional: Set a distinct color for the head
		// head.setColor(Color.BLUE); 
		segments.add(head); // Add the initial head segment

		// Start stationary - movement begins after the first key press
		deltaX = 0;
		deltaY = 0;
	}

	/**
	 * Updates the snake's intended direction based on user input (direction codes).
	 * Prevents the snake from reversing directly onto itself.
	 * 
	 * @param direction 1:Up, 2:Down, 3:Left, 4:Right
	 */
	public void changeDirection(int direction) {
		// Check requested direction AND ensure it's not the direct opposite
		// of the current movement axis. Allows turning 90 degrees.
		
		if (direction == 1 && deltaY <= 0) { // Requesting UP, currently not moving UP
			// Set vertical movement upwards, stop horizontal movement
			deltaY = MOVEMENT_SIZE; 
			deltaX = 0;
		} else if (direction == 2 && deltaY >= 0) { // Requesting DOWN, currently not moving DOWN
			// Set vertical movement downwards, stop horizontal movement
			deltaY = -MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 3 && deltaX >= 0) { // Requesting LEFT, currently not moving LEFT
			// Set horizontal movement left, stop vertical movement
			deltaY = 0;
			deltaX = -MOVEMENT_SIZE;
		} else if (direction == 4 && deltaX <= 0) { // Requesting RIGHT, currently not moving RIGHT
		    // Set horizontal movement right, stop vertical movement
			deltaY = 0;
			deltaX = MOVEMENT_SIZE;
		}
		// If the input is the direct opposite (e.g., pressing Left while moving Right), 
		// or an invalid direction code, the current deltaX/deltaY remain unchanged.
	}


	/**
	 * Moves the snake one step in its current direction (deltaX, deltaY).
	 * Adds a new head segment in the new position.
	 * Removes the tail segment unless the snake grew this turn (ate food).
	 */
	public void move() {
		// If deltaX and deltaY are both 0, the snake hasn't started moving yet.
		// Don't do anything until a direction is set by changeDirection.
		if (deltaX == 0 && deltaY == 0) {
			return; 
		}

		// Get the current head segment to calculate the new position from.
		BodySegment currentHead = segments.getFirst();

		// Calculate the coordinates for the new head segment.
		double newX = currentHead.getX() + deltaX;
		double newY = currentHead.getY() + deltaY;

		// Create the new head segment at the calculated position.
		BodySegment newHead = new BodySegment(newX, newY, SEGMENT_SIZE);
		// Optional: Match the color if you have special head colors
		// newHead.setColor(currentHead.getColor()); 

		// Add the new head to the very beginning (front) of the linked list.
		segments.addFirst(newHead);

		// Check if the snake ate food during the last game loop iteration.
		if (grewThisTurn) {
			// If it grew, simply reset the flag and DO NOT remove the tail.
			// This makes the snake longer by one segment.
			grewThisTurn = false; 
		} else {
			// If it did not grow, remove the last segment (the tail)
			// to simulate the body following the head.
			// This keeps the snake the same length.
            // Ensure there's more than one segment before removing (though with growth this is unlikely to be an issue)
            if (segments.size() > 1) { 
			    segments.removeLast();
            }
		}
	}

	/**
	 * Draws the snake by drawing each segment.
	 */
	public void draw() {
		for (BodySegment segment : segments) {
			segment.draw();
		}
	}

	/**
	 * Checks if the snake's head collided with the given food.
	 * If a collision occurs, sets the flag to grow on the next move.
	 * 
	 * @param f the food object
	 * @return true if the head overlaps the food, false otherwise
	 */
	public boolean eatFood(Food f) {
		BodySegment head = segments.getFirst();
		
		// Simple distance-based collision check (center-to-center)
		double dx = head.getX() - f.getX();
		double dy = head.getY() - f.getY();
		// Optimization: compare squared distance to squared radii sum
		double distanceSquared = dx * dx + dy * dy; 

		double radiiSum = SEGMENT_SIZE + f.getSize();
		double radiiSumSquared = radiiSum * radiiSum;

		// If the distance between centers is less than the sum of the radii, they overlap.
		if (distanceSquared < radiiSumSquared) {
			// Collision! Set the flag so the snake grows during the next move() call.
			this.grewThisTurn = true; 
			return true; // Report that food was eaten
		} else {
			return false; // No collision
		}
	}

	/**
	 * Checks if the head of the snake is within the game boundaries (0.0 to 1.0).
	 * 
	 * @return true if the head is in bounds, false otherwise
	 */
	public boolean isInbounds() {
		BodySegment head = segments.getFirst();
		double headX = head.getX();
		double headY = head.getY();

		// Check if any part of the head circle goes outside the 0.0 to 1.0 range.
		boolean outLeft = headX - SEGMENT_SIZE < 0.0;
		boolean outRight = headX + SEGMENT_SIZE > 1.0;
		boolean outBottom = headY - SEGMENT_SIZE < 0.0;
		boolean outTop = headY + SEGMENT_SIZE > 1.0;

		// If any of these are true, the snake is out of bounds.
		if (outLeft || outRight || outBottom || outTop) {
			return false; 
		}
		
		// Otherwise, it's still in bounds.
		return true;
	}
}