package com.wgu.el.wgustudent.repository;


import android.arch.lifecycle.LiveData;

import com.wgu.el.wgustudent.database.WguDatabase;
import com.wgu.el.wgustudent.entity.Assessment;
import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.entity.NoteWithCourse;
import com.wgu.el.wgustudent.entity.Term;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class WguRepositoryImpl implements WguRepository {

    @Inject
    WguDatabase db;

    public WguRepositoryImpl(WguDatabase wguDatabase) { this.db = wguDatabase; }

    @Override
    public Completable addTerm(Term term) {
        return Completable.fromAction(() -> db.termDao().insertTerm(term));
    }

    @Override
    public LiveData<List<Term>> getTerms() {
        //Here is where we would do more complex logic, like getting events from a cache
        //then inserting into the database etc. In this example we just go straight to the dao.
        return db.termDao().getTerms();
    }

    @Override
    public LiveData<Term> getTermById(int id) {
        return db.termDao().getTermById(id);
    }

    @Override
    public Completable deleteTerm(Term term) {
        return Completable.fromAction(() -> db.termDao().deleteTerm(term));
    }

    @Override
    public Completable updateTerm(Term term) {
        return Completable.fromAction(() -> db.termDao().updateTerm(term));
    }

    @Override
    public Completable addAssessment(Assessment assessment) {
        return Completable.fromAction(() -> db.assessmentDao().insertAssessment(assessment));
    }

    @Override
    public LiveData<List<Assessment>> getAssessments() {
        return db.assessmentDao().getAssessments();
    }

    @Override
    public LiveData<List<Assessment>> getAssessmentsFromCourse(int id) {
        return db.assessmentDao().getAssessmentsFromCourse(id);
    }

    @Override
    public LiveData<Assessment> getAssessmentById(int id) {
        return db.assessmentDao().getAssessmentById(id);
    }

    @Override
    public Completable deleteAssessment(Assessment assessment) {
        return Completable.fromAction(() -> db.assessmentDao().deleteAssessment(assessment));
    }

    @Override
    public Completable updateAssessment(Assessment assessment) {
        return Completable.fromAction(() -> db.assessmentDao().updateAssessment(assessment));
    }

    @Override
    public Completable addCourse(Course course) {
        return Completable.fromAction(() -> db.courseDao().insertCourse(course));
    }

    @Override
    public LiveData<List<Course>> getCourses() {
        return db.courseDao().getCourses();
    }

    @Override
    public LiveData<List<Course>> getCoursesFromTerm(int id) {
        return db.courseDao().getCoursesFromTerm(id);
    }

    @Override
    public LiveData<Course> getCourseById(int id) {
        return db.courseDao().getCourseById(id);
    }

    @Override
    public Completable deleteCourse(Course course) {
        return Completable.fromAction(() -> db.courseDao().deleteCourse(course));
    }

    @Override
    public Completable updateCourse(Course course) {
        return Completable.fromAction(() -> db.courseDao().updateCourse(course));
    }

    @Override
    public Completable addNote(Note note) {
        return Completable.fromAction(() -> db.noteDao().insertNote(note));
    }

    @Override
    public LiveData<List<Note>> getNotes() {
        return db.noteDao().getNotes();
    }

    @Override
    public LiveData<List<Note>> getNotesFromCourse(int id) {
        return db.noteDao().getNotesFromCourse(id);
    }

    @Override
    public LiveData<Note> getNoteById(int id) {
        return db.noteDao().getNoteById(id);
    }

    @Override
    public Completable deleteNote(Note note) {
        return Completable.fromAction(() -> db.noteDao().deleteNote(note));
    }

    @Override
    public Completable updateNote(Note note) {
        return Completable.fromAction(() -> db.noteDao().updateNote(note));
    }

//    @Override
//    public LiveData<List<NoteWithCourse>> getNotesWithCourse(int id) {
//        return db.noteDao().getNotesWithCourse(id);
//    }

}
