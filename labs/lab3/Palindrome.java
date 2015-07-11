/*********************************************************************/
/* Program: Palindrome                                               */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Lab Assignment #3                                                 */
/* October 28, 2011                                                  */
/*                                                                   */
/* This programs figures out if a String is a Palindrome or not, it  */
/* includes letter, and numbers, as well as ignoring spaces.         */
/*                                                                   */
/* Input:                                                            */
/* A String                                                          */
/*                                                                   */
/* Output:                                                           */
/* The determination if the String is a Palindrome or not.           */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.util.Scanner;

public class Palindrome {

	static Scanner scan = new Scanner( System.in );

	public static void main (String arg[]){

		//Variables
		String phrase = "", changed = "";
		int i;
		char ch;

		//Collect user input
		System.out.println("Please enter your test phrase: ");
		phrase = scan.nextLine().toLowerCase();

		//Removing white space
		String cleanString = phrase.replaceAll("\\s+", "");

		//Creating a string that is backwards of the original string
		for(i=0; i<cleanString.length(); i++){
			ch = cleanString.charAt(i);
			changed = ch + changed;
		}

		//If cleanString is equal to the backwards version of itself the it is a palindrome!
		if(cleanString.equals(changed)){
			System.out.println("That's a palindrome!  Bye.");
		}
		else{
			System.out.println("That isn't palindrome!  Bye.");
		}
	}
}
