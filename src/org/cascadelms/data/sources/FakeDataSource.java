package org.cascadelms.data.sources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.cascadelms.SelectSchoolActivity.SchoolsDataSource;
import org.cascadelms.StreamActivity.CourseDataSource;
import org.cascadelms.data.models.Assignment;
import org.cascadelms.data.models.BlogPost;
import org.cascadelms.data.models.Comment;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.Document;
import org.cascadelms.data.models.Grade;
import org.cascadelms.data.models.School;
import org.cascadelms.data.models.StreamItem;
import org.cascadelms.data.models.User;
import org.cascadelms.fragments.AssignmentsFragment.AssignmentsDataSource;
import org.cascadelms.fragments.CourseBlogFragment.BlogDataSource;
import org.cascadelms.fragments.DocumentsFragment.DocumentsDataSource;
import org.cascadelms.fragments.GradesFragment.GradesDataSource;
import org.cascadelms.fragments.SocialStreamFragment.StreamDataSource;

public class FakeDataSource implements SchoolsDataSource, CourseDataSource,
        DocumentsDataSource, AssignmentsDataSource, BlogDataSource,
        StreamDataSource, GradesDataSource
{
    /* Static initializer */
    {
        /* Builds the data to return. */
        FakeDataSource.initCourseData();
        FakeDataSource.initDocumentsData();
        FakeDataSource.initAssignmentsData();
        FakeDataSource.initGradesData();
        FakeDataSource.initSchoolData();
        FakeDataSource.initStreamData();
        FakeDataSource.initBlogData();
    }

    private static FakeDataSource instance;

    private FakeDataSource()
    {
		/* Private constructor */
    }

    public static FakeDataSource getInstance()
    {
        if (instance == null)
        {
            instance = new FakeDataSource();
        }
        return instance;
    }

    /* Social Stream sort comparator class */
    private static class SocialStreamComparator implements
            Comparator<StreamItem>
    {
        @Override
        public int compare(StreamItem itemA, StreamItem itemB)
        {
            return itemA.getPostDate().compareTo(itemB.getPostDate());
        }
    }

    /* CourseDataSource */
	/* Course ID Constants */
    private static final int COURSE_ID_DESIGN = 0;
    private static final int COURSE_ID_MACHINE_LEARNING = 1;
    private static final int COURSE_ID_AI = 2;
    private static final int COURSE_ID_INFO_RETRIEVAL = 3;
    private static final int COURSE_ID_UI = 4;

    private static ArrayList<Course> courseList;

    private static void initCourseData()
    {
		/* Builds the course list */
        courseList = new ArrayList<Course>();
        courseList.add((new Course.Builder(COURSE_ID_DESIGN,
                "CS5002-Senior Design", "Senior Design Class", 2, "Fall 2013",
                true)).build());
        courseList.add((new Course.Builder(COURSE_ID_MACHINE_LEARNING,
                "CS6037-Machine Learning", "Machine Learning Description", 2,
                "Fall 2013", true)).build());
        courseList.add((new Course.Builder(COURSE_ID_AI,
                "CS6033-Artificial Intellegence", "AI Description", 2,
                "Fall 2013", true)).build());
        courseList.add((new Course.Builder(COURSE_ID_INFO_RETRIEVAL,
                "CS6054-Info Retrieval", "Info Retrieval Description", 2,
                "Fall 2013", true)).build());
        courseList
                .add((new Course.Builder(COURSE_ID_UI,
                        "CS6067-User Interface", "UI Description", 2,
                        "Fall 2013", true)).build());
    }

    @Override
    public List<Course> getAvailableCourses()
    {
        return courseList;
    }

    /* DocumentsDataSource */
	/* ID Constants for Directories */
    private static final int DOCUMENT_FOLDER_LECTURE_NOTES = 555;
    private static final int DOCUMENT_FOLDER_PAST_PROJECTS = 444;

    private static ArrayList<Document> documentsListDesign;
    private static ArrayList<Document> documentsListMachine;
    private static ArrayList<Document> documentsListAI;
    private static ArrayList<Document> documentsListInfo;
    private static ArrayList<Document> documentsListUI;
    private static ArrayList<Document> directoryListPreviousProjects;
    private static ArrayList<Document> directoryListLectures;

    private static void initDocumentsData()
    {
		/* Builds Documents lists for each course. */
        documentsListDesign = new ArrayList<Document>();
        String cascadeImageURL = "http://www.cascadelms.org/assets/img/cascade.png";

		/* List for Senior Design */
        documentsListDesign.add(Document.Builder.getFileBuilder(0,
                "Syllabus-2013", ".pdf", 20000, cascadeImageURL, true).build());
        documentsListDesign.add(Document.Builder.getFileBuilder(1,
                "Project Teams", ".txt", 5000, cascadeImageURL, true).build());
        documentsListDesign.add(Document.Builder.getFileBuilder(2,
                "Sample Project Poster", ".png", 200000, cascadeImageURL, true)
                .build());
        documentsListDesign.add(Document.Builder.getFileBuilder(3,
                "Abstract Edits", ".docx", 25000, cascadeImageURL, true).build());
        documentsListDesign.add(Document.Builder.getFolderBuilder(
                DOCUMENT_FOLDER_PAST_PROJECTS, "Previous Projects").build());

		/* A list for the Previous Projects subfolder in Senior Design. */
        directoryListPreviousProjects = new ArrayList<Document>();
        directoryListPreviousProjects.add(Document.Builder.getFileBuilder(4,
                "PreviousProjects1", ".pdf", 25000, cascadeImageURL, true).build());
        directoryListPreviousProjects.add(Document.Builder.getFileBuilder(5,
                "PreviousProjects2", ".pdf", 25000, cascadeImageURL, true).build());
        directoryListPreviousProjects.add(Document.Builder.getFileBuilder(6,
                "PreviousProjects3", ".pdf", 25000, cascadeImageURL, true).build());
        directoryListPreviousProjects.add(Document.Builder.getFileBuilder(7,
                "PreviousProjects4", ".pdf", 25000, cascadeImageURL, true).build());

		/* List for Machine Learning */
        documentsListMachine = new ArrayList<Document>();
        documentsListMachine.add(Document.Builder.getFileBuilder(10,
                "MachineSyllabus-2013", ".pdf", 20000, cascadeImageURL, true)
                .build());
        documentsListMachine
                .add(Document.Builder.getFileBuilder(11,
                        "Supervised Learning", ".ppt", 50000, cascadeImageURL, true)
                        .build());
        documentsListMachine.add(Document.Builder.getFileBuilder(12,
                "Transduction Training Cases", ".dat", 543210, cascadeImageURL, true)
                .build());
        documentsListMachine.add(Document.Builder.getFileBuilder(13,
                "Cluster Analysis", ".ppt", 25000, cascadeImageURL, true).build());

		/* List for Artificial Intelligence */
        documentsListAI = new ArrayList<Document>();
        // documentsListAI.add( Document.Builder.getFileBuilder( 100,
        // "AI_Syllabus_2013-14", ".docx", 20000,
        // cascadeImageURL )
        // .build() );
        documentsListAI.add(Document.Builder.getFolderBuilder(
                DOCUMENT_FOLDER_LECTURE_NOTES, "Lectures").build());

		/* List for the Lectures sub-folder in AI */
        directoryListLectures = new ArrayList<Document>();
        directoryListLectures.add(Document.Builder.getFileBuilder(101,
                "Lecture_1", ".ppt", 50000, cascadeImageURL, true).build());
        directoryListLectures.add(Document.Builder.getFileBuilder(102,
                "Lecture_2", ".ppt", 50000, cascadeImageURL, true).build());
        directoryListLectures.add(Document.Builder.getFileBuilder(103,
                "Lecture_3", ".ppt", 50000, cascadeImageURL, true).build());
        directoryListLectures.add(Document.Builder.getFileBuilder(104,
                "Lecture_4", ".ppt", 50000, cascadeImageURL, true).build());
        directoryListLectures.add(Document.Builder.getFileBuilder(105,
                "Lecture_5", ".ppt", 50000, cascadeImageURL, true).build());
        directoryListLectures.add(Document.Builder.getFileBuilder(106,
                "Lecture_6", ".ppt", 50000, cascadeImageURL, true).build());
        directoryListLectures.add(Document.Builder.getFileBuilder(107,
                "Lecture_7", ".ppt", 50000, cascadeImageURL, true).build());
        directoryListLectures.add(Document.Builder.getFileBuilder(108,
                "Lecture_8", ".ppt", 50000, cascadeImageURL, true).build());

		/* List for Information Retrieval */
        documentsListInfo = new ArrayList<Document>();
        documentsListInfo.add(Document.Builder.getFileBuilder(10,
                "Info_retrieval2013", ".pdf", 20000, cascadeImageURL, true).build());
        documentsListMachine.add(Document.Builder.getFileBuilder(11,
                "Lecture Notes on a very obscure topic with a long title",
                ".ppt", 50000, cascadeImageURL, true).build());

		/* Leaves the UI course as an empty list with no Documents. */
        documentsListUI = new ArrayList<Document>();
    }

    @Override
    public List<Document> getDocumentsForCourse(int courseId)
    {
        switch (courseId)
        {
            case COURSE_ID_DESIGN:
            {
                return documentsListDesign;
            }
            case COURSE_ID_MACHINE_LEARNING:
            {
                return documentsListMachine;
            }
            case COURSE_ID_AI:
            {
                return documentsListAI;
            }
            case COURSE_ID_INFO_RETRIEVAL:
            {
                return documentsListInfo;
            }
            case COURSE_ID_UI:
            {
                return documentsListUI;
            }
            default:
            {
                return null;
            }
        }
    }

    @Override
    public List<Document> getDocumentsInFolder(int courseId, Document folder)
    {
        switch (folder.getId())
        {
            case DOCUMENT_FOLDER_LECTURE_NOTES:
            {
                return directoryListLectures;
            }
            case DOCUMENT_FOLDER_PAST_PROJECTS:
            {
                return directoryListPreviousProjects;
            }
            default:
            {
                return new ArrayList<Document>();
            }
        }
    }

    private static Comment[] generateComments(int seed)
    {
        Random random = new Random(seed);

        Comment[] comments = new Comment[random.nextInt(6)];

        for (int i = 0; i < comments.length; ++i)
        {
            comments[i] = new Comment("Comment " + (i + 1), new User(-1, "John Doe", ""),
                    new Date());
        }

        return comments;
    }

    private static ArrayList<StreamItem> streamItemsListAll;
    private static ArrayList<StreamItem> streamItemsListDesign;
    private static ArrayList<StreamItem> streamItemsListMachine;
    private static ArrayList<StreamItem> streamItemsListAI;
    private static ArrayList<StreamItem> streamItemsListInfo;
    private static ArrayList<StreamItem> streamItemsListUI;
    private static HashMap<Integer, Integer> streamItemsIdToIndex;

    /* Stream Data */
    private static void initStreamData()
    {
        Random random = new Random(123456);
        int seed = 0;

        streamItemsListAll = new ArrayList<StreamItem>();
        streamItemsListDesign = new ArrayList<StreamItem>();
        streamItemsListMachine = new ArrayList<StreamItem>();
        streamItemsListAI = new ArrayList<StreamItem>();
        streamItemsListInfo = new ArrayList<StreamItem>();
        streamItemsListUI = new ArrayList<StreamItem>();
        streamItemsIdToIndex = new HashMap<Integer, Integer>();

        for (Assignment assignment : assignmentsListDesign)
        {
            StreamItem item = new StreamItem.Builder(
                    streamItemsListAll.size(), assignment.getOpenDate(),
                    new User(1, "Fred Annexstein", "fake://url"),
                    random.nextInt(10), null, random.nextInt(10),
                    "New assignment posted: " + assignment.getTitle(),
                    "New assignment posted: " + assignment.getTitle())
                    .setComments(generateComments(++seed)).build();

            streamItemsListAll.add(item);
            streamItemsListDesign.add(item);
        }
        for (Assignment assignment : assignmentsListMachine)
        {
            StreamItem item = new StreamItem.Builder(
                    streamItemsListAll.size(), assignment.getOpenDate(),
                    new User(2, "Michael Helmick", "fake://url"),
                    random.nextInt(10), null, random.nextInt(10),
                    "New assignment posted: " + assignment.getTitle(),
                    "New assignment posted: " + assignment.getTitle())
                    .setComments(generateComments(++seed)).build();
            streamItemsListAll.add(item);
            streamItemsListMachine.add(item);
        }
        for (Assignment assignment : assignmentsListAI)
        {
            StreamItem item = new StreamItem.Builder(
                    streamItemsListAll.size(), assignment.getOpenDate(),
                    new User(1, "Fred Annexstein", "fake://url"),
                    random.nextInt(10), null, random.nextInt(10),
                    "New assignment posted: " + assignment.getTitle(),
                    "New assignment posted: " + assignment.getTitle())
                    .setComments(generateComments(++seed)).build();
            streamItemsListAll.add(item);
            streamItemsListAI.add(item);
        }
        for (Assignment assignment : assignmentsListInfo)
        {
            StreamItem item = new StreamItem.Builder(
                    streamItemsListAll.size(), assignment.getOpenDate(),
                    new User(1, "Fred Annexstein", "fake://url"),
                    random.nextInt(10), null, random.nextInt(10),
                    "New assignment posted: " + assignment.getTitle(),
                    "New assignment posted: " + assignment.getTitle())
                    .setComments(generateComments(++seed)).build();
            streamItemsListAll.add(item);
            streamItemsListInfo.add(item);
        }
        for (Assignment assignment : assignmentsListUI)
        {
            StreamItem item = new StreamItem.Builder(
                    streamItemsListAll.size(), assignment.getOpenDate(),
                    new User(1, "Fred Annexstein", "fake://url"),
                    random.nextInt(10), null, random.nextInt(10),
                    "New assignment posted: " + assignment.getTitle(),
                    "New assignment posted: " + assignment.getTitle())
                    .setComments(generateComments(++seed)).build();
            streamItemsListAll.add(item);
            streamItemsListUI.add(item);
        }

        // Sort by date
        SocialStreamComparator comparator = new SocialStreamComparator();

        Collections.sort(streamItemsListAll,
                Collections.reverseOrder(comparator));
        Collections.sort(streamItemsListDesign,
                Collections.reverseOrder(comparator));
        Collections.sort(streamItemsListMachine,
                Collections.reverseOrder(comparator));
        Collections.sort(streamItemsListAI,
                Collections.reverseOrder(comparator));
        Collections.sort(streamItemsListInfo,
                Collections.reverseOrder(comparator));
        Collections.sort(streamItemsListUI,
                Collections.reverseOrder(comparator));

        for (int i = 0; i < streamItemsListAll.size(); ++i)
        {
            streamItemsIdToIndex.put(streamItemsListAll.get(i).getId(), i);
        }
    }

    /* Assignments Data */
    private static ArrayList<Assignment> assignmentsListDesign;
    private static ArrayList<Assignment> assignmentsListMachine;
    private static ArrayList<Assignment> assignmentsListAI;
    private static ArrayList<Assignment> assignmentsListInfo;
    private static ArrayList<Assignment> assignmentsListUI;

    private static void initAssignmentsData()
    {
		/* Dates to use in Assignment building. */
        Date availableDate = new Date(1387381568000L);
        Date lastSubmissionDate = new Date(1398440768000L);
        int id = 0;
        long dueTime = 1392181200000L;
        final long week = 604800000L;

        assignmentsListDesign = new ArrayList<Assignment>();
        assignmentsListDesign.add(new Assignment.Builder(id++,
                "150-Word Project Abstract", Assignment.Category.ASSIGNMENT,
                availableDate, new Date(dueTime += week), lastSubmissionDate,
                true, 100).setPointsEarned(95).build());
        assignmentsListDesign.add(new Assignment.Builder(id++,
                "Presentation Slides", Assignment.Category.ASSIGNMENT,
                availableDate, new Date(dueTime += week), lastSubmissionDate,
                true, 100).setPointsEarned(92.5).build());
        assignmentsListDesign.add(new Assignment.Builder(id++, "Test Plans",
                Assignment.Category.ASSIGNMENT, availableDate, new Date(
                dueTime += week), lastSubmissionDate, true, 100
        )
                .setPointsEarned(50).build());
        assignmentsListDesign.add(new Assignment.Builder(id++,
                "Current Events Essay", Assignment.Category.ASSIGNMENT,
                availableDate, new Date(dueTime += week), lastSubmissionDate,
                true, 100).setPointsEarned(0).build());
        assignmentsListDesign.add(new Assignment.Builder(id++,
                "Online User Manual", Assignment.Category.ASSIGNMENT,
                availableDate, new Date(dueTime += week), lastSubmissionDate,
                false, 100).build());

        assignmentsListMachine = new ArrayList<Assignment>();
        assignmentsListMachine.add(new Assignment.Builder(id++,
                "Take-Home Exam", Assignment.Category.ASSIGNMENT, new Date(
                System.currentTimeMillis()), new Date(System
                .currentTimeMillis() + 5000L), new Date(System
                .currentTimeMillis() + 10000L), true, 5
        )
                .setPointsEarned(5).build());

        long today = new Date().getTime();
        assignmentsListAI = new ArrayList<Assignment>();
        assignmentsListAI.add(new Assignment.Builder(id++,
                "Upcoming Assignment", Assignment.Category.ASSIGNMENT,
                new Date(today + week), new Date(today + (2L * week)),
                new Date(today + (3L * week)), false, 20).build());
        assignmentsListAI.add(new Assignment.Builder(id++,
                "Current Assignment", Assignment.Category.ASSIGNMENT, new Date(
                today - week), new Date(today + week), new Date(
                today + (2L * week)), false, 20
        ).build());
        assignmentsListAI.add(new Assignment.Builder(id++,
                "Past Due Assignment", Assignment.Category.ASSIGNMENT,
                new Date(today - (2L * week)), new Date(today - week),
                new Date(today + week), false, 20).build());
        assignmentsListAI.add(new Assignment.Builder(id++,
                "Closed Assignment", Assignment.Category.ASSIGNMENT, new Date(
                today - (3L * week)),
                new Date(today - (2L * week)), new Date(today - week),
                false, 20
        ).build());

        long now = (new Date()).getTime();
        int labNumber = 0;
        assignmentsListInfo = new ArrayList<Assignment>();
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());
        now += week;
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());
        now += week;
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());
        now += week;
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());
        now += week;
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());
        now += week;
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());
        now += week;
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());
        now += week;
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());
        now += week;
        assignmentsListInfo.add(new Assignment.Builder(id++, "Lab "
                + ++labNumber, Assignment.Category.ASSIGNMENT, new Date(now
                + week), new Date(now + (2 * week)), new Date(now
                + (3 * week)), false, 100).build());

        assignmentsListUI = new ArrayList<Assignment>();
		/* This list is left empty on purpose. */
    }

    @Override
    public List<Assignment> getAssignmentsForCourse(int courseId)
    {
        switch (courseId)
        {
            case COURSE_ID_DESIGN:
            {
                return assignmentsListDesign;
            }
            case COURSE_ID_MACHINE_LEARNING:
            {
                return assignmentsListMachine;
            }
            case COURSE_ID_AI:
            {
                return assignmentsListAI;
            }
            case COURSE_ID_INFO_RETRIEVAL:
            {
                return assignmentsListInfo;
            }
            case COURSE_ID_UI:
            {
                return assignmentsListUI;
            }
            default:
            {
                return null;
            }
        }
    }

    /* Grades Data */
    private static ArrayList<Grade> gradesListDesign;
    private static ArrayList<Grade> gradesListMachine;
    private static ArrayList<Grade> gradesListAI;
    private static ArrayList<Grade> gradesListInfo;
    private static ArrayList<Grade> gradesListUI;

    private static void initGradesData()
    {
		/* Transform Assignments into Grades. */
        gradesListDesign = new ArrayList<Grade>();
        for (Assignment assignment : assignmentsListDesign)
        {
            gradesListDesign.add(new Grade(assignment.getId(), assignment
                    .getTitle(), assignment.getCategory(), assignment
                    .getDueDate(), Grade.Type.SCORE, assignment
                    .getPointsEarned(), assignment.getPointsPossible()));
        }
        gradesListMachine = new ArrayList<Grade>();
        for (Assignment assignment : assignmentsListMachine)
        {
            gradesListMachine.add(new Grade(assignment.getId(), assignment
                    .getTitle(), assignment.getCategory(), assignment
                    .getDueDate(), Grade.Type.SCORE, assignment
                    .getPointsEarned(), assignment.getPointsPossible()));
        }
        gradesListAI = new ArrayList<Grade>();
        for (Assignment assignment : assignmentsListAI)
        {
            gradesListAI.add(new Grade(assignment.getId(), assignment
                    .getTitle(), assignment.getCategory(), assignment
                    .getDueDate(), Grade.Type.SCORE, assignment
                    .getPointsEarned(), assignment.getPointsPossible()));
        }
        gradesListInfo = new ArrayList<Grade>();
        for (Assignment assignment : assignmentsListInfo)
        {
            gradesListInfo.add(new Grade(assignment.getId(), assignment
                    .getTitle(), assignment.getCategory(), assignment
                    .getDueDate(), Grade.Type.SCORE, assignment
                    .getPointsEarned(), assignment.getPointsPossible()));
        }
        gradesListUI = new ArrayList<Grade>();
        for (Assignment assignment : assignmentsListUI)
        {
            gradesListUI.add(new Grade(assignment.getId(), assignment
                    .getTitle(), assignment.getCategory(), assignment
                    .getDueDate(), Grade.Type.SCORE, assignment
                    .getPointsEarned(), assignment.getPointsPossible()));
        }
    }

    @Override
    public List<Grade> getGradesForCourse(int courseId)
    {
        switch (courseId)
        {
            case COURSE_ID_DESIGN:
            {
                return gradesListDesign;
            }
            case COURSE_ID_MACHINE_LEARNING:
            {
                return gradesListMachine;
            }
            case COURSE_ID_AI:
            {
                return gradesListAI;
            }
            case COURSE_ID_INFO_RETRIEVAL:
            {
                return gradesListInfo;
            }
            case COURSE_ID_UI:
            {
                return gradesListUI;
            }
            default:
            {
                return new ArrayList<Grade>();
            }
        }
    }

    /* Blog Data */
    private static ArrayList<BlogPost> blogListDesign;
    private static ArrayList<BlogPost> blogListMachine;
    private static ArrayList<BlogPost> blogListAI;
    private static ArrayList<BlogPost> blogListInfo;
    private static ArrayList<BlogPost> blogListUI;

    private static void initBlogData()
    {
        Random random = new Random(654321);
        long now = (new Date()).getTime();
        final long week = 604800000L;

        blogListDesign = new ArrayList<BlogPost>();

        blogListDesign
                .add(new BlogPost.Builder(
                        1,
                        "Presentation Schedule",
                        false,
                        "Fred Annexstein",
                        new Date(now),
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                                + "Nulla viverra elit libero, et egestas magna tempus eu."
                                + "Nam sit amet mauris at nulla aliquet auctor quis ac metus."
                                + "Proin a est imperdiet, iaculis tortor sed, ullamcorper lorem."
                                + "Mauris ullamcorper libero consectetur adipiscing laoreet."
                                + "Curabitur risus enim, sollicitudin ut lorem at,"
                                + "dictum ullamcorper tortor. Nulla facilisi."
                                + "Etiam bibendum justo sit amet erat aliquet, ut auctor est pellentesque."
                                + "Nulla facilisi.", random.nextInt(10),
                        new User[]{}, random.nextInt(10)
                ).build());
        blogListDesign
                .add(new BlogPost.Builder(
                        2,
                        "Submission Requirements",
                        false,
                        "Fred Annexstein",
                        new Date(now + week),
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                                + "Nulla viverra elit libero, et egestas magna tempus eu."
                                + "Nam sit amet mauris at nulla aliquet auctor quis ac metus."
                                + "Proin a est imperdiet, iaculis tortor sed, ullamcorper lorem."
                                + "Mauris ullamcorper libero consectetur adipiscing laoreet."
                                + "Curabitur risus enim, sollicitudin ut lorem at,"
                                + "dictum ullamcorper tortor. Nulla facilisi."
                                + "Etiam bibendum justo sit amet erat aliquet, ut auctor est pellentesque."
                                + "Nulla facilisi.", random.nextInt(10),
                        new User[]{}, random.nextInt(10)
                ).build());

        blogListMachine = new ArrayList<BlogPost>();

        for (int i = 1; i <= 10; ++i)
            blogListMachine.add(new BlogPost.Builder(i, "Blog Post " + i,
                    false, "Michael Helmick", new Date(now + week), "Post "
                    + i + " description.", random.nextInt(10),
                    new User[]{}, random.nextInt(10)
            ).build());

        blogListAI = new ArrayList<BlogPost>();

        blogListAI.add(new BlogPost.Builder(1, "A Blog Post", false,
                "Fred Annexstein", new Date(now), "Hello, world!", random
                .nextInt(10), new User[]{}, random.nextInt(10)
        )
                .build());

        blogListInfo = new ArrayList<BlogPost>();

        blogListInfo
                .add(new BlogPost.Builder(
                        1,
                        "Presentation Schedule",
                        false,
                        "Fred Annexstein",
                        new Date(now),
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                                + "Nulla viverra elit libero, et egestas magna tempus eu."
                                + "Nam sit amet mauris at nulla aliquet auctor quis ac metus."
                                + "Proin a est imperdiet, iaculis tortor sed, ullamcorper lorem."
                                + "Mauris ullamcorper libero consectetur adipiscing laoreet."
                                + "Curabitur risus enim, sollicitudin ut lorem at,"
                                + "dictum ullamcorper tortor. Nulla facilisi."
                                + "Etiam bibendum justo sit amet erat aliquet, ut auctor est pellentesque."
                                + "Nulla facilisi.", random.nextInt(10),
                        new User[]{}, random.nextInt(10)
                ).build());
        blogListInfo
                .add(new BlogPost.Builder(
                        2,
                        "Submission Requirements",
                        false,
                        "Fred Annexstein",
                        new Date(now + week),
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                                + "Nulla viverra elit libero, et egestas magna tempus eu."
                                + "Nam sit amet mauris at nulla aliquet auctor quis ac metus."
                                + "Proin a est imperdiet, iaculis tortor sed, ullamcorper lorem."
                                + "Mauris ullamcorper libero consectetur adipiscing laoreet."
                                + "Curabitur risus enim, sollicitudin ut lorem at,"
                                + "dictum ullamcorper tortor. Nulla facilisi."
                                + "Etiam bibendum justo sit amet erat aliquet, ut auctor est pellentesque."
                                + "Nulla facilisi.", random.nextInt(10),
                        new User[]{}, random.nextInt(10)
                ).build());

        blogListUI = new ArrayList<BlogPost>();
    }

    @Override
    public List<BlogPost> getBlogPostsForCourse(int courseId)
    {
        switch (courseId)
        {
            case COURSE_ID_DESIGN:
            {
                return blogListDesign;
            }
            case COURSE_ID_MACHINE_LEARNING:
            {
                return blogListMachine;
            }
            case COURSE_ID_AI:
            {
                return blogListAI;
            }
            case COURSE_ID_INFO_RETRIEVAL:
            {
                return blogListInfo;
            }
            case COURSE_ID_UI:
            {
                return blogListUI;
            }
            default:
            {
                return new ArrayList<BlogPost>();
            }
        }
    }

    @Override
    public List<StreamItem> getAllStreamItems()
    {
        return streamItemsListAll;
    }

    @Override
    public List<StreamItem> getStreamItemsForCourse(int courseId)
    {
        switch (courseId)
        {
            case COURSE_ID_DESIGN:
            {
                return streamItemsListDesign;
            }
            case COURSE_ID_MACHINE_LEARNING:
            {
                return streamItemsListMachine;
            }
            case COURSE_ID_AI:
            {
                return streamItemsListAI;
            }
            case COURSE_ID_INFO_RETRIEVAL:
            {
                return streamItemsListInfo;
            }
            case COURSE_ID_UI:
            {
                return streamItemsListUI;
            }
            default:
            {
                return new ArrayList<StreamItem>();
            }
        }
    }

    @Override
    public StreamItem getStreamItemDetail(long postId)
    {
        return streamItemsListAll.get(streamItemsIdToIndex.get(postId));
    }

    private static ArrayList<School> schoolsList;

    private static void initSchoolData()
    {
        schoolsList = new ArrayList<School>();

        schoolsList.add(new School("University of Cincinnati",
                "https://cascade.ceas.uc.edu/",
                "https://cascade.ceas.uc.edu/index/shibboleth"));
        for (int i = 2; i <= 6; ++i)
        {
            schoolsList
                    .add(new School("School " + i,
                            "http://www.cascadelms.org/",
                            "http://www.cascadelms.org/"));
        }
    }

    @Override
    public List<School> getSchools()
    {
        return schoolsList;
    }
}
