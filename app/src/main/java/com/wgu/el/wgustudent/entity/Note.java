package com.wgu.el.wgustudent.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import com.wgu.el.wgustudent.util.WguConst;


//@Entity(tableName = WguConst.TABLE_NAME_NOTES)
@Entity(tableName = WguConst.TABLE_NAME_NOTES, foreignKeys = {
        @ForeignKey(
                entity = Course.class,
                parentColumns = WguConst.TABLE_COURSES_COL_ID,
                childColumns = WguConst.TABLE_NOTES_COL_COURSE_ID,
                onUpdate = ForeignKey.SET_NULL,
                onDelete = ForeignKey.SET_NULL)}, indices = {
        @Index(value = WguConst.TABLE_NOTES_COL_COURSE_ID)
})
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WguConst.TABLE_NOTES_COL_ID)
    private int id;

    @ColumnInfo(name = WguConst.TABLE_NOTES_COL_NAME)
    private String noteName;

    @ColumnInfo(name = WguConst.TABLE_NOTES_COL_DETAIL)
    private String noteDetail;

    @ColumnInfo(name = WguConst.TABLE_NOTES_COL_PICTURE)
    private String notePicture;

    @ColumnInfo(name = WguConst.TABLE_NOTES_COL_COURSE_ID)
    private int courseId;

    public Note(int id, String noteName, String noteDetail, @Nullable String notePicture, int courseId) {
        this.id = id;
        this.noteName = noteName;
        this.noteDetail = noteDetail;
        this.notePicture = notePicture;
        this.courseId = courseId;
    }

    @Ignore
    public Note(int id, String noteName, String noteDetail) {
        this.id = id;
        this.noteName = noteName;
        this.noteDetail = noteDetail;
    }

    @Ignore
    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteDetail() {
        return noteDetail;
    }

    public void setNoteDetail(String noteDetail) {
        this.noteDetail = noteDetail;
    }

    public String getNotePicture() {
        return notePicture;
    }

    public void setNotePicture(String notePicture) {
        this.notePicture = notePicture;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
