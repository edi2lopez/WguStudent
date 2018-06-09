package com.wgu.el.wgustudent.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wgu.el.wgustudent.entity.Assessment;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_ASSESSMENTS + " ORDER BY id DESC")
    LiveData<List<Assessment>> getAssessments();

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_ASSESSMENTS + " WHERE id = :id LIMIT 1")
    LiveData<Assessment> getAssessmentById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_ASSESSMENTS + " WHERE " +
            WguConst.TABLE_ASSESSMENTS_COL_COURSE_ID + " = :courseId")
    LiveData<List<Assessment>> getAssessmentsFromCourse(int courseId);
    
}
