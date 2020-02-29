//Luke Valentine
//Brianna Gaede

package clueGame;

public class BoardCell  {
	private int row;
	private int column;
	public String Initial;
	private DoorDirection doorDirection;
	
	public String getInitial() {
		return Initial;
	}
	public void setInitial(String letter) {
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
		if (Initial.length() == 2) {
			if (Initial.charAt(1) == 'U')
				doorDirection = DoorDirection.UP;
			else if (Initial.charAt(1) == 'D')
				doorDirection = DoorDirection.DOWN;
			else if (Initial.charAt(1) == 'R')
				doorDirection = DoorDirection.RIGHT;
			else if (Initial.charAt(1) == 'L')
				doorDirection = DoorDirection.LEFT;
			else
				doorDirection = DoorDirection.NONE;
		}
		System.out.println(doorDirection);
		if (doorDirection == DoorDirection.NONE)
			return false;
		else
			return true;
	}
	
	
	public Object getDoorDirection() {
		
		return (doorDirection);
	}
	
	

	
	
}
	
