package org.cascadelms.data.sources;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.data.models.Assignment;
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
	private static final String NAME_COMMENT_COUNT = "comment_count";

	/* Course Constants */
	private static final String NAME_COURSE_CONTAINER = "courses";
	private static final String NAME_COURSE = "course";
	private static final String NAME_COURSE_TITLE = "title";
	private static final String NAME_COURSE_DESCRIPTION = "description";
	private static final String NAME_COURSE_TERM = "term";
	private static final String NAME_COURSE_TERM_ID = "id";
	private static final String NAME_COURSE_TERM_SEMESTER = "semester";
	private static final String NAME_COURSE_CURRENT_FLAG = "current";

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
	private static final String NAME_FEED_ITEMS = "feed_items";
	private static final String NAME_FEED_ITEM = "item";
	private static final String NAME_FEED_ITEM_POST_DATE = "date";
	private static final String NAME_FEED_ITEM_AUTHOR = "user";
	private static final String NAME_A_PLUS_COUNT = "aplus_count";
	private static final String NAME_A_PLUS_USERS = "aplus_users";
	private static final String NAME_FEED_ITEM_BODY = "body";
	private static final String NAME_FEED_ITEM_BODY_HTML = "body_html";

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
	{
		throw new RuntimeException( "Method Stub" ); // TODO
	}

	public static List<BlogPost> parseBlogPosts( InputStream xmlStream )
	{
		throw new RuntimeException( "Method Stub" ); // TODO
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
	{
		throw new RuntimeException( "Method Stub" ); // TODO
	}

	public static List<Grade> parseGrades( InputStream xmlStream )
	{
		throw new RuntimeException( "Method Stub" ); // TODO
	}

	public static StreamItem parseFeedPost( InputStream xmlStream )
	{
		throw new RuntimeException( "Method Stub" ); // TODO
	}

	private Element getRootElement()
	{
		return this.document.getRootElement();
	}

	private Course parseCourse( Element courseElement ) throws ParseException
	{
		if( courseElement.getName().equalsIgnoreCase( NAME_COURSE ) )
		{
			int id = Integer.parseInt( this.getChildTextOrThrow( courseElement,
					NAME_ID ) );
			String title = this.getChildTextOrThrow( courseElement,
					NAME_COURSE_TITLE );
			String description = this.getChildTextOrThrow( courseElement,
					NAME_COURSE_DESCRIPTION );
			Element termElement = this.getChildElementOrThrow( courseElement,
					NAME_COURSE_TERM );
			int termId = Integer.parseInt( this.getChildTextOrThrow(
					termElement, NAME_COURSE_TERM_ID ) );
			String semester = this.getChildTextOrThrow( termElement,
					NAME_COURSE_TERM_SEMESTER );
			boolean current = Boolean.parseBoolean( this.getChildTextOrThrow(
					termElement, NAME_COURSE_CURRENT_FLAG ) );
			return new Course.Builder( id, title, description, termId,
					semester, current ).build();
		} else
		{
			throw new ParseException( "Expected top tag of course to be <"
					+ NAME_COURSE + "> instead got <" + courseElement.getName()
					+ ">" );
		}
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
		if( userElement.getName().equalsIgnoreCase( NAME_USER ) )
		{
			int id = Integer.parseInt( this.getChildTextOrThrow( userElement,
					NAME_ID ) );
			String name = this
					.getChildTextOrThrow( userElement, NAME_USER_NAME );
			String gravatarURL = this.getChildTextOrThrow( userElement,
					NAME_USER_GRAVATAR );
			return new User( id, name, gravatarURL );
		} else
		{
			throw new ParseException( "Expected top tag of user to be <"
					+ NAME_USER + "> instead got <" + userElement.getName()
					+ ">" );
		}
	}

	private User[] parseUsers( Element usersElement ) throws ParseException
	{
		if( usersElement.getName().equalsIgnoreCase( NAME_USERS ) )
		{
			List<User> users = new ArrayList<User>();
			for ( Element userElement : usersElement.getChildren() )
			{
				users.add( this.parseUser( userElement ) );
			}
			return users.toArray( new User[users.size()] );
		} else
		{
			throw new ParseException( "Expected top tag of users group to be "
					+ NAME_USERS );
		}
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
		if( feedItemElement.getName().equals( NAME_FEED_ITEM ) )
		{
			int id = Integer.parseInt( this.getChildTextOrThrow(
					feedItemElement, NAME_ID ) );
			Date postDate = XMLParser.dateFromDateString( this
					.getChildTextOrThrow( feedItemElement,
							NAME_FEED_ITEM_POST_DATE ) );
			User author = this.parseUser( this.getChildElementOrThrow(
					feedItemElement, NAME_FEED_ITEM_AUTHOR ) );
			int aPlusCount = Integer.parseInt( this.getChildTextOrThrow(
					feedItemElement, NAME_A_PLUS_COUNT ) );
			User[] aPlusUsers = this.parseUsers( this.getChildElementOrThrow(
					this.getChildElementOrThrow( feedItemElement,
							NAME_A_PLUS_USERS ), NAME_USERS ) );
			int commentCount = Integer.parseInt( this.getChildTextOrThrow(
					feedItemElement, NAME_COMMENT_COUNT ) );
			String body = this.getChildTextOrThrow( feedItemElement,
					NAME_FEED_ITEM_BODY );
			String bodyHTML = this.getChildTextOrThrow( feedItemElement,
					NAME_FEED_ITEM_BODY_HTML );
			return new StreamItem.Builder( id, postDate, author, aPlusCount,
					aPlusUsers, commentCount, body, bodyHTML ).build();
		} else
		{
			throw new ParseException( "Root element of a feed item should be "
					+ NAME_FEED_ITEM );
		}
	}

	private Assignment parseAssignment( Element element )
	{
		throw new RuntimeException( "Unimplemented method." ); // TODO
	}

	private BlogPost parseBlogPost( Element element ) throws JDOMException
	{
		throw new RuntimeException( "Unimplemented method" ); // TODO
	}

	private Comment parseComment( Element element )
	{
		throw new RuntimeException( "Unimplemented method." ); // TODO
	}

	private org.cascadelms.data.models.Document parseDocument(
			Element documentElement ) throws ParseException
	{
		if( documentElement.getName().equals( NAME_DOCUMENT ) )
		{
			int id = Integer.parseInt( this.getChildTextOrThrow(
					documentElement, NAME_ID ) );
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
				String extension = this.getChildTextOrThrow( documentElement,
						NAME_DOCUMENT_EXTENSION );
				int size = Integer.parseInt( this.getChildTextOrThrow(
						documentElement, NAME_DOCUMENT_SIZE ) );
				String documentURL = this.getChildTextOrThrow( documentElement,
						NAME_DOCUMENT_URL );
				return org.cascadelms.data.models.Document.Builder
						.getFileBuilder( id, title, extension, size,
								documentURL ).build();
			}
		} else
		{
			throw new ParseException( "Root element of a document should be "
					+ NAME_DOCUMENT + ">." );
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

	public static Date dateFromDateString( String dateString )
			throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat(
				"EEE MMM FF HH:mm:ss ZZZ yyyy" );
		Date parsedDate;
		try
		{
			parsedDate = df.parse( dateString );
		} catch( java.text.ParseException e )
		{
			throw new ParseException(
					"Could not parse a date from the date string." );
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
