package com.wgu.el.wgustudent.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.wgu.el.wgustudent.dao.AssessmentDao;
import com.wgu.el.wgustudent.dao.CourseDao;
import com.wgu.el.wgustudent.dao.NoteDao;
import com.wgu.el.wgustudent.dao.TermDao;
import com.wgu.el.wgustudent.entity.Assessment;
import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.entity.Term;
import com.wgu.el.wgustudent.util.WguConst;

@Database(entities = {
        Assessment.class, Course.class,
        Note.class,
        Term.class
}, version = WguConst.DATABASE_VERSION, exportSchema = false)
public abstract class WguDatabase extends RoomDatabase {

    public abstract AssessmentDao assessmentDao();
    public abstract CourseDao courseDao();
    public abstract NoteDao noteDao();
    public abstract TermDao termDao();

}
