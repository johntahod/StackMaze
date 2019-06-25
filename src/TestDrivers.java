import java.io.IOException;

public class TestDrivers {

	public static void main(String[] args)throws IOException {

		Grid maze = new Grid("/Users/debratahod/eclipse-workspace/StackMaze03/src/SampleMaze.txt");
		
		Grid maze1 = new Grid("/Users/debratahod/eclipse-workspace/StackMaze03/src/SampleMaze.txt");

		maze.print();

		//SolveMaze finds positions in Up,Right,Dowm,Left order
		maze.SolveMaze();
		
		
		//SolveMaze1 finds positions in Up,Right,Left,Down order

		maze1.SolveMaze1();
		maze.GUI();
//
//		System.out.println("\n\nFind a path using a stack: ");
//


	}

}
