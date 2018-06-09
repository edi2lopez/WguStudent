package com.wgu.el.wgustudent.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_COURSES + " ORDER BY id ASC")
    LiveData<List<Course>> getCourses();

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_COURSES + " WHERE id = :id LIMIT 1")
    LiveData<Course> getCourseById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_COURSES + " WHERE " +
            WguConst.TABLE_COURSES_COL_TERM_ID + " = :termId" + " ORDER BY id ASC")
    LiveData<List<Course>> getCoursesFromTerm(int termId);

//    @Query("SELECT " +
//            WguConst.TABLE_COURSES_COL_NAME +", " +
//            WguConst.TABLE_COURSES_COL_TERM_ID +
//            " FROM "  +
//            WguConst.TABLE_NAME_COURSES +
//            " WHERE " +
//            WguConst.TABLE_COURSES_COL_TERM_ID + " = :termId")
//    LiveData<List<Course>> getCoursesFromTerm(int termId);

}
