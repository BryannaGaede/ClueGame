//Luke Valentine
//Brianna Gaede

package clueGame;

public class BoardCell  {
	private int row;
	private int column;
	private DoorDirection doorDirection;
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
		if (doorDirection == DoorDirection.NONE)
			return false;
		else
			return true;
	}
	public Object getDoorDirection() {
		
		return (doorDirection);
	}
	public Object getInitial() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
}
	
