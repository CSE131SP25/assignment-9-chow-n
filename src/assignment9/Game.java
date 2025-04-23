package assignment9;

import java.awt.Color;
import java.awt.event.KeyEvent;

import edu.princeton.cs.introcs.StdDraw;

public class Game {
	private Snake snake;
	private Food food;
	private int score; // Added for scoring feature
    private boolean gameOver; 
	
	public Game() {
		StdDraw.enableDoubleBuffering();
		
		snake = new Snake();
		food = new Food();
		score = 0; // Initialize score
        gameOver = false; // Game starts running
	}
	
	public void play() {
		while (!gameOver) { 
			// 1. Handle User Input & Change Direction
			int dir = getKeypress();
			if (dir != -1) {
				snake.changeDirection(dir);
			}

			// 2. Check for Eating
			boolean ateFood = snake.eatFood(food);

			// 3. Move the Snake (incorporates growth if ateFood was true)
			snake.move();

			// 4. Check Game Over Conditions
            // Check bounds first
			if (!snake.isInbounds()) {
				gameOver = true;
			}
			if (ateFood && !gameOver) { // Don't create new food if game just ended
				food = new Food();
				score++; // Increment score
			}

			// 6. Update the drawing
			updateDrawing();
		}
        
        // --- Game Over Screen ---
        	displayGameOver();

	}
			/*
			 * 1. Pass direction to your snake
			 * 2. Tell the snake to move
			 * 3. If the food has been eaten, make a new one
			 * 4. Update the drawing
			 */
	
	private int getKeypress() {
		if(StdDraw.isKeyPressed(KeyEvent.VK_W)) {
			return 1;
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
			return 2;
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
			return 3;
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_D)) {
			return 4;
		} else {
			return -1;
		}
	}
	
	/**
	 * Clears the screen, draws the snake and food, pauses, and shows the content
	 */
	private void updateDrawing() {
		StdDraw.clear(StdDraw.LIGHT_GRAY); // Use a light background

		// 2. Draw snake and food
		snake.draw();
		food.draw();

		// 3. Draw Score (Extra Feature)
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(0.1, 0.95, "Score: " + score); // Position score top-left

		// 4. Pause (50-100 ms is good, adjust for desired speed)
		StdDraw.pause(80); // Slower speed might be easier initially

		// 5. Show
		StdDraw.show();
		
		/*
		 * 1. Clear screen
		 * 2. Draw snake and food
		 * 3. Pause (50 ms is good)
		 * 4. Show
		 */
	}
	
	private void displayGameOver() {
        StdDraw.setPenColor(Color.RED);
        StdDraw.setFont(StdDraw.getFont().deriveFont(30f)); // Make font larger
        StdDraw.text(0.5, 0.6, "Game Over!");
        StdDraw.setFont(StdDraw.getFont().deriveFont(20f)); // Slightly smaller font for score
        StdDraw.text(0.5, 0.4, "Final Score: " + score);
        StdDraw.show(); // Show the final screen
    }
	
	public static void main(String[] args) {
		Game g = new Game();
		g.play();
	}
}
