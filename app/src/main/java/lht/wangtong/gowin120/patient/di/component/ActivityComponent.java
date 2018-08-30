package lht.wangtong.gowin120.patient.di.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import lht.wangtong.gowin120.patient.di.module.ActivityModule;
import lht.wangtong.gowin120.patient.di.scope.ContextLife;
import lht.wangtong.gowin120.patient.di.scope.PerActivity;
import lht.wangtong.gowin120.patient.ui.consult.chat.AutoChatActivity;
import lht.wangtong.gowin120.patient.ui.consult.doctor.CommentActivity;
import lht.wangtong.gowin120.patient.ui.consult.doctor.DoctorActivity;
import lht.wangtong.gowin120.patient.ui.consult.supposeask.SupposeDetailActivity;
import lht.wangtong.gowin120.patient.ui.home.appointment.AppointmentServiceActivity;
import lht.wangtong.gowin120.patient.ui.home.classroom.ClassroomListActivity;
import lht.wangtong.gowin120.patient.ui.home.classroom.CourseDetailActivity;
import lht.wangtong.gowin120.patient.ui.home.data.EyeHealthDataActivity;
import lht.wangtong.gowin120.patient.ui.home.detection.DetectionCompleteActivity;
import lht.wangtong.gowin120.patient.ui.home.detection.EyeDetectionActivity;
import lht.wangtong.gowin120.patient.ui.home.dynamic.MyDynamicActivity;
import lht.wangtong.gowin120.patient.ui.home.plan.AddPlanActivity;
import lht.wangtong.gowin120.patient.ui.home.plan.ProtectEyePlanActivity;
import lht.wangtong.gowin120.patient.ui.home.prevention.EyePreventionActivity;
import lht.wangtong.gowin120.patient.ui.home.store.StoreActivity;
import lht.wangtong.gowin120.patient.ui.home.surveyeye.SurveyEyesActivity;
import lht.wangtong.gowin120.patient.ui.home.surveyeye.SurveyResultActivity;
import lht.wangtong.gowin120.patient.ui.home.tryglasses.TryGlassesActivity;
import lht.wangtong.gowin120.patient.ui.login.AuthLoginActivity;
import lht.wangtong.gowin120.patient.ui.login.LoginActivity;
import lht.wangtong.gowin120.patient.ui.login.RegisterActivity;
import lht.wangtong.gowin120.patient.ui.mine.address.AddAddressActivity;
import lht.wangtong.gowin120.patient.ui.mine.address.MyAddressActivity;
import lht.wangtong.gowin120.patient.ui.mine.appointment.AppointmentActivity;
import lht.wangtong.gowin120.patient.ui.mine.appointment.AppointmentDetailActivity;
import lht.wangtong.gowin120.patient.ui.mine.balance.BalanceActivity;
import lht.wangtong.gowin120.patient.ui.mine.balance.BalanceDetailActivity;
import lht.wangtong.gowin120.patient.ui.mine.balance.MyBalanceActivity;
import lht.wangtong.gowin120.patient.ui.mine.collection.MyCollectionActivity;
import lht.wangtong.gowin120.patient.ui.mine.coupons.MyCouponsActivity;
import lht.wangtong.gowin120.patient.ui.mine.coupons.ReceiveCouponActivity;
import lht.wangtong.gowin120.patient.ui.mine.family.FamilyInfoActivity;
import lht.wangtong.gowin120.patient.ui.mine.family.MyFamilyActivity;
import lht.wangtong.gowin120.patient.ui.mine.info.MineInfoActivity;
import lht.wangtong.gowin120.patient.ui.mine.info.UserHeadActivity;
import lht.wangtong.gowin120.patient.ui.mine.integration.MyIntegrationActivity;
import lht.wangtong.gowin120.patient.ui.mine.recharge.RechargeActivity;
import lht.wangtong.gowin120.patient.ui.mine.recharge.RechargeSuccActivity;
import lht.wangtong.gowin120.patient.ui.mine.report.ApplyReportActivity;
import lht.wangtong.gowin120.patient.ui.mine.report.ReportCenterActivity;
import lht.wangtong.gowin120.patient.ui.mine.report.ReportDetailActivity;
import lht.wangtong.gowin120.patient.ui.mine.service.MyServiceActivity;
import lht.wangtong.gowin120.patient.ui.mine.service.MyServiceDetailActivity;
import lht.wangtong.gowin120.patient.ui.mine.setting.SettingActivity;
import lht.wangtong.gowin120.patient.ui.mine.setting.about.AboutActivity;
import lht.wangtong.gowin120.patient.ui.mine.setting.agreement.AgreementActivity;
import lht.wangtong.gowin120.patient.ui.mine.setting.feedback.FeedbackActivity;
import lht.wangtong.gowin120.patient.ui.mine.setting.reset.ResetPasswordActivity;
import lht.wangtong.gowin120.patient.ui.science.category.CategoryDetailActivity;
import lht.wangtong.gowin120.patient.ui.search.SearchActivity;
import lht.wangtong.gowin120.patient.ui.service.category.ServiceDetailActivity;
import lht.wangtong.gowin120.patient.ui.service.order.OrderActivity;
import lht.wangtong.gowin120.patient.ui.splash.SplashActivity;

