package org.cascadelms.socialstream;

public class SocialStreamComment
{
    private int mAuthorId = -1;
    private String mAuthorName;
    private String mDate;
    private String mContent;
    
    public int getAuthorId()
    {
        return mAuthorId;
    }
    public void setAuthorId(int authorId)
    {
        mAuthorId = authorId;
    }
    public String getAuthorName()
    {
        return mAuthorName;
    }
    public void setAuthorName(String authorName)
    {
        mAuthorName = authorName;
    }
    public String getDate()
    {
        return mDate;
    }
    public void setDate(String date)
    {
        mDate = date;
    }
    public String getContent()
    {
        return mContent;
    }
    public void setContent(String content)
    {
        mContent = content;
    }
}
