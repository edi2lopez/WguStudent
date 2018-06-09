package com.wgu.el.wgustudent.repository;

import android.arch.lifecycle.LiveData;

import com.wgu.el.wgustudent.entity.Assessment;
import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.entity.NoteWithCourse;
import com.wgu.el.wgustudent.entity.Term;

import java.util.List;

import io.reactivex.Completable;

public interface WguRepository {

    Completable addTerm(Term term);
    LiveData<List<Term>> getTerms();
    LiveData<Term> getTermById(int id);
    Completable deleteTerm(Term term);
    Completable updateTerm(Term term);

    Completable addCourse(Course course);
    LiveData<List<Course>> getCourses();
    LiveData<List<Course>> getCoursesFromTerm(int id);
    LiveData<Course> getCourseById(int id);
    Completable deleteCourse(Course course);
    Completable updateCourse(Course course);

    Completable addAssessment(Assessment assessment);
    LiveData<List<Assessment>> getAssessments();
    LiveData<List<Assessment>> getAssessmentsFromCourse(int id);
    LiveData<Assessment> getAssessmentById(int id);
    Completable deleteAssessment(Assessment assessment);
    Completable updateAssessment(Assessment assessment);

    Completable addNote(Note note);
    LiveData<List<Note>> getNotes();
    LiveData<List<Note>> getNotesFromCourse(int id);
    LiveData<Note> getNoteById(int id);
    Completable deleteNote(Note note);
    Completable updateNote(Note note);

//    LiveData<List<NoteWithCourse>> getNotesWithCourse(int id);

}
