package org.cascadelms.data.sources;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.cascadelms.StreamActivity.CourseDataSource;
import org.cascadelms.course_documents.DocumentsFragment.DocumentsDataSource;
import org.cascadelms.data.models.Assignment;
import org.cascadelms.data.models.BlogPost;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.Document;
import org.cascadelms.data.models.StreamItem;
import org.cascadelms.fragments.AssignmentsFragment.AssignmentsDataSource;
import org.cascadelms.fragments.CourseBlogFragment.BlogDataSource;
import org.cascadelms.fragments.SocialStreamFragment.StreamDataSource;

public class FakeDataSource implements CourseDataSource, DocumentsDataSource,
		AssignmentsDataSource, BlogDataSource, StreamDataSource
{
	private static ArrayList<Course> courseList;

	private static ArrayList<Document> documentsListDesign;
	private static ArrayList<Document> documentsListMachine;
	private static ArrayList<Document> documentsListAI;
	private static ArrayList<Document> documentsListInfo;
	private static ArrayList<Document> documentsListUI;

	/* TODO Lists with no fake data in them yet. */
	private static ArrayList<Assignment> emptyAssignmentsList;
	private static ArrayList<BlogPost> emptyBlogPostList;
	private static ArrayList<StreamItem> emptyStreamItemList;

	/* Constants */
	private static final int COURSE_ID_DESIGN = 0;
	private static final int COURSE_ID_MACHINE_LEARNING = 1;
	private static final int COURSE_ID_AI = 2;
	private static final int COURSE_ID_INFO_RETRIEVAL = 3;
	private static final int COURSE_ID_UI = 4;

	/* Static initializer */
	{
		/* Builds the course list */
		courseList = new ArrayList<Course>();
		courseList.add( ( new Course.Builder( COURSE_ID_DESIGN,
				"CS5002-Senior Design" ) ).build() );
		courseList.add( ( new Course.Builder( COURSE_ID_MACHINE_LEARNING,
				"CS6037-Machine Learning" ) ).build() );
		courseList.add( ( new Course.Builder( COURSE_ID_AI,
				"CS6033-Artificial Intellegence" ) ).build() );
		courseList.add( ( new Course.Builder( COURSE_ID_INFO_RETRIEVAL,
				"CS6054-Info Retrieval" ) ).build() );
		courseList.add( ( new Course.Builder( COURSE_ID_UI,
				"CS6067-User Interface" ) ).build() );

		/* Builds the documents list for each course. */
		documentsListDesign = new ArrayList<Document>();
		try
		{
			documentsListDesign.add( Document.Builder.getFileBuilder( 0,
					"Syllabus-2013", ".pdf", 20000,
					new URL( "http://example.com/Syllabus2013.pdf" ) ).build() );
			documentsListDesign.add( Document.Builder.getFileBuilder( 1,
					"Project Teams", ".txt", 5000,
					new URL( "http://example.com/ProjectTeams.txt" ) ).build() );
			documentsListDesign.add( Document.Builder.getFileBuilder( 2,
					"Sample Project Poster", ".png", 200000,
					new URL( "http://example.com/Sample_project_poster.png" ) )
					.build() );
			documentsListDesign.add( Document.Builder.getFileBuilder( 3,
					"Abstract Edits", ".docx", 25000,
					new URL( "http://example.com/Abstract_edits.docx" ) )
					.build() );
			documentsListDesign.add( Document.Builder.getFileBuilder( 4,
					"PreviousProjects1", ".pdf", 25000,
					new URL( "http://example.com/Previous_Projects1.pdf" ) )
					.build() );
			documentsListDesign.add( Document.Builder.getFileBuilder( 5,
					"PreviousProjects2", ".pdf", 25000,
					new URL( "http://example.com/Previous_Projects2.pdf" ) )
					.build() );
			documentsListDesign.add( Document.Builder.getFileBuilder( 6,
					"PreviousProjects3", ".pdf", 25000,
					new URL( "http://example.com/Previous_Projects3.pdf" ) )
					.build() );
			documentsListDesign.add( Document.Builder.getFileBuilder( 7,
					"PreviousProjects4", ".pdf", 25000,
					new URL( "http://example.com/Previous_Projects4.pdf" ) )
					.build() );
		} catch( MalformedURLException e )
		{
		}
		documentsListMachine = new ArrayList<Document>();
		try
		{
			documentsListMachine.add( Document.Builder.getFileBuilder( 10,
					"MachineSyllabus-2013", ".pdf", 20000,
					new URL( "http://example.com/Syllabus2013.pdf" ) ).build() );
			documentsListMachine.add( Document.Builder.getFileBuilder( 11,
					"Supervised Learning", ".ppt", 50000,
					new URL( "http://example.com/Supervised.ppt" ) ).build() );
			documentsListMachine.add( Document.Builder.getFileBuilder( 12,
					"Transduction Training Cases", ".dat", 543210,
					new URL( "http://example.com/training-cases.dat" ) )
					.build() );
			documentsListMachine.add( Document.Builder.getFileBuilder( 13,
					"Cluster Analysis", ".ppt", 25000,
					new URL( "http://example.com/Clustering.ppt" ) ).build() );
		} catch( MalformedURLException e )
		{
		}
		documentsListAI = new ArrayList<Document>();
		try
		{
			documentsListAI.add( Document.Builder.getFileBuilder( 100,
					"AI_Syllabus_2013-14", ".docx", 20000,
					new URL( "http://example.com/AI_Syllabus_2013-14.docx" ) )
					.build() );
			documentsListAI.add( Document.Builder.getFileBuilder( 101,
					"Lecture_1", ".ppt", 50000,
					new URL( "http://example.com/Lecture.ppt" ) ).build() );
			documentsListAI.add( Document.Builder.getFileBuilder( 102,
					"Lecture_2", ".ppt", 50000,
					new URL( "http://example.com/Lecture.ppt" ) ).build() );
			documentsListAI.add( Document.Builder.getFileBuilder( 103,
					"Lecture_3", ".ppt", 50000,
					new URL( "http://example.com/Lecture.ppt" ) ).build() );
			documentsListAI.add( Document.Builder.getFileBuilder( 104,
					"Lecture_4", ".ppt", 50000,
					new URL( "http://example.com/Lecture.ppt" ) ).build() );
			documentsListAI.add( Document.Builder.getFileBuilder( 105,
					"Lecture_5", ".ppt", 50000,
					new URL( "http://example.com/Lecture.ppt" ) ).build() );
			documentsListAI.add( Document.Builder.getFileBuilder( 106,
					"Lecture_6", ".ppt", 50000,
					new URL( "http://example.com/Lecture.ppt" ) ).build() );
			documentsListAI.add( Document.Builder.getFileBuilder( 107,
					"Lecture_7", ".ppt", 50000,
					new URL( "http://example.com/Lecture.ppt" ) ).build() );
			documentsListAI.add( Document.Builder.getFileBuilder( 108,
					"Lecture_8", ".ppt", 50000,
					new URL( "http://example.com/Lecture.ppt" ) ).build() );
		} catch( MalformedURLException e )
		{
		}
		documentsListInfo = new ArrayList<Document>();
		try
		{
			documentsListInfo.add( Document.Builder.getFileBuilder( 10,
					"Info_retrieval2013", ".pdf", 20000,
					new URL( "http://example.com/Syllabus2013.pdf" ) ).build() );
			documentsListMachine.add( Document.Builder.getFileBuilder( 11,
					"Lecture Notes on a very obscure topic with a long title",
					".ppt", 50000,
					new URL( "http://example.com/Supervised.ppt" ) ).build() );
		} catch( MalformedURLException e )
		{
		}

		/* Leaves the UI course as an empty list with no Documents. */
		documentsListUI = new ArrayList<Document>();

		/* Builds Assignment lists for each course. */
		emptyAssignmentsList = new ArrayList<Assignment>();
		/* TODO Create a list of fake data to return for each course. */

		emptyBlogPostList = new ArrayList<BlogPost>();
		/* TODO Create fake blog posts to return for each course. */

		emptyStreamItemList = new ArrayList<StreamItem>();
		/* TODO Create fake Stream items to return for each course. */
	}

	private static FakeDataSource instance;

	private FakeDataSource()
	{
		/* Private constructor */
	}

	public static FakeDataSource getInstance()
	{
		if( instance == null )
		{
			instance = new FakeDataSource();
		}
		return instance;
	}

	/* CourseDataSource */
	@Override
	public List<Course> getAvailableCourses()
	{
		return courseList;
	}

	/* DocumentsDataSource Methods */
	@Override
	public List<Document> getDocumentsForCourse( int courseId )
	{
		switch( courseId )
		{
			case COURSE_ID_DESIGN:
			{
				return documentsListDesign;
			}
			case COURSE_ID_MACHINE_LEARNING:
			{
				return documentsListMachine;
			}
			case COURSE_ID_AI:
			{
				return documentsListAI;
			}
			case COURSE_ID_INFO_RETRIEVAL:
			{
				return documentsListInfo;
			}
			case COURSE_ID_UI:
			{
				return documentsListUI;
			}
			default:
			{
				return null;
			}
		}
	}

	@Override
	public List<Document> getDocumentsInFolder( Document folder )
	{
		// TODO Create some test data to return.
		return new ArrayList<Document>();
	}

	/* AssignmentsDataSource Methods */
	@Override
	public List<Assignment> getAssignmentsForCourse( int courseId )
	{
		/* TODO Return lists of fake data on a course by course basis. */
		switch( courseId )
		{
			case COURSE_ID_DESIGN:
			{

			}
			case COURSE_ID_MACHINE_LEARNING:
			{

			}
			case COURSE_ID_AI:
			{

			}
			case COURSE_ID_INFO_RETRIEVAL:
			{

			}
			case COURSE_ID_UI:
			{

			}
			default:
			{
				return emptyAssignmentsList;
			}
		}
	}

	@Override
	public List<BlogPost> getBlogPostsForCourse( int courseId )
	{
		/* TODO Return lists of fake data on a course by course basis. */
		switch( courseId )
		{
			case COURSE_ID_DESIGN:
			{

			}
			case COURSE_ID_MACHINE_LEARNING:
			{

			}
			case COURSE_ID_AI:
			{

			}
			case COURSE_ID_INFO_RETRIEVAL:
			{

			}
			case COURSE_ID_UI:
			{

			}
			default:
			{
				return emptyBlogPostList;
			}
		}
	}

	@Override
	public List<StreamItem> getAllStreamItems()
	{
		return emptyStreamItemList;
	}

	@Override
	public List<StreamItem> getStreamItemsForCourse( int courseId )
	{
		/* TODO Return lists of fake data on a course by course basis. */
		switch( courseId )
		{
			case COURSE_ID_DESIGN:
			{

			}
			case COURSE_ID_MACHINE_LEARNING:
			{

			}
			case COURSE_ID_AI:
			{

			}
			case COURSE_ID_INFO_RETRIEVAL:
			{

			}
			case COURSE_ID_UI:
			{

			}
			default:
			{
				return emptyStreamItemList;
			}
		}
	}
}
