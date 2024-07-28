/**
 * The DataStructure class implements the DT interface.
 *
 * DataStructure class is a linked list implementation of a 2D coordinate system.
 * Each node in the list contains a Point object, which represents a point in the 2D coordinate system.
 * The linked list is sorted in non-decreasing order by both the X-axis and the Y-axis values of the Point objects.
 * It is one by creating 2 "chains" so that one chain is sorted by X-axis and the other sorted by Y-axis.
 * The chains are made from the same nodes.
 * Every node has 2 sets of next and prev pointer. One of the set is regarding the X-axis and the other the Y-axis.
 *
 *
 * !note: In some methods a boolean argument is passed - axis.
 * True value indicates the X-axis and a false value indicates the Y -axis.
 */

public class DataStructure implements DT {

	//////////////// FIELDS ////////////////

	private Container headX;
	private Container headY;
	private Container tailX;
	private Container tailY;

	private int length; //the number of points in the list.


	//////////////// CONSTRUCTORS ////////////////
	/**
	 * empty constructor
	 */

	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure() {
		this.length=0;
	}


	/**
	 * constructor with first node (container)
	 */
	public DataStructure(Container c) {
		this.length=1;

		this.headX = c;
		this.headY = c;
		this.tailX = c;
		this.tailY = c;
	}

	//////////////// functions ////////////////


	/**
	 * The function first creates a new Container object to wrap the Point object,
	 * If the new Container is the head of some chain, the function insert it to the first place and update the head.
	 * If not, it iterates over each chain of the list to determine the appropriate insertion point for the new container amd insert it there.
	 * At the end it updates the minimum and maximum X and Y.
	 * <p>
	 * Run Time: O(n),n is the number of points already in the list. The function iterate through the  list to find the insertion point.
	 */
	@Override
	public void addPoint(Point point) {

		Container this_node = new Container(point); //cover the point by Container

		if (isEmpty()) {
			setHead(this_node,true);
			setHead(this_node,false);
			setTail(this_node,true);
			setTail(this_node,false);
			this.length++;
			return;
		}

		boolean axis = true;
		for (int i = 0; i < 2; i++) {

			//checks if the new node is the head.
			if (this_node.getValue(axis) < this.getHead(axis).getValue(axis)) { //this node is the new head of the axis chain

				Container old_head = getHead(axis);

				this_node.setNext(old_head, axis); //set this node's axis next to the old head
				old_head.setPrev(this_node, axis); //set old head's axis prev to this node

				setHead(this_node, axis); //update the head
			}//end of if for head

			else { //not the head chain by axis

				Container pointer = justBefore(this_node.getValue(axis), axis);

				if (pointer.getNext(axis)!=null)
					pointer.getNext(axis).setPrev(this_node, axis);//set pointer's next axis's prev to this node

				this_node.setPrev(pointer, axis); //set this node's axis's prev to pointer
				this_node.setNext(pointer.getNext(axis),axis); //set this node next

				pointer.setNext(this_node, axis); //set pointer's axis's next to this node

			}// end else

			if (this_node.getPrev(axis)==getTail(axis)) //if this mode is the tail of the list
				setTail (this_node,axis);

			axis = false; //next iteration it will insert to the Y chain.
		} //end for

		this.length++;
	}//end function addPoint


