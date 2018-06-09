package com.wgu.el.wgustudent.injection;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class WguFactory  extends ViewModelProvider.NewInstanceFactory {

    private WguApplication application;

    public WguFactory(WguApplication application) { this.application = application; }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof WguComponent.Injectable) {
            ((WguComponent.Injectable) t).inject(application.getWguComponent());
        }
        return t;
    }

}
