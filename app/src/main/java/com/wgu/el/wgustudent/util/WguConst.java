package com.wgu.el.wgustudent.util;


public class WguConst {

    // Date Dialog Tags
    public static final int TERM_START_DATE_TAG = 9999;
    public static final int TERM_END_DATE_TAG = 9998;
    public static final int COURSE_START_DATE_TAG = 9997;
    public static final int COURSE_END_DATE_TAG = 9996;
    public static final int ASSESSMENT_DATE = 9995;

    // Intents // TODO remove & optimize unused tags
    public static final int MAIN_ACTIVITY_CODE = 9994;
    public static final int TERM_LIST_ACTIVITY_CODE = 9993;
    public static final int TERM_DETAIL_ACTIVITY_CODE = 9992;
    public static final int COURSE_LIST_ACTIVITY_CODE = 9991;
    public static final int COURSE_DETAIL_ACTIVITY_CODE = 9990;
    public static final int ASSESSMENT_LIST_ACTIVITY_CODE = 9989;
    public static final int ASSESSMENT_DETAIL_ACTIVITY_CODE = 9988;
    public static final int NOTE_LIST_ACTIVITY_CODE = 9987;
    public static final int NOTE_DETAIL_ACTIVITY_CODE = 9986;

    // Permissions
    public static final int REQUEST_IMAGE_CAPTURE = 9985;
    public static final int REQUEST_IMAGE_GALLERY = 9984;
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 9984;

    public static final String MAIN_ACTIVITY_TAG = "MainActivityTag";
    public static final String TERMS_LIST_ACTIVITY_TAG = "TermsListActivityTag";
    public static final String TERM_DETAIL_ACTIVITY_TAG = "TermDetailActivityTag";
    public static final String COURSES_LIST_ACTIVITY_TAG = "CoursesListActivityTag";
    public static final String COURSE_DETAIL_ACTIVITY_TAG = "CourseDetailActivityTag";
    public static final String ASSESSMENTS_LIST_ACTIVITY_TAG = "AssessmentsListActivityTag";
    public static final String ASSESSMENT_DETAIL_ACTIVITY_TAG = "AssessmentDetailActivityTag";
    public static final String NOTES_LIST_ACTIVITY_TAG = "NotesListActivityTag";
    public static final String NOTE_DETAIL_ACTIVITY_TAG = "NoteDetailActivityTag";

    public static final String COURSE_ID_TAG = "CourseIdTag";
    public static final String NOTE_ID_TAG = "NoteIdTag";
    public static final String COURSE_TERM_ID_TAG = "CourseTermIdTag";
    public static final String ASSESSMENT_COURSE_ID_TAG = "AssessmentCourseIdTag";
    public static final String NOTE_COURSE_ID_TAG = "AssessmentCourseIdTag";

    public static final String EMPTY_TERM_ID_TAG = "EmptyTermIdTag";
    public static final String EMPTY_COURSE_ID_TAG = "EmptyCourseIdTag";
    public static final String EMPTY_ASSESSMENT_ID_TAG = "EmptyAssessmentIdTag";
    public static final String EMPTY_NOTE_ID_TAG = "EmptyNoteIdTag";

    //Database constants
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "wgu_database.db";

    public static final String TABLE_NAME_TERMS = "terms";
    public static final String TABLE_TERMS_COL_ID = "id";
    public static final String TABLE_TERMS_COL_NAME = "name";
    public static final String TABLE_TERMS_COL_START_DATE = "start_date";
    public static final String TABLE_TERMS_COL_END_DATE = "end_date";

    public static final String TABLE_NAME_COURSES = "courses";
    public static final String TABLE_COURSES_COL_ID = "id";
    public static final String TABLE_COURSES_COL_NAME = "name";
    public static final String TABLE_COURSES_COL_START_DATE = "start_date";
    public static final String TABLE_COURSES_COL_END_DATE = "end_date";
    public static final String TABLE_COURSES_COL_START_DATE_REMINDER = "start_date_reminder";
    public static final String TABLE_COURSES_COL_END_DATE_REMINDER = "end_date_reminder";
    public static final String TABLE_COURSES_COL_STATUS = "status";
    public static final String TABLE_COURSES_COL_MENTOR_NAME = "mentor_name";
    public static final String TABLE_COURSES_COL_MENTOR_PHONE = "mentor_phone";
    public static final String TABLE_COURSES_COL_MENTOR_EMAIL = "mentor_email";
    public static final String TABLE_COURSES_COL_TERM_ID = "termId";

    public static final String TABLE_NAME_ASSESSMENTS = "assessments";
    public static final String TABLE_ASSESSMENTS_COL_ID = "id";
    public static final String TABLE_ASSESSMENTS_COL_NAME = "assessment_name";
    public static final String TABLE_ASSESSMENTS_COL_TYPE = "assessment_type";
    public static final String TABLE_ASSESSMENTS_COL_DATE = "assessment_date";
    public static final String TABLE_ASSESSMENTS_COL_REMINDER = "reminder";
    public static final String TABLE_ASSESSMENTS_COL_COURSE_ID = "courseId";

    public static final String TABLE_NAME_NOTES = "notes";
    public static final String TABLE_NOTES_COL_ID = "id";
    public static final String TABLE_NOTES_COL_NAME = "note_name";
    public static final String TABLE_NOTES_COL_DETAIL = "note_detail";
    public static final String TABLE_NOTES_COL_PICTURE = "note_picture";
    public static final String TABLE_NOTES_COL_COURSE_ID = "courseId";

}
