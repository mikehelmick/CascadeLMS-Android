package org.cascadelms.data.models;

public class School
{
    private final String schoolName;
    private final String cascadeUrl;
    private final String authUrl;

    public School(String schoolName, String cascadeUrl, String authUrl)
    {
        this.schoolName = schoolName;
        this.cascadeUrl = cascadeUrl;
        this.authUrl = authUrl;
    }

    public String getSchoolName()
    {
        return schoolName;
    }

    public String getCascadeUrl()
    {
        return cascadeUrl;
    }

    public String getAuthUrl()
    {
        return authUrl;
    }
}
