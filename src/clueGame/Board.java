package clueGame;

import java.awt.Graphics;
import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

import clueGame.BoardCell;
import clueGame.BoardCell.DoorDirection;

public class Board extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final int MAX_BOARD_SIZE = 50;
	private static int numRows = 26;
	private static int numColumns = 26;
	public final static int NUM_PLAYERS = 6;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private boolean fullConfig = false;

	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<>();
	private HashSet<BoardCell> targets = new HashSet<BoardCell>();
	private HashSet<BoardCell> visited = new HashSet<BoardCell>();
	private Map<Character, String> legend;
	//was #rows and #colms
	public static BoardCell board[][] = new BoardCell[numRows][numColumns];

	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	private static ArrayList<Room> rooms = new ArrayList<Room>();
	private static ArrayList<Card> allCards = new ArrayList<Card>();
	//new
	private static Solution theAnswer;
	private Card disproveCard;
	private String answer; 
	
	//variables to mark player turns
	private static int playerIndex = 0;
	private static boolean gameBegun = false;
	private static int dieRoll;


	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one copy
	Board() {
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		//I kept separate try catches so I could tell which file errored
		try {
			loadRoomConfig(roomConfigFile);
		} catch (IOException e) {
			System.out.println("Trouble loading roomConfigFile - txt");
		}
		try {
			loadBoardConfig(boardConfigFile);
		} catch (IOException e) {
			System.out.println("Trouble loading boardConfigFile - csv");
		}
		//if statement keeps old tests working
		if(fullConfig) {
			try {
				loadPlayerConfig(playerConfigFile);
			} catch (IOException e) {
				System.out.println("Trouble loading playerConfigFile");
			}
			try {
				loadWeaponConfig(weaponConfigFile);
			} catch (IOException e) {
				System.out.println("Trouble loading weaponConfigFile");
			}
		}

		calcAdjacencies();
		if(fullConfig) {
			buildDeck();
			dealCards();
		}
	}
	
	/*
	 * Play game using gui after gui is printed after board is initialized
	 */
	

	public static void handleNextPlayer() {
		//if this is the very first turn, roll the die and start the game
		if(!gameBegun) {
			rollDie();
			gameBegun = true;
			System.out.println("first turn");
			//otherwise check if a human player's turn is complete and go to next player
		} else {
			//move to next player
			playerIndex = (playerIndex+1) % NUM_PLAYERS;
			//update the gui to change to the next player
		}
		/*clicking next player does the following:
		 * checks if human player turn is done
		 * rolls dice
		 * starts next player turn
		 */
		
		/*starting next player turn does the following:
		 * roll die
		 * calc targets
		 * check status
		 */
		
		/*status is computer
		 * accuse?
		 * make move
		 * suggest
		 * disprove
		 * 
		 * status is human
		 * prompt if accusation is wanted
		 * show targets
		 * take input for target
		 * suggest
		 * disprove
		 */
	}

	public static void handleAccusationRequestFromHumanPlayer() {
		System.out.println("Button Pressed to Accuse");	
	}
	
	/*
	 * ***********************Painting***********************
	 */

	@Override
	public void paintComponent(Graphics cell) {
		super.paintComponent(cell);
		//each board cell prints it out
		for(int row = 0; row < numRows; row ++) {
			for(int col = 0; col < numColumns; col ++) {
				board[row][col].draw(cell);
			}
		}
		//paint names of rooms on top of colored room cells
		for(int row = 0; row < numRows; row ++) {
			for(int col = 0; col < numColumns; col ++) {
				if(board[row][col].doPrint()) {
					board[row][col].printName(cell);
				}
			}
		}
		//paint players on top of board cells
		for (Player p: players) {
			p.draw(cell, 20);
		}	
	}
	
	/*
	 * ***********************SET UP CARDS AND PLAYERS***********************
	 */

	public void buildDeck() {
		//make cards for all rooms

		for(Room room : rooms) {
			if(!room.getName().equals("Closet")) {
				Card card = new Card(CardType.ROOM, room.getName());
				allCards.add(card);
			}
		}
		//make cards for all players
		for(Player player: players) {
			Card card = new Card(CardType.PERSON, player.getName());
			allCards.add(card);
		}
		//make cards for each weapon
		for(Weapon weapon: weapons) {
			Card card = new Card(CardType.WEAPON, weapon.getName());
			allCards.add(card);
		}
	}

	public void dealCards() {
		selectAnswer();
		//give card at random index to players until all cards are used
		int cardsLeftToDeal = allCards.size()-3;
		int currentPlayer = players.size();
		//count down until all cards are dealt
		while(cardsLeftToDeal > 0) {
			Random rand = new Random();
			int nextIndex = rand.nextInt(allCards.size());
			if(allCards.get(nextIndex).isDealt()) {
				continue;
			} else {
				//only increase the index of the player being dealt if the card gets dealt
				currentPlayer = (currentPlayer+1) % players.size();
				players.get(currentPlayer).myCards.add(allCards.get(nextIndex));
				allCards.get(nextIndex).setStatus(true);
				cardsLeftToDeal -= 1;
			}
		}

	}

	public void selectAnswer() {
		//select a room (index 0 through numRooms)
		Random rand = new Random();
		int nextIndex1 = rand.nextInt(rooms.size()-1);
		int nextIndex2 = rand.nextInt(players.size());
		int nextIndex3 = rand.nextInt(weapons.size());
		theAnswer = new Solution(allCards.get(nextIndex2+rooms.size()).getName(), 
				allCards.get(nextIndex1).getName(), 
				allCards.get(nextIndex3+rooms.size()+players.size()-1).getName());
		allCards.get(nextIndex1).setStatus(true);
		//select a person (index numRooms+1 through numRooms + numPeople)
		allCards.get(nextIndex2+rooms.size()).setStatus(true);
		//select a weapon (index numRooms+numPeople +1 through numCards)
		//nextIndex = rand.nextInt(weapons.size());
		allCards.get(nextIndex3+rooms.size()+players.size()-1).setStatus(true);
	}

	public static Card handleSuggestion(Solution playerSuggestion, Player currentPlayer) {
		for(Player player : players) {
			if(player.disproveSuggestion(playerSuggestion)!=null && player != currentPlayer) {
				return player.disproveSuggestion(playerSuggestion);
			}
		}
		return null;
	}

	public boolean checkAccusation(Solution accusation) {
		String accusationStr = accusation.person + accusation.room + accusation.weapon;
		if (answer.contentEquals(accusationStr)) {
			return true;
		}
		else {
			return false;
		}
		
	}


	/*
	 * ***********************SET UP BOARD*****************************
	 */

	public void calcAdjacencies() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				HashSet<BoardCell> adjSet = new HashSet<BoardCell>();
				BoardCell testCell = new BoardCell();
				BoardCell startCell = new BoardCell();
				startCell = board[row][col];
				// up
				if (row > 0) {
					testCell = board[row - 1][col];
					if (adjHelp(testCell, startCell))
						adjSet.add(board[row - 1][col]);
				}
				// down
				if (numRows - 1 > row) {
					testCell = board[row + 1][col];
					if (adjHelp(testCell, startCell))
						adjSet.add(board[row + 1][col]);
				}
				// right
				if (col < numColumns - 1) {
					testCell = board[row][col + 1];
					if (adjHelp(testCell, startCell))
						adjSet.add(board[row][col + 1]);
				}
				// left
				if (col > 0) {
					testCell = board[row][col - 1];
					if (adjHelp(testCell, startCell))
						adjSet.add(board[row][col - 1]);
				}
				// push the adj list and corresponding cell to map
				adjMatrix.put(getCellAt(row, col), adjSet);
			}
		}
	}

	public HashSet<BoardCell> calcTargets(int row, int col, int dieRoll) {
		// create new hashset
		BoardCell startCell = getCellAt(row, col);
		BoardCell originalStart = new BoardCell();
		originalStart = getCellAt(row, col);
		HashSet<BoardCell> foundTargets = new HashSet<BoardCell>();
		// clear the old one
		targets.clear();
		foundTargets = findAllTargets(startCell, dieRoll, originalStart);
		// remove the startCell if it exists (illegal move)
		foundTargets.remove(startCell);
		return (foundTargets);
	}

	public HashSet<BoardCell> findAllTargets(BoardCell startCell, int stepsRemaining, BoardCell startingSquare) {
		// go through every adj cell
		for (BoardCell testCell : getAdjList(startCell.getRow(), startCell.getColumn())) {
			if (testCell.getRow() == startingSquare.getRow() && testCell.getColumn() == startingSquare.getColumn()) {
				visited.add(startingSquare);
				continue;
			}
			if (testCell.isDoorway() && isGoodDoor(testCell, startCell)) {
				targets.add(testCell);
				continue;
			}
			if (visited.contains(testCell)) {
				continue;
			}
			// add to visited
			visited.add(testCell);
			// if numsteps are 1 we have reached are target
			if (stepsRemaining == 1) {
				// System.out.println(b.getInitialFull());
				targets.add(testCell);
			}
			// else call recursive
			else if (!testCell.isDoorway()) {
				findAllTargets(testCell, stepsRemaining - 1, startingSquare);
			}
			visited.remove(testCell);
		}
		// return them targets
		return (targets);
	}

	/*
	 * *************************HELPER FUNCTIONS*************************************
	 */
	

	private static void rollDie() {
		Random rand = new Random();
		dieRoll = rand.nextInt(6)+1;
		
	}

	public boolean adjHelp(BoardCell testCell, BoardCell startCell) {
		// if the cell is not a doorway and it has two initials don't enter
		// if it is not an exit and don't leave the room
		// if you are on a walkway only enter if door
		// if it is a walkway you can keep walking on the walkway
		if ((!(testCell.isDoorway() && isGoodDoor(testCell, startCell)) && testCell.getInitials().length() == 2)
				|| ((!isExitSquare(testCell, startCell)) && startCell.getInitials().length() == 2)
				|| ((isSameRoom(testCell, startCell) && startCell.getFirstInitial() != 'W'))
				|| (startCell.getFirstInitial() == 'W' && testCell.getFirstInitial() != 'W'
				&& testCell.getInitials().length() != 2)
				|| (startCell.getFirstInitial() != 'W' && testCell.getFirstInitial() == 'W'
				&& testCell.getInitials().length() != 2 && startCell.getInitials().length() != 2)) {
			return false;
		}
		// otherwise you can not move
		else {
			return true;
		}
	}

	// checks if target is the right placement to exit based on starting origin
	// being a door and it's direction
	public boolean isExitSquare(BoardCell target, BoardCell origin) {
		// a walkway above a door with direction up
		if (origin.getRow() > target.getRow() && origin.getDoorDirection() == DoorDirection.UP) {
			return true;
		}
		// a walkway below a door with direction down
		else if (origin.getRow() < target.getRow() && origin.getDoorDirection() == DoorDirection.DOWN) {
			return true;
		}
		// a walkway to the left of a door with direction left
		else if (origin.getColumn() > target.getColumn() && origin.getDoorDirection() == DoorDirection.LEFT) {
			return true;
		}
		// a walkway to the right of a door with direction right
		else if (origin.getColumn() < target.getColumn() && origin.getDoorDirection() == DoorDirection.RIGHT) {
			return true;
		} else {
			return false;
		}
	}

	// checks door direction and adds to targets if it works
	public boolean isGoodDoor(BoardCell target, BoardCell origin) {
		// a cell under a door, and door direction is up
		if (origin.getRow() < target.getRow() && target.getDoorDirection() == DoorDirection.UP) {
			return true;
		}
		// walkway is above door and door direction is down
		else if (origin.getRow() > target.getRow() && target.getDoorDirection() == DoorDirection.DOWN) {
			return true;
		}
		// walkway is to the left of door and door direction is left
		else if (origin.getColumn() < target.getColumn() && target.getDoorDirection() == DoorDirection.LEFT) {
			return true;
		}
		// walkway is to the right of door and door direction is right
		else if (origin.getColumn() > target.getColumn() && target.getDoorDirection() == DoorDirection.RIGHT) {
			return true;
		} else {
			return false;
		}
	}

	// checks to see if the target has the same initial as the origin cell
	public boolean isSameRoom(BoardCell target, BoardCell origin) {
		if (origin.getFirstInitial() == target.getFirstInitial()) {
			return true;
		} else {
			return false;
		}
	}

	// checks if the symbol is a closet
	public boolean isCloset(BoardCell target) {
		if (target.getFirstInitial() == 'X') {
			return true;
		} else {
			return false;
		}
	}


	public static boolean isRoom(int row, int col) {
		if(getCellAt(row,col).getFirstInitial() != 'X' && getCellAt(row,col).getFirstInitial() != 'W') {
			return true;
		} else {
			return false;

		}
	}

	/*
	 * ********************CONFIGURATION METHODS********************************
	 */

	public void setCardConfigFiles(String txtPlayer, String txtWeapon) {
		fullConfig = true;
		playerConfigFile = txtPlayer;
		weaponConfigFile = txtWeapon;
	}
	public void setConfigFiles(String csv, String txt) {
		boardConfigFile = csv;
		roomConfigFile = txt;
	}

	public void loadPlayerConfig(String player_txt) throws IOException{
		String line = "";
		String name = "";
		String color = "";
		int row = 0;
		int col = 0;
		char status = ' ';
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(player_txt), "UTF-8"));
		while((line= br.readLine()) != null) {
			//player name
			name = line;
			//player color
			if((line = br.readLine()) == null) {
				System.out.println("File formatted wrong");
			} else {
				color = line;
			}
			//player start row
			if((line = br.readLine()) == null) {
				System.out.println("File formatted wrong");
			} else {
				row = Integer.parseInt(line);
			}
			//player start column
			if((line = br.readLine()) == null) {
				System.out.println("File formatted wrong");
			} else {
				col = Integer.parseInt(line);
			}
			//human or computer option:
			if((line = br.readLine()) == null) {
				System.out.println("File formatted wrong");
			} else {
				status = line.charAt(0);
			}
			//create that player object store in players array list
			if(status == 'C') {
				Player player = new ComputerPlayer(name,row,col,color);
				players.add(player);
			} else if(status == 'H') {
				Player player = new HumanPlayer(name,row,col,color);
				players.add(player);
			}
		}
		br.close();
	}

	public void loadWeaponConfig(String weapon_txt) throws IOException{
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(weapon_txt), "UTF-8"));	
		while((line= br.readLine()) != null) {
			Weapon weapon = new Weapon(line);
			weapons.add(weapon);
		}
		br.close();
	}

	public void loadRoomConfig(String legend_txt) throws IOException {
		String line = "";
		String splitBy = ", ";
		String[] lines;
		legend = new HashMap<Character, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(legend_txt), "UTF-8"));
		while ((line = br.readLine()) != null) {
			lines = line.split(splitBy);
			char x = lines[0].charAt(0);
			legend.put(x, lines[1]);
			Room room = new Room(lines[0].charAt(0),lines[1], lines[2]);
			rooms.add(room);
		}
		br.close();
	}

	public void loadBoardConfig(String layout_csv) throws IOException {
		// set up file reader for csv
		BufferedReader in = new BufferedReader(new FileReader(layout_csv));
		String line;
		int current_row = 0;
		int current_column = 0;
		// used to make sure there are the same number of columns in each row
		int number_of_itterations = 0;
		int cols_seen = 0;
		while ((line = in.readLine()) != null) {
			String[] initials = line.split(",");
			for (String cell_initials : initials) {
				board[current_row][current_column] = new BoardCell(current_row, current_column, cell_initials);
				current_column++;
			}
			if (current_column != cols_seen) {
				if (number_of_itterations == 0) {
					cols_seen = current_column;
					number_of_itterations += 1;
				} else {
					System.out.println("CSV file has columns of uneven length");
				}
			}
			current_column = 0;
			current_row++;
		}
		in.close();
		numRows = current_row;
		numColumns = cols_seen;
	}

	/*
	 ************************** GETTERS AND SETTERS *********************************
	 */

	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMatrix.get(getCellAt(row, col));
	}

	public static BoardCell getCellAt(int row, int col) {
		// return the corresponding2D array
		return board[row][col];
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/*
	 * ***********************USED FOR TESTING***************************************
	 */

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public static ArrayList<Player> getPlayers() {
		return players;
	}

	public static ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	public Solution getSolution() {
		return (theAnswer);
	}

	public static ArrayList<Card> getCards() {
		return allCards;
	}

	public boolean cardExists(CardType type, String name) {
		for(Card card : allCards) {
			if(card.getType() == type && card.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public int cardCount(CardType type) {
		int counter = 0;
		for(Card card : allCards) {
			if(card.getType() == type) {
				counter += 1;
			}
		}
		return counter;
	}

	public boolean noDuplicates(Card card) {
		int counter = 0;
		for(Player player : players) {
			if(player.hasCard(card)) {
				counter += 1;
			}
		}
		if(counter > 1) {
			return false;
		} else return true;
	}

	public CardType getType(String name) {
		CardType type = CardType.NONE;
		for(Card card : allCards) {
			if(card.getName().equals(name)) {
				type = card.getType();
			}
		}
		return type;
	}

	public void setSolution(String person, String room, String weapon) {

	}

	public Object getDisproveCard() {
		return disproveCard;
	}

	public void setCards(String player, String cardName, CardType type) {

	}

	public void setAnswer(String person, String room, String weapon) {
		answer = person+room+weapon;
	}

	public static String getRoomName(char firstInitial) {
		for(Room room : rooms) {
			if(room.getInitial() == firstInitial) {
				return room.getName();
			}
		}
		return null;
	}

	public static void setAnswer(Solution fakeAnswer) {
		theAnswer = fakeAnswer;		
	}

	public static void addPlayer(Player testPlayer) {
		players.add(testPlayer);
	}

	public static ArrayList<Room> getRooms() {
		// TODO Auto-generated method stub
		return rooms;
	}

	public BoardCell[][] getBoard() {
		return board;
	}

	public static int getPlayerIndex() {
		return playerIndex;
	}



	

	

}
