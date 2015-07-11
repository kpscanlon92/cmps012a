/*********************************************************************/
/* Program: Mortgage                                                 */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*          Tabitha Chirrick (tchirric@ucsc.edu)                     */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Programming Assignment #1                                         */
/* October 3, 2011                                                   */
/*                                                                   */
/* This program takes user input (loan, interest rate and years to   */
/* pay off loan) and calculates an output(monthly mortgage and APR)  */
/*                                                                   */
/* Input:                                                            */
/* loan amount, interest rate, years to pay off loan                 */
/*                                                                   */
/* Output:                                                           */
/* APR, monthly mortgage payment                                     */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.util.*;
import java.lang.Math;

class Mortgage{

	public static void main (String[] args){

		int loan, time;
		double mmp;
		float interest, apr;
		Scanner scan = new Scanner(System.in);

		System.out.println("Welcome to Kelly and Tabitha's APR and Monthly Mortgage Payment calculator!");

		//collect user input
		System.out.println("What is your loan amount?");
		loan = scan.nextInt();
		System.out.println("What is the interest rate?");
		interest = scan.nextFloat();
		System.out.println("How many years do you have to pay off your loan?");
		time = scan.nextInt();

		//calculate apr
		apr = (float) (Math.pow(( 1 + interest/1200 ), 12 ) - 1 ) * 100;

		//calculate monthly mortgage payment
		mmp = (double) (loan*interest/1200) / ( 1 - 1 / (Math.pow((1 + interest/1200), 12 * time)));

		//output
		System.out.println("Your Annual Percentage Rate is: ");
		System.out.format("%.2f",apr);
		System.out.print("%\n");
		System.out.println("Your Monthly Mortgage Payment is: ");
		System.out.print("$");
		System.out.format("%.2f\n",mmp);
		System.out.println("Thank you for using Kelly and Tabitha's APR and Monthly Mortgage Payment calculator!");

	}


}
