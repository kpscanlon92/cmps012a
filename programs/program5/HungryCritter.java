import java.util.ArrayList;

//import program5.Life.PPM;

public class HungryCritter {
	public String name;
	public int row;
	public int column;
	public int position;
	public boolean alive = true;

	//Constructor for HungryCritter
	public HungryCritter( int column, int row, int position){
		this.column = column;
		this.row = row;
		this.position = position;
	}

	//Search the 3x3 window for yummyCritters, if there isn't one then moves randomly in that 3x3 window
	public boolean checkHungry(Life life, Life.PPM ppm, int[][] terrain){

		for (int i = 0; i < life.yummyList.length; i++) {
			if(Math.abs(life.yummyList[i].column-this.column)<=1 && Math.abs(life.yummyList[i].row-this.row)<=1){
				eat(life, position);
				ppm.updatePixel(life.yummyList[i].row, life.yummyList[i].column, life.scale, 0, 0);
				life.addDeadThing( life.yummyList[i].row, life.yummyList[i].column);
				return true;
			}
		}	
		moveHungry(life, terrain);
		return false;
	}


	// eat yummyCritters if there is one
	// if more than one yummy critters are next to me, eat the first available one from yummyList	 
	// Turns into a yummyCritter if it eats one
	public void eat (Life life, int position){
		
		int lengthHungry = life.hungryList.length;
		for(int i = 0, j = 0; i < lengthHungry; i++ ) {
			if( life.hungryList[i].position != this.position){
				life.hungryList[j] = life.hungryList[i];
				j++;
			}
		}
		//Decrementing the amount of critters
		--lengthHungry;
		//Filling the gap in critterArray
		life.hungryList[lengthHungry] = null;
		
		//initializing a hungrycritter list
		HungryCritter [] list = life.hungryList;
		life.hungryList = new HungryCritter[list.length-1];
		for ( int inx=0; inx < list.length - 1; inx++ ) {
			life.hungryList[inx] = list[inx];
		}
	}

	/*********************************************************************/
	/* moveHungry method                                                 */
	/*                                                                   */
	/* moves all the critters in the hungry array on the ppm             */
	/*                                                                   */
	/* Input: the hungry critter array                                   */
	/* Output: a file to the program to turn into a ppm                  */
	/*********************************************************************/
	
	public void moveHungry (Life life, int[][] terrain ){
		// code fragment to check if cells within a 3x3 window
		// note: there are 8 = 3x3 - 1 candidate cells
		boolean repeat =true;
		int randomNum;
		int xCoor=0, yCoor=0;
		int [][] line = null;
		while (repeat) {
			line = null;
			// get a random number between 0..8
			randomNum = Life.random.nextInt(8);
			// turn this value to be 0..3 and 5..8
			// position 5 is the center of the 3x3 window
			if (randomNum > 3)
				randomNum++;
			// convert that to a coordinate in a 3x3 window
			yCoor = row - 1 + randomNum / 3;
			xCoor = column - 1 + randomNum % 3;

			if ( xCoor >= 0 && xCoor < life.columns && yCoor >= 0 && yCoor < life.rows && life.isOccupied(xCoor, yCoor) == false )
			{
				// check to see if that location is visible
				int[] ref1 = {terrain[row][column], row, column};
				int[] ref2 = {terrain[yCoor][xCoor],yCoor, xCoor};
				line = hungryClearLine(terrain, ref1, ref2);
				if(line!=null)
					repeat = false;
			}
		}

		if ( line != null ) {
			this.row = yCoor;
			this.column = xCoor;
			life.updateHungryPPM(line);
		}
	}

	/*********************************************************************/
	/* hungryClearLine method                                            */
	/*                                                                   */
	/* Check if the two pixels are visible by one another                */
	/*                                                                   */
	/* Input: two pixels, the terrain, and reference points              */
	/* Output: an array of hungries                                      */
	/*********************************************************************/

	private int[][] hungryClearLine(int[][] terrain, int[] ref1, int[] ref2){

		ArrayList<int[]> line = new ArrayList<int[]>();
		// get the higher height of the two reference points
		int higherEnd = Math.max(ref1[0], ref2[0]);
		double slope=0;
		// if slope not defined, set slope to infinite
		if(ref1[1]==ref2[1])
			slope = Double.POSITIVE_INFINITY;
		else  // if slope is defined, calculate the slope of the line
			slope = (double)(ref1[2]-ref2[2])/(ref1[1]-ref2[1]);

		// if the line is more vertical (|slope|>=1)
		if (Math.abs(slope) >= 1) {
			// get the smaller and bigger y coordinate of the two ends
			int smallY = Math.min(ref1[2], ref2[2]);
			int bigY = Math.max(ref1[2], ref2[2]);
			// check the pixels on the line to see if the line is clear
			int high, low;
			if (smallY==this.row){ 
				high = bigY;
				low = smallY+1;
			}else{
				high = bigY-1;
				low = smallY;
			}
			for (int y = low; y <= high; y++) {
				// line function x = (1/slope)*y + b
				double b = (double) (ref2[2] * ref1[1] - ref1[2] * ref2[1])
				/ (ref2[2] - ref1[2]);
				// calculate the x coordinate
				int x = (int) Math.round((1 / slope) * y + b);

				if (terrain[x][y]>higherEnd)
					return null;
				int[] a = {x,y};
				line.add(a);
			}
		} else { // if the line is more horizontal (|slope|<1)
			// get the smaller and bigger x coordinate of the two ends
			int smallX = Math.min(ref1[1], ref2[1]);
			int bigX = Math.max(ref1[1], ref2[1]);
			// check the pixels on the line to see if the line is clear
			int high, low;
			if (smallX==this.column){ 
				high = bigX;
				low = smallX+1;
			}else{
				high = bigX-1;
				low = smallX;
			}

			for (int x = low; x <= high; x++) {
				// line function y = slope*x + b
				double b = (double) (ref2[1] * ref1[2] - ref1[1] * ref2[2])
				/ (ref2[1] - ref1[1]);
				// calculate the x coordinate
				int y = (int) Math.round(slope * x + b);

				if (terrain[x][y] > higherEnd)
					return null;
				int[] a = {x,y};
				line.add(a);
			}
		}
		int[][] l = new int[line.size()][2];
		for(int i=0;i<line.size();i++){
			l[i][0] = line.get(i)[0];
			l[i][1] = line.get(i)[1];
		}
		return l;
	}

}
