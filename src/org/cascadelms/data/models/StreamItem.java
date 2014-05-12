package org.cascadelms.data.models;

import java.util.Arrays;
import java.util.Date;

/**
 * An immutable data model class representing a single stream item.
 */
public class StreamItem
{
    /* Required Attributes */
    private final int id;
    private final Date postDate;
    private final User author;

    private final int aPlusCount;
    private final User[] aPlusUsers;

    private final int commentCount;

    private final String body;
    private final String bodyHTML;

    /* Optional Attributes */
    private final Comment[] comments;

    /**
     * Private constructor to be used by the Builder class.
     *
     * @param builder
     */
    private StreamItem(StreamItem.Builder builder)
    {
        this.id = builder.id;
        this.postDate = builder.postDate;
        this.author = builder.author;
        this.aPlusCount = builder.aPlusCount;
        this.aPlusUsers = builder.aPlusUsers;
        this.commentCount = builder.commentCount;
        this.body = builder.body;
        this.bodyHTML = builder.bodyHTML;

        this.comments = builder.comments;

        if (this.comments != null)
        {
            assert comments.length == commentCount;
        }
    }

    public static class Builder
    {
        /* Required Attributes */
        private int id;
        private Date postDate;
        private User author;

        private int aPlusCount;
        private User[] aPlusUsers;

        private int commentCount = 0;

        private String body;
        private String bodyHTML;

        /* Optional Attributes */
        private Comment[] comments;

        /**
         * Constructs a Builder with all the required attributes of a
         * StreamItem.
         *
         * @param id
         * @param postDate
         * @param author
         * @param aPlusCount
         * @param aPlusUsers
         * @param commentCount
         * @param body
         * @param bodyHTML
         */
        public Builder(int id, Date postDate, User author, int aPlusCount,
                       User[] aPlusUsers, int commentCount, String body,
                       String bodyHTML)
        {
            this.id = id;
            this.postDate = postDate;
            this.author = author;
            this.aPlusCount = aPlusCount;
            this.aPlusUsers = aPlusUsers;
            this.commentCount = commentCount;
            this.body = body;
            this.bodyHTML = bodyHTML;
        }

        public Builder setComments(Comment[] comments)
        {
            this.comments = comments;
            return this;
        }

        public StreamItem build()
        {
            return new StreamItem(this);
        }
    }

    public int getId()
    {
        return id;
    }

    public Date getPostDate()
    {
        return postDate;
    }

    public User getAuthor()
    {
        return author;
    }

    public int getAPlusCount()
    {
        return aPlusCount;
    }

    public User[] getAPlusUsers()
    {
        return aPlusUsers;
    }

    public int getCommentCount()
    {
        return commentCount;
    }

    public String getBody()
    {
        return body;
    }

    public String getBodyHTML()
    {
        return bodyHTML;
    }

    public Comment[] getComments()
    {
        return comments;
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
        result = prime * result
                + ((bodyHTML == null) ? 0 : bodyHTML.hashCode());
        result = prime * result + commentCount;
        result = prime * result + Arrays.hashCode(comments);
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result
                + ((postDate == null) ? 0 : postDate.hashCode());
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
        StreamItem other = (StreamItem) obj;
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
        if (body == null)
        {
            if (other.body != null)
                return false;
        } else if (!body.equals(other.body))
            return false;
        if (bodyHTML == null)
        {
            if (other.bodyHTML != null)
                return false;
        } else if (!bodyHTML.equals(other.bodyHTML))
            return false;
        if (commentCount != other.commentCount)
            return false;
        if (!Arrays.equals(comments, other.comments))
            return false;
        if (id != other.id)
            return false;
        if (postDate == null)
        {
            if (other.postDate != null)
                return false;
        } else if (!postDate.equals(other.postDate))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "StreamItem [id=" + id + ", postDate=" + postDate + ", author="
                + author + ", aPlusCount=" + aPlusCount + ", aPlusUsers="
                + Arrays.toString(aPlusUsers) + ", commentCount="
                + commentCount + ", body=" + body + ", bodyHTML=" + bodyHTML
                + ", comments=" + Arrays.toString(comments) + "]";
    }
}