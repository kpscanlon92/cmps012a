/*********************************************************************/
/* Program: Matrix                                                   */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Lab Assignment #4                                                 */
/* November 14, 2011                                                 */
/*                                                                   */
/* This programs reads in the matrices R, S, and T, and computes the */
/* specified dot products and matrix multiplication. Then outputs    */
/* the results to the user after each step as well as to the file    */
/* names corresponding to the operation.                             */                                              
/*                                                                   */
/* Input:                                                            */
/* None                                                              */
/*                                                                   */
/* Output:                                                           */
/* The results after each step as well as saving the new matrix to   */
/* the file names corresponding to the operation.                    */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {
	public static void main (String [] args){

		//Creates an instance of the class matrix
		//Used because computeDotProduct and computeMatrixProduct are instance methods
		Matrix m = new Matrix();

		//Reading in R, S, and T
		int[][] R = readMatrix("R");
		int[][] S = readMatrix("S");
		int[][] T = readMatrix("T");

		//A 1D array, V
		int[] V =  { 1, 1, 1, 1};

		//2D arrays, M1, RS, and M
		int[][] M1 = m.computeMatrixProduct(T, R);
		int[][] RS = m.computeMatrixProduct(R, S);
		int[][] M = m.computeMatrixProduct(RS, T);

		//OUTPUT!

		//Printing the name of that corresponds to the operation
		System.out.println("V x T:");
		//Calling method computeDotProduct
		int[]  VT = m.computeDotProduct(V, T);
		//Print the vector output
		printVector(VT);
		//Writing the vector to the file
		writeVector(VT, "VxT", false);

		System.out.println("V x S:");
		int[]  VS = m.computeDotProduct(V, S);
		printVector(VS);
		writeVector(VS, "VxS", false);

		System.out.println("V x R:");		
		int[]  VR = m.computeDotProduct(V, R);
		printVector(VR);
		writeVector(VR, "VxR", false);

		System.out.println("V x M1:");
		int[]  VM1 = m.computeDotProduct(V, M1);
		printVector(VM1);
		writeVector(VM1, "VxM1", false);

		System.out.println("V x M:");
		int[]  VM = m.computeDotProduct(V, M);
		printVector(VM);
		writeVector(VM, "VxM", false);

		System.out.println("Files saved!  Bye.");

	}

	/**
	 * Reads in the given file and populates a 2D array with its contents
	 * @param filename -- the name of the matrix file to red in
	 * @return a 2D array of form [row][col]
	 */
	public static int[][] readMatrix(String filename) {
		try {
			// since we don't know the size of the matrix, store the values in an array list
			// so that it will grow automatically as we read in the matrix
			ArrayList< ArrayList< Integer > > dynamicArray = new ArrayList< ArrayList< Integer > >();

			// connect a scanner to the file 
			Scanner fileReader = new Scanner(new File(filename));

			// while there is a row of values to parse
			while(fileReader.hasNext()) {
				// get the next line of the file
				String input = fileReader.nextLine();

				String[] values = input.split("\\s+");

				ArrayList< Integer > row = new ArrayList< Integer >();

				for(int index = 0; index < values.length; ++index)
					row.add(Integer.parseInt(values[index]));

				dynamicArray.add(row);
			}

			// now that we have the values, and know the dimensions of the array
			// we can create the fixed size in[][]
			int[][] matrix = new int[dynamicArray.size()][dynamicArray.get(0).size()];

			// convert the dynamic array into a fixed size int[][]
			for(int row = 0; row < dynamicArray.size(); ++row) {
				for(int col = 0; col < dynamicArray.get(0).size(); ++col) {
					matrix[row][col] = dynamicArray.get(row).get(col);
				}
			}

			return matrix;

		} catch (FileNotFoundException e) {
			System.out.println("Failed to read in file: " + filename + "!  Reason: " + e.getLocalizedMessage());
			return null;
		}
	}



	/*********************************************************************/
	/*  coputeDotProduct( )                                              */
	/*                                                                   */
	/*  Takes a vector and a matrix and computes the dot product with    */
	/*  each row of the matrix and the vector.                           */
	/*                                                                   */
	/* Input: A vector(a 1D array) and a Matrix(a 2D array)              */
	/* Output: a 1D array of form [row]                                  */
	/*********************************************************************/

	private int[] computeDotProduct(int[] vector, int[][] matrix){
		//The resulting 1D array
		int result[] = new int[matrix[0].length];

		//Iterating through the rows of the matrix and the columns of the vector
		for (int i = 0; i < matrix[0].length; i++){
			for (int j = 0; j < vector.length; j++){
				result[i] = result[i] + vector[j]*matrix[j][i];
			}
		}
		return result;

	}

	/*********************************************************************/
	/*  computeMatrixProduct ( )                                         */
	/*                                                                   */
	/*  Takes the 2 matrices, breaks matrix2 into vectors, calls the     */
	/*  dotProduct method, combines the output from the dot product to   */
	/*  form a 2D array.                                                 */
	/*                                                                   */
	/* Input: Two matrices (both 2D arrays).                             */
	/* Output: a 2D array of form [row][col]                             */
	/*********************************************************************/

	private int[][] computeMatrixProduct(int[][] matrix1, int[][]matrix2){
		//The resulting 2D array
		int result[][] = new int[matrix1.length][matrix2[0].length];

		//Iterate through the columns of matrix2
		for(int i = 0; i < matrix1.length; i++){
			//v is the vector from matrix1 that is used in the dot product
			int[] v = new int[matrix1[0].length];
			//Iterate through the row of matrix1
			for ( int j = 0; j < matrix1[0].length; j++ ) {
				v[j] = matrix1[i][j];
			}

			//r is the vector that results from computing the dot product
			int[] r = computeDotProduct( v, matrix2 );
			//Combining the results from the dot product to get a matrix
			for ( int j = 0; j < r.length; j++ ) {
				result[i][j] = r[j];
			}
		}
		return result;
	}

	/*********************************************************************/
	/*  printVector ( )                                                  */
	/*                                                                   */
	/*  Takes a vector and prints it out.                                */
	/*                                                                   */
	/* Input:  A vector (1D array).                                      */
	/* Output: Void.                                                     */
	/*********************************************************************/

	public static void printVector(int[] vector) {
		//Iterates through the array to make a vector
		for(int index = 0; index < vector.length; ++index) {
			System.out.print(vector[index] + " ");
		}
		System.out.println();
	}

	public static void printMatrix(int[][] matrix){

		for(int row = 0; row < matrix.length; ++row) {
			for(int col = 0; col < matrix[row].length; ++col) {

				System.out.print(matrix[row][col] + " ");
			}

			System.out.println();
		}


	}
	/**
	 * Writes the given vector to the file with the given name
	 * @param vector - the vector to write
	 * @param filename - the name of the file to write the matrix to
	 * @param append - if true, the method will write the given matrix
	 * to the specified file by appending it to the existing file, if
	 * one exists
	 */
	public static void writeVector(int[] vector, String filename, boolean append) {

		try {
			// connect an output pipe to the file, and overwrite if the file already exists
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename), append));

			for(int index = 0; index < vector.length; ++index) {
				// write the value to the file
				writer.print(vector[index] + " ");

				writer.println();
			}

			// close the pipe so the file will be saved properly
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Failed to write file: " + filename + "!  Reason: " + e.getLocalizedMessage());
			return ;
		}
	}


	/**
	 * Writes the given matrix to the file with the given name
	 * @param matrix - the matrix in form [row][col] to write
	 * @param filename - the name of the file to write the matrix to
	 * @param append - if true, the method will write the given matrix
	 * to the specified file by appending it to the existing file, if
	 * one exists
	 */
	public static void writeMatrix(int[][] matrix, String filename, boolean append) {

		try {
			// connect an output pipe to the file, and overwrite if the file already exists
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename), append));

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
	}
}
