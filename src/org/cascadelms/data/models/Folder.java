package org.cascadelms.data.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Folder
{
    private final Document directory;
    private final Folder parent;
    private final HashMap<Document, Folder> children;

    public static Folder createRootFolder(List<Document> contents)
    {
        return new Folder(Document.rootDocument(), contents, null);
    }

    public Folder(Document directory, List<Document> contents, Folder parent)
    {
        this.directory = directory;
        this.parent = parent;
        this.children = new HashMap<Document, Folder>();
        for (Document doc : contents)
        {
            this.children.put(doc, null);
        }
    }

    public List<Document> getContents()
    {
        return new ArrayList<Document>(this.children.keySet());
    }

    public Folder getParentFolder()
    {
        return this.parent;
    }

    public Folder getSubdir(Document subdir)
    {
        if (subdir.isFolder())
        {
            return this.children.get(subdir);
        } else
        {
            throw new IllegalArgumentException(subdir.getTitle()
                    + " is not a folder.  Cannot get its contents.");
        }
    }
}
