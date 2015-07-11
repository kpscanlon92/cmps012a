/*********************************************************************/
/* Program: Days                                                     */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*			Tabitha Chirrick (tchirric@ucsc.edu)                     */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Program Assignment #2                                             */
/* October 17, 2011                                                  */
/*                                                                   */
/* This programs figures out the amount of days from 1/1/1800 to a   */
/* month, day, and year that the user inputs.                        */
/*                                                                   */
/* Input:                                                            */
/* Month, Day, Year                                                  */
/*                                                                   */
/* Output:                                                           */
/* The amount of days from 1/1/1800 to the given date.               */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.util.*;

class Days {

	static Scanner scan = new Scanner( System.in );

	public static void main (String[] args) {

		int year, month, day, mathMonth = 0, leapMonth, leapYear = 0, notLeap = 0, totalYear, answer = 0;

		//Collect user input
		System.out.print("Enter month: ");
		month = scan.nextInt();		
		System.out.print("Enter day: ");		
		day = scan.nextInt() - 1;		
		System.out.print("Enter year: ");		
		year = scan.nextInt();

		//Checks the validity of the day and month
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 9:
		case 11:
			if (day > 30){
				System.out.println("The date you entered is invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		case 4:
		case 6:
		case 8:
		case 10:
		case 12:
			if (day > 29){
				System.out.println("The date you entered is invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		case 2:
			if (day > 28){
				System.out.println("The date you entered is invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		default:
			System.out.println("The date you entered is invalid.");
			System.out.println("Bye.");
			return;
		}

		//Checks the validity of the year
		if (year < 1800){
			System.out.println("The date you entered is invalid.");
			System.out.println("Bye.");
			return;
		}

		//Check for leap year for the Month and Day
		if ((year % 100 != 0 && year % 4 == 0) || year % 400 == 0){
			leapMonth = 29;
		}else 
			leapMonth = 28;

		// Month and Day math
		//Calculating the days in prior months and adding to days
		switch (month){
		case 1:
			mathMonth = day;
			break;
		case 2:
			mathMonth = day + 31;
			break;
		case 3:
			mathMonth = day + leapMonth + 31;
			break;
		case 4:
			mathMonth = day + leapMonth + 62;
			break;
		case 5:
			mathMonth = day + leapMonth + 92;
			break;
		case 6:
			mathMonth = day + leapMonth + 123;
			break;
		case 7:
			mathMonth = day + leapMonth + 153;
			break;
		case 8:
			mathMonth = day + leapMonth + 184;
			break;
		case 9:
			mathMonth = day + leapMonth + 215;
			break;
		case 10:
			mathMonth = day + leapMonth + 245;
			break;
		case 11:
			mathMonth = day + leapMonth + 276;
			break;
		case 12:
			mathMonth = day + leapMonth + 306;
			break;
		}

		//Calculation of leap years
		for( int mathYear = 1800; mathYear < year; ++mathYear ){
			if ((mathYear % 100 != 0 && mathYear % 4 == 0) || mathYear % 400 == 0){
				leapYear++;
			}else 
				notLeap++;	
		}
		
		
		//Calculation of total days in all past years
		totalYear = (leapYear*366) + (notLeap*365);

		//Answer
		answer = totalYear + mathMonth;

		//computed output
		System.out.println("The number of days since 1/1/1800 is: " + answer );
		System.out.println("Bye.");
	}
}		
