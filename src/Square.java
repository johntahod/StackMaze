
/**
 * @author John Tahod
 * Square Class for Grid
 *
 */
public class Square {
	
	int row, col; 

	/** Constructor to make square at row,col
	 * @param row
	 * @param col
	 */
	public Square(int row, int col) {
		this.row = row; 
		this.col = col;
	}
	/**
	 * @return row of square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return col of square
	 */
	public int getCol() { 
		return col;
	}

	public void print() {
		System.out.println("(" + row+ "," + col + ")");
	}
	/**
	 * @return square pos  move up
	 */
	public Square moveUp() {
		
		return new Square(row-1, col);
	}
	
	/**
	 * @return square pos move down
	 */
	public Square moveDown() {
		
		return new Square(row+1, col);
	}
	
	/**
	 * @return square pos move right
	 */
	public Square moveRight() {
		return new Square(row, col+1);
	}
	
	/**
	 * @return square pso move left
	 */
	public Square moveLeft() {
		return new Square(row, col-1);
	}


}
