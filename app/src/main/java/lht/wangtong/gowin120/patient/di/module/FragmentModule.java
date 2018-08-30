package lht.wangtong.gowin120.patient.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;


import dagger.Module;
import dagger.Provides;
import lht.wangtong.gowin120.patient.di.scope.ContextLife;
import lht.wangtong.gowin120.patient.di.scope.PerFragment;

/**
 *
 * @author luoyc
 * @date 2017/1/19
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
