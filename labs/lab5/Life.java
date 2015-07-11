/*********************************************************************/
/* Program: Life                                                     */
/* Authors: Kelly Scanlon (kpscanlo@ucsc.edu)                        */
/*                                                                   */
/* CMP 12A/L, Fall 2011                                              */
/* Lab Assignment #5                                                 */
/* November 20, 2011                                                 */
/*                                                                   */
/* This programs creates critters to be killed off in a random order */
/* by using classes and array manipulation.                          */
/*                                                                   */
/* Input:                                                            */
/* How many critters the user wants to kill.                         */
/*                                                                   */
/* Output:                                                           */
/* The order that the critters are being killed, as well as the      */
/* critters that are still alive after each round of killing.        */
/*                                                                   */
/* Bugs and limitations:                                             */
/* Success rate is 99.99% unless someone really blew it.             */
/*********************************************************************/

import java.util.*;

public class Life {

	public static void main(String[] args){

		//Create Scanner, Initialize variables
		Scanner scan = new Scanner(System.in);
		int critters = 0;
		//Create new random object rand
		Random rand = new Random(0);

		//Ask for user input
		System.out.println("Enter the number of critters you would like to kill: ");
		//Check if user input is valid
		try
		{
			critters = scan.nextInt();
			
		}
		catch(Exception e){
			System.out.println("You entered bad data. Bye." );
			System.exit(0);
		}
		
		//Create array for critters
		YummyCritter[] critterArray = new YummyCritter[critters];

		//Put critter name and position in array
		for(int i = 0; i < critters; i++){
			critterArray[i] = new YummyCritter( "critter" + (i + 1), i );
		}

		//Loop through killing critters
		boolean running = true;
		while (running ){
			if (critters > 0){
				System.out.println("Currently Alive Creatures:");
				//Loop through the alive critters
				for(int i = 0; i < critters - 1; ++i) {
					System.out.print(critterArray[i].name + ", ");
				}
				System.out.println(critterArray[critters-1].name);

				//Picking the critter that is going to be killed
				int removeInx = rand.nextInt(critters); 

				System.out.println("I choose to kill " + critterArray[removeInx].name + "!");
				System.out.println("");

				//Looping through critterArray and removing the critter that is killed
				//Also removing the critter's position and shifting positions
				for(int i = 0, j = 0; i < critters; i++ ) {
					if( critterArray[i].position != removeInx){
						critterArray[j] = critterArray[i];
						critterArray[j].position = j;
						j++;
					}
				}
				//Decrementing the amount of critters
				--critters;
				//Filling the gap in critterArray
				critterArray[critters] = null;
			}else
				running = false;
		}
		System.out.println("Oh no! All the critters are dead!  Bye!");
	}
}
