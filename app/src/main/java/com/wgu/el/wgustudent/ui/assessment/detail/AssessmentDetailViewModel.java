package com.wgu.el.wgustudent.ui.assessment.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wgu.el.wgustudent.database.AssessmentType;
import com.wgu.el.wgustudent.entity.Assessment;
import com.wgu.el.wgustudent.injection.WguComponent;
import com.wgu.el.wgustudent.repository.WguRepository;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AssessmentDetailViewModel extends ViewModel implements WguComponent.Injectable {

    private static final String TAG = "AssessmentDetailViewMod";
    
    private Assessment assessment;
    private String assessmentName;
    private Date assessmentDate;
    private boolean reminder;
    private AssessmentType assessmentType;
    private int courseId;

    @Inject
    WguRepository wguRepository;

    @Override
    public void inject(WguComponent wguComponent) {
        wguComponent.inject(this);
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void addAssessment() {
        assessment = new Assessment(0, assessmentName, assessmentType, assessmentDate, reminder, courseId);
        wguRepository.addAssessment(assessment).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() { Log.d(TAG, "Assessment Added:"); }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error thrown Assessment Added:");
                    }
                });
    }

    public LiveData<Assessment> getAssessmentById(int id) {
        return wguRepository.getAssessmentById(id);
    }

    public void updateAssessment(Assessment assessment) {
        this.assessment = assessment;
        assessment.setAssessmentName(assessmentName);
        assessment.setAssessmentDate(assessmentDate);
        assessment.setReminder(reminder) ;
        assessment.setAssessmentType(assessmentType);
//        assessment.setCourseId(courseId);
        wguRepository.updateAssessment(assessment).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Assessments Updated:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error thrown Assessments Updated:");
                    }
                });
    }
    
}