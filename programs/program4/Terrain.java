/*********************************************************************/
/* Program: Terrain.java                                             */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*			Tabitha Chirrick (tchirric@ucsc.edu)                     */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Program Assignment #4                                             */
/* November 14, 2011                                                 */
/*                                                                   */
/* This program performs basic processing of a terrain image         */
/*                                                                   */
/* Input:                                                            */
/* Test files, method calls(s,f,l,v,q), file names, reference points,*/ 
/* confirmation of certain executions                                */
/*                                                                   */
/* Output:                                                           */
/*  Writes a pgm file to an output the user specifies.               */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.util.*;
import java.io.*;

class Terrain{
	// These are all global variables, a very useful tool.
	private static int width, height, scale;

	public static void main(String[] args){

		//scanner for keyboard input
		Scanner scan = new Scanner(System.in);
		int[][] pictureArray = null;
		//Reads in the file from user
		System.out.println("Welcome to my terrain checker: ");
		boolean run = true;
		while ( run ){
			pictureArray = readInFile();
			if(pictureArray==null){
				System.out.println("File not found.");
			}else
				run = false;
		}

		boolean running = true;
		while ( running ){
			//Asks the user what they would like to do
			System.out.println("");
			System.out.println("What do you want to do: (S)ave, (F)ill, (L)ine, (V)isible, (Q)uit:");
			String input = scan.next().toUpperCase();
			char c = input.charAt(0);

			//Sends user to specific method for the function they want to do, i.e. save, fill, line, visible or quit
			if (c == 'S'){
				save(pictureArray);
			}
			else if (c == 'F'){
				fill(pictureArray);
			}
			else if (c == 'L'){
				line(pictureArray);
			}
			else if (c == 'V'){
				visible(pictureArray);
			}
			else if (c == 'Q'){
				running = false;
			}else 
				System.out.println("The letter you entered is incorrect. Bye.");
		}
		System.out.println("Bye.");
	}


	/********************************************************/
	/*  method: readInFile() reads a pgm image to memory    */
	/*  by: Zhipan Wang                                     */
	/*  based on the code written by Alex Pang, etc.        */
	/********************************************************/

