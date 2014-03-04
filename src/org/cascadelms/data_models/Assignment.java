package org.cascadelms.data_models;

import java.util.Date;

/**
 * An immutable data model class representing an assignment from the Cascade LMS database.
 * <p>
 * New instances should be constructed using the {@link Assignment.Builder} class.
 *
 */
public class Assignment extends Item 
{
	private final AssignmentCategory category;
	
	private final String description;
	
	private final Date openDate;
	private final Date dueDate;
	private final Date closeDate;

	private final boolean upcomming;
	private final boolean current;
	private final boolean past;
	private final boolean closed;
	private final boolean gradesReleased;
	
	private final double pointsEarned;
	private final double pointsPossible;
	
	private Assignment( Assignment.Builder builder )
	{
		/* TODO Validate on the object fields after copying, because Builder is not thread safe. */
		super( builder.id, builder.title );
		this.category = builder.category;
		this.description = builder.description;
		this.openDate = builder.openDate;
		this.dueDate = builder.dueDate;
		this.closeDate = builder.closeDate;
		this.upcomming = builder.upcomming;
		this.current = builder.current;
		this.past = builder.past;
		this.closed = builder.closed;
		this.gradesReleased = builder.gradesReleased;
		this.pointsEarned = builder.pointsEarned;
		this.pointsPossible = builder.pointsPossible;
	}
	
	public AssignmentCategory getCategory() {
		return category;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public boolean isUpcomming() {
		return upcomming;
	}

	public boolean isCurrent() {
		return current;
	}

	public boolean isPast() {
		return past;
	}

	public boolean isClosed() {
		return closed;
	}

	public boolean isGradesReleased() {
		return gradesReleased;
	}

	public double getPointsEarned() {
		return pointsEarned;
	}

	public double getPointsPossible() {
		return pointsPossible;
	}

	public String getDescription() {
		return description;
	}

	public enum AssignmentCategory
	{
		ASSIGNMENT
	}
	
	public class Builder
	{
		private final long id;
		private final String title;
		
		private AssignmentCategory category;
		private String description;
		
		private Date openDate;
		private Date dueDate;
		private Date closeDate;

		private boolean upcomming;
		private boolean current;
		private boolean past;
		private boolean closed;
		private boolean gradesReleased;
		
		private double pointsEarned;
		private double pointsPossible;
		
		public Builder( long id, String title )
		{
			this.id = id;
			this.title = title;
		}
		
		public Assignment build()
		{
			return new Assignment( this );
		}
		
		public Builder setCategory( AssignmentCategory category )
		{
			this.category = category;
			return this;
		}
		
		public Builder setDescription( String description )
		{
			this.description = description;
			return this;
		}
		
		public Builder setOpenDate( Date openDate )
		{
			this.openDate = openDate;
			return this;
		}
		
		
		public Builder setDueDate( Date dueDate )
		{
			this.dueDate = dueDate;
			return this;
		}

		public Builder setCloseDate( Date closeDate )
		{
			this.closeDate = closeDate;
			return this;
		}
		
		public Builder setUpcoming( boolean upcomming )
		{
			this.upcomming = upcomming;
			return this;
		}

		public Builder setCurrent( boolean current )
		{
			this.current = current;
			return this;
		}
		
		public Builder setPast( boolean past )
		{
			this.past = past;
			return this;
		}
		
		public Builder setClosed( boolean closed )
		{
			this.closed = closed;
			return this;
		}
		
		public Builder setGradesReleased( boolean gradesReleased )
		{
			this.gradesReleased = gradesReleased;
			return this;
		}
		
		public Builder setPointsEarned( double pointsEarned )
		{
			this.pointsEarned = pointsEarned;
			return this;
		}
		
		public Builder setPointsPossible( double pointsPossible )
		{
			this.pointsPossible = pointsPossible;
			return this;
		}
	}

}
