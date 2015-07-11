/*********************************************************************/
/* Program: Life.java                                                */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/* Tabitha Chirrick (tchirric@ucsc.edu)                              */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Program Assignment #5                                             */
/* November 28, 2011                                                 */
/*                                                                   */
/* This program prints out a series of ppm images that show the      */
/* cycle of hungry critters and yummy critters using multiple classes*/
/*                                                                   */
/* Input:                                                            */
/* A pgm file, the number of yummy critters, the number of hungry    */
/* critters, and the number of steps desired in the cycle.           */
/*                                                                   */
/* Output:                                                           */
/* Writes a ppm file to several output files depicting each step     */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.io.*;
import java.util.*;

//the life class controls the rules of the game and sets up the skeleton of the program
public class Life {

	public int columns, rows, scale;
	private Scanner scan = new Scanner(System.in);
	public static Random random = new Random(0);
	private int[][] pgmArray = null;
	public HungryCritter[] hungryList;
	public YummyCritter[] yummyList;
	private PPM ppm;
	private RC[] deadThings;
	private int deadCount = 0;

	//the main method in the class Life
	public static void main( String[] args ) {

		Life life = new Life();

		life.run();

	}


	//a subset method of main that runs the program
	public void run(){

		//gets user input and reads in the file from user
		System.out.println("Let's rid the world of hunger!");
		boolean run = true;
		while ( run ){
			pgmArray = readInFile();
			if(pgmArray==null){
				System.out.println("File not found.");
			}else
				run = false;
		}

		//Asks user for further input (number of hungry critters)
		System.out.println("How many hungry critters do you want?");

		//Check if user input is valid
		int hungrys = 0;
		try
		{
			hungrys = scan.nextInt();

		}
		catch(Exception e){
			System.out.println("You entered bad data. Bye." );
			System.exit(0);
		}

		boolean[][] critterMap = new boolean[rows][columns];
		hungryList = createHungryCritters(hungrys, critterMap);

		//Ask for more user input (yummy critters)
		System.out.println("How many yummy critters do you want?");

		//Check if user input is valid
		int yummys = 0;
		try
		{
			yummys = scan.nextInt();

		}
		catch(Exception e){
			System.out.println("You entered bad data. Bye." );
			System.exit(0);
		}
		//creates an array of yummy critters
		yummyList = createYummyCritters(yummys, critterMap);

		//asks user for the number of steps they'd like the program to execute
		System.out.print("Enter the number of steps: ");
		int steps = scan.nextInt();

		//sets up the deadThings and deadCount arrays
		deadThings = new RC[yummys + hungrys];
		deadCount = 0;


		//turns the pgm image entered by the user into a ppm image usable by the program
		ppm = new PPM(pgmArray);
		for ( int jnx = 0; jnx < rows; jnx++ ){
			for ( int inx = 0; inx < columns; inx++ ){
				ppm.updatePixel(jnx, inx, pgmArray[jnx][inx], pgmArray[jnx][inx], pgmArray[jnx][inx]);
			}
		}
		//for all items in the hungryList, turns them blue
		for ( int i = 0; i < hungryList.length; i++){
			ppm.updatePixel(hungryList[i].row, hungryList[i].column, 0, 0, scale);
		}
		//for all items in the yummyList, turns them green
		for ( int i = 0; i < yummyList.length; i++){
			ppm.updatePixel(yummyList[i].row, yummyList[i].column, 0, scale, 0);
		}


		//using inx to iterate through the steps
		for ( int inx = 0; inx < steps; inx++ ) {		
			save(ppm, inx);
			for( int jnx = 0; jnx < hungryList.length; ) {
				if ( hungryList[jnx].checkHungry(this, ppm, pgmArray) == false ) {
					ppm.updatePixel(hungryList[jnx].row, hungryList[jnx].column, 0, 0, scale);
					++jnx;
				}
			}
			//makes it possible for the red pixel to be overwritten by a hungry or yummy track
			clearDeadThings();
			for ( int knx = 0; knx < yummyList.length; knx++ ) {
				yummyList[knx].moveYummy(this, pgmArray);
				ppm.updatePixel(yummyList[knx].row, yummyList[knx].column, 0, scale, 0);
			}
		}
		//calls the save method
		save(ppm, steps);
		System.out.println("Check the tracks saved in PPM files for each step. \nBye");
	}


