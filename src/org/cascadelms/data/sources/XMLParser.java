package org.cascadelms.data.sources;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cascadelms.data.models.BlogPost;
import org.cascadelms.data.models.BlogPost.Builder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser
{
	private static SAXBuilder builder;

	{
		XMLParser.builder = new SAXBuilder();
	}

	private Document document;

	/* BlogPost Constants */
	private static final String NAME_BLOG_POST = "blog_post";
	private static final String NAME_BLOG_POST_ID = "id";
	private static final String NAME_BLOG_POST_TITLE = "title";
	private static final String NAME_BLOG_POST_FEATURED = "featured";
	private static final String NAME_BLOG_POST_AUTHOR_NAME = "author";
	private static final String NAME_BLOG_POST_POSTED_DATE = "posted_at";
	private static final String NAME_BLOG_POST_COMMENT_COUNT = "comments";
	private static final String NAME_BLOG_POST_BODY = "body";

	public XMLParser( InputStream source ) throws IOException, JDOMException
	{
		this.document = XMLParser.builder.build( source );
	}

	private BlogPost parseBlogPost( Element element ) throws JDOMException
	{
		int id;
		String title;
		boolean featured;
		String author;
		Date postedDate;
		int commentCount;

		/* Optional */
		String body;

		if( element.getName().equals( NAME_BLOG_POST ) )
		{
			/* Parses the BlogPost fields */
			id = Integer.parseInt( this.getChildElementOrThrow( element,
					NAME_BLOG_POST_ID ).getTextNormalize() );
			title = this.getChildElementOrThrow( element, NAME_BLOG_POST_TITLE )
					.getTextNormalize();
			featured = Boolean.parseBoolean( this.getChildElementOrThrow(
					element, NAME_BLOG_POST_FEATURED ).getTextNormalize() );
			author = this.getChildElementOrThrow( element,
					NAME_BLOG_POST_AUTHOR_NAME ).getTextNormalize();
			DateFormat dateFormat = new SimpleDateFormat( "" );
			try
			{
				postedDate = dateFormat.parse( this.getChildElementOrThrow(
						element, NAME_BLOG_POST_POSTED_DATE )
						.getTextNormalize() );
			} catch( ParseException e )
			{
				throw new JDOMException(
						"Could not parse a date from the date string "
								+ this.getChildElementOrThrow( element,
										NAME_BLOG_POST_POSTED_DATE )
										.getTextNormalize() );
			}
			commentCount = Integer.parseInt( this.getChildElementOrThrow(
					element, NAME_BLOG_POST_COMMENT_COUNT ).getTextNormalize() );

			BlogPost.Builder builder = new Builder( id, title, featured,
					author, postedDate, null, commentCount );

			// /* Parses the body field, which may not be present. */
			// try
			// {
			// builder.setBody( this.getChildElementOrThrow( element,
			// NAME_BLOG_POST_BODY ).getTextNormalize() );
			// } catch( JDOMException e )
			// {
			// /* Does not set the body, if not present. */
			// }

			// /* */
			// try
			// {
			// builder.setComments( this.parseComments(
			// this.getChildElementOrThrow( element, NAME_BLOG_POST_COMMENTS ) )
			// );
			// }
			return new BlogPost( builder );
			// return builder.build();
		} else
		{
			throw new IllegalArgumentException( "Root element must be <"
					+ NAME_BLOG_POST + ">" );
		}
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
			throws JDOMException
	{
		Element child = element.getChild( cname );
		if( child == null )
		{
			throw new JDOMException( element.getName()
					+ " has no child element " + "\"" + cname + "\"." );
		} else
		{
			return child;
		}
	}
}
