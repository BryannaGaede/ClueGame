package clueGame;

import java.util.Map;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 25;
	private BoardCell[][] board;
	private Map<Character,String> legend;
	private Map<BoardCell,Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	
	// variable used for singleton pattern
		private static Board theInstance = new Board();
		// constructor is private to ensure only one can be created
		private Board() {}
		// this method returns the only Board
		public static Board getInstance() {
			return theInstance;
		}
	
	
	public void initialize() {
		
	}
	public void loadRoomConfig() {
		
	}
	public void loadBoardConfig() {
		
	}
	public void calcAdjacencies() {
		
	}
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	
	//getters and setters
	public int getNumRows() {
		return numRows;
	}
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}
	public BoardCell[][] getBoard() {
		return board;
	}
	public void setBoard(BoardCell[][] board) {
		this.board = board;
	}
	public Map<Character, String> getLegend() {
		return legend;
	}
	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}
	public Map<BoardCell, Set<BoardCell>> getAdjMatrix() {
		return adjMatrix;
	}
	public void setAdjMatrix(Map<BoardCell, Set<BoardCell>> adjMatrix) {
		this.adjMatrix = adjMatrix;
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public void setTargets(Set<BoardCell> targets) {
		this.targets = targets;
	}
	public String getBoardConfigFile() {
		return boardConfigFile;
	}
	public void setBoardConfigFile(String boardConfigFile) {
		this.boardConfigFile = boardConfigFile;
	}
	public String getRoomConfigFile() {
		return roomConfigFile;
	}
	public void setRoomConfigFile(String roomConfigFile) {
		this.roomConfigFile = roomConfigFile;
	}
	public int getMAX_BOARD_SIZE() {
		return MAX_BOARD_SIZE;
	}
	
	
}
