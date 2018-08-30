package lht.wangtong.gowin120.patient.di.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import lht.wangtong.gowin120.patient.di.module.FragmentModule;
import lht.wangtong.gowin120.patient.di.scope.ContextLife;
import lht.wangtong.gowin120.patient.di.scope.PerFragment;
import lht.wangtong.gowin120.patient.ui.consult.ConsultFragment;
import lht.wangtong.gowin120.patient.ui.consult.HistoryConsultFragment;
import lht.wangtong.gowin120.patient.ui.consult.NewConsultFragment;
import lht.wangtong.gowin120.patient.ui.home.HomeFragment;
import lht.wangtong.gowin120.patient.ui.home.detection.AchromatopsiaFragment;
import lht.wangtong.gowin120.patient.ui.home.detection.AstigmatismFragment;
import lht.wangtong.gowin120.patient.ui.home.detection.MacularFragment;
import lht.wangtong.gowin120.patient.ui.home.detection.VisionTestFragment;
import lht.wangtong.gowin120.patient.ui.mine.MineFragment;
import lht.wangtong.gowin120.patient.ui.science.ScienceFragment;
import lht.wangtong.gowin120.patient.ui.science.category.CategoryFragment;
import lht.wangtong.gowin120.patient.ui.service.ServiceFragment;
import lht.wangtong.gowin120.patient.ui.service.category.ServiceCategoryFragment;

;

/**
 *
 * @author luoyc
 * @date 2017/1/19
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(HomeFragment fragment);

    void inject(ScienceFragment fragment);

    void inject(MineFragment fragment);

    void inject(ConsultFragment fragment);

    void inject(CategoryFragment fragment);

    void inject(VisionTestFragment fragment);

    void inject(AchromatopsiaFragment fragment);

    void inject(ServiceFragment fragment);

    void inject(ServiceCategoryFragment fragment);

    void inject(MacularFragment fragment);

    void inject(AstigmatismFragment fragment);

    void inject(NewConsultFragment fragment);

    void inject(HistoryConsultFragment fragment);
}
