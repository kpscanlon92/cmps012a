/*********************************************************************/
/* Program: Bonus Days                                               */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*			Tabitha Chirrick (tchirric@ucsc.edu)                     */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Program Assignment #2                                             */
/* October 17, 2011                                                  */
/*                                                                   */
/* This programs figures out the amount of days between two dates    */
/* that the user inputs.                                             */
/*                                                                   */
/* Input:                                                            */
/* Month 1, Day 1, Year 1, Month 2, Day 2, Year 2                    */
/*                                                                   */
/* Output:                                                           */
/* The amount of days between the two dates inclusive.               */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.util.*;

class BonusDays {

	static Scanner scan = new Scanner( System.in );

	public static void main (String[] args) {

		int month1, day1, year1, leapMonth1, mathMonth1 = 0, month2, day2, year2, leapMonth2, mathMonth2 = 0, 
			mathMonth, leapYear = 0, notLeap = 0, totalYear = 0, answer = 0;

		//Collect user input
		System.out.print("Enter 1st month: ");
		month1 = scan.nextInt();		
		System.out.print("Enter 1st day: ");		
		day1 = scan.nextInt();		
		System.out.print("Enter 1st year: ");		
		year1 = scan.nextInt();
		System.out.print("Enter 2nd month: ");
		month2 = scan.nextInt();		
		System.out.print("Enter 2nd day: ");		
		day2 = scan.nextInt();		
		System.out.print("Enter 2nd year: ");		
		year2 = scan.nextInt();

		//Checks the validity of the 1st day and 1st month
		switch (month1) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 9:
		case 11:
			if (day1 > 31){
				System.out.println("The dates you entered are invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		case 4:
		case 6:
		case 8:
		case 10:
		case 12:
			if (day1 > 30){
				System.out.println("The dates you entered are invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		case 2:
			if (day1 > 29){
				System.out.println("The dates you entered are invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		default:
			System.out.println("The dates you entered are invalid.");
			System.out.println("Bye.");
			return;
		}

		//Checks the validity of the 2nd day and 2nd month
		switch (month2) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 9:
		case 11:
			if (day2 > 31){
				System.out.println("The dates you entered are invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		case 4:
		case 6:
		case 8:
		case 10:
		case 12:
			if (day2 > 30){
				System.out.println("The dates you entered are invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		case 2:
			if (day2 > 29){
				System.out.println("The dates you entered are invalid.");
				System.out.println("Bye.");
				return;
			}
			break;
		default:
			System.out.println("The dates you entered are invalid.");
			System.out.println("Bye.");
			return;
		}

		//Checks the validity of the year
		if (year1 < 1800 || year2 < 1800){
			System.out.println("The dates you entered are invalid.");
			System.out.println("Bye.");
			return;
		}
		
		//Check for leap year for the 1st Month and 1st Day
		if ((year1 % 100 != 0 && year1 % 4 == 0) || year1 % 400 == 0){
			leapMonth1 = 29;
		}else 
			leapMonth1 = 28;
		
		
		//1st Month and Day math
		switch (month1){
		case 1:
			mathMonth1 = day1;
			break;
		case 2:
			mathMonth1 = day1 + 31;
			break;
		case 3:
			mathMonth1 = day1 + leapMonth1 + 31;
			break;
		case 4:
			mathMonth1 = day1 + leapMonth1 + 62;
			break;
		case 5:
			mathMonth1 = day1 + leapMonth1 + 92;
			break;
		case 6:
			mathMonth1 = day1 + leapMonth1 + 123;
			break;
		case 7:
			mathMonth1 = day1 + leapMonth1 + 153;
			break;
		case 8:
			mathMonth1 = day1 + leapMonth1 + 184;
			break;
		case 9:
			mathMonth1 = day1 + leapMonth1 + 215;
			break;
		case 10:
			mathMonth1 = day1 + leapMonth1 + 245;
			break;
		case 11:
			mathMonth1 = day1 + leapMonth1 + 276;
			break;
		case 12:
			mathMonth1 = day1 + leapMonth1 + 306;
			break;
		}
		
		//Check for leap year for the 2nd Month and 2nd Day math
		if ((year2 % 100 != 0 && year2 % 4 == 0) || year2 % 400 == 0){
			leapMonth2 = 29;
		}else 
			leapMonth2 = 28;

		//2nd Month and Day math
		switch (month2){
		case 1:
			mathMonth2 = day2;
			break;
		case 2:
			mathMonth2 = day2 + 31;
			break;
		case 3:
			mathMonth2 = day2 + leapMonth2 + 31;
			break;
		case 4:
			mathMonth2 = day2 + leapMonth2 + 62;
			break;
		case 5:
			mathMonth2 = day2 + leapMonth2 + 92;
			break;
		case 6:
			mathMonth2 = day2 + leapMonth2 + 123;
			break;
		case 7:
			mathMonth2 = day2 + leapMonth2 + 153;
			break;
		case 8:
			mathMonth2 = day2 + leapMonth2 + 184;
			break;
		case 9:
			mathMonth2 = day2 + leapMonth2 + 215;
			break;
		case 10:
			mathMonth2 = day2 + leapMonth2 + 245;
			break;
		case 11:
			mathMonth2 = day2 + leapMonth2 + 276;
			break;
		case 12:
			mathMonth2 = day2 + leapMonth2 + 306;
			break;
		}
		
		if ((year2 % 100 != 0 && year2 % 4 == 0) || year2 % 400 == 0){
			mathMonth = Math.abs( mathMonth2 - mathMonth1 );
		}else if ((year1 % 100 != 0 && year1 % 4 == 0) || year1 % 400 == 0){
			mathMonth = Math.abs( mathMonth2 - mathMonth1 );
		}else
			mathMonth = Math.abs( mathMonth2 - mathMonth1 ) + 1;
	
		
		//Calculation of leap years
		for( int mathYear = year1; mathYear < year2; ++mathYear ){
			if ((mathYear % 100 != 0 && mathYear % 4 == 0) || mathYear % 400 == 0){
				leapYear++;
			}else 
				notLeap++;	
		}
		
		
		//Calculation of total days in all past years
		totalYear = (leapYear*366) + (notLeap*365);
		
		answer = mathMonth + totalYear;
		
		//computed output
		System.out.println("Between " + month1 + "/" + day1 + "/" + year1 + " and " + 
				month2 + "/" + day2 + "/" + year2 + " there are " + answer + " days inclusive.");
		System.out.println("Bye.");
	}
}