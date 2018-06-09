package com.wgu.el.wgustudent.ui.term.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wgu.el.wgustudent.entity.Term;
import com.wgu.el.wgustudent.injection.WguComponent;
import com.wgu.el.wgustudent.repository.WguRepository;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TermDetailViewModel extends ViewModel implements WguComponent.Injectable {

    private static final String TAG = "TermDetailViewModel";

    private Term term;
    private String termName;
    private Date termStartDate, termEndDate;

    @Inject
    WguRepository wguRepository;

    @Override
    public void inject(WguComponent wguComponent) {
        wguComponent.inject(this);
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(Date termStartDate) { this.termStartDate = termStartDate; }

    public Date getTermEndDate() { return termEndDate; }

    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }

    public void addTerm() {
        term = new Term(0, termName, termStartDate, termEndDate);
        wguRepository.addTerm(term).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Terms Changed:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error thrown:");
                    }
                });
    }

    public LiveData<Term> getTermById(int id) {
        return wguRepository.getTermById(id);
    }

    public void updateTerm(Term term) {
        this.term = term;
        term.setTermName(termName);
        term.setTermStartDate(termStartDate);
        term.setTermEndDate(termEndDate);
        wguRepository.updateTerm(term).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Terms Changed:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error thrown:");
                    }
                });
    }

}
