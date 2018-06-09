package com.wgu.el.wgustudent.ui.term.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wgu.el.wgustudent.entity.Term;
import com.wgu.el.wgustudent.injection.WguComponent;
import com.wgu.el.wgustudent.repository.WguRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TermsListViewModel extends ViewModel implements WguComponent.Injectable {

    private static final String TAG = "TermsListViewModel";

    @Inject
    WguRepository wguRepository;

    @Override
    public void inject(WguComponent wguComponent) {
        wguComponent.inject(this);
        terms = wguRepository.getTerms();
    }

    private LiveData<List<Term>> terms = new MutableLiveData<>();

    public LiveData<List<Term>> getTerms() {
        return terms;
    }

    public void deleteTerm(Term term) {
        wguRepository.deleteTerm(term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete - deleted term :");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "OnError - deleted term :");
                    }
                });
    }

}
