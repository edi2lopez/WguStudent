package com.wgu.el.wgustudent.ui.note.detail;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.injection.WguComponent;
import com.wgu.el.wgustudent.repository.WguRepository;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteDetailViewModel  extends ViewModel implements WguComponent.Injectable {

    private static final String TAG = "NoteDetailViewModel";

    private Note note;
    private String noteName;
    private String noteDetail;
    private String notePicture;
    private int courseId;

    @Inject
    WguRepository wguRepository;

    @Override
    public void inject(WguComponent wguComponent) {
        wguComponent.inject(this);
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

    public void addNote() {
        note = new Note(0, noteName, noteDetail, notePicture, courseId);
        wguRepository.addNote(note).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() { Log.d(TAG, "Notes Added:"); }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error thrown Note Added:");
                    }
                });
    }

    public LiveData<Note> getNoteById(int id) {
        return wguRepository.getNoteById(id);
    }

    public void updateNote(Note note) {
        this.note = note;
        note.setNoteName(noteName);
        note.setNoteDetail(noteDetail);
        note.setNotePicture(notePicture);
//        note.setCourseId(courseId);
        wguRepository.updateNote(note).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Note updated:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error thrown note updated:");
                    }
                });
    }
    
}
