package com.wgu.el.wgustudent.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.wgu.el.wgustudent.database.AssessmentType;
import com.wgu.el.wgustudent.database.DateTypeConverter;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.Date;

//@Entity(tableName = WguConst.TABLE_NAME_ASSESSMENTS)
@Entity(tableName = WguConst.TABLE_NAME_ASSESSMENTS, foreignKeys = {
        @ForeignKey(
                entity = Course.class,
                parentColumns = WguConst.TABLE_COURSES_COL_ID,
                childColumns = WguConst.TABLE_ASSESSMENTS_COL_COURSE_ID,
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)}, indices = {
        @Index(value = WguConst.TABLE_ASSESSMENTS_COL_COURSE_ID)
})
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WguConst.TABLE_ASSESSMENTS_COL_ID)
    private int id;

    @ColumnInfo(name = WguConst.TABLE_ASSESSMENTS_COL_NAME)
    private String assessmentName;

    @TypeConverters(AssessmentType.class)
    @ColumnInfo(name = WguConst.TABLE_ASSESSMENTS_COL_TYPE)
    private AssessmentType assessmentType;

    @TypeConverters(DateTypeConverter.class)
    @ColumnInfo(name = WguConst.TABLE_ASSESSMENTS_COL_DATE)
    private Date assessmentDate;

    @ColumnInfo(name = WguConst.TABLE_ASSESSMENTS_COL_REMINDER)
    private boolean reminder;

    @ColumnInfo(name = WguConst.TABLE_ASSESSMENTS_COL_COURSE_ID)
    private int courseId;

    public Assessment(int id, String assessmentName, AssessmentType assessmentType, Date assessmentDate, boolean reminder, int courseId) {
        this.id = id;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDate = assessmentDate;
        this.reminder = reminder;
        this.courseId = courseId;
    }

    @Ignore
    public Assessment(int id, String assessmentName, int courseId) {
        this.id = id;
        this.assessmentName = assessmentName;
        this.courseId = courseId;
    }

    @Ignore
    public Assessment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
