package org.cascadelms;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * A base class for Fragments whose data depends on the currently selected course.  Subclasses 
 * receive an instance of {@link CourseDataProvider} to access selected course data.  
 * <p>
 * Note that the hosting Activity must implement {@link CourseDataProvider} or a 
 * <code>ClassCastException</code> will be thrown.
 *
 */
public class CourseFragment extends Fragment 
{
	protected CourseDataProvider mCourseDataProvider;
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		
		/* The host Activity must implement CourseDataProvider or an exception is thrown. */
		try 
		{
			this.mCourseDataProvider = (CourseDataProvider) activity;
		} 
		catch ( ClassCastException e ) 
		{
			throw new ClassCastException( "Activity " + activity 
					+ " must implement " + CourseDataProvider.class.getName() 
					+ " to host " + CourseFragment.class.getName() );
		}
	}

	/**
	 * An interface for objects which are capable of providing data about the course which is currently selected in the app.  
	 */
	public interface CourseDataProvider
	{
		public abstract int getCourseId();
		
		public abstract String getCourseTitle();
	}
}
