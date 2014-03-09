package org.cascadelms.data_models;

import java.util.ArrayList;
import java.util.List;

public class FakeDataSource implements CascadeDataSource
{
	static ArrayList<Course> courseList;
	
	/* Static initializer */
	{
		courseList = new ArrayList<Course>();	
		courseList.add( (new Course.Builder( 0, "CS5002-Senior Design" )).build() );
		courseList.add( (new Course.Builder( 1, "CS6037-Machine Learning" )).build() );
		courseList.add( (new Course.Builder( 2, "CS6033-Artificial Intellegence" )).build() );
		courseList.add( (new Course.Builder( 3, "CS6054-Info Retrieval" )).build() );
		courseList.add( (new Course.Builder( 4, "CS6067-User Interface" )).build() );
	};
	
	@Override
	public List<Course> getRegisteredCourses() 
	{
		return courseList;
	}

	@Override
	public Course getCourseById(int id) 
	{
		return courseList.get( id );
	}

	@Override
	public List<StreamItem> getAllSocialStreamItems() 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException( "Method not implemented." );
	}

	@Override
	public List<StreamItem> getAllSocialStreamItemsForCourse(int courseId) 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException( "Method not implemented." );
	}

	@Override
	public List<Assignment> getAllAssignmentsForCourse(int courseId) 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException( "Method not implemented." );
	}

	@Override
	public Assignment getAssignmentById(int id) 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException( "Method not implemented." );
	}

	@Override
	public List<Document> getAllDocumentsForCourse(int courseId) 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException( "Method not implemented." );
	}

	@Override
	public List<BlogPost> getAllBlogPostsForCourse(int courseId) 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException( "Method not implemented." );
	}

	@Override
	public BlogPost getBlogPostById(int id) 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException( "Method not implemented." );
	}

}
