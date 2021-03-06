package org.cascadelms.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An immutable data model class representing a single course from the
 * CascadeLMS database.
 * <p/>
 * New instances should be constructed using the {@link Course.Builder} class.
 */
public class Course implements Parcelable
{
    /* Required Attributes */
    private final int id;
    private final String title;
    private final String description;

    private final int termId;
    private final String semester;
    private final boolean current;

    private Course(Course.Builder builder)
    {
        /* Sets the fields according to the builder. */
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.termId = builder.termId;
        this.semester = builder.semester;
        this.current = builder.current;

		/* Validates data */
        if (this.title == null)
        {
            throw new IllegalStateException("Course title cannot be null.");
        }
    }

    /**
     * @return the id of this <code>Course</code>
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the title of this <code>Course</code>
     */
    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public int getTermId()
    {
        return termId;
    }

    public String getSemester()
    {
        return semester;
    }

    public boolean isCurrent()
    {
        return current;
    }

    @Override
    public String toString()
    {
        return this.title;
    }

    /**
     * A builder class used to construct new {@link Course} instances. The
     * <code>Builder</code> is constructed with the required attributes.
     * Optional attributes can be set with the <code>Builder</code>'s set
     * methods. Finally, create the <code>Course</code> by calling
     * <code>Builder.build()</code>.
     */
    public static class Builder
    {
        private int id;
        private String title;
        private String description;
        private int termId;
        private String semester;
        private boolean current;

        public Builder(int id, String title, String description, int termId,
                       String semester, boolean current)
        {
            this.id = id;
            this.title = title;
            this.description = description;
            this.termId = termId;
            this.semester = semester;
            this.current = current;
        }

        public Course build()
        {
            return new Course(this);
        }
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(termId);
        dest.writeString(semester);
        dest.writeByte((byte) (current ? 1 : 0));
    }

    public static Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>()
    {
        @Override
        public Course createFromParcel(Parcel source)
        {
            return new Course.Builder(source.readInt(), source.readString(),
                    source.readString(), source.readInt(), source.readString(),
                    (source.readByte() != 0)).build();
        }

        @Override
        public Course[] newArray(int size)
        {
            return new Course[size];
        }
    };

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (current ? 1231 : 1237);
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + id;
        result = prime * result
                + ((semester == null) ? 0 : semester.hashCode());
        result = prime * result + termId;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        Course other = (Course) obj;
        if (current != other.current)
            return false;
        if (description == null)
        {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id != other.id)
            return false;
        if (semester == null)
        {
            if (other.semester != null)
                return false;
        } else if (!semester.equals(other.semester))
            return false;
        if (termId != other.termId)
            return false;
        if (title == null)
        {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
}
