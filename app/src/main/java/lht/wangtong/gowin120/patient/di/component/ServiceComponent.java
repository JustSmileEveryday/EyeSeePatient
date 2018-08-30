package lht.wangtong.gowin120.patient.di.component;

import android.content.Context;

import dagger.Component;
import lht.wangtong.gowin120.patient.di.module.ServiceModule;
import lht.wangtong.gowin120.patient.di.scope.ContextLife;
import lht.wangtong.gowin120.patient.di.scope.PerService;


/**
 * Created by lw on 2017/1/19.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
