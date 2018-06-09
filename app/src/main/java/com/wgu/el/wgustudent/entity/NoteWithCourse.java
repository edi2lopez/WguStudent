package com.wgu.el.wgustudent.entity;


import android.arch.persistence.room.ColumnInfo;

import com.wgu.el.wgustudent.util.WguConst;

// TODO show associated course in list, not implemented for this assignment
public class NoteWithCourse {

    public int id;

    @ColumnInfo(name = WguConst.TABLE_NOTES_COL_NAME)
    public String noteName;

    @ColumnInfo(name = WguConst.TABLE_COURSES_COL_NAME)
    public String courseName;

}
