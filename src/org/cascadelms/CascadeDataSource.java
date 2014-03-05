package org.cascadelms;

import java.util.List;

import org.cascadelms.data_models.Assignment;
import org.cascadelms.data_models.Course;
import org.cascadelms.data_models.Document;
import org.cascadelms.data_models.StreamItem;

/**
 * An interface for objects that provide access to Cascade LMS data.  This interface defines the signatures of methods
 * used to retrieve model data from the Cascade server.  How that data is obtained is left up to the 
 * implementer.
 */
public interface CascadeDataSource 
{		
	/**
	 * Retrieves a listing of courses which user is currently registered for.
	 * @return a {@link List} of {@link Course} objects.
	 */
	public abstract List<Course> getRegisteredCourses();
	
	/**
	 * Retrieves course data based on its id in the Cascade database.
	 * @param id the id of the course in the cascade database
	 * @return a {@link Course} with the given id
	 */
	public abstract Course getCourseById( int id );
	
	/**
	 * Retrieves all the items from the user's social stream.
	 * @return a {@link List} of {@link StreamItem} objects
	 */
	public abstract List<StreamItem> getAllSocialStreamItems();
	
	/**
	 * Retrieves only items from the user's social stream which are related to a specified
	 * course.
	 * @param courseId the id of the course to filter by
	 * @return a {@link List} of {@link StreamItem} objects
	 */
	public abstract List<StreamItem> getAllSocialStreamItemsForCourse( int courseId );
	
	/**
	 * Retrieves assignments for a given course from the database.  
	 * @param courseId the id of the course to filter by
	 * @return a {@link List} of {@link Assignment} objects
	 */
	public abstract List<Assignment> getAllAssignmentsForCourse( int courseId );
	
	/**
	 * Retrieves an assignment given its database id
	 * @param id the id of the assignment in the database
	 * @return the {@link Assignment}
	 */
	public abstract Assignment getAssignmentById( int id );
}
