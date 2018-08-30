package lht.wangtong.gowin120.patient.di.module;

import android.app.Activity;
import android.content.Context;


import dagger.Module;
import dagger.Provides;
import lht.wangtong.gowin120.patient.di.scope.ContextLife;
import lht.wangtong.gowin120.patient.di.scope.PerActivity;

/**
 * Created by lw on 2017/1/19.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
