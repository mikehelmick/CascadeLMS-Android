package org.cascadelms;
import java.util.ArrayList;
import java.util.List;

import org.cascadelms.data_models.Course;


public class CascadeDataSource 
{
	//private HTTPRequester;
	//private XMLReader;
	
	/**
	 * Constructs a new instance of {@link CascadeDataSource}.  No actual communications take 
	 * place in the constructor, so it returns very quickly.
	 */
	public CascadeDataSource()
	{
		/* Initialize HTTPRequester and XMLReader when implemented. TODO */
	}
	
	
	public List<Course> getAvailableCourses()
	{
		return CascadeDataSource.FakeData.courseList;
	}
	
	/**
	 * A class for storing test data.  CascadeDataSource should return data from this class 
	 * until actual server communications are implemented. 
	 * @author Alex
	 *
	 */
	private static class FakeData
	{
		static ArrayList<Course> courseList;
		
		/* Static initializer */
		{
			courseList = new ArrayList<Course>();	
			courseList.add( new Course( 0, "CS5002-Senior Design", null ) );
			courseList.add( new Course( 1, "CS6037-Machine Learning", null ) );
			courseList.add( new Course( 2, "CS6033-Artificial Intellegence", null ) );
			courseList.add( new Course( 3, "CS6054-Info Retrieval", null ) );
			courseList.add( new Course( 4, "CS6067-User Interface", null ) );
		};
		
	}
}
