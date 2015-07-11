import java.util.ArrayList;

public class YummyCritter {
	public String name;
	public int row;
	public int column;
	public int position;

	//Constructor for YummyCritter
	public YummyCritter( int column, int row, int position){
		this.column = column;
		this.row = row;
		this.position = position;
	}

	/*********************************************************************/
	/* moveYummy method                                                  */
	/*                                                                   */
	/* moves yummy on the larger terrain                                 */
	/*                                                                   */
	/* Input: the yummy array and the terrain                            */
	/* Output: an updated position for the yummy                         */
	/*********************************************************************/
	
	public void moveYummy (Life life, int[][] terrain ){
		// code fragment to check if cells within a 7x7 window
		// note: there are 48 = 7x7 - 1 candidate cells
		boolean repeat =true;
		int randomNum;
		int xCoor=0, yCoor=0;
		int [][] line = null;
		while (repeat) {
			line = null;
			// get a random number between 0..47
			randomNum = Life.random.nextInt(48);
			// turn this value to be 0..23 and 25..48
			// position 24 is the center of the 7x7 window
			if (randomNum > 23)
				randomNum++;
			// convert that to a coordinate in a 7x7 window
			yCoor = row - 3 + randomNum / 7;
			xCoor = column - 3 + randomNum % 7;

			if ( xCoor >= 0 && xCoor < life.columns && yCoor >= 0 && yCoor < life.rows && life.isOccupied(xCoor, yCoor) == false )
			{
				// check to see if that location is visible
				int[] ref1 = {terrain[row][column], row, column}; 
				int[] ref2 = {terrain[yCoor][xCoor],yCoor, xCoor};
				line = clearLine(terrain, ref1, ref2);
				if(line!=null)
					repeat = false;
			}
		}

		if ( line != null ) {
			life.updateYummyPPM(line);
			this.row = yCoor;
			this.column = xCoor;
			life.removeDeadThing( yCoor, xCoor );
		}
	}

	/*********************************************************************/
	/* clearLine method                                                  */
	/*                                                                   */
	/* Check if the two pixels are visible by one another                */
	/*                                                                   */
	/* Input: two pixels, the terrain, and reference points              */
	/* Output: an array of hungries                                      */
	/*********************************************************************/
	
	private int[][] clearLine(int[][] terrain, int[] ref1, int[] ref2){

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
			if (smallY==this.row){ //switched
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
			if (smallX==this.column){ //switched
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