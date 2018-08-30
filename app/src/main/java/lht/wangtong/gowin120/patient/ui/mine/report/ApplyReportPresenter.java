package lht.wangtong.gowin120.patient.ui.mine.report;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.vondear.rxtool.view.RxToast;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class ApplyReportPresenter extends BasePresenter<ApplyReportContact.View> implements ApplyReportContact.Presenter {

    @Inject
    public ApplyReportPresenter() {
    }

    @Override
    public void saveRadiographScreenReportApply(String name, String sex, String age, String mobilePhone, String agentName, String remark, String memberAddrId) {
        if (checkData(name, sex, age, mobilePhone, agentName)) {
            HttpUtil.getObject(Api.saveRadiographScreenReportApply.mapClear()
                    .addMap("name", name)
                    .addMap("sex", sex)
                    .addMap("age", age)
                    .addMap("mobilePhone", mobilePhone)
                    .addMap("agentName", agentName)
                    .addMap("remark", remark)
                    .addMap("memberAddrId", memberAddrId)
                    .addBody(), String.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    if (b) {
                        mView.showSuccess(true);
                    }
                }
            });
        }
    }

    @Override
    public boolean checkData(String name, String sex, String age, String mobilePhone, String agentName) {
        if (TextUtils.isEmpty(name)) {
            RxToast.error("请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(sex)) {
            RxToast.error("请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(age)) {
            RxToast.error("请输入年龄");
            return false;
        }
        if (TextUtils.isEmpty(mobilePhone)) {
            RxToast.error("请输入手机号");
            return false;
        }
        if (TextUtils.isEmpty(agentName)) {
            RxToast.error("请输入筛查点");
            return false;
        }
        return true;
    }

    @Override
    public void queryReportApplyStatus() {
        HttpUtil.getObject(Api.queryReportApplyStatus.mapClear()
                .addBody(), boolean.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    boolean isCanApply = (boolean) obj;
                    if (isCanApply) {
                        mView.showSuccess(false);
                    } else {
                        mView.showSuccess(true);
                    }
                }
            }
        });
    }
}
