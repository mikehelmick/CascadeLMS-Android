package org.cascadelms.socialstream;

public class SocialStreamPost
{
    private int mPostId = -1;
    private int mAuthorId = -1;
    private String mAuthorName;
    private String mDate;
    private String mQuickUrl;
    private String mContent;
    private int mScore = 0;
    private int mCommentCount = 0;
    
    public int getPostId()
    {
        return mPostId;
    }
    public void setPostId(int postId)
    {
        mPostId = postId;
    }
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
    public String getPostDate()
    {
        return mDate;
    }
    public void setDate(String date)
    {
        mDate = date;
    }
    public String getQuickUrl()
    {
        return mQuickUrl;
    }
    public void setQuickUrl(String quickUrl)
    {
        mQuickUrl = quickUrl;
    }
    public String getContent()
    {
        return mContent;
    }
    public void setContent(String content)
    {
        mContent = content;
    }
    public int getScore()
    {
        return mScore;
    }
    public void setScore(int score)
    {
        mScore = score;
    }
    public int getCommentCount()
    {
        return mCommentCount;
    }
    public void setCommentCount(int commentCount)
    {
        mCommentCount = commentCount;
    }
}
