//////////////// DON'T CHANGE THIS FILE ////////////////

public interface DT 
{
	
	//O(n)
	void addPoint(Point point); 
	
	//O(n)
	Point[] getPointsInRangeRegAxis(int min, int max, boolean axis);
	
	//O(n)
	Point[] getPointsInRangeOppAxis(int min, int max, boolean axis);

	//O(1)
	double getDensity();

	//O(|A|) - |A| is the number of points that will be deleted from the data structure
	void narrowRange(int min, int max, boolean axis);
	
	//O(1)
	boolean	getLargestAxis();

	//O(n)
	Container getMedian(boolean axis);
	
	//O(min(n, |B|log|B|)) - B is the number of points in the strip. 
	//You may use Arrays.sort to implement this function, but you may not change the class Point for that sake
	Point[]	nearestPairInStrip(Container container, double width, boolean axis);

	//??? - you need to compute the running time of this funtion.
	Point[]	nearestPair();
}
