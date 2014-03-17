package org.cascadelms.data.sources;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
	/* TODO Lists with no fake data in them yet. */
	private static ArrayList<BlogPost> emptyBlogPostList;
	private static ArrayList<StreamItem> emptyStreamItemList;

	/* Static initializer */
	{
		/* Builds the data to return. */
		FakeDataSource.initCourseData();
		FakeDataSource.initDocumentsData();
		FakeDataSource.initAssignmentsData();

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
	/* Course ID Constants */
	private static final int COURSE_ID_DESIGN = 0;
	private static final int COURSE_ID_MACHINE_LEARNING = 1;
	private static final int COURSE_ID_AI = 2;
	private static final int COURSE_ID_INFO_RETRIEVAL = 3;
	private static final int COURSE_ID_UI = 4;

	private static ArrayList<Course> courseList;

	private static void initCourseData()
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
	}

	@Override
	public List<Course> getAvailableCourses()
	{
		return courseList;
	}

	/* DocumentsDataSource */
	/* ID Constants for Directories */
	private static final int DOCUMENT_FOLDER_LECTURE_NOTES = 555;
	private static final int DOCUMENT_FOLDER_PAST_PROJECTS = 444;

	private static ArrayList<Document> documentsListDesign;
	private static ArrayList<Document> documentsListMachine;
	private static ArrayList<Document> documentsListAI;
	private static ArrayList<Document> documentsListInfo;
	private static ArrayList<Document> documentsListUI;
	private static ArrayList<Document> directoryListPreviousProjects;
	private static ArrayList<Document> directoryListLectures;

	private static void initDocumentsData()
	{
		/* Builds Documents lists for each course. */
		documentsListDesign = new ArrayList<Document>();
		try
		{
			/* List for Senior Design */
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
			documentsListDesign.add( Document.Builder.getFolderBuilder(
					DOCUMENT_FOLDER_PAST_PROJECTS, "Previous Projects" )
					.build() );

			/* A list for the Previous Projects subfolder in Senior Design. */
			directoryListPreviousProjects = new ArrayList<Document>();
			directoryListPreviousProjects.add( Document.Builder.getFileBuilder(
					4, "PreviousProjects1", ".pdf", 25000,
					new URL( "http://example.com/Previous_Projects1.pdf" ) )
					.build() );
			directoryListPreviousProjects.add( Document.Builder.getFileBuilder(
					5, "PreviousProjects2", ".pdf", 25000,
					new URL( "http://example.com/Previous_Projects2.pdf" ) )
					.build() );
			directoryListPreviousProjects.add( Document.Builder.getFileBuilder(
					6, "PreviousProjects3", ".pdf", 25000,
					new URL( "http://example.com/Previous_Projects3.pdf" ) )
					.build() );
			directoryListPreviousProjects.add( Document.Builder.getFileBuilder(
					7, "PreviousProjects4", ".pdf", 25000,
					new URL( "http://example.com/Previous_Projects4.pdf" ) )
					.build() );

			/* List for Machine Learning */
			documentsListMachine = new ArrayList<Document>();
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

			/* List for Artificial Intelligence */
			documentsListAI = new ArrayList<Document>();
			documentsListAI.add( Document.Builder.getFileBuilder( 100,
					"AI_Syllabus_2013-14", ".docx", 20000,
					new URL( "http://example.com/AI_Syllabus_2013-14.docx" ) )
					.build() );
			documentsListAI.add( Document.Builder.getFolderBuilder(
					DOCUMENT_FOLDER_LECTURE_NOTES, "Lectures" ).build() );

			/* List for the Lectures sub-folder in AI */
			directoryListLectures = new ArrayList<Document>();
			directoryListLectures.add( Document.Builder.getFileBuilder( 101,
					"Lecture_1", ".ppt", 50000,
					new URL( "http://example.com/Lecture1.ppt" ) ).build() );
			directoryListLectures.add( Document.Builder.getFileBuilder( 102,
					"Lecture_2", ".ppt", 50000,
					new URL( "http://example.com/Lecture2.ppt" ) ).build() );
			directoryListLectures.add( Document.Builder.getFileBuilder( 103,
					"Lecture_3", ".ppt", 50000,
					new URL( "http://example.com/Lecture3.ppt" ) ).build() );
			directoryListLectures.add( Document.Builder.getFileBuilder( 104,
					"Lecture_4", ".ppt", 50000,
					new URL( "http://example.com/Lecture4.ppt" ) ).build() );
			directoryListLectures.add( Document.Builder.getFileBuilder( 105,
					"Lecture_5", ".ppt", 50000,
					new URL( "http://example.com/Lecture5.ppt" ) ).build() );
			directoryListLectures.add( Document.Builder.getFileBuilder( 106,
					"Lecture_6", ".ppt", 50000,
					new URL( "http://example.com/Lecture6.ppt" ) ).build() );
			directoryListLectures.add( Document.Builder.getFileBuilder( 107,
					"Lecture_7", ".ppt", 50000,
					new URL( "http://example.com/Lecture7.ppt" ) ).build() );
			directoryListLectures.add( Document.Builder.getFileBuilder( 108,
					"Lecture_8", ".ppt", 50000,
					new URL( "http://example.com/Lecture8.ppt" ) ).build() );

			/* List for Information Retrieval */
			documentsListInfo = new ArrayList<Document>();
			documentsListInfo.add( Document.Builder.getFileBuilder( 10,
					"Info_retrieval2013", ".pdf", 20000,
					new URL( "http://example.com/Syllabus2013.pdf" ) ).build() );
			documentsListMachine.add( Document.Builder.getFileBuilder( 11,
					"Lecture Notes on a very obscure topic with a long title",
					".ppt", 50000,
					new URL( "http://example.com/Supervised.ppt" ) ).build() );

			/* Leaves the UI course as an empty list with no Documents. */
			documentsListUI = new ArrayList<Document>();
		} catch( MalformedURLException e )
		{

		}
	}

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
		switch( folder.getId() )
		{
			case DOCUMENT_FOLDER_LECTURE_NOTES:
			{
				return directoryListLectures;
			}
			case DOCUMENT_FOLDER_PAST_PROJECTS:
			{
				return directoryListPreviousProjects;
			}
			default:
			{
				return null;
			}
		}
	}

	/* Assignments Data */
	private static ArrayList<Assignment> assignmentsListDesign;
	private static ArrayList<Assignment> assignmentsListMachine;
	private static ArrayList<Assignment> assignmentsListAI;
	private static ArrayList<Assignment> assignmentsListInfo;
	private static ArrayList<Assignment> assignmentsListUI;

	private static void initAssignmentsData()
	{
		/* Dates to use in Assignment building. */
		Date availableDate = new Date( 1387381568 );
		Date lastSubmissionDate = new Date( 1398440768 );
		int id = 0;
		long dueTime = 1392181200;
		long week = 604800;

		assignmentsListDesign = new ArrayList<Assignment>();
		assignmentsListDesign.add( new Assignment.Builder( id++,
				"150-Word Project Abstract", Assignment.Category.ASSIGNMENT,
				availableDate, new Date( dueTime += week ), lastSubmissionDate,
				true, 100 ).build() );
		assignmentsListDesign.add( new Assignment.Builder( id++,
				"Presentation Slides", Assignment.Category.ASSIGNMENT,
				availableDate, new Date( dueTime += week ), lastSubmissionDate,
				true, 100 ).build() );
		assignmentsListDesign.add( new Assignment.Builder( id++, "Test Plans",
				Assignment.Category.ASSIGNMENT, availableDate, new Date(
						dueTime += week ), lastSubmissionDate, true, 100 )
				.build() );
		assignmentsListDesign.add( new Assignment.Builder( id++,
				"Current Events Essay", Assignment.Category.ASSIGNMENT,
				availableDate, new Date( dueTime += week ), lastSubmissionDate,
				true, 100 ).build() );
		assignmentsListDesign.add( new Assignment.Builder( id++,
				"Online User Manual", Assignment.Category.ASSIGNMENT,
				availableDate, new Date( dueTime += week ), lastSubmissionDate,
				false, 100 ).build() );
		assignmentsListMachine = new ArrayList<Assignment>();
		/* TODO add more assignment data. */
		assignmentsListAI = new ArrayList<Assignment>();
		assignmentsListInfo = new ArrayList<Assignment>();
		assignmentsListUI = new ArrayList<Assignment>();
	}

	@Override
	public List<Assignment> getAssignmentsForCourse( int courseId )
	{
		switch( courseId )
		{
			case COURSE_ID_DESIGN:
			{
				return assignmentsListDesign;
			}
			case COURSE_ID_MACHINE_LEARNING:
			{
				return assignmentsListMachine;
			}
			case COURSE_ID_AI:
			{
				return assignmentsListAI;
			}
			case COURSE_ID_INFO_RETRIEVAL:
			{
				return assignmentsListInfo;
			}
			case COURSE_ID_UI:
			{
				return assignmentsListUI;
			}
			default:
			{
				return null;
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
