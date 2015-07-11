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

public class YummyCritter {
	public String name;
	public int position;

	public YummyCritter( String name, int position){
		this.name = name;
		this.position = position;  
	}
}
