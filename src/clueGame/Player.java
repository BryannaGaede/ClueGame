package clueGame;

import java.awt.Color;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

public class Player {
	protected String playerName;
	protected int row;
	protected int column;
	protected Color color;
	protected ArrayList<Card> myCards = new ArrayList<Card>();
	protected ArrayList<Card> seenCards = new ArrayList<Card>();
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

	public boolean hasCard(Card test) {
		for(Card card : myCards) {
			if(card == test) {
				return true;
			}
		}
		return false;
		}

	public int getCardCount() {
		return myCards.size();
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public void setLocation(Set<BoardCell> targets) {
		// TODO Auto-generated method stub
		//random choice no rooms
		//unseen room must go
		//just visited chosen random
		
	}

	public void setLocation(int row, int col) {
		this.row = row;
		this.column = col;
	}

	}
