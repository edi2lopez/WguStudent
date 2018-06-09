package com.wgu.el.wgustudent.ui.note.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.entity.NoteWithCourse;
import com.wgu.el.wgustudent.injection.WguComponent;
import com.wgu.el.wgustudent.repository.WguRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NotesListViewModel extends ViewModel implements WguComponent.Injectable {

    private static final String TAG = "NotesListViewModel";
    private int courseId;

    @Inject
    WguRepository wguRepository;

    @Override
    public void inject(WguComponent wguComponent) {
        wguComponent.inject(this);
        notes = wguRepository.getNotes();
        notesFromCourse = wguRepository.getNotesFromCourse(courseId);
    }

    private LiveData<List<Note>> notes = new MutableLiveData<>();

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void deleteNote(Note note) {
        wguRepository.deleteNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete - deleted note :");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "OnError - throwable :");
                    }
                });
    }

    public int getCourseId() { return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId;  }

    private LiveData<List<Note>> notesFromCourse = new MutableLiveData<>();

    public LiveData<List<Note>> getNotesFromCourse(int id) {
        return wguRepository.getNotesFromCourse(id);
    }

    // TODO show associated course in list, not implemented for this assignment
//    private LiveData<List<NoteWithCourse>> notesWithCourse = new MutableLiveData<>();
//
//    public LiveData<List<NoteWithCourse>> getNotesWithCourse(int id) {
//        return wguRepository.getNotesWithCourse(id);
//    }
    
}
