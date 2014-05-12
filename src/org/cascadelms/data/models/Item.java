package org.cascadelms.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A base class for an immutable entity from the Cascade LMS database.
 */
public class Item implements Parcelable
{
    protected final int id;
    protected final String title;

    protected Item(int id, String title)
    {
        if (title == null)
        {
            throw new IllegalArgumentException("Item cannot have a null title.");
        }
        this.id = id;
        this.title = title;
    }

    public int getId()
    {
        return this.id;
    }

    public String getTitle()
    {
        return this.title;
    }

    @Override
    public String toString()
    {
        return this.title;
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
    }

    public static Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>()
    {
        @Override
        public Item createFromParcel(Parcel source)
        {
            return new Item(source.readInt(), source.readString());
        }

        @Override
        public Item[] newArray(int size)
        {
            return new Item[size];
        }
    };
}