	/**
	 * The function returns an array of Point in range of values of axis's values.
	 * The array is sorted in non-decreasing order by the axis value.
	 * It creates an array with appropriate  size.
	 * Then it goes thought the list and find the first node on the range.
	 * Then it adds the Point in range to the array.
	 * <p>
	 * Run Time O(n), where n is the number of nodes in the linked list.
	 * This is because the function must traverse through the list twice. First to count the number of nodes in range
	 * and second to  add the appropriate Point objects to the output array.
	 */

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, boolean axis) {

		int size = counterInRange(min, max, axis); //how many nodes there are at the range
		Point[] points_arr = new Point[size]; //create array

		Container pointer = justAfter(min,axis);

		for (int i = 0; i < points_arr.length; i++) {
			points_arr[i] = pointer.getData();
			pointer = pointer.getNext(axis);
		}//end for
		return points_arr;
	}//end getPointsInRangeRegAxis


	/**
	 * The function returns an array of Point in range of values of axis's values.
	 * The array is sorted in non-decreasing order by the other axis value.
	 * <p>
	 * It then creates an array of Point objects with a length equal to the number of nodes within the range.
	 * <p>
	 * Next, it sets a pointer to the head of the list and iterates through the list until it finds the first node in range.
	 * Note that the function literates on the chain of the other axis value.
	 * Then it adds each node's data to the array until it has reached the end of the range.
	 *
	 * Run Time O(n), where n is the number of nodes in the linked list.
	 * This is because the function must traverse through the list twice. First to count the number of nodes in range
	 * and second to  add the appropriate Point objects to the output array.
	 */

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, boolean axis) {
		int size = counterInRange(min, max, axis); //how many nodes there are at the range
		Point[] PointsArr = new Point[size]; //create array of the correct size

		Container pointer = getHead(!axis); //start from the head of thr other axis

		int i = 0; //index
		while (pointer != null) { //go over the list
			if (min <= pointer.getValue(axis) && pointer.getValue(axis) <= max) { //it the value is in range
				PointsArr[i] = pointer.getData(); //put it in
				i++;
			} //end if
			pointer = pointer.getNext(!axis);
		}//end while
		return PointsArr;
	} //end getPointsInRangeOppAxis
	//The time complexity is O(n), because the function go over the list without rehearse


	/**
	 * First the function calculates the length of the x-axis and y-axis.
	 * Then, if either the length of the x-axis or y-axis is zero, it returns -1 to indicate an error.
	 * Finally, it returns the density of the points in the space
	 * by dividing the number of points in the linked list by the product of the length of the x-axis and y-axis.
	 *
	 * The runtime complexity is O(1). Because the function only performs a fixed number of operations.
	 * It achieve it by using the length value instead of calculate it.
	 */
	@Override
	public double getDensity() {

		double lengthX= getTail(true).getValue(true)-getHead(true).getValue(true);
		double lengthY= getTail(false).getValue(false)-getHead(false).getValue(false);

		if (lengthX==0 || lengthY==0 )
			return -1;

		return (getListLength()/(lengthX*lengthY));

	}

	/**
	 * First the function go over from the head of the list to the first node in range, deleting the nodes as it go throw.
	 * Second it go over backward from the tail of the list to the first node in range, deleting the nodes as it go throw.
	 *
	 * Time complexity: O(|A|) where |A| is the number of points to be deleted.
	 * This is because it goes from the head of the list up to the first container the range
	 * and from the tail of the list backward up to the first container the range.
	 */
	@Override
	public void narrowRange(int min, int max, boolean axis) {


		Container pointer = getHead(axis);
		Container nextPointer = pointer.getNext(axis);

		while (pointer != null && pointer.getValue(axis)<min) { //delete nodes that are before min.
			deleteContainer(pointer);
			pointer=nextPointer;
			if (nextPointer!=null)
				nextPointer = pointer.getNext(axis);
		} //end while


		pointer = getTail(axis);
		Container beforePointer = pointer.getPrev(axis);

		while (pointer != null && pointer.getValue(axis)>max) { //delete nodes that are larger that max.
			deleteContainer(pointer);
			pointer=beforePointer;
			if (beforePointer!=null)
				beforePointer = pointer.getPrev(axis);
		}// end while

	}// end narrowRange

	/**
	 * The function use axisLength function in order to calculate the axis' length.
	 * Then it compare them and return true if X axis is bigger and false otherwise.
	 *
	 * Run time is O(1) since the axisLength function's run time is O(1).
	 * It archived it by using the head and tail of an axis chain, which hold the minimum and maximum axis' value.
	 */
	@Override
	public boolean getLargestAxis() {
		return (axisLength(true)>axisLength(false));
	}


	/**
	 * The function returns the median node of the linked list regard an axis chain.
	 * It first calculates the index of the median node by dividing the total number of nodes in the linked list by 2.
	 * Then, it iterates over the axis chain and returns the node at the calculated median index.
	 *
	 * The runtime complexity is O(n), where n is the number in the list.
	 * This because its literates through half of the list without going back.
	 */
	@Override
	public Container getMedian(boolean axis) {

		int medianIndex = counterInRange(Integer.MIN_VALUE, Integer.MAX_VALUE,axis)/2;
		Container pointer = getHead(axis);

		for (int i =0; i<medianIndex; i++){
			pointer=pointer.getNext(axis);
		}
		return pointer;
	}

	@Override
	public Point[] nearestPairInStrip(Container container, double width, boolean axis) {
// 		int min = container.getValue(axis)- (int) width/2;
// 		int max = container.getValue(axis)+ (int) width/2;

// 		Point[] points = getPointsInRangeRegAxis (min, max, axis);




// 		return findClosestPairRec (points, 0,  points.length - 1);
        return null;
	}

	private Point[] findClosestPairRec(Point[] points, int start, int end) {
		if (end - start < 3) {
			// If there are only 2 or 3 points, brute-force
// 			return bruteForce(points, start, end);
            return null;
		} else {
			// Divide the set of points into two halves
			int mid = (start + end) / 2;
			Point[] closestLeft = findClosestPairRec(points, start, mid);
			Point[] closestRight = findClosestPairRec(points, mid + 1, end);

			// Determine the closest pair among the left and right halves

			// Find the closest pair that spans the two halves

			// Determine the closest pair among all pairs

			return null;
		}
	}



	@Override
	public Point[] nearestPair() {
		// TODO Auto-generated method stub
		return null;
	}


	//TODO: add members, methods, etc.

	/////////////////////// GET FUNCTIONS ///////////////////////

	private int getListLength() {
		return this.length;
	}
	private Container getHead(boolean axis) {
		if (axis)
			return this.headX;
		else
			return this.headY;
	}

	private Container getTail(boolean axis) {
		if (axis)
			return this.tailX;
		else
			return this.tailY;
	}



	/////////////////////// SET FUNCTIONS ///////////////////////

	private void setHead(Container c, boolean axis) {
		if (axis)
			this.headX = c;
		else
			this.headY = c;
	}

	private void setTail(Container c, boolean axis) {
		if (axis)
			this.tailX = c;
		else
			this.tailY = c;
	}



	/////////////////////// OTHER FUNCTIONS ///////////////////////

	/**
	 * count the number of nodes\containers that is axis's value is in the range (include the min and max)
	 */
	private int counterInRange(int min, int max, boolean axis) {

		if (max < min) //just no
			return 0;

		int count = 0; //the result

		Container pointer = this.getHead(axis); //start from the head

		while (pointer != null && pointer.getValue(axis) <= max) {
			if (pointer.getValue(axis) >= min)
				count++;

			pointer = pointer.getNext(axis);

		}//end while

		return count;
	}//end counter


	/**
	 * return the container that has the maximum value that is still smaller than val
	 * 	doesn't work if the val is smaller than all nodes' value
	 */
	private Container justBefore(int val, boolean axis) {

		Container pointer = this.getHead(axis);
		while (pointer.getNext(axis) != null && pointer.getNext(axis).getValue(axis) < val) {
			pointer = pointer.getNext(axis);
			//stops at the maximum node that is has smaller value that this_node
		}//end while
		return pointer;
	}//end fun justBefore

	/**
	 * get min value and return a pointer to the first node that is equal or big from the min value.
	 */
	private Container justAfter(int min, boolean axis) {

		Container pointer = getHead(axis);
		while (pointer != null && pointer.getValue(axis) < min) {
			pointer = pointer.getNext(axis);
		}//end while

		return pointer;
	}//end justAfter


	private boolean isEmpty() {
		return (this.headX==null);
	}

	/**
	 * Delete a Container from linked list. If needed update list's head and tail.
	 *
	 * Run Time of O(1) because it preforms constant number of actions
	 */
	private void deleteContainer(Container c){
		boolean axis = true;
		for (int i=0; i<2; i++) {

			if (c.getPrev(axis) != null) // c isn't the head
				c.getPrev(axis).setNext(c.getNext(axis), axis);
			else{ // c is the head
				setHead(c.getNext(axis), axis);
				getHead(axis).setPrev(null,axis);
			}
			if (c.getNext(axis) != null) // c isn't the tail
				c.getNext(axis).setPrev(c.getPrev(axis), axis);
			else { // c is the tail
				setTail(c.getPrev(axis), axis);
				getTail(axis).setNext(null,axis);
			}

			axis=false; //next literate refer to the Y axis

		}//end for
		this.length--;
	} // end of deleteContainer

	private int axisLength(boolean axis) {
		return (getTail(axis).getValue(axis)-getHead(axis).getValue(axis));
	}



	}//end DataStructure class

