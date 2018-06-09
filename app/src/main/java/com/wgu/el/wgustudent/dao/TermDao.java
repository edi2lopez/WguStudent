package com.wgu.el.wgustudent.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wgu.el.wgustudent.entity.Term;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.List;

@Dao
public interface TermDao {

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_TERMS + " ORDER BY id ASC")
    LiveData<List<Term>> getTerms();

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_TERMS + " WHERE id = :id LIMIT 1")
    LiveData<Term> getTermById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(Term term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTerms(List<Term> terms);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

}
