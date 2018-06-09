package com.wgu.el.wgustudent.ui.course.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wgu.el.wgustudent.database.Status;
import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.injection.WguComponent;
import com.wgu.el.wgustudent.repository.WguRepository;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CourseDetailViewModel extends ViewModel implements WguComponent.Injectable {

    private static final String TAG = "CourseDetailViewModel";

    private Course course;
    private String courseName, mentorName, mentorEmail, mentorPhone;
    private Date courseStartDate, courseEndDate;
    private boolean courseStartDateReminder, courseEndDateReminder;
    private Status status;
    private int termId;

    @Inject
    WguRepository wguRepository;

    @Override
    public void inject(WguComponent wguComponent) {
        wguComponent.inject(this);
    }

    public Course getCourse() {  return course;  }

    public void setCourse(Course course) { this.course = course;  }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStartDate() { return courseStartDate;   }

    public void setCourseStartDate(Date courseStartDate) { this.courseStartDate = courseStartDate; }

    public Date getCourseEndDate() { return courseEndDate; }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public boolean isCourseStartDateReminder() {return courseStartDateReminder; }

    public void setCourseStartDateReminder(boolean courseStartDateReminder) { this.courseStartDateReminder = courseStartDateReminder;  }

    public boolean isCourseEndDateReminder() {return courseEndDateReminder; }

    public void setCourseEndDateReminder(boolean courseEndDateReminder) { this.courseEndDateReminder = courseEndDateReminder;  }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status;  }

    public int getTermId() { return termId; }

    public void setTermId(int termId) { this.termId = termId;  }

    public void addCourse() {
        course = new Course(0, courseName, courseStartDate,
                courseEndDate, courseStartDateReminder, courseEndDateReminder, status, mentorName, mentorEmail, mentorPhone, termId);
        wguRepository.addCourse(course).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() { Log.d(TAG, "Courses Changed:"); }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error thrown:");
                    }
                });
    }

    public LiveData<Course> getCourseById(int id) {
        return wguRepository.getCourseById(id);
    }

    public void updateCourse(Course course) {
        this.course = course;
        course.setCourseName(courseName);
        course.setCourseStartDate(courseStartDate);
        course.setCourseEndDate(courseEndDate);
        course.setCourseStartDateReminder(courseStartDateReminder);
        course.setCourseEndDateReminder(courseEndDateReminder) ;
        course.setMentorName(mentorName);
        course.setMentorEmail(mentorEmail);
        course.setMentorPhone(mentorPhone);
        course.setStatus(status);
        course.setTermId(termId);
        wguRepository.updateCourse(course).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Courses Changed:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error thrown:");
                    }
                });
    }

}
