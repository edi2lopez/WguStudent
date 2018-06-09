package com.wgu.el.wgustudent.injection;

import android.app.Application;

public class WguApplication extends Application {


    private final WguComponent wguComponent = createWguComponent();

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO Install a Crashlytics tree in production
    }

    protected WguComponent createWguComponent() {
        return DaggerWguComponent.builder()
                .wguModule(new WguModule(this))
                .build();
    }

    public WguComponent getWguComponent() {
        return wguComponent;
    }

}
