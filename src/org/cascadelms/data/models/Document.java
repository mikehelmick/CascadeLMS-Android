package org.cascadelms.data.models;

import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An immutable data model representing a document from the Cascade LMS
 * database.
 * <p/>
 * Note that this class uses the builder pattern to construct instances.
 */
public class Document extends Item implements Parcelable
{
    /* Required Attributes */
    private final boolean isFolder;

    /* Optional Attributes */
    private final String extension;
    private final long fileSize;
    private final String documentURL;
    private final boolean isExternal;

    /* Constants */
    private static final int ROOT_ID = -1;

    private Document(Builder builder)
    {
        /* Sets attributes as specified in the Builder. */
        super(builder.id, builder.title);
        this.isFolder = builder.isFolder;
        this.extension = builder.extension;
        this.fileSize = builder.fileSize;
        this.documentURL = builder.documentURL;
        isExternal = builder.isExternal;

		/* Check validity of the data. */
        if (this.isFolder)
        {
            if (this.extension != null)
            {
                throw new IllegalStateException(
                        "A Document folder cannot have an extension.");
            }
            if (this.fileSize != 0)
            {
                throw new IllegalStateException(
                        "A Document folder must have a size of 0.");
            }
            if (this.documentURL != null)
            {
                throw new IllegalStateException(
                        "A Document folder cannot have a document URL.");
            }
            if (this.isExternal)
            {
                throw new IllegalStateException(
                        "A Document folder must always be internal.");
            }
        }

    }

    /**
     * Returns this extension of the file associated with this document.
     *
     * @return the extension as a <code>String</code>
     */
    public String getFileExtension()
    {
        return extension;
    }

    /**
     * Returns this size of the file associated with this document.
     *
     * @return the file size in bytes as a <code>long</code>
     */
    public long getFileSize()
    {
        return fileSize;
    }

    /**
     * Returns the filename (with extension) of this document's file.
     *
     * @return
     */
    public String getFileName()
    {
        // TODO: There are several cases where this fails to the filename.
        return this.documentURL.substring(documentURL.lastIndexOf("/") + 1);
    }

    /**
     * Returns a String URL to this document's associated file.
     *
     * @return
     */
    public String getDocumentURL()
    {
        return documentURL;
    }

    /**
     * Returns whether this document represents a file or a directory.
     *
     * @return <code>true</code> if this documents is a directory,
     * <code>false</code> if it is a file.
     */
    public boolean isFolder()
    {
        return this.isFolder;
    }

    /**
     * Returns whether this document's URL refers to a third-party site.
     *
     * @return <code>true</code> if this document's URL is external to the Cascade installation.
     * <code>false</code> if the document is hosted on Cascade.
     */
    public boolean isExternal()
    {
        return this.isExternal;
    }

    public static Document rootDocument()
    {
        return new Document(Builder.getFolderBuilder(ROOT_ID, "Root"));
    }

    /**
     * A Builder class used to construct instances of {@link Document}.
     * <p/>
     * Get a Builder using one of two static methods:<br>
     * 1) <code>Builder.getFileBuilder()</code> to build an instance
     * representing a file.<br>
     * 2) <code>Builder.getFolderBuilder()</code> to build an instance
     * representing a directory.
     * <p/>
     * Finally, construct the <code>Document</code> by calling
     * <code>Builder.build()</code>
     */
    public static class Builder
    {
        /* Required Attributes */
        private int id;
        private String title;
        private boolean isFolder;

        /* Optional Attributes */
        private String extension;
        private long fileSize;
        private String documentURL;
        private boolean isExternal;

        /**
         * Construct a <code>Builder</code> instance for building a
         * {@link Document} that represents a file.
         *
         * @param id
         * @param title
         * @param extension
         * @param fileSize
         * @param documentURL
         * @param isExternal
         * @return
         */
        public static Builder getFileBuilder(int id, String title,
                                             String extension, long fileSize, String documentURL, boolean isExternal)
        {
            return new Builder(id, title, false, extension, fileSize,
                    documentURL, isExternal);
        }

        /**
         * Construct a <code>Builder</code> instance for building a
         * {@link Document} that represents a directory.
         *
         * @param id
         * @param title
         * @return
         */
        public static Builder getFolderBuilder(int id, String title)
        {
            return new Builder(id, title, true, null, 0, null, false);
        }

        private Builder(int id, String title, boolean isFolder,
                        String extension, long fileSize, String documentURL, boolean isExternal)
        {
            this.id = id;
            this.title = title;
            this.isFolder = isFolder;
            this.extension = extension;
            this.fileSize = fileSize;
            this.documentURL = documentURL;
            this.isExternal = isExternal;
        }

        public Document build()
        {
            return new Document(this);
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
		/*
		 * Note that the boolean value is written as a byte and that the URL is
		 * written as a string.
		 */
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (isFolder ? 1 : 0));
        dest.writeString(extension);
        dest.writeLong(fileSize);
        dest.writeString(documentURL.toString());
        dest.writeByte((byte) (isExternal ? 1 : 0));
    }

    public static Parcelable.Creator<Document> CREATOR = new Parcelable.Creator<Document>()
    {
        @Override
        public Document createFromParcel(Parcel source)
        {
            int id = source.readInt();
            String title = source.readString();
            boolean isFolder = (source.readByte() != 0);
            String extension = source.readString();
            long fileSize = source.readLong();
            String urlString = source.readString();
            boolean isExternal = (source.readByte() != 0);

			/* Builds the Document */
            if (isFolder)
            {
                return Builder.getFolderBuilder(id, title).build();
            } else
            {
                return Builder.getFileBuilder(id, title, extension, fileSize,
                        urlString, isExternal).build();
            }
        }

        @Override
        public Document[] newArray(int size)
        {
            return new Document[size];
        }
    };

    public static class DocumentComparator implements Comparator<Document>
    {
        @Override
        public int compare(Document first, Document second)
        {
			/*
			 * Places folders ahead of files. Retains previous ordering
			 * otherwise.
			 */
            if (first.isFolder)
            {
                if (second.isFolder)
                {
                    return 0;
                } else
                {
                    return -1;
                }
            } else
            {
                if (second.isFolder)
                {
                    return 1;
                } else
                {
                    return 0;
                }
            }
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((documentURL == null) ? 0 : documentURL.hashCode());
        result = prime * result
                + ((extension == null) ? 0 : extension.hashCode());
        result = prime * result + (int) (fileSize ^ (fileSize >>> 32));
        result = prime * result + (isFolder ? 1231 : 1237);
        result = prime * result + (isExternal ? 1249 : 1259);
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
        Document other = (Document) obj;
        if (documentURL == null)
        {
            if (other.documentURL != null)
                return false;
        } else if (!documentURL.equals(other.documentURL))
            return false;
        if (extension == null)
        {
            if (other.extension != null)
                return false;
        } else if (!extension.equals(other.extension))
            return false;
        if (fileSize != other.fileSize)
            return false;
        if (isFolder != other.isFolder)
            return false;
        if (isExternal != other.isExternal)
            return false;
        return true;
    }
}
