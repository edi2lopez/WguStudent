package com.wgu.el.wgustudent.ui.assessment.list;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wgu.el.wgustudent.entity.Assessment;
import com.wgu.el.wgustudent.injection.WguComponent;
import com.wgu.el.wgustudent.repository.WguRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AssessmentsListViewModel extends ViewModel implements WguComponent.Injectable {

    private static final String TAG = "AssessmentsListViewMode";
    private int courseId;

    @Inject
    WguRepository wguRepository;

    @Override
    public void inject(WguComponent wguComponent) {
        wguComponent.inject(this);
        assessments = wguRepository.getAssessments();
        assessmentsFromCourse= wguRepository.getAssessmentsFromCourse(courseId);
    }

    private LiveData<List<Assessment>> assessments = new MutableLiveData<>();

    public LiveData<List<Assessment>> getAssessments() {
        return assessments;
    }

    public void deleteAssessment(Assessment assessment) {
        wguRepository.deleteAssessment(assessment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete - deleted assessment :");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "OnError - throwable delete assessment :");
                    }
                });
    }

    public int getCourseId() { return courseId; }

    public void setCourseId(int assessmentId) { this.courseId = assessmentId;  }

    private LiveData<List<Assessment>> assessmentsFromCourse = new MutableLiveData<>();

    public LiveData<List<Assessment>> getAssessmentsFromCourse(int id) {
        return wguRepository.getAssessmentsFromCourse(id);
    }

}
