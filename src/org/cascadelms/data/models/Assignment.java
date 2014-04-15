package org.cascadelms.data.models;

import java.util.Comparator;
import java.util.Date;

/**
 * An immutable data model class representing an assignment from the Cascade LMS
 * database.
 * <p>
 * New instances should be constructed using the {@link Assignment.Builder}
 * class.
 */
public class Assignment extends Item
{
	/* Required Attributes */
	private final Category category;
	private final Date openDate;
	private final Date dueDate;
	private final Date closeDate;
	private final boolean gradesReleased;
	private final double pointsPossible;

	/* Optional Attributes */
	private final String description;
	private final double pointsEarned;

	private Assignment( Assignment.Builder builder )
	{
		super( builder.id, builder.title );

		/* Sets fields according to the builder. */
		this.category = builder.category;
		this.description = builder.description;
		this.openDate = builder.openDate;
		this.dueDate = builder.dueDate;
		this.closeDate = builder.closeDate;
		this.gradesReleased = builder.gradesReleased;
		this.pointsEarned = builder.pointsEarned;
		this.pointsPossible = builder.pointsPossible;

		/* Validates the Data */
		// FIXME: Validation of Assignments turned off until fixed.
		// if( this.title == null )
		// {
		// throw new IllegalStateException( "Assignment title cannot be null."
		// );
		// }
		// if( this.openDate == null )
		// {
		// throw new IllegalStateException(
		// "Assignment open date cannot be null." );
		// }
		// if( this.dueDate == null )
		// {
		// throw new IllegalStateException(
		// "Assignment due date cannot be null." );
		// }
		// if( this.closeDate == null )
		// {
		// throw new IllegalStateException(
		// "Assignment close date cannot be null." );
		// }
		// if( this.pointsPossible <= 0 )
		// {
		// throw new IllegalStateException(
		// "Assignment points possible must be greater than zero." );
		// }
		// if( this.dueDate.before( this.openDate ) )
		// {
		// throw new IllegalStateException(
		// "Assignment cannot be due before it is available." );
		// }
		// if( this.closeDate.before( this.dueDate ) )
		// {
		// throw new IllegalStateException(
		// "Assignment cannot close before it is due." );
		// }
	}

	/**
	 * @return the {@link Category} associated with this assignment
	 */
	public Category getCategory()
	{
		return category;
	}

	/**
	 * @return the {@link Date} that this assignment becomes available
	 */
	public Date getOpenDate()
	{
		return openDate;
	}

	/**
	 * @return the {@link Date} that this assignment is due
	 */
	public Date getDueDate()
	{
		return dueDate;
	}

	/**
	 * @return the {@link Date} that submissions are no longer accepted for this
	 *         assignment
	 */
	public Date getCloseDate()
	{
		return closeDate;
	}

	/**
	 * Returns whether this assignment is unavailable as of yet.
	 * 
	 * @return <code>true</code> if the current date & time are before this
	 *         assignment's open date, <code>false</code> otherwise.
	 */
	public boolean isUpcoming()
	{
		return this.openDate.after( new Date() );
	}

	/**
	 * Returns whether this assignment is currently available and not past due.
	 * 
	 * @return <code>true</code> if the current date & time are before this
	 *         assignment's due date and after its open date.
	 */
	public boolean isCurrent()
	{
		Date now = new Date();
		return this.openDate.before( now ) && this.dueDate.after( now );
	}

	/**
	 * Returns whether this assignment is past due, but submissions are still
	 * being accepted.
	 * 
	 * @return <code>true</code> if the current date & time are before this
	 *         assignment's close date and after is due date.
	 */
	public boolean isPastDue()
	{
		Date now = new Date();
		return this.dueDate.before( now ) && this.closeDate.after( now );
	}

	/**
	 * Return whether this assignment is accepting submissions.
	 * 
	 * @return <code>true</code> if the current date & time are after this
	 *         assignment's close date.
	 */
	public boolean isClosed()
	{
		return this.closeDate.before( new Date() );
	}

	/**
	 * Return whether grades are available to view for this assignment.
	 * 
	 * @return <code>true</code> if grades are available
	 */
	public boolean gradesAreReleased()
	{
		return gradesReleased;
	}

	/**
	 * Returns the amount of points earned on this assignment.
	 * 
	 * @return the earned points as a <code>double</code>, or
	 *         <code>Double.NaN</code> if no grade has been entered
	 */
	public double getPointsEarned()
	{
		return pointsEarned;
	}

	/**
	 * Returns this maximum points possible for this assignment.
	 * 
	 * @return the maximum points as a <code>double</code>
	 */
	public double getPointsPossible()
	{
		return pointsPossible;
	}

	/**
	 * Gets the text description of this assignment.
	 * 
	 * @return the description <code>String</code>; possibly <code>null</code>
	 */
	public String getDescription()
	{
		return description;
	}

	@Override
	public String toString()
	{
		return this.title;
	}

