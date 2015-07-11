/*********************************************************************/
/* Program: Bio                                                      */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*	    Tabitha Chirrick (tchirric@ucsc.edu)                     */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Program Assignment #3                                             */
/* October 31, 2011                                                  */
/*                                                                   */
/* This programs figures prints out a Biorhythm Chart.               */
/*                                                                   */
/* Input:                                                            */
/* Birth Month, Birth Day, Birth Year, Desired Month, and            */
/* Desired Year                                                      */
/*                                                                   */
/* Output:                                                           */
/*  the day of the week that you were born on, and a biorhythm chart */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/


import java.util.*;

class Bio {
	static Scanner scan = new Scanner( System.in );

	public static void main (String[] args) {

		int month, day, year, numOfDays, calNumOfDays, calMonth, calYear, days;
		int P, I, E;
		char p='P', i='I', e='E';
		String chartString="";

		//Collect user input
		System.out.print("Enter birth month: ");
		month = scan.nextInt();		
		System.out.print("Enter birth day: ");		
		day = scan.nextInt();		
		System.out.print("Enter birth year: ");		
		year = scan.nextInt();
		System.out.print("Enter desired month: ");
		calMonth = scan.nextInt();
		System.out.print("Enter desired year: ");
		calYear = scan.nextInt();

		//The output of the method numberOfDays
		numOfDays = numberOfDays(month, day, year);
		calNumOfDays = numberOfDays(calMonth, 1, calYear);

		//Prints out the day of the week that you were born on and a title
		System.out.println("");
		System.out.println("    Did you know you were born on a " + dayOfWeek(numOfDays) + "?\n");
		System.out.println("    Numeromancer Biorhythm Chart");

		//The output of the method calendarMath
		days = calendarMath(calMonth, calYear);


		//Prints the month/day/year for all of the days in the desired month
		//Prints the day of the week
		//String = i, ch = P or I or E, int = -20 to 20
		//Prints the biorhythm chart
		System.out.println("Date              +--------------------+--------------------+");
		for(int j=0; j<days-1; j++){
			System.out.format( "%2d/%2d/%4d  %4.3s  ", calMonth, j+1, calYear, dayOfWeek(calNumOfDays +j));
			P = (int) Math.round(Math.sin((2*Math.PI*(calNumOfDays + j - numOfDays))/23)* 20) + 21;
			I = (int) Math.round(Math.sin((2*Math.PI*(calNumOfDays + j - numOfDays))/33)* 20) + 21;
			E = (int) Math.round(Math.sin((2*Math.PI*(calNumOfDays + j - numOfDays))/28)* 20) + 21;
			chartString = "|                    +                    |";
			chartString = changeCharacter(P, p, chartString);
			chartString = changeCharacter(I, i, chartString);
			chartString = changeCharacter(E, e, chartString);
			System.out.print(chartString + "\n");
		}
		System.out.println("                  +--------------------+--------------------+ ");

		System.out.println("Bye.");

	}

	/*********************************************************************/
	/*  numberOfDays ( )                                                 */
	/*                                                                   */
	/*  This method figures out the amount of days from 1/1/1800 to      */
	/*  the month, day, and year that the user inputs.                   */
	/*  This Method was taken from the program 2 sample solution.        */
	/*                                                                   */
	/* Input:  Month, Day, Year                                          */
	/* Output: The amount of days from 1/1/1800 to the given date.       */
	/*********************************************************************/
	static int numberOfDays(int month, int day, int year){
		boolean  valid = true;

		/********************************************/
		/*  Check validity of date.                 */
		/********************************************/

		if (year<1800 || month<1 || month>12 || day<1)
			valid = false;

		if (day>31)
			valid = false;

		if (day>30 && (month==4 || month==6 || month==9 ||month==11))
			valid = false;

		if (month==2 && day>29)
			valid = false;

		if (month==2 && day>28 && (year%4!=0 || (year%100==0 && year%400!=0)))
			valid = false;

		if (!valid) {
			System.out.println("The date you entered is invalid." );
			System.out.println("Bye.");
			System.exit(0);
			// or you can put the rest of the code below in a giant else clause
		}

		/********************************************/
		/* Calculate number of days since 1/1/1800. */
		/********************************************/

		// number of years passed since 1800
		int yearsSince1800 = year - 1800;

		// number of leap years passed since 1800.
		// added 200 to account for year 2000 ("fractional 400 year cycle")
		int leapYearsSince1800 = yearsSince1800/4 - yearsSince1800/100 + (yearsSince1800+200)/400; 

		// calculate the number of days since 1/1/1800 to the entered date
		int daysSince1800 = 365*(yearsSince1800-leapYearsSince1800) + 366*leapYearsSince1800;

		switch( month ) {
		case 12 : daysSince1800 += 30;
		case 11 : daysSince1800 += 31;
		case 10 : daysSince1800 += 30;
		case  9 : daysSince1800 += 31;
		case  8 : daysSince1800 += 31;
		case  7 : daysSince1800 += 30;
		case  6 : daysSince1800 += 31;
		case  5 : daysSince1800 += 30;
		case  4 : daysSince1800 += 31;
		case  3 : daysSince1800 += 28;
		case  2 : daysSince1800 += 31;
		default : daysSince1800 += day -1;
		}

		if (year%4==0 && year%100 !=0 || year%400==0)
			if (month<3)
				daysSince1800--;

		return daysSince1800;
	}


	/*********************************************************************/
	/*  dayOfWeek ( )                                                    */
	/*                                                                   */
	/*  This method returns a day of the week.                           */
	/*                                                                   */
	/* Input:  numOfDays                                                 */
	/* Output: The days of the week.                                     */
	/*********************************************************************/

	static String dayOfWeek(int numOfDays ){
		String day = null;

		if( numOfDays % 7 == 0 ){
			day = "Wednesday";
		}else if( numOfDays % 7 == 1){
			day = "Thursday";
		}else if( numOfDays % 7 == 2 ){
			day = "Friday";
		}else if( numOfDays % 7 == 3 ){
			day = "Saturday";
		}else if( numOfDays % 7 == 4 ){
			day = "Sunday";
		}else if( numOfDays % 7 == 5 ){
			day = "Monday";
		}else if( numOfDays % 7 == 6){
			day = "Tuesday";
		}
		return day;
	}


	/*********************************************************************/
	/*  calendarMath ( )                                                 */
	/*                                                                   */
	/*  This method calculates the days that are in the desired month.   */
	/*                                                                   */
	/* Input:  calMonth, calYear                                         */
	/* Output: The days in the desired month                             */
	/*********************************************************************/

	static int calendarMath(int calMonth, int calYear) {
		int days = 0;

		switch (calMonth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 9:
		case 11:
			days = 31;
			break;
		case 4:
		case 6:
		case 8:
		case 10:
		case 12:
			days = 30;
			break;
		case 2:
			if ((calYear % 100 != 0 && calYear % 4 == 0) || calYear % 400 == 0){
				days = 29;
			}else 
				days = 28;
			break;
		}
		return days;
	}

	/*************************************************/
	/* Returns a new string which is a copy of s,    */
	/* but with the nth character replaced by ch.    */
	/* This is an unmodifided method given to us     */
	/* for this program.                             */
	/*************************************************/

	static String changeCharacter( int n, char ch, String s )
	{
		// check to see if n is valid
		if (n < 0 || n > s.length()-1)
		{
			System.out.println( "Invalid parameters to changeCharacter\n" );
			return( null );
		}

		char[] tmp = s.toCharArray();
		tmp[n] = ch;
		return( new String(tmp) );
	}
}
