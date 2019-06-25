import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;



/**
 * @author John Tahod
 * Program solves a maze/grid and using 
 * a LinkedStack to push and pop positions until
 * it reaches the Exit(Row,Col)
 *
 */
public class Grid {

	private char[][] mazeGrid;
	public static int startX,startY ;
	char openSpace ='1';
	int numCols;
	int numRows;
	public static int exitRow;
	public static  int exitCol;



	/**
	 * @param filename get file path, make sure its in src folder of this proj.
	 * @throws FileNotFoundException
	 * 
	 * makes a grid according to file , making the '1''s into empty spaces 
	 */
	public Grid(String filename) throws FileNotFoundException
	{
		int count = 0;
		File mazeFile = new File(filename);
		Scanner scan1 = null;
		try 
		{
			scan1 = new Scanner(mazeFile);
		} catch (FileNotFoundException e) 
		{
			System.out.println("File was not found");
		}

		String yy = scan1.nextLine();//read the first line
		String xx = scan1.nextLine(); //read the second line
		Scanner scan12 = new Scanner(yy);
		Scanner scan123 = new Scanner(xx);


		/*get grid size
		 * start point as well
		 * 
		 * */
		numRows = scan12.nextInt(); //get the number of rows
		numCols = scan12.nextInt(); //get the number of cols
		startX = scan123.nextInt(); //get the start pointrow of rows
		startY = scan123.nextInt(); //get the start pointcol of cols


		mazeGrid = new char [numRows+1][numCols+1];


		//get all positions and make squares and add to grid
		while(scan1.hasNext())
		{
			int i;
			String line = scan1.nextLine();
			System.out.println(line);
			for( i = 0; i < line.length(); i++) //for each character in the string line
			{

				//make copy of elements in file on put in 2d array 
				mazeGrid[count][i]=line.charAt(i);

				if(mazeGrid[count][i]=='1') {
					mazeGrid[count][i]= ' ';

				}
				//find exit coordinates
				if(mazeGrid[count][i]=='E') {
					exitRow = count;
					exitCol = i;

					//found exit
					//System.out.println("exit foundat "+ exitRow +" " +exitCol);
				}
			}
			count++;
			if(count == numRows ); //Once we have gotten to the end of the maze
			{
				System.out.println("finish reading n storing");
			}
		}
	}

	/**
	 * @return size of the maze/grid
	 *
	 */
	public int size() {
		return mazeGrid.length;
	}

	/**prints grid
	 * 
	 */
	public void print() {
		for (int i=0; i<size(); i++) {
			for (int j=0; j<size(); j++) {
				System.out.print(mazeGrid[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}
	public String printCourse() {

		StringBuffer text = new StringBuffer();


		for (int i=0; i<size(); i++) {
			for (int j=0; j<size(); j++) {
				text.append(mazeGrid[i][j]);
				//text.append(' ');
			}
			//System.out.println();
		
			text.append("\n");
		
		//return text+ "\n";
		}
		return text+ "\n";

	}
		

	/**
	 * @param i cur row
	 * @param j cur col
	 * @param value character to mark been here
	 * @return char to mark
	 */
	public char markPos(int i, int j, char value) {
		assert(isPosValid(i,j)); 
		char tmp = mazeGrid[i][j];
		mazeGrid[i][j] = value;
		return tmp;
	}

	/**
	 * @param pos of the cur square
	 * @param value mark with a char
	 * @return
	 */
	public char BeenHere (Square pos, char value) {
		return markPos(pos.getRow(), pos.getCol(), value); 
	}


	/**
	 * @param i row
	 * @param j col
	 * @return true if visited current location
	 */
	public boolean haveBeenHere(int i, int j) {

		if(mazeGrid[i][j] == '*'&&isPosValid(i,j)==true) {
			return true;
		}
		else return false;
	}
	public boolean isMarked(Square pos) {
		return haveBeenHere(pos.getRow(), pos.getCol());
	}


	public boolean isClear(int i, int j) {
		if(isPosValid(i,j)==true && mazeGrid[i][j]!='0'&& mazeGrid[i][j]!='*') 
		{
			return true;
		}

		else 
			return false;

	}
	public boolean isClear(Square pos) {
		return isClear(pos.getRow(), pos.getCol());
	}

	//true if cell is within maze 
	public boolean isPosValid(int i, int j) {
		if (i >= 0 && i<size()+1 && j>= 0 && j<size()+1) return true; 
		else return false;
	}
	//true if cell is within maze 
	//get square pos and check if Pos is valid
	public boolean isPosValid(Square pos) {
		if(isPosValid(pos.getRow(), pos.getCol())==true){
			return true;
		}
		else return false;
		//return isPosValid(pos.getRow(), pos.getCol());
	}


	public boolean foundExit( int i, int j) {

		if(i== exitRow && j ==exitCol) {
			return true;
		}
		else
			return false;
	}
	public boolean foundExit(Square pos) {
		if(foundExit(pos.getRow(), pos.getCol())==true) {
			return true;
		}
		return false;
	}



	/** uses a stack to solve maze
	 * get all valid positions
	 * keep Pushing positions until the exit is found,
	 * if reach dead end start to Pop the positions in stack
	 * to back track
	 * Goes in Up,Right,Down,Left order
	 * 
	 */
	public void SolveMaze() {


		LinkedStack<Square> positions = new LinkedStack<Square>(); 


		Square current;
		Square next;
		positions.push(new Square(startX, startY)); 

		while (!positions.isEmpty()) {


			current = positions.pop();
			print();

			if (foundExit(current)) break;

			//mark the current position 
			BeenHere(current, '*');
			print();

			//put its neighbors in the stack
			next = current.moveUp();
			print();

			if (isPosValid(next)==true && isClear(next)==true) {
				positions.push(next);
			}
			next = current.moveRight(); 
			print();

			if (isPosValid(next)==true && isClear(next)==true) { 
				positions.push(next);
				//next = current.moveLeft(); 
			}
			next = current.moveDown(); 

			print();

			if (isPosValid(next)==true && isClear(next)==true) {
				positions.push(next);
			}
			next = current.moveLeft(); 

			if (isPosValid(next)==true && isClear(next)==true) {
				positions.push(next);
			}
		}

		if (!positions.isEmpty())
			System.out.println("*****MAZE COMPLETE******");

		else System.out.println("NO EXIT FOUND...you stuck");
		print();


	}
	
	/**method solves in directions of Up,Right,Left,Down order
	 * 
	 */
	public void SolveMaze1() {


		LinkedStack<Square> positions = new LinkedStack<Square>(); 


		Square current;
		Square next;
		positions.push(new Square(startX, startY)); 

		while (!positions.isEmpty()) {


			current = positions.pop();
			print();

			if (foundExit(current)) break;

			//mark the current position 
			BeenHere(current, '*');
			
			//undo comment for debuggin each step
			
			print();

			//put its neighbors in the stack
			next = current.moveUp();
			print();

			if (isPosValid(next)==true && isClear(next)==true) {
				positions.push(next);
			}
			next = current.moveRight(); 
			print();

			if (isPosValid(next)==true && isClear(next)==true) { 
				positions.push(next);
			}
			next = current.moveLeft(); 

			print();

			if (isPosValid(next)==true && isClear(next)==true) {
				positions.push(next);
			}
			next = current.moveDown(); 

			if (isPosValid(next)==true && isClear(next)==true) {
				positions.push(next);
			}
		}

		if (!positions.isEmpty())
			System.out.println("*****MAZE COMPLETE******");

		else System.out.println("NO EXIT FOUND...you stuck");
		print();


	}
	public void GUI() {
		JFrame frame = new JFrame();

		JTextArea textarea = new JTextArea();
		JPanel panel = new JPanel();
		JPanel framepanel = new JPanel();
		JPanel mainPan = new JPanel();


		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JButton start = new JButton("START");
		JButton load = new JButton("LOAD");
		
		frame.setSize(500, 500);
		panel.setVisible(true);
		textarea.setVisible(true);
		
		panel.setLayout(new GridLayout(2,8));
		mainPan.add(start);
		mainPan.add(load);

		framepanel.add(textarea);
		frame.add(framepanel, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);
		frame.add(mainPan, BorderLayout.NORTH);
		frame.setVisible(true);

		// prints course to onto GUI
		textarea.append(printCourse());

		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ 
				textarea.setText("");
				textarea.append(printCourse());
		


			}
		});



	}
	

	







}








