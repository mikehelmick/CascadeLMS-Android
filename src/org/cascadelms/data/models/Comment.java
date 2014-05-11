package org.cascadelms.data.models;

import java.util.Date;

public class Comment
{
    private final String body;
    private final User author;
    private final Date createdAt;

    public Comment(String body, User author, Date createdAt)
    {
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
    }

    public String getBody()
    {
        return body;
    }

    public User getAuthor()
    {
        return author;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }
}
