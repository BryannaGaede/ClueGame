package clueGame;

import java.awt.Color;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Player {
	protected String playerName;
	protected int row;
	protected int column;
	protected Color color;
	protected ArrayList<Card> myCards = new ArrayList<Card>();
	protected ArrayList<Card> seenCards = new ArrayList<Card>();
	protected Status status = Status.NONE;
	protected boolean isInRoom = false;
	protected char lastVisitedRoom = ' ';
	
	public Player(String name, int row2, int col, String color) {
		this.playerName = name;
		this.row = row2;
		this.column = col;
		this.color = convertColor(color);
		this.isInRoom = roomState(row2,col);
	}

	private boolean roomState(int row, int col) {
		if(Board.isRoom(row,col)) {
			lastVisitedRoom = Board.getCellAt(row,col).getFirstInitial();
			return true;
		}
		else {
			return false;
		}
	}

	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	

	public void setLocation(Set<BoardCell> targets) {
		boolean noRooms = true;
		ArrayList<BoardCell> givenCells = new ArrayList<BoardCell>();
		for(BoardCell target : targets) {
			if(target.isDoorway() || target.isRoom()) {
				noRooms = false;
			}
			givenCells.add(target);
		}
		//random choice no rooms
		if(noRooms) {
			Random rand = new Random();
			int nextIndex = rand.nextInt(targets.size());
			row = givenCells.get(nextIndex).getRow();
			column = givenCells.get(nextIndex).getColumn();
		} 
		else {
			//unseen room must go
			boolean unvisitedRoom = false;

			for(BoardCell target : targets) {
				if((target.isDoorway()  || target.isRoom()) && target.getFirstInitial() != lastVisitedRoom) {
					row = target.getRow();
					column = target.getColumn();
					lastVisitedRoom = target.getFirstInitial();
					unvisitedRoom = true;
					break;
				}
			}
			//just visited chosen random
			if(!unvisitedRoom) {
				Random rand = new Random();
				int nextIndex = rand.nextInt(targets.size());
				row = givenCells.get(nextIndex).getRow();
				column = givenCells.get(nextIndex).getColumn();
			}
		}
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

	public void setLocation(int row, int col) {
		this.row = row;
		this.column = col;
	}

	public void addCard(Card testCard) {
		myCards.add(testCard);		
	}

	public Solution getSolution() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getLastVisited() {
		return lastVisitedRoom;
	}

	}
