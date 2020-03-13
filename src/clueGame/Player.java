package clueGame;

import java.awt.Color;

import java.lang.reflect.Field;

public class Player {
	protected String playerName;
	protected int row;
	protected int column;
	protected Color color;
	protected Card[] myCards;
	protected Card[] seenCards;
	protected Status status = Status.NONE;
	
	public Player(String name, int row2, int col, String color) {
		this.playerName = name;
		this.row = row2;
		this.column = col;
		this.color = convertColor(color);
	}

	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public Color convertColor(String strColor) {     
		Color color;      
		try {              
			// We can use reflection to convert the string to a color         
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());         
			color = (Color)field.get(null);    
			} catch (Exception e) {           
				color = null;
				// Not defined, but we'll call it purple     
				}
		return color; 
		}

	/*
	 * ********************USED FOR TESTING*************************************
	 */
	public String getName() {
		return this.playerName;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}

	public Color getColor() {
		return color;
	}

	public Status getStatus() {
		return status;
	} 
	}
