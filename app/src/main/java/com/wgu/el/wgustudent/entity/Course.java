package com.wgu.el.wgustudent.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.wgu.el.wgustudent.database.DateTypeConverter;
import com.wgu.el.wgustudent.database.Status;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.Date;

@Entity(tableName = WguConst.TABLE_NAME_COURSES, foreignKeys = {
    @ForeignKey(
        entity = Term.class,
        parentColumns = WguConst.TABLE_TERMS_COL_ID,
        childColumns = WguConst.TABLE_COURSES_COL_TERM_ID,
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)}, indices = {
    @Index(value = WguConst.TABLE_COURSES_COL_TERM_ID)
})
//@Entity(tableName = WguConst.TABLE_NAME_COURSES, foreignKeys = {
//        @ForeignKey(
//                entity = Term.class,
//                parentColumns = WguConst.TABLE_TERMS_COL_ID,
//                childColumns = WguConst.TABLE_COURSES_COL_TERM_ID
//        )
//})
public class Course {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_ID)
    private int id;

    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_NAME)
    private String courseName;

    @TypeConverters(DateTypeConverter.class)
    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_START_DATE)
    private Date courseStartDate;

    @TypeConverters(DateTypeConverter.class)
    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_END_DATE)
    private Date courseEndDate;

    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_START_DATE_REMINDER)
    private boolean courseStartDateReminder;

    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_END_DATE_REMINDER)
    private boolean courseEndDateReminder;

    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_MENTOR_NAME)
    private String mentorName;

    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_MENTOR_EMAIL)
    private String mentorEmail;

    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_MENTOR_PHONE)
    private String mentorPhone;

    @TypeConverters(Status.class)
    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_STATUS)
    private Status status;

    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_TERM_ID)
    private int termId;

    public Course(int id, String courseName, Date courseStartDate, Date courseEndDate,
                  boolean courseStartDateReminder, boolean courseEndDateReminder, Status status,
                  String mentorName, String mentorEmail, String mentorPhone, int termId) {
        this.id = id;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStartDateReminder = courseStartDateReminder;
        this.courseEndDateReminder = courseEndDateReminder;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorEmail = mentorEmail;
        this.mentorPhone = mentorPhone;
        this.termId = termId;
    }

    @Ignore
    public Course(int id, String courseName, int termId) {
        this.id = id;
        this.courseName = courseName;
        this.termId = termId;
    }

    @Ignore
    public Course() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public boolean isCourseStartDateReminder() {
        return courseStartDateReminder;
    }

    public void setCourseStartDateReminder(boolean courseStartDateReminder) {
        this.courseStartDateReminder = courseStartDateReminder;
    }

    public boolean isCourseEndDateReminder() { return courseEndDateReminder; }

    public void setCourseEndDateReminder(boolean courseEndDateReminder) {
        this.courseEndDateReminder = courseEndDateReminder;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public Status getStatus() { return status;  }

    public void setStatus(Status status) { this.status = status;  }

    public int getTermId() {return termId; }

    public void setTermId(int termId) { this.termId = termId; }

}