	/**
	 * A builder class used to construct new {@link Assignment} instances. The
	 * <code>
	 * Builder</code> is constructed with the required attributes. Optional
	 * attributes can be set with the <code>Builder</code>'s set methods.
	 * Finally, create the <code>Assignment</code> by calling
	 * <code>Builder.build()</code>.
	 */
	public static class Builder
	{
		/* Required Attributes */
		private final int id;
		private final String title;
		private Category category;
		private Date openDate;
		private Date dueDate;
		private Date closeDate;
		private boolean gradesReleased;
		private double pointsPossible;

		/* Optional Attributes */
		private String description;
		private double pointsEarned = Double.NaN;

		/**
		 * Constructs a new <code>Builder</code> instance.
		 * 
		 * @param id
		 *            the id of the assignment in the Cascade database.
		 * @param title
		 * @param category
		 * @param openDate
		 * @param dueDate
		 * @param closeDate
		 * @param gradesReleased
		 * @param pointsPossible
		 */
		public Builder( int id, String title, Category category, Date openDate,
				Date dueDate, Date closeDate, boolean gradesReleased,
				double pointsPossible )
		{
			this.id = id;
			this.title = title;
			this.category = category;
			this.openDate = openDate;
			this.dueDate = dueDate;
			this.closeDate = closeDate;
			this.gradesReleased = gradesReleased;
			this.pointsPossible = pointsPossible;
		}

		/**
		 * Finishes building and returns the created {@link Assignment}.
		 * 
		 * @return
		 */
		public Assignment build()
		{
			return new Assignment( this );
		}

		public Builder setDescription( String description )
		{
			this.description = description;
			return this;
		}

		public Builder setPointsEarned( double pointsEarned )
		{
			this.pointsEarned = pointsEarned;
			return this;
		}
	}

	public static class AssignmentComparator implements Comparator<Assignment>
	{
		final int LHS_FIRST = -1;
		final int RHS_FIRST = 1;
		final int EQUAL = 0;

		@Override
		public int compare( Assignment lhs, Assignment rhs )
		{
			/*
			 * Used to sort Assignments by their status (i.e. upcoming,
			 * available, past due, closed) and then sort by date within each
			 * category.
			 */
			if( lhs.isUpcoming() )
			{
				if( rhs.isUpcoming() )
				{
					if( lhs.getOpenDate().before( rhs.getOpenDate() ) )
					{
						return LHS_FIRST;
					} else if( rhs.getOpenDate().before( lhs.getOpenDate() ) )
					{
						return RHS_FIRST;
					} else
					{
						return EQUAL;
					}
				} else if( rhs.isCurrent() || rhs.isPastDue() || rhs.isClosed() )
				{
					return LHS_FIRST;
				}
			} else if( lhs.isCurrent() )
			{
				if( rhs.isUpcoming() )
				{
					return RHS_FIRST;
				} else if( rhs.isCurrent() )
				{
					if( lhs.getDueDate().before( rhs.getDueDate() ) )
					{
						return LHS_FIRST;
					} else if( rhs.getDueDate().before( lhs.getDueDate() ) )
					{
						return RHS_FIRST;
					} else
					{
						return EQUAL;
					}
				} else if( rhs.isPastDue() || rhs.isClosed() )
				{
					return LHS_FIRST;
				}
			} else if( lhs.isPastDue() )
			{
				if( rhs.isUpcoming() || rhs.isCurrent() )
				{
					return RHS_FIRST;
				} else if( rhs.isPastDue() )
				{
					if( lhs.getCloseDate().before( rhs.getCloseDate() ) )
					{
						return LHS_FIRST;
					} else if( rhs.getCloseDate().before( lhs.getCloseDate() ) )
					{
						return RHS_FIRST;
					} else
					{
						return EQUAL;
					}
				} else if( rhs.isClosed() )
				{
					return LHS_FIRST;
				}
			} else if( lhs.isClosed() )
			{
				if( rhs.isUpcoming() || rhs.isCurrent() || rhs.isPastDue() )
				{
					return RHS_FIRST;
				} else if( rhs.isClosed() )
				{
					if( lhs.getCloseDate().before( rhs.getCloseDate() ) )
					{
						return LHS_FIRST;
					} else if( rhs.getCloseDate().before( lhs.getCloseDate() ) )
					{
						return RHS_FIRST;
					} else
					{
						return EQUAL;
					}
				}
			}
			throw new IllegalStateException(
					"Assignment Comparator did not return an ordering." );
		}
	}

	/**
	 * Constants for the Assignment attribute 'Category'.
	 * <p>
	 * So far, only Assignment has been encountered. Other categories may exist.
	 */
	public enum Category
	{
		ASSIGNMENT;

		public static Category fromString( String string )
		{
			for ( Category cat : Category.values() )
			{
				if( cat.name().equalsIgnoreCase( string ) )
				{
					return cat;
				}
			}
			throw new IllegalArgumentException( "No Category named " + string );
		}
	}
}