	/*********************************************************************/
	/* readInFile method                                                 */
	/*                                                                   */
	/* Reads in the given file                                           */
	/*                                                                   */
	/* Input: filename -- the name of the pgm file to read in            */
	/* Output: a file to the program to turn into a ppm                  */
	/*********************************************************************/

	private int[][] readInFile() {// Scanner fileScan) {

		System.out.print("Enter terrain file: ");
		String filename = scan.nextLine();

		// scanner for file input
		Scanner fileScan;

		// handle the FileNotFound exception
		try {
			fileScan = new Scanner(new File(filename));
		} catch (Exception e) {
			// in case file is not found,
			return null;
		}

		// Stores picture format ID; not used
		fileScan.next();
		// Stores picture width
		columns = fileScan.nextInt();
		// Stores picture height
		rows = fileScan.nextInt();
		// Stores scale value
		scale = fileScan.nextInt();

		int[][] pictureArray = new int[rows][columns];

		// Assumes a rectangular image. Read pixel values in row-wise order
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++)
				pictureArray[i][j] = fileScan.nextInt();
		}

		System.out.print("Read terrain file: ");
		System.out.print(columns + " by "+ rows);
		System.out.println(", height ranges from 0 to " + scale +".");

		return pictureArray;
	}

	// create the hungry critters, and store them in an array list
	private HungryCritter[] createHungryCritters(int hungrys, boolean[][] critterMap){
		HungryCritter[] hungryList= new HungryCritter[hungrys];
		for(int i=0; i<hungrys;i++){
			int xCoor, yCoor;
			boolean repeat;
			do{
				repeat = false;
				yCoor = random.nextInt(rows);
				xCoor = random.nextInt(columns);
				// check the critterMap to see if this pixel is occupied; if so, reallocate
				if(critterMap[yCoor][xCoor])
					repeat = true;
			}while(repeat);
			critterMap[yCoor][xCoor] = true;
			hungryList[i] = new HungryCritter(xCoor, yCoor, i);
		}
		return hungryList;
	}

	// create the yummy critters, and store them in an array list
	private YummyCritter[] createYummyCritters(int yummys, boolean[][] critterMap){
		YummyCritter[] yummyList= new YummyCritter[yummys];
		for(int i=0; i<yummys;i++){
			int xCoor, yCoor;
			boolean repeat;
			do{
				repeat = false;
				yCoor = random.nextInt(rows);
				xCoor = random.nextInt(columns);
				// check the critterMap to see if this pixel is occupied; if so, reallocate
				if(critterMap[yCoor][xCoor])
					repeat = true;
			}while(repeat);
			critterMap[yCoor][xCoor] = true;
			yummyList[i] = new YummyCritter(xCoor, yCoor, i);
		}
		return yummyList;
	}

	//increases the number of dead critters 
	public void addDeadThing( int row, int column ) {
		deadThings[this.deadCount] = new RC(row, column);
		++deadCount;
	}

	//sets deadcount equal to zero
	public void clearDeadThings( ) {
		deadCount = 0;
	}

	//a check to see if there is a dead critter there
	public boolean hasDeadThing( int row, int column ) {
		for ( int inx = 0; inx < deadCount; inx++ ) {
			if ( deadThings[inx].row == row && deadThings[inx].column == column ) {
				return true;
			}
		}
		return false;
	}

	/*********************************************************************/
	/* removeDeadThing method                                            */
	/*                                                                   */
	/* Takes in an array and removes the dead critters from it           */
	/*                                                                   */
	/* Input: the program array                                          */
	/* Output: a new array that has removed the dead critters            */
	/*********************************************************************/

	public void removeDeadThing( int row, int column ) {
		for ( int inx = 0; inx < deadCount; inx++ ) {
			if ( deadThings[inx].row == row && deadThings[inx].column == column ) {
				deadThings[inx].row = -1; 
				deadThings[inx].column = -1;
				break;
			}
		}
	}

	//class RC collects information for calculating the array
	class RC {
		private int row;
		private int column;

		public RC( int row, int column ) {
			this.row = row;
			this.column= column;
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}
	}

	//the class PPM deals with turning pgms into ppms
	class PPM {
		private Pixel [][] pixels;
		public PPM( int[][] terrain ) {
			pixels = new Pixel[terrain.length][terrain[0].length];
			for ( int inx = 0; inx < terrain.length; inx++ ) {
				for ( int jnx = 0; jnx < terrain[0].length; jnx++ ) {
					pixels[inx][jnx] = new Pixel();
				}
			}
		}

		//updates the pixel to the RGB position 
		public void updatePixel( int row, int column, int red, int green, int blue ) {
			if ( hasDeadThing( row, column ) == false ) {
				pixels[row][column].setBlue(blue);
				pixels[row][column].setRed(red);
				pixels[row][column].setGreen(green);
			}
		}

		public Pixel getPixel( int row, int column ) {
			return pixels[row][column];
		}

		public int rowsPixel(PPM ppm){
			return pixels.length;
		}

		public int columnsPixel(PPM ppm){
			return pixels[0].length;
		}
		//class pixels create red blue and green pixels according to the designation by hungy or yummy or dead
		class Pixel {
			private int red = 0;
			private int green = 0;
			private int blue = 0;

			public Pixel() {
				red = scale;
				green = scale;
				blue = scale;
			}

			public String toString() {
				return red + " " + green + " " + blue;
			}

			public Pixel( int red, int green, int blue ) {
				this.red = red;
				this.blue = blue;
				this.green = green;
			}
			//sets a red pixel
			public void setRed(int red) {
				this.red = red;
			}
			public int getRed() {
				return red;
			}
			//sets a green pixel
			public void setGreen(int green) {
				this.green = green;
			}
			public int getGreen() {
				return green;
			}
			//sets a blue pixel
			public void setBlue(int blue) {
				this.blue = blue;
			}
			public int getBlue() {
				return blue;
			}
		}
	}

	//checks to see if there are neighbors in the hungryCritter window of sight
	public static int hungryNeighbor(int row, int column, boolean[][] w){
		int neighborCount = 0;
		boolean ALIVE = false;
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j<= 1; j++)
				if(w[row + i][column +j] == ALIVE)
					neighborCount = neighborCount + 1;
		if(w[row][column] == ALIVE)
			neighborCount--;
		return neighborCount;
	}

	//checks to see if a space is occupied before a critter moves
	public boolean isOccupied(int column, int row){
		boolean result = false;
		for (int i = 0; i < this.hungryList.length; i++){
			//			if ( hungryList[i] ==  null)
			//				continue;
			if( hungryList[i].row == row && hungryList[i].column == column){
				result = true;
				break;
			}		
		}
		for (int i = 0; i < this.yummyList.length; i++){
			if( yummyList[i].row == row && yummyList[i].column == column){
				result = true;
				break;
			}
		}
		return result;
	}

	//updates the hungry array on the entire ppm
	public void updateHungryPPM(int[][] line){
		for( int i = 0; i < line.length; i++){
			ppm.updatePixel(line[i][0], line[i][1], 0, 0, scale);
		}
	}

	//updates the yummy array on the entire ppm
	public void updateYummyPPM(int[][] line){
		for( int i = 0; i < line.length; i++){
			ppm.updatePixel(line[i][0], line[i][1], 0, scale, 0);
		}
	}

	/*********************************************************************/
	/*  save method                                                      */
	/*                                                                   */
	/*  Reads in the given file and saves it to an output file           */
	/*                                                                   */
	/* Input:  the ppm array, and the number of the file                 */
	/* Output: a file and a confirmation of saved file                   */
	/*********************************************************************/

	public void save(PPM ppm, int num) {

		String filename = null;

		filename = ("tracks" + num + ".ppm");

		try {
			// connect an output pipe to the file, and overwrite if the file already exists
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename), false));

			writer.println("P3");
			writer.println(columns + " " + rows);
			writer.println(scale);


			for(int row = 0; row < ppm.rowsPixel(ppm); ++row) {
				for(int col = 0; col < ppm.columnsPixel(ppm); ++col) {
					// write the value to the file
					writer.print(ppm.getPixel(row, col) + " ");
				}

				writer.println();
			}

			// close the pipe so the file will be saved properly
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Failed to write file: " + filename + "!  Reason: " + e.getLocalizedMessage());
			return ;
		}
	}
}

