package com.wgu.el.wgustudent.injection;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.wgu.el.wgustudent.database.WguDatabase;
import com.wgu.el.wgustudent.repository.WguRepository;
import com.wgu.el.wgustudent.repository.WguRepositoryImpl;
import com.wgu.el.wgustudent.util.WguConst;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WguModule {

    private WguApplication wguApplication;

    public WguModule(WguApplication wguApplication) {
        this.wguApplication = wguApplication;
    }

    @Provides
    Context applicationContext() {
        return wguApplication;
    }

    @Provides
    @Singleton
    WguRepository providesWguRepository(WguDatabase wguDatabase) {
        return new WguRepositoryImpl(wguDatabase);
    }

    @Provides
    @Singleton
    WguDatabase providesWguDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), WguDatabase.class, WguConst.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

}
