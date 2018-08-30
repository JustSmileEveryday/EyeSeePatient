package lht.wangtong.gowin120.patient.di.module;

import android.app.Service;
import android.content.Context;


import dagger.Module;
import dagger.Provides;
import lht.wangtong.gowin120.patient.di.scope.ContextLife;
import lht.wangtong.gowin120.patient.di.scope.PerService;

/**
 * Created by lw on 2017/1/19.
 */
@Module
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context ProvideServiceContext() {
        return mService;
    }
}