	public static int[][] readInFile(){

		//scanner for keyboard input
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter the file to read: ");
		String filename = scan.nextLine();

		//scanner for file input
		Scanner fileScan;

		//handle the FileNotFound exception
		try{
			fileScan = new Scanner(new File(filename));
		}catch(Exception e){
			// in case file is not found,
			return null;
		}

		//Stores picture format ID; not used
		String imageFormat = fileScan.next();
		//Stores picture width
		width = fileScan.nextInt();
		//Stores picture height
		height = fileScan.nextInt();
		//Stores scale value
		scale = fileScan.nextInt();

		int[][] pictureArray = new int[height][width];

		// Assumes a rectangular image.  Read pixel values in row-wise order 
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++)
				pictureArray[i][j] = fileScan.nextInt();
		}

		System.out.print( "Read terrain file: " );
		System.out.print( pictureArray[0].length + " by " + pictureArray.length + ", ");
		System.out.println( "height range from 0 .. " + scale );

		return pictureArray;
	}


	/*********************************************************************/
	/*  save method                                                      */
	/*                                                                   */
	/*  Reads in the given file and saves it to an output file           */
	/*                                                                   */
	/* Input:  filename -- the name of the pgm file to read in           */
	/* Output: a file and a confirmation of saved file                   */
	/*********************************************************************/

	public static void save(int[][] matrix) {

		String filename = null;
		Scanner scan = new Scanner(System.in);
		
		//asks user for input filename to save
		boolean running = true;
		while ( running ){

			System.out.print("Enter file name to save: ");
			filename = scan.next();
			
			//check to see if file exists and if user wants to overwrite the file if it does exist
			if ( new File(filename).exists() ) {
				System.out.println( "WARNING: " + filename + " exists.");
				System.out.print( "Overwrite (y/n)? " );
				String answer = scan.next();
				if ( answer.equalsIgnoreCase( "Y" ) ){
					running = false;
				}
				else {
					continue;
				}
			}else
				running = false;
		}

		try {
			// connect an output pipe to the file, and overwrite if the file already exists
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename), false));
			
			writer.println("P2");
			writer.println(width + " " + height);
			writer.println(scale);
			
			for(int row = 0; row < matrix.length; ++row) {
				for(int col = 0; col < matrix[row].length; ++col) {
					// write the value to the file
					writer.print(matrix[row][col] + " ");
				}

				writer.println();
			}

			// close the pipe so the file will be saved properly
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Failed to write file: " + filename + "!  Reason: " + e.getLocalizedMessage());
			return ;
		}

		System.out.println( "Terrain file saved." );
	}

	/*********************************************************************/
	/*  fill method                                                      */
	/*                                                                   */
	/*  Takes user input and compares entered reference point to other   */
    /*  points in the array. Fills points below reference with 0.        */
	/*                                                                   */
	/* Input:  array to be filled                                        */
	/* Output: returns a 2D array of form [row][col]                     */
	/*********************************************************************/

	public static void fill(int[][] pictureArray){

		int y = 0, x = 0;
		Scanner scan = new Scanner(System.in);
		
		//gathers user input for reference point
		boolean running = true;
		while ( running ){

			System.out.print("Enter reference point: ");
			y = scan.nextInt();
			x = scan.nextInt();
			
			//checks to see if reference point is in the array
			if (y > height || y < 0 || x > width || x < 0){
				System.out.println("WARNING: reference point is not in terrain.");
				continue;
			}
			running = false;
		}

		int refScale = pictureArray[y][x];
		
		//iterates through the array, looking for points to mark as 0
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++){
				if( pictureArray[i][j] < refScale ){
					pictureArray[i][j] = 0;	
				}else
					pictureArray[i][j] = scale/2;			
			}
		}

		System.out.println("Marking all pixels below " + refScale + " as 0, and others as " + scale/2);
	}

	/*********************************************************************/
	/*  line method                                                      */
	/*                                                                   */
	/*  Checks to see if there is a clear line of sight between          */
	/*  two points.                                                      */
	/*                                                                   */
	/* Input:  a 2 dimensional RA                                        */
	/* Output: a 2D array of form [row][col]                             */
	/*********************************************************************/

	public static void line(int[][] pictureArray){

		int y1I = 0, y2I = 0, x1I = 0, x2I = 0;
		float slope = 0;
		Scanner scan = new Scanner(System.in);

		//gets a point from the user
		boolean running1 = true;
		while ( running1 ){

			System.out.print("Enter point 1: ");
			y1I = scan.nextInt();
			x1I = scan.nextInt();

			//checks to see if point is in the array
			if (y1I > height || y1I < 0 || x1I > width || x1I < 0){
				System.out.println("WARNING: reference point is not in terrain.");
				continue;
			}
			running1 = false;
		}
		
		//gets the second point from the user
		boolean running2 = true;
		while ( running2 ){

			System.out.print("Enter point 2: ");
			y2I = scan.nextInt();
			x2I = scan.nextInt();

			//checks to see if point is in the array
			if (y2I > height || y2I < 0 || x2I > width || x2I < 0){
				System.out.println("WARNING: reference point is not in terrain.");
				continue;
			}
			running2 = false;
		}

		float x1 = (float)x1I;
		float y1 = (float)y1I;
		float x2 = (float)x2I;
		float y2 = (float)y2I;

		// calculates the slope
		if(x2 != x1){
			slope = (y2 - y1)/(x2 - x1);
		}
		//creates reference points out of the two entered points
		int refScale1 = pictureArray[y1I][x1I];
		int refScale2 = pictureArray[y2I][x2I];
		boolean visible = false;

		int xval, yval;

		if ( x1 < x2 ) {
			xval = 1;
		}
		else {
			xval = -1;
		}
		if ( y1 < y2 ) {
			yval = 1;
		}
		else {
			yval = -1;
		}
		
		float b =  y1 - (slope * x1);

		//begins checking the reference points against each other, this checks for infinite slopes
		if ( (x2 >= x1I-1 && x2 <= x1I+1) && (y2 >= y1I-1 && y2 <= y1I+1) ) {
			visible = true;
		}else if (y1 == y2){
			visible = true;
			for ( int x = x1I; x != x2; x += xval ) {
				if( pictureArray[y1I][x] > refScale1 && pictureArray[y1I][x] > refScale2){
					visible = false;
					break;
				}
			}
		//if x1 and x2 equal one another
		}else if(x1 == x2){
			visible = true;
			for ( int y = y1I; y != y2; y += yval ) {
				if( pictureArray[y][x1I] > refScale1 && pictureArray[y][x1I] > refScale2){
					visible = false;
					break;
				} 
			}
		//checks visibility if x values are larger than y values
		}else if(Math.abs(x1 - x2) > Math.abs(y1 - y2)){
			visible = true;
			for ( int x = x1I+xval; x != x2; x += xval ) {
				double y = (slope*x) + b;; 
				long yResult = Math.round(y);
				if (yResult <  0 || yResult >= pictureArray.length ){
					continue;
				}
				if( pictureArray[(int) yResult][x] > refScale1 && pictureArray[(int) yResult][x] > refScale2){
					visible = false;
					break;
				}
			}
		//checks visibility if y values are larger than x values
		}else if(Math.abs(x1 - x2) < Math.abs(y1 - y2)){
			visible = true;
			for ( int y = y1I+yval; y != y2; y += yval ) {
				double x = (y/slope) - b/slope;
				long xResult = Math.round(x);
				if (xResult <  0 || xResult >= pictureArray[0].length ){
					continue;
				}
				if( pictureArray[y][(int) xResult] > refScale1 && pictureArray[y][(int) xResult] > refScale2){
					visible = false;
					break;
				}
			}
		//if the x and y values equal one another
		}else if(Math.abs(x1 - x2) == Math.abs(y1 - y2)){
			visible = true;
			for ( int x = x1I; x != x2; x += xval ) {
				for( int y = y1I; y != y2; y += yval ){
					if( pictureArray[y][x] > refScale1 && pictureArray[y][x] > refScale2){
						visible = false;
						break;
					}
				}
			}
		}

		//prints out output from all above algorithms
		if(visible == false){
			System.out.println("Point 2 is not visible from point 1.");
		}else
			System.out.println("Point 2 is visible from point 1.");
	}

	/*********************************************************************/
	/*  visible method                                                   */
	/*                                                                   */
	/*  checks to see what part of the array is visible from a point     */
	/*                                                                   */
	/* Input:  a two dimensional array                                   */
	/* Output: a 2D array of form [row][col]                             */
	/*********************************************************************/

	public static void visible(int[][] pictureArray){
		int y1I = 0, y2 = 0, x1I = 0, x2 = 0;
		float slope = 0;
		int [][] result = new int[width][height];

		int refY = 0, refX = 0;
		Scanner scan = new Scanner(System.in);

		//collects user input
		boolean running = true;
		while ( running ){

			System.out.print("Enter reference point: ");
			y1I = scan.nextInt();
			x1I = scan.nextInt();
			
			//checks to see if reference point is in the terrain
			if (refY > height || refY < 0 || refX > width || refX < 0){
				System.out.println("WARNING: reference point is not in terrain.");
				continue;
			}
			running = false;
		}

		float x1 = (float)x1I;
		float y1 = (float)y1I;

		//iterates through the array, checking for points against the reference point
		for ( int i = 0; i < pictureArray.length ; i ++ ){
			for ( int j = 0; j < pictureArray[0].length; j ++ ){
				y2 = i;
				x2 = j;	
				
				//slope calculation
				if(x2 != x1){
					slope = (y2 - y1)/(x2 - x1);
				}
				
				//sets up reference points in the two dimensional array
				int refScale1 = pictureArray[y1I][x1I];
				int refScale2 = pictureArray[y2][x2];
				boolean visible = false;

				int xval, yval;

				if ( x1 < x2 ) {
					xval = 1;
				}
				else {
					xval = -1;
				}
				if ( y1 < y2 ) {
					yval = 1;
				}
				else {
					yval = -1;
				}

				float b =  y1 - (slope * x1);

				//checks visibility
				if ( (x2 >= x1I-1 && x2 <= x1I+1) && (y2 >= y1I-1 && y2 <= y1I+1) ) {
					visible = true;
				}else if (y1 == y2){
					visible = true;
					for ( int x = x1I; x != x2; x += xval ) {
						if( pictureArray[y1I][x] > refScale1 && pictureArray[y1I][x] > refScale2){
							visible = false;
							break;
						}
					}
				//if x values equal one another
				}else if(x1 == x2){
					visible = true;
					for ( int y = y1I; y != y2; y += yval ) {
						if( pictureArray[y][x1I] > refScale1 && pictureArray[y][x1I] > refScale2){
							visible = false;
							break;
						} 
					}	
				//if x values are larger than y values
				}else if(Math.abs(x1 - x2) > Math.abs(y1 - y2)){
					visible = true;
					for ( int x = x1I+xval; x != x2; x += xval ) {
						double y = (slope*x) + b;; 
						long yResult = Math.round(y);
						if (yResult <  0 || yResult >= pictureArray.length ){
							continue;
						}
						if( pictureArray[(int) yResult][x] > refScale1 && pictureArray[(int) yResult][x] > refScale2){
							visible = false;
							break;
						}
					}
				//if y values are larger than x values
				}else if(Math.abs(x1 - x2) < Math.abs(y1 - y2)){
					visible = true;
					for ( int y = y1I+yval; y != y2; y += yval ) {
						double x = (y/slope) - (b/slope);
						long xResult = Math.round(x);
						if (xResult <  0 || xResult >= pictureArray[0].length ){
							continue;
						}
						if( pictureArray[y][(int) xResult] > refScale1 && pictureArray[y][(int) xResult] > refScale2){
							visible = false;
							break;
						}
					}
				//if y and x values are equal
				}else if(Math.abs(x1 - x2) == Math.abs(y1 - y2)){
					visible = true;
					for ( int x = x1I, y = y1I; x != x2 && x != x2; x += xval, y += yval ) {
						if( pictureArray[y][x] > refScale1 && pictureArray[y][x] > refScale2){
							visible = false;
							break;
						}
					}
				}

				if(visible == true){
					result[y2][x2] = scale;
				}
				else {
					result[y2][x2] = pictureArray[y2][x2];
				}
			}
		}
		//setting results array to equal picturearray
		for ( int x = 0; x < width; x++ ) {
			for ( int y = 0; y < height; y++ ) {
				pictureArray[y][x] = result[y][x];
			}
		}
		//output
		System.out.println("Marking all pixels visible from "+ y1I + "," + x1I +" as white. \nDone.");
	}
}