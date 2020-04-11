package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Room extends BoardCell {
	private char initial;
	private String name;
	private String type;
	
	public Room(char initial, String name, String type) {
		super();
		this.initial = initial;
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public char getInitial() {
		return initial;
	}
	
	public String getType() {
		return type;
	}
	
	public void draw(Graphics g, Board c, int cellSize) {
		// Checks to see if it is a target and highlights it

		g.setColor(Color.gray);
		g.fillRect(getColumn()*cellSize, getRow()*cellSize, cellSize, cellSize/4);
		
		
	}
	
}
