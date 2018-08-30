package lht.wangtong.gowin120.patient.ui.mine.setting.agreement;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.HomeActivityInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/26
 */

public class AgreementPresenter extends BasePresenter<AgreementContact.View> implements AgreementContact.Presenter {

    @Inject
    public AgreementPresenter() {
    }

    @Override
    public void initData(int type,String activityId) {
        switch (type) {
            case 1:
                //用户注册协议
                mView.setData(1, "file:///android_asset/CloudPatientProtocol.html",null);
                break;
            case 2:
                //患者就诊告知书 协议
                mView.setData(2, "file:///android_asset/patient_notice.html",null);
                break;
            case 3:
                //首页活动
                getActivityInfo(activityId);
                break;
            default:
                break;
        }
    }

    @Override
    public void getActivityInfo(String activityId) {
        HttpUtil.getObject(Api.viewActivityInfo.mapClear()
                .addMap("activityId", activityId)
                .addBody(), HomeActivityInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
               if (b){
                   mView.setData(3, "", (HomeActivityInfo) obj);
               }
            }
        });
    }


}