/**
 * @author luoyc
 * @date 2017/1/19
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();


    void inject(MineInfoActivity activity);

    void inject(UserHeadActivity activity);

    void inject(ReportCenterActivity activity);

    void inject(ReportDetailActivity activity);

    void inject(AppointmentActivity activity);

    void inject(AppointmentDetailActivity activity);

    void inject(MyCollectionActivity activity);

    void inject(MyFamilyActivity activity);

    void inject(FamilyInfoActivity activity);

    void inject(AppointmentServiceActivity activity);

    void inject(EyeDetectionActivity activity);

    void inject(DetectionCompleteActivity activity);

    void inject(EyePreventionActivity activity);

    void inject(ProtectEyePlanActivity activity);

    void inject(AddPlanActivity activity);

    void inject(EyeHealthDataActivity activity);

    void inject(SettingActivity activity);

    void inject(FeedbackActivity activity);

    void inject(AboutActivity activity);

    void inject(AgreementActivity activity);

    void inject(ResetPasswordActivity activity);

    void inject(LoginActivity activity);

    void inject(MyDynamicActivity activity);

    void inject(RegisterActivity activity);

    void inject(SplashActivity activity);

    void inject(CategoryDetailActivity activity);

    void inject(ServiceDetailActivity activity);

    void inject(OrderActivity activity);

    void inject(MyServiceActivity activity);

    void inject(MyServiceDetailActivity activity);

    void inject(DoctorActivity activity);

    void inject(CommentActivity activity);

    void inject(AutoChatActivity activity);

    void inject(SupposeDetailActivity activity);

    void inject(lht.wangtong.gowin120.patient.ui.science.category.CommentActivity activity);

    void inject(SearchActivity activity);

    void inject(ClassroomListActivity activity);

    void inject(CourseDetailActivity activity);

    void inject(MyBalanceActivity activity);

    void inject(BalanceActivity activity);

    void inject(BalanceDetailActivity activity);

    void inject(RechargeActivity activity);

    void inject(RechargeSuccActivity activity);

    void inject(MyIntegrationActivity activity);

    void inject(MyCouponsActivity activity);

    void inject(ReceiveCouponActivity activity);

    void inject(TryGlassesActivity activity);

    void inject(SurveyEyesActivity activity);

    void inject(SurveyResultActivity activity);

    void inject(AuthLoginActivity activity);

    void inject(MyAddressActivity activity);

    void inject(AddAddressActivity activity);

    void inject(ApplyReportActivity activity);

    void inject(StoreActivity activity);
}
