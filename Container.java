

//Don't change the class name
public class Container{

	//fields

	private Point data;//Don't delete or change this field;
	private  Container prevX;
	private Container prevY;
	private Container nextX;
	private Container nextY;


	//constructors


	//constructor with data
	public Container(Point p)
	{
		this.data =p;
	}


	//duplicate constructor
	public Container(Container other)
	{
		this.prevX=other.prevX;
		this.prevY=other.prevY;
		this.nextX = other.nextX;
		this.nextY=other.nextY;
		this.data =other.data;
	}


//get functions

	public Container getPrev(boolean axis)
	{
		if (axis)
			return this.prevX;
		else
			return this.prevY;
	}

	public Container getNext(boolean axis)
	{
		if (axis)
			return nextX;
		else
			return nextY;

	}


	//Don't delete or change this function
	public Point getData()
	{
		return data;
	}

	public int getValue(boolean axis){

		if (axis) //x axis
			return this.getData().getX();
		else
			return this.getData().getY();
	}


	//set functions 
	public void setPrev(Container c, boolean axis)
	{
		if (axis)
			this.prevX=c;
		else
			this.prevY=c;
	}

	public void setNext(Container c, boolean axis)
	{
		if (axis)
			this.nextX=c;
		else
			this.nextY=c;
	}

}//end class container
