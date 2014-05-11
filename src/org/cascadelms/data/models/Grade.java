package org.cascadelms.data.models;

import java.util.Date;

import org.cascadelms.data.models.Assignment.Category;

/**
 * An immutable model class representing a grade from Cascade LMS database.
 * <p/>
 * This class does not use a builder because all fields are mandatory.
 */
public class Grade
{
    private final int assignmentId;
    private final String assignmentTitle;
    private final Category assignmentCategory;
    private final Date postDate;
    private final Type gradeType;
    private final double pointsEarned;
    private final double pointsPossible;

    public Grade(int assignmentId, String assignmentTitle,
                 Category assignmentCategory, Date postDate, Type gradeType,
                 double pointsEarned, double pointsPossible)
    {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.assignmentCategory = assignmentCategory;
        this.postDate = postDate;
        this.gradeType = gradeType;
        this.pointsEarned = pointsEarned;
        this.pointsPossible = pointsPossible;
    }

    /**
     * A constructor with no pointsEarned parameter.
     *
     * @param assignmentId
     * @param assignmentTitle
     * @param assignmentCategory
     * @param postDate
     * @param gradeType
     * @param pointsPossible
     */
    public Grade(int assignmentId, String assignmentTitle,
                 Category assignmentCategory, Date postDate, Type gradeType,
                 double pointsPossible)
    {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.assignmentCategory = assignmentCategory;
        this.postDate = postDate;
        this.gradeType = gradeType;
        this.pointsEarned = Double.NaN;
        this.pointsPossible = pointsPossible;
    }

    public int getAssignmentId()
    {
        return assignmentId;
    }

    public String getAssignmentTitle()
    {
        return assignmentTitle;
    }

    public Category getAssignmentCategory()
    {
        return assignmentCategory;
    }

    public Date getPostDate()
    {
        return postDate;
    }

    public Type getGradeType()
    {
        return gradeType;
    }

    public double getPointsEarned()
    {
        return pointsEarned;
    }

    public double getPointsPossible()
    {
        return pointsPossible;
    }

    @Override
    public String toString()
    {
        String pointsEarnedString;
        if (Double.isNaN(this.pointsEarned))
        {
            pointsEarnedString = "-";
        } else
        {
            pointsEarnedString = Double.toString(this.pointsEarned);
        }
        return pointsEarnedString + "/" + this.pointsPossible + " : "
                + this.assignmentTitle;
    }

    /**
     * Possible values for a Grade's grade_type attribute. So far, only Score
     * has been observed.
     */
    public enum Type
    {
        SCORE;

        public static Type fromString(String string)
        {
            for (Type type : Type.values())
            {
                if (type.name().equalsIgnoreCase(string))
                {
                    return type;
                }
            }
            throw new IllegalArgumentException("No Type named " + string);
        }
    }
}
