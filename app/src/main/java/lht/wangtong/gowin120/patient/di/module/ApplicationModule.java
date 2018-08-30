package lht.wangtong.gowin120.patient.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.di.scope.ContextLife;
import lht.wangtong.gowin120.patient.di.scope.PerApp;


/**
 * Created by lw on 2017/1/19.
 */
@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
