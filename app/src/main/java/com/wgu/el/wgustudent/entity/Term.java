package com.wgu.el.wgustudent.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.wgu.el.wgustudent.database.DateTypeConverter;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.Date;


@Entity(tableName = WguConst.TABLE_NAME_TERMS)
public class Term {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WguConst.TABLE_TERMS_COL_ID)
    private int id;

    @ColumnInfo(name = WguConst.TABLE_TERMS_COL_NAME)
    private String termName;

    @TypeConverters(DateTypeConverter.class)
    @ColumnInfo(name = WguConst.TABLE_TERMS_COL_START_DATE)
    private Date termStartDate;

    @TypeConverters(DateTypeConverter.class)
    @ColumnInfo(name = WguConst.TABLE_TERMS_COL_END_DATE)
    private Date termEndDate;

    public Term(int id, String termName, Date termStartDate, Date termEndDate) {
        this.id = id;
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    @Ignore
    public Term() {}

    @Ignore
    public Term(int id, String termName) {
        this.id = id;
        this.termName = termName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    public Date getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }

    // For Spinner
    @Override
    public String toString() {
        return this.termName;
    }

}
