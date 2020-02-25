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
	
	public Board getInstance() {
		return null;
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
}
