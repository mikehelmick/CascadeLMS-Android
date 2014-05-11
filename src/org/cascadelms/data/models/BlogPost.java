package org.cascadelms.data.models;

import java.util.Arrays;
import java.util.Date;

/**
 * An immutable data model class representing a blog post from the Cascade LMS
 * database.
 * <p/>
 * New instances should be created using the {@link BlogPost.Builder} class.
 */
public class BlogPost extends Item
{
    /* Required Attributes */
    private final boolean featured;
    private final String author;
    private final Date postedDate;
    private final int commentCount;
    private final String body;
    private final int aPlusCount;
    private final User[] aPlusUsers;

    /* Optional Attributes */
    private final Comment[] comments;

    private BlogPost(BlogPost.Builder builder)
    {
        super(builder.id, builder.title);
        this.featured = builder.featured;
        this.author = builder.author;
        this.postedDate = builder.postedDate;
        this.body = builder.body;
        this.aPlusCount = builder.aPlusCount;
        this.aPlusUsers = builder.aPlusUsers;
        this.commentCount = builder.commentCount;
        this.comments = builder.comments;

        if (this.comments != null && this.commentCount != this.comments.length)
        {
            throw new IllegalStateException(
                    "Comment count does not match the number of comments.");
        }
    }

    /**
     * Returns whether this blog post is featured or not.
     *
     * @return <code>true</code> if this post is featured.
     */
    public boolean isFeatured()
    {
        return featured;
    }

    /**
     * Returns the name of the user who authored this post.
     *
     * @return a <code>String</code> of the author's name.
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * Gets the text body of the blog post.
     *
     * @return the post body as a <code>String</code>
     */
    public String getBody()
    {
        return body;
    }

    /**
     * Gets the <code>Comment</code> objects associated with this blog post.
     *
     * @return an array of <code>Comment</code> objects.
     */
    public Comment[] getComments()
    {
        return comments;
    }

    /**
     * Gets the number of comments or <code>Comment</code> objects associated
     * with this blog post.
     *
     * @return the count as an <code>int</code>
     */
    public int getCommentCount()
    {
        return commentCount;
    }

    /**
     * Gets the {@link Date} that this blog post was published.
     *
     * @return the publish <code>Date</code>
     */
    public Date getPostedDate()
    {
        return postedDate;
    }

    /**
     * Gets the A+ count (score) of this post.
     *
     * @return the count as an <code>int</code>
     */
    public int getaPlusCount()
    {
        return aPlusCount;
    }

    public User[] getaPlusUsers()
    {
        return aPlusUsers;
    }

    public static class Builder
    {
        /* Required Attributes */
        private int id;
        private String title;
        private boolean featured;
        private String author;
        private Date postedDate;
        private String body;
        private int aPlusCount;
        private User[] aPlusUsers;
        private int commentCount = 0;

        /* Optional Attributes */
        private Comment[] comments;

        public Builder(int id, String title, boolean featured, String author,
                       Date postedDate, String body, int aPlusScore,
                       User[] aPlusUsers, int commentCount)
        {
            this.id = id;
            this.title = title;
            this.featured = featured;
            this.author = author;
            this.postedDate = postedDate;
            this.body = body;
            this.aPlusCount = aPlusScore;
            this.aPlusUsers = aPlusUsers;
            this.commentCount = commentCount;
        }

        public Builder setComments(Comment[] comments)
        {
            this.comments = comments;
            return this;
        }

        public BlogPost build()
        {
            return new BlogPost(this);
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + aPlusCount;
        result = prime * result + Arrays.hashCode(aPlusUsers);
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + commentCount;
        result = prime * result + Arrays.hashCode(comments);
        result = prime * result + (featured ? 1231 : 1237);
        result = prime * result
                + ((postedDate == null) ? 0 : postedDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlogPost other = (BlogPost) obj;
        if (aPlusCount != other.aPlusCount)
            return false;
        if (!Arrays.equals(aPlusUsers, other.aPlusUsers))
            return false;
        if (author == null)
        {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (commentCount != other.commentCount)
            return false;
        if (!Arrays.equals(comments, other.comments))
            return false;
        if (featured != other.featured)
            return false;
        if (postedDate == null)
        {
            if (other.postedDate != null)
                return false;
        } else if (!postedDate.equals(other.postedDate))
            return false;
        if (body == null)
        {
            if (other.body != null)
                return false;
        } else if (!body.equals(other.body))
            return false;
        return true;
    }
}
