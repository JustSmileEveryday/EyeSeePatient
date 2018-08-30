package lht.wangtong.gowin120.patient.di.component;

import android.content.Context;

import dagger.Component;
import lht.wangtong.gowin120.patient.di.module.ApplicationModule;
import lht.wangtong.gowin120.patient.di.scope.ContextLife;
import lht.wangtong.gowin120.patient.di.scope.PerApp;


/**
 * Created by lw on 2017/1/19.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}