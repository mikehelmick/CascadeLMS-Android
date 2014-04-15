package org.cascadelms.data.sources;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.data.models.Assignment;
import org.cascadelms.data.models.Assignment.Category;
import org.cascadelms.data.models.BlogPost;
import org.cascadelms.data.models.Comment;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.Grade;
import org.cascadelms.data.models.StreamItem;
import org.cascadelms.data.models.User;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser
{
	/* Shared Constants */
	private static final String NAME_ID = "id";

	/* User Constants */
	private static final String NAME_USERS = "user";
	private static final String NAME_USER = "user";
	private static final String NAME_USER_NAME = "name";
	private static final String NAME_USER_GRAVATAR = "gravatar_url";

	/* Course Constants */
	private static final String NAME_COURSE_CONTAINER = "courses";
	private static final String NAME_COURSE = "course";
	private static final String NAME_COURSE_TITLE = "title";
	private static final String NAME_COURSE_DESCRIPTION = "description";
	private static final String NAME_COURSE_TERM = "term";
	private static final String NAME_COURSE_TERM_ID = "id";
	private static final String NAME_COURSE_TERM_SEMESTER = "semester";
	private static final String NAME_COURSE_CURRENT_FLAG = "current";
	private static final String NAME_COURSE_OVERVIEW = "course_overview";

	/* Document Constants */
	private static final String NAME_DOCUMENTS = "documents";
	private static final String NAME_DOCUMENT = "document";
	private static final String NAME_DOCUMENT_TITLE = "title";
	private static final String NAME_DOCUMENT_EXTENSION = "extension";
	private static final String NAME_DOCUMENT_SIZE = "size";
	private static final String NAME_DOCUMENT_URL = "document_url";
	private static final String NAME_DOCUMENT_FOLDER = "folder";

	/* Social Feed Constants */
	private static final String NAME_FEED_HOME = "home";
    private static final String NAME_FEED_COURSE = "course_overview";
	private static final String NAME_FEED_ITEMS = "feed_items";
	private static final String NAME_FEED_ITEM = "item";
    private static final String NAME_FEED_POST = "post";
	private static final String NAME_FEED_ITEM_POST_DATE = "date";
	private static final String NAME_FEED_ITEM_AUTHOR = "user";
	private static final String NAME_A_PLUS_COUNT = "aplus_count";
	private static final String NAME_A_PLUS_USERS = "aplus_users";
	private static final String NAME_FEED_ITEM_BODY = "body";
	private static final String NAME_FEED_ITEM_BODY_HTML = "body_html";
	private static final String NAME_FEED_ITEM_COMMENT_COUNT = "comment_count";
    private static final String NAME_FEED_ITEM_COMMENTS = "comments";

	/* Blog Post Constants */
	private static final String NAME_BLOG_POSTS = "blog_posts";
	private static final String NAME_BLOG_POST = "blog_post";
	private static final String NAME_BLOG_POST_TITLE = "title";
	private static final String NAME_BLOG_POST_FEATURED_FLAG = "featured";
	private static final String NAME_BLOG_POST_AUTHOR_NAME = "author";
	private static final String NAME_BLOG_POST_POSTED_DATE = "posted_at";
	private static final String NAME_BLOG_POST_COMMENT_COUNT = "comments";
	private static final String NAME_BLOG_POST_BODY = "body";
	private static final String NAME_BLOG_POST_APLUS_COUNT = "aplus_count";
	private static final String NAME_BLOG_POST_APLUS_USERS = "aplus_users";
	
	/* Comment Constants */
    private static final String NAME_COMMENT = "comment";
    private static final String NAME_COMMENT_USER = "user";
    private static final String NAME_COMMENT_CREATED_AT = "created_at";
    private static final String NAME_COMMENT_BODY = "body_html";

	/* Assignments Constants */
	private static final String NAME_ASSIGNMENT = "assignment";
	private static final String NAME_ASSIGNMENTS = "assignments";
	private static final String NAME_ASSIGNMENT_TITLE = "title";
	private static final String NAME_ASSIGNMENT_CATEGORY = "category";
	private static final String NAME_ASSIGNMENT_OPEN_DATE = "open_date";
	private static final String NAME_ASSIGNMENT_DUE_DATE = "due_date";
	private static final String NAME_ASSIGNMENT_CLOSE_DATE = "close_date";
	private static final String NAME_ASSIGNMENT_GRADE_RELEASED = "grades_released";
	private static final String NAME_ASSIGNMENT_POINTS_POSSIBLE = "points_possible";

	/* Grades Constants */
	private static final String NAME_GRADE = "grade";
	private static final String NAME_GRADES = "grades";
	private static final String NAME_GRADE_ASSIGNMENT_ID = "assignment_id";
	private static final String NAME_GRADE_ASSIGNMENT_NAME = "assignment";
	private static final String NAME_GRADE_CATEGORY = "category";
	private static final String NAME_GRADE_POST_DATE = "date";
	private static final String NAME_GRADE_TYPE = "grade_type";
	private static final String NAME_GRADE_POINTS_EARNED = "grade";
	private static final String NAME_GRADE_POINTS_POSSIBLE = "points_possible";
	private static final String NAME_GRADE_PERCENTAGE = "percentage";

	private static SAXBuilder builder;

	{
		XMLParser.builder = new SAXBuilder();
	}

	private Document document;

	/**
	 * Creates an XMLParser to read XML from a String.
	 * 
	 * @param source
	 *            a <code>String</code>
	 * @throws IOException
	 * @throws JDOMException
	 */
	private XMLParser( String source ) throws ParseException
	{
		try
		{
			Reader reader = new StringReader( source );
			this.document = XMLParser.builder.build( reader );
		} catch( JDOMException e )
		{
			throw new ParseException( e );
		} catch( IOException e )
		{
			throw new ParseException( e );
		}
	}

	/**
	 * Creates an XMLParser to read from an InputStream.
	 * 
	 * @param source
	 *            an <code>InputStrea</code>
	 * @throws JDOMException
	 * @throws IOException
	 * @throws ParseException
	 */
	private XMLParser( InputStream source ) throws ParseException
	{
		try
		{
			this.document = XMLParser.builder.build( source );
		} catch( JDOMException e )
		{
			throw new ParseException( e );
		} catch( IOException e )
		{
			throw new ParseException( e );
		}
	}

	/**
	 * Parses a list of StreamItems from an XML InputStream.
	 * 
	 * @param xmlStream
	 * @return
	 * @throws ParseException
	 */
	public static List<StreamItem> parseFeed( InputStream xmlStream )
			throws ParseException
	{
		List<StreamItem> items = new ArrayList<StreamItem>();

		XMLParser parser = new XMLParser( xmlStream );

		/* Ensures the root element of the XML is correct. */
		if( parser.document.getRootElement().getName()
				.equalsIgnoreCase( NAME_FEED_HOME ) )
		{
			/* Gets the container element for feed items and parses each item. */
			Element feedItemsElement = parser.getChildElementOrThrow(
					parser.getRootElement(), NAME_FEED_ITEMS );
			for ( Element feedItem : feedItemsElement.getChildren() )
			{
                parser.assertElementName( feedItem, NAME_FEED_ITEM );
				items.add( parser.parseFeedItem( feedItem ) );
			}
			return items;
		} else
		{
			throw new ParseException( "Root element of feed must be "
					+ NAME_FEED_HOME );
		}
	}

	public static List<Course> parseCourseList( InputStream xmlStream )
			throws ParseException
	{
		List<Course> courses = new ArrayList<Course>();

		XMLParser parser = new XMLParser( xmlStream );

		/* Ensures the root element of the XML is correct. */
		if( parser.document.getRootElement().getName()
				.equalsIgnoreCase( NAME_COURSE_CONTAINER ) )
		{
			/* Gets the container element for courses and parses each item. */
			for ( Element courseElement : parser.getRootElement().getChildren() )
			{
				courses.add( parser.parseCourse( courseElement ) );
			}
			return courses;
		} else
		{
			throw new ParseException( "Root element of course list must be "
					+ NAME_COURSE_CONTAINER );
		}
	}

	public static List<StreamItem> parseCourseFeed( InputStream xmlStream )
            throws ParseException
	{
        List<StreamItem> items = new ArrayList<StreamItem>();

        XMLParser parser = new XMLParser( xmlStream );

		/* Ensures the root element of the XML is correct. */
        if( parser.document.getRootElement().getName()
                .equalsIgnoreCase( NAME_FEED_COURSE ) )
        {
			/* Gets the container element for feed items and parses each item. */
            Element feedItemsElement = parser.getChildElementOrThrow(
                    parser.getRootElement(), NAME_FEED_ITEMS );
            for ( Element feedItem : feedItemsElement.getChildren() )
            {
                parser.assertElementName(feedItem, NAME_FEED_ITEM);
                items.add( parser.parseFeedItem( feedItem ) );
            }
            return items;
        } else
        {
            throw new ParseException( "Root element of feed must be "
                    + NAME_FEED_COURSE );
        }
	}

	public static List<BlogPost> parseBlogPosts( InputStream xmlStream )
			throws ParseException
	{
		List<BlogPost> posts = new ArrayList<BlogPost>();

		XMLParser parser = new XMLParser( xmlStream );

		/* Ensures the root element of the XML is correct. */
		parser.assertElementName( parser.getRootElement(), NAME_BLOG_POSTS );
		/*
		 * Gets the container element for blog posts and parses each document.
		 */
		for ( Element blogPostElement : parser.getRootElement().getChildren() )
		{
			posts.add( parser.parseBlogPost( blogPostElement ) );
		}
		return posts;
	}

	public static List<org.cascadelms.data.models.Document> parseDocuments(
			InputStream xmlStream ) throws ParseException
	{
		List<org.cascadelms.data.models.Document> documents = new ArrayList<org.cascadelms.data.models.Document>();

		XMLParser parser = new XMLParser( xmlStream );

		/* Ensures the root element of the XML is correct. */
		if( parser.document.getRootElement().getName()
				.equalsIgnoreCase( NAME_DOCUMENTS ) )
		{
			/*
			 * Gets the container element for documents and parses each
			 * document.
			 */
			for ( Element documentElement : parser.getRootElement()
					.getChildren() )
			{
				documents.add( parser.parseDocument( documentElement ) );
			}
			return documents;
		} else
		{
			throw new ParseException( "Root element of document list must be <"
					+ NAME_DOCUMENTS + ">." );
		}
	}

	public static List<Assignment> parseAssignments( InputStream xmlStream )
			throws ParseException
	{
		List<Assignment> assignments = new ArrayList<Assignment>();

		XMLParser parser = new XMLParser( xmlStream );

		/* Ensures the root element of the XML is correct. */
		parser.assertElementName( parser.getRootElement(), NAME_ASSIGNMENTS );
		/*
		 * Gets the container element for assignments and parses each
		 * assignment.
		 */
		for ( Element assignmentElement : parser.getRootElement().getChildren() )
		{
			assignments.add( parser.parseAssignment( assignmentElement ) );
		}
		return assignments;
	}

	public static List<Grade> parseGrades( InputStream xmlStream )
			throws ParseException
	{
		List<Grade> grades = new ArrayList<Grade>();

		XMLParser parser = new XMLParser( xmlStream );

		/* Ensures the root element of the XML is correct. */
		parser.assertElementName( parser.getRootElement(), NAME_GRADES );
		/*
		 * Gets the container element for grades and parses each grade.
		 */
		for ( Element gradeElement : parser.getRootElement().getChildren() )
		{
			grades.add( parser.parseGrade( gradeElement ) );
		}
		return grades;
	}

	public static StreamItem parseFeedPost( InputStream xmlStream )
            throws ParseException
	{
        StreamItem item = null;

        XMLParser parser = new XMLParser( xmlStream );

		/* Ensures the root element of the XML is correct. */
        if( parser.document.getRootElement().getName()
                .equalsIgnoreCase( NAME_FEED_POST ) )
        {
            return parser.parseFeedItem( parser.document.getRootElement() );
        } else
        {
            throw new ParseException( "Root element of post must be "
                    + NAME_FEED_POST );
        }
	}

	private Element getRootElement()
	{
		return this.document.getRootElement();
	}

	private Course parseCourse( Element courseElement ) throws ParseException
	{
		this.assertElementName( courseElement, NAME_COURSE );

		int id = Integer.parseInt( this.getChildTextOrThrow( courseElement,
				NAME_ID ) );
		String title = this.getChildTextOrThrow( courseElement,
				NAME_COURSE_TITLE );
		String description = this.getChildTextOrThrow( courseElement,
				NAME_COURSE_DESCRIPTION );
		Element termElement = this.getChildElementOrThrow( courseElement,
				NAME_COURSE_TERM );
		int termId = Integer.parseInt( this.getChildTextOrThrow( termElement,
				NAME_COURSE_TERM_ID ) );
		String semester = this.getChildTextOrThrow( termElement,
				NAME_COURSE_TERM_SEMESTER );
		boolean current = Boolean.parseBoolean( this.getChildTextOrThrow(
				termElement, NAME_COURSE_CURRENT_FLAG ) );
		return new Course.Builder( id, title, description, termId, semester,
				current ).build();
	}

	/**
	 * Parses a single User element.
	 * 
	 * @param userElement
	 * @return
	 * @throws ParseException
	 */
	private User parseUser( Element userElement ) throws ParseException
	{
		this.assertElementName( userElement, NAME_USER );

		int id = Integer.parseInt( this.getChildTextOrThrow( userElement,
				NAME_ID ) );
		String name = this.getChildTextOrThrow( userElement, NAME_USER_NAME );
		String gravatarURL = this.getChildTextOrThrow( userElement,
				NAME_USER_GRAVATAR );
		return new User( id, name, gravatarURL );
	}

	private User[] parseUsers( Element usersElement ) throws ParseException
	{
		this.assertElementName( usersElement, NAME_USERS );
		List<User> users = new ArrayList<User>();
		for ( Element userElement : usersElement.getChildren() )
		{
			users.add( this.parseUser( userElement ) );
		}
		return users.toArray( new User[users.size()] );
	}

	/**
	 * Parses a single Item element and returns a StreamItem.
	 * 
	 * @param feedItemElement
	 * @return
	 * @throws ParseException
	 */
	private StreamItem parseFeedItem( Element feedItemElement )
			throws ParseException
	{
		int id = Integer.parseInt( this.getChildTextOrThrow( feedItemElement,
				NAME_ID ) );
		Date postDate = XMLParser.dateFromDateString( this.getChildTextOrThrow(
				feedItemElement, NAME_FEED_ITEM_POST_DATE ) );
		User author = this.parseUser( this.getChildElementOrThrow(
				feedItemElement, NAME_FEED_ITEM_AUTHOR ) );
		int aPlusCount = Integer.parseInt( this.getChildTextOrThrow(
				feedItemElement, NAME_A_PLUS_COUNT ) );
		User[] aPlusUsers = this.parseUsers( this.getChildElementOrThrow( this
				.getChildElementOrThrow( feedItemElement, NAME_A_PLUS_USERS ),
				NAME_USERS ) );
		int commentCount = Integer.parseInt( this.getChildTextOrThrow(
				feedItemElement, NAME_FEED_ITEM_COMMENT_COUNT ) );
		String body = this.getChildTextOrThrow( feedItemElement,
				NAME_FEED_ITEM_BODY );
		String bodyHTML = this.getChildTextOrThrow( feedItemElement,
				NAME_FEED_ITEM_BODY_HTML );
        List<Comment> commentList = new ArrayList<Comment>();

        Element commentsElement = feedItemElement.getChild(NAME_FEED_ITEM_COMMENTS);

        if (commentsElement != null)
        {
            for (Element comment : commentsElement.getChildren())
            {
                commentList.add(parseComment(comment));
            }

            return new StreamItem.Builder(id, postDate, author, aPlusCount,
                    aPlusUsers, commentCount, body, bodyHTML)
                    .setComments(commentList.toArray(new Comment[commentList.size()])).build();
        }

        return new StreamItem.Builder(id, postDate, author, aPlusCount,
                aPlusUsers, commentCount, body, bodyHTML).build();
	}

	private Assignment parseAssignment( Element assignmentElement )
			throws ParseException
	{
		this.assertElementName( assignmentElement, NAME_ASSIGNMENT );

		int id = Integer.parseInt( this.getChildTextOrThrow( assignmentElement,
				NAME_ID ) );
		String title = this.getChildTextOrThrow( assignmentElement,
				NAME_ASSIGNMENT_TITLE );
		Assignment.Category category = Category.fromString( this
				.getChildTextOrThrow( assignmentElement,
						NAME_ASSIGNMENT_CATEGORY ) );
		Date openDate = XMLParser.dateFromAlternateDateString( this
				.getChildTextOrThrow( assignmentElement,
						NAME_ASSIGNMENT_OPEN_DATE ) );
		Date dueDate = XMLParser.dateFromAlternateDateString( this
				.getChildTextOrThrow( assignmentElement,
						NAME_ASSIGNMENT_DUE_DATE ) );
		Date closeDate = XMLParser.dateFromAlternateDateString( this
				.getChildTextOrThrow( assignmentElement,
						NAME_ASSIGNMENT_CLOSE_DATE ) );
		boolean gradesReleased = Boolean.parseBoolean( this
				.getChildTextOrThrow( assignmentElement,
						NAME_ASSIGNMENT_GRADE_RELEASED ) );
		double pointsPossible = 0;

        Element pointsPossibleElement = assignmentElement.getChild(NAME_ASSIGNMENT_POINTS_POSSIBLE);

        if (pointsPossibleElement != null && !pointsPossibleElement.getText().isEmpty())
        {
            pointsPossible = Double.parseDouble(pointsPossibleElement.getText());
        }

		return new Assignment.Builder( id, title, category, openDate, dueDate,
				closeDate, gradesReleased, pointsPossible ).build();
	}

	private BlogPost parseBlogPost( Element postElement ) throws ParseException
	{
		this.assertElementName( postElement, NAME_BLOG_POST );

		int id = Integer.parseInt( this.getChildTextOrThrow( postElement,
				NAME_ID ) );
		String title = this.getChildTextOrThrow( postElement,
				NAME_BLOG_POST_TITLE );
		boolean featured = Boolean.parseBoolean( this.getChildTextOrThrow(
				postElement, NAME_BLOG_POST_FEATURED_FLAG ) );
		String authorName = this.getChildTextOrThrow( postElement,
				NAME_BLOG_POST_AUTHOR_NAME );
		Date postDate = XMLParser
				.dateFromAlternateDateString( this.getChildTextOrThrow(
						postElement, NAME_BLOG_POST_POSTED_DATE ) );
		String body = this.getChildTextOrThrow( postElement,
				NAME_BLOG_POST_BODY );
		int aPlusCount = Integer.parseInt( this.getChildTextOrThrow(
				postElement, NAME_BLOG_POST_APLUS_COUNT ) );
		User[] aPlusUsers = this.parseUsers( this.getChildElementOrThrow( this
				.getChildElementOrThrow( postElement,
						NAME_BLOG_POST_APLUS_USERS ), NAME_USERS ) );
		int commentCount = Integer.parseInt( this.getChildTextOrThrow(
				postElement, NAME_BLOG_POST_COMMENT_COUNT ) );

		return new BlogPost.Builder( id, title, featured, authorName, postDate,
				body, aPlusCount, aPlusUsers, commentCount ).build();
	}

	private Grade parseGrade( Element gradeElement ) throws ParseException
	{
		this.assertElementName( gradeElement, NAME_GRADE );

		int assignmentId = Integer.parseInt( this.getChildTextOrThrow(
				gradeElement, NAME_GRADE_ASSIGNMENT_ID ) );
		String assignmentTitle = this.getChildTextOrThrow( gradeElement,
				NAME_GRADE_ASSIGNMENT_NAME );
		Assignment.Category category = Category.fromString( this
				.getChildTextOrThrow( gradeElement, NAME_GRADE_CATEGORY ) );
		Date postDate = XMLParser.dateFromAlternateDateString( this
				.getChildTextOrThrow( gradeElement, NAME_GRADE_POST_DATE ) );
		Grade.Type type = Grade.Type.fromString( this.getChildTextOrThrow(
				gradeElement, NAME_GRADE_TYPE ) );
		String pointsEarnedString = this.getChildTextOrThrow( gradeElement,
				NAME_GRADE_POINTS_EARNED );
		double pointsPossible = Double.parseDouble( this.getChildTextOrThrow(
				gradeElement, NAME_GRADE_POINTS_POSSIBLE ) );
		/* Points earned may not exist. */
		try
		{
			double pointsEarned = Double.parseDouble( pointsEarnedString );
			return new Grade( assignmentId, assignmentTitle, category,
					postDate, type, pointsEarned, pointsPossible );
		} catch( NumberFormatException e )
		{
			return new Grade( assignmentId, assignmentTitle, category,
					postDate, type, pointsPossible );
		}
	}

	private Comment parseComment( Element element )
            throws ParseException
	{
        assertElementName( element, NAME_COMMENT );

        String body = this.getChildTextOrThrow( element,
                NAME_COMMENT_BODY );
        User author = parseUser( this.getChildElementOrThrow(
                element, NAME_COMMENT_USER ) );
        Date createdAt = XMLParser.dateFromDateString( this.getChildTextOrThrow(
                element, NAME_COMMENT_CREATED_AT ) );

        return new Comment(body, author, createdAt);
	}

	private org.cascadelms.data.models.Document parseDocument(
			Element documentElement ) throws ParseException
	{
		this.assertElementName( documentElement, NAME_DOCUMENT );

		int id = Integer.parseInt( this.getChildTextOrThrow( documentElement,
				NAME_ID ) );
		String title = this.getChildTextOrThrow( documentElement,
				NAME_DOCUMENT_TITLE );
		try
		{
			/* If the XML contains a <folder> element it is a folder. */
			Element folderElement = this.getChildElementOrThrow(
					documentElement, NAME_DOCUMENT_FOLDER );
			if( folderElement.getTextNormalize().equalsIgnoreCase( "true" ) )
			{
				return org.cascadelms.data.models.Document.Builder
						.getFolderBuilder( id, title ).build();
			} else
			{
				throw new ParseException( "Document is not a folder." );
			}
		} catch( ParseException e )
		{
            String extension = "";
            int size = 0;

            Element extensionElement = documentElement.getChild(NAME_DOCUMENT_EXTENSION);

            if (extensionElement != null)
			    extension = extensionElement.getText();

            Element sizeElement = documentElement.getChild(NAME_DOCUMENT_SIZE);

            if (sizeElement != null && !sizeElement.getText().isEmpty())
                size = Integer.parseInt(sizeElement.getText());

			String documentURL = this.getChildTextOrThrow( documentElement,
					NAME_DOCUMENT_URL );
			return org.cascadelms.data.models.Document.Builder.getFileBuilder(
					id, title, extension, size, documentURL ).build();
		}
	}

	private StreamItem parseStreamItem()
	{
		throw new RuntimeException( "Unimplemented method" ); // TODO
	}

	/**
	 * Returns the first child of <code>element</code> named <code>cname</code>
	 * or throws a {@link JDOMException} if there is no child element with that
	 * name.
	 * 
	 * @param element
	 *            the parent element
	 * @param cname
	 *            the name of the child element to find
	 * @return the first child {@link Element}
	 * @throws JDOMException
	 *             if there is no child element with that name
	 */
	private Element getChildElementOrThrow( Element element, String cname )
			throws ParseException
	{
		Element child = element.getChild( cname );
		if( child == null )
		{
			throw new ParseException( "<" + element.getName()
					+ "> has no child element " + "<" + cname + ">." );
		} else
		{
			return child;
		}
	}

	private String getChildTextOrThrow( Element element, String cname )
			throws ParseException
	{
		return this.getChildElementOrThrow( element, cname ).getTextNormalize();
	}

	private void assertElementName( Element element, String expectedName )
			throws ParseException
	{
		if( !element.getName().equalsIgnoreCase( expectedName ) )
		{
			throw new ParseException( "Expected " + expectedName + " but got "
					+ element.getName() );
		}
	}

	public static Date dateFromDateString( String dateString )
			throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat(
				"EEE MMM d HH:mm:ss ZZZ yyyy" );
		Date parsedDate;
		try
		{
			parsedDate = df.parse( dateString );
		} catch( java.text.ParseException e )
		{
            // Sometimes the Cascade API spits out a different date format. Huh.
            df = new SimpleDateFormat(
                    "MMM d, yyyy" );

            try
            {
                parsedDate = df.parse( dateString );
            } catch (java.text.ParseException e1)
            {
                throw new ParseException(
                        "Could not parse a date from the date string." );
            }
        }

		return parsedDate;
	}

	/**
	 * Parses a Date from the alternate date format used by Cascade. Ex: Wed, 15
	 * Jan 2014 18:27:00 GMT
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date dateFromAlternateDateString( String dateString )
			throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat(
				"EEE, FF MMM yyyy HH:mm:ss zzz" );
		Date parsedDate;
		try
		{
			parsedDate = df.parse( dateString );
		} catch( java.text.ParseException e )
		{
			throw new ParseException(
					"Could not parse a date from the alternate style date string." );
		}
		return parsedDate;
	}

	/**
	 * An Exception thrown when {@link XMLParser} is unable to parse XML for any
	 * reason.
	 */
	public static class ParseException extends Exception
	{
		public ParseException( String message )
		{
			super( message );
		}

		public ParseException( Throwable cause )
		{
			super( cause );
		}

		private static final long serialVersionUID = 1L;
	}

	private static final Logger LOGGER = Logger.getLogger( XMLParser.class
			.getName() );
}
