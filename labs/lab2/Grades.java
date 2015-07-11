/*********************************************************************/
/* Program: Factors and Primes                                       */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Lab Assignment #2                                                 */
/* October 10, 2011                                                  */
/*                                                                   */
/* This programs compares a letter grade to the Asian grade scale.   */
/*                                                                   */
/* Input:                                                            */
/* A letter                                                          */
/*                                                                   */
/* Output:                                                           */
/* What the letter grade means on the Asian grade scale.             */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.util.*;

class Grades {

	public static void main (String[] args) {

		Scanner scan = new Scanner( System.in );

		System.out.print("Enter the grade letter: ");
		String grade = scan.next();
		char c = grade.charAt(0);

		if ( (c == 'A') ||(c == 'a') ){
			System.out.println("Grade letter A means Average.");
		}
		else if ( (c == 'B') || (c == 'b') ){
			System.out.println("Grade letter B means Bad.");
		}
		else if ( (c == 'C') || (c == 'c') ){
			System.out.println("Grade letter C means Catastrophe.");
		}
		else if ( (c == 'D') || (c == 'd') ){
			System.out.println("Grade letter D means Disowned.");
		}
		else if ( (c == 'F') || (c == 'f') ){
			System.out.println("Grade letter F means Forever Forgotten.");
		}else 
			System.out.println( grade + " is not a valid letter grade.");
		
		System.out.println("Bye.");
	}
}
