package com.wgu.el.wgustudent.ui.course.list;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.injection.WguComponent;
import com.wgu.el.wgustudent.repository.WguRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoursesListViewModel extends ViewModel implements WguComponent.Injectable {

    private static final String TAG = "CoursesListViewModel";

//    private Course course;
    private int termId;

    @Inject
    WguRepository wguRepository;

    @Override
    public void inject(WguComponent wguComponent) {
        wguComponent.inject(this);
        courses = wguRepository.getCourses();
        coursesFromTerm = wguRepository.getCoursesFromTerm(termId);
    }

    private LiveData<List<Course>> courses = new MutableLiveData<>();

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public void deleteCourse(Course course) {
        wguRepository.deleteCourse(course)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete - deleted course :");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "OnError - throwable :");
                    }
                });
    }

    public int getTermId() { return termId; }

    public void setTermId(int termId) { this.termId = termId;  }

    private LiveData<List<Course>> coursesFromTerm = new MutableLiveData<>();

    public LiveData<List<Course>> getCoursesFromTerm(int id) {
        return wguRepository.getCoursesFromTerm(id);
    }

}
