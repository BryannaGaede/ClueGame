//Luke Valentine
//Brianna Gaede

package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private String Initial;
	private DoorDirection doorDirection;
	private static BoardCell instance = new BoardCell();
	public DoorDirection direction;

	

	public BoardCell(int row, int column, String initial) {
		super();
		this.row = row;
		this.column = column;
		this.Initial = initial;
		direction = DoorDirection.NONE;

		if (getInitialFull().length() == 2) {
			if (getInitialFull().charAt(1) == 'U')
				direction = DoorDirection.UP;
			else if (getInitialFull().charAt(1) == 'D')
				direction = DoorDirection.DOWN;
			else if (getInitialFull().charAt(1) == 'R')
				direction = DoorDirection.RIGHT;
			else if (Initial.charAt(1) == 'L')
				direction = DoorDirection.LEFT;
		}

	}

	public BoardCell() {
		// TODO Auto-generated constructor stub
	}

	public enum DoorDirection {
		NONE(0, 0), UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);
		private int x, y;
		DoorDirection(int X, int Y) {
			x = X;
			y = Y;
		}
		public int getRow() {
			return x;
		}
		public int getCol() {
			return y;
		}
	};

	public boolean isDoorway() {

		// System.out.println(doorDirection);
		if (doorDirection != DoorDirection.UP && doorDirection != DoorDirection.DOWN
				&& doorDirection != DoorDirection.LEFT && doorDirection != DoorDirection.RIGHT)
			return false;
		else
			return true;
	}

	public DoorDirection getDoorDirection() {
		return (direction);
	}
	public char getInitial() {
		return Initial.charAt(0);
	}

	public String getInitialFull() {
		return Initial;
	}

	public void setInitial(String letter) {
		this.Initial = new String();
		this.Initial = letter;
		
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
