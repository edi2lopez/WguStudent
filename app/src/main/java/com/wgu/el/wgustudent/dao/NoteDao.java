package com.wgu.el.wgustudent.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.entity.NoteWithCourse;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_NOTES + " ORDER BY id DESC")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_NOTES+ " WHERE id = :id LIMIT 1")
    LiveData<Note> getNoteById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM " + WguConst.TABLE_NAME_NOTES + " WHERE " +
            WguConst.TABLE_NOTES_COL_COURSE_ID + " = :courseId")
    LiveData<List<Note>> getNotesFromCourse(int courseId);

    // TODO show associated course in list, not implemented for this assignment
//    @Query("SELECT notes.id, " +
//            "notes.note_name as noteName, " +
//            "courses.name as courseName " +
//            "FROM notes INNER JOIN courses ON notes.courseId = courses.id " +
//            "WHERE notes.id = :courseId "
//    )
//    LiveData<List<NoteWithCourse>> getNotesWithCourse(int courseId);

}
