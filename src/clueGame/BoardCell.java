//Luke Valentine
//Brianna Gaede

package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private  String initial;
	private DoorDirection doorDirection;

	public BoardCell(int row, int column, String initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;

		if (initial.length() == 2) {
			if (initial.charAt(1) == 'U') {
				this.doorDirection = DoorDirection.UP;
			}
			else if (initial.charAt(1) == 'D') {
				this.doorDirection = DoorDirection.DOWN;
			}
			else if (initial.charAt(1) == 'R') {
				this.doorDirection = DoorDirection.RIGHT;
			}
			else if (initial.charAt(1) == 'L') {
				this.doorDirection = DoorDirection.LEFT;
			}
			else {
				this.doorDirection = DoorDirection.NONE;
			}
		} else {
			this.doorDirection = DoorDirection.NONE;
		}
	}

	public BoardCell() {
	}

	public static enum DoorDirection {

		NONE(0,0),UP(-1,0),DOWN(1,0),LEFT(0,-1),RIGHT(0,1);
		private int x,y;
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
		if (doorDirection == DoorDirection.NONE) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isRoom() {
		if(initial.charAt(0) != 'W' && initial.charAt(0) != 'F') {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", Initial=" + initial + ", doorDirection="
				+ doorDirection + "]";
	}

	/*
	 * ***********************GETTERS AND SETTERS****************************
	 */


	public DoorDirection getDoorDirection() {
		if (doorDirection == DoorDirection.UP) {
			return DoorDirection.UP;
		}
		else if(doorDirection == DoorDirection.DOWN) {
			return DoorDirection.DOWN;
		}
		else if(doorDirection == DoorDirection.LEFT) {
			return DoorDirection.LEFT;
		}
		else if(doorDirection == DoorDirection.RIGHT) {
			return DoorDirection.RIGHT;
		}
		return (doorDirection);
	}

	public char getFirstInitial() {
		return initial.charAt(0);
	}

	public String getInitials() {
		return initial;
	}

	public void setInitials(String letter) {
		this.initial = new String();
		this.initial = letter;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}

