// Name: Karan Singh Gill
// USC loginid: karansig
// CS 455 PA3
// Fall 2017

import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls

 */

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   private int rows = 0;
   private int cols = 0;
   private boolean[][] mazeData;
   private boolean[][] isVisited;
   private MazeCoord startLoc;
   private MazeCoord exitLoc;
   private static LinkedList<MazeCoord> path;
   
  

   /**
   Representation invariant:
   StartLoc and ExitLoc have coordinates in the maze
   Maze coordinates go from 0 to rows-1 and 0 to cols-1
   mazeData[][] is true for walls and false for free space
   */
   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments above for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param exitLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
      rows = mazeData.length;
      cols = mazeData[0].length;
      this.mazeData = new boolean[rows][cols];

      //creating the local mazeData. Could be done without loop, but it's nice
      //to use FREE and WALL
      for (int i=0;i<rows;i++) {
	 for (int j=0; j<cols;j++){
	    if (mazeData[i][j]) {
	       this.mazeData[i][j] = WALL; 
	    }
	    else {
	       this.mazeData[i][j] = FREE; 
	    }
	 }
      }

      this.startLoc = startLoc;
      this.exitLoc = exitLoc;
      this.path = new LinkedList<MazeCoord>();//initializing LinkedList
   }


   /**
      Returns the number of rows in the maze
      @return number of rows
   */
   public int numRows() {
      return rows;  
   }

   
   /**
      Returns the number of columns in the maze
      @return number of columns
   */   
   public int numCols() {
      return cols; 
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
      int row = loc.getRow();
      int col = loc.getCol();
      if (mazeData[row][col]) {
	 return true;
      }
      else{
	 return false;
      }
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
      return startLoc;
   }
   
   
   /**
     Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
      return exitLoc;
   }

   
   /**
      Returns the path through the maze. First element is start location, and
      last element is exit location.  If there was not path, or if this is called
      before a call to search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {
      System.out.println("DEBUG: " + path);
      return path;
   }


   /**
      Find a path from start location to the exit location (see Maze
      constructor parameters, startLoc and exitLoc) if there is one.
      Client can access the path found via getPath method.

      @return whether a path was found.
      An important part is the isVisited boolean array which has
      the same dimensions as the maze, and stores whether or not
      a location has been visited by the searchHelper function already.
      It is, of course, initialized with all values being false
    */
   public boolean search()  {  
      path.clear(); //just in case search is being called again
      isVisited = new boolean[rows][cols];
      return searchHelper(startLoc);//calls helper function
   }

   /** 
   This function actually does the recursive searching
   Since there are no "virtual walls" a series of
   if statements ensure that it does not accidentally 
   go out of the bounds of the mazeData array
   Otherwise, it generally follows the format of the 
   suggested algorithm in the PA3 problem statement
   */
   private boolean searchHelper(MazeCoord loc) {
      if (hasWallAt(loc)) {
	 return false;
      }
      else if (wasVisited(loc)) {
	 return false;
      }
      else if (loc.equals(exitLoc)) {
	 path.addFirst(loc);//addFirst ensures that order is from start to end
	 return true;
      }
      else {
	 int row = loc.getRow();
	 int col = loc.getCol();
	 isVisited[row][col] = true;//sets visited to true 
	 if (row !=0 ) {
	    if (searchHelper(new MazeCoord(row-1,col))) {
	       path.addFirst(loc);
	       return true;
	    }
	 }
	 if (row != rows -1) {
	    if (searchHelper(new MazeCoord(row+1,col))) {
	       path.addFirst(loc);
	       return true;
	    }
	 }
	 if (col != 0) {
	    if (searchHelper(new MazeCoord(row,col-1))) {
	       path.addFirst(loc);
	       return true;
	    }
	 }
	 if (col != cols -1) {
	    if (searchHelper(new MazeCoord(row,col+1))) {
	       path.addFirst(loc);
	       return true;
	    }
	 }
      }
      return false;
   }

   /** 
   This class just checks if a location has been visited by checking
   the isVisited array
   */
   private boolean wasVisited(MazeCoord loc) {
      int row = loc.getRow();
      int col = loc.getCol();
      if (isVisited[row][col]) {
	 return true;
      }
      else{
	 return false;
      }
   }
}
