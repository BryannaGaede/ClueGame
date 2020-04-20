package clueGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	private static final int CIRCLE_RADIUS = 7;
	protected String playerName;
	protected int row;
	protected int column;
	protected Color color;
	protected ArrayList<Card> myCards = new ArrayList<Card>();
	private ArrayList<Card> seenCards = new ArrayList<Card>();
	protected Status status = Status.NONE;
	protected boolean isInRoom = false;
	protected char lastVisitedRoom = ' ';
	private Solution recentSuggestion = new Solution("","","");
	private ArrayList<Card> allCards = Board.getCards();
	
	public Player(String name, int row2, int col, String color) {
		this.playerName = name;
		this.row = row2;
		this.column = col;
		this.color = convertColor(color);
		this.isInRoom = roomState(row2,col);
		//reset all cards to unseen
		for(Card card : allCards) {
			card.setStatus(false);
		}
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
		ArrayList<Card> possibleGives = new ArrayList<Card>();
		for(Card mine : myCards) {
			if(mine.getName().equals(suggestion.person) || mine.getName().equals(suggestion.room) || mine.getName().equals(suggestion.weapon)) {
				possibleGives.add(mine);
			}
		}
		if(possibleGives.size()>0) {
			Random rand = new Random();
			int nextIndex = rand.nextInt(possibleGives.size());
			return possibleGives.get(nextIndex);
		} else {
			return null;
		}
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

	public void createSuggestion() {
		recentSuggestion.room = Board.getRoomName(Board.getCellAt(row, column).getFirstInitial());
		ArrayList<Card> possiblePlayers = new ArrayList<Card>();
		ArrayList<Card> possibleWeapons = new ArrayList<Card>();
		if(seenCards.size()>0) {
			//mark seen cards as seen, my cards need to be marked too, but omitted for testing
			for(Card seen : seenCards) {
				for(Card all : allCards) {
					if(seen.getName().equals(all.getName())) {
						all.setStatus(true);
					}
				}
			}
			//store available cards:
			for(Card all : allCards) {
				if(all.getStatus() == false) {
					if(all.getType() == CardType.PERSON) {
						possiblePlayers.add(all);
					} else if(all.getType() == CardType.WEAPON) {
						possibleWeapons.add(all);
					}
				}
			}
		} else {
			for(Card all : allCards) {
				if(all.getType() == CardType.PERSON) {
					possiblePlayers.add(all);
				} else if(all.getType() == CardType.WEAPON) {
					possibleWeapons.add(all);
				}
			}
		}

		//pick a weapon and a player
		Random rand = new Random();
		int nextIndex = rand.nextInt(possiblePlayers.size());
		recentSuggestion.person = possiblePlayers.get(nextIndex).getName();
		nextIndex = rand.nextInt(possibleWeapons.size());
		recentSuggestion.weapon = possibleWeapons.get(nextIndex).getName();
		
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

	public void draw(Graphics g, int cellSize) {
		int x = row;
		int y = column;
		g.setColor(getColor());
		g.drawOval(y*cellSize, x*cellSize, CIRCLE_RADIUS*2,CIRCLE_RADIUS*2);
		g.fillOval(y*cellSize, x*cellSize, CIRCLE_RADIUS*2,CIRCLE_RADIUS*2);
		
	}
	
	public void makeMove(BoardCell target) {
		column = target.getColumn();
		row = target.getRow();
	}
	/*
	 * ********************USED FOR TESTING*************************************
	 */
	
	public void removeCard (Card card) {
		for (Card x: myCards) {
			if (x == card) {
				myCards.remove(card);
			}
		}
	}
	
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

	public void viewCard(Card seen) {
		seenCards.add(seen);
	}

	public Solution getSolution() {
		return null;
	}

	public Object getLastVisited() {
		return lastVisitedRoom;
	}

	public Solution getSuggestion() {
		return recentSuggestion;
	}

	public String getRoom() {
		return Board.getRoomName(Board.getCellAt(row, column).getFirstInitial());
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}

	public void clearSeen() {
		seenCards = new ArrayList<Card>();
	}

	public void setStatus(char playerStatus) {
		if (playerStatus == 'C') {
			status = Status.COMPUTER;
		}
		else {
			status = Status.HUMAN;
		}
	}
	

	public int getColumn() {
		return column;
	}

	



	}
