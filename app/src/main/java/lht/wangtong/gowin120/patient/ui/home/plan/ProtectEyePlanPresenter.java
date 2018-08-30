package lht.wangtong.gowin120.patient.ui.home.plan;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.PlanScheduleInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/21
 */

public class ProtectEyePlanPresenter extends BasePresenter<ProtectEyePlanContact.View> implements ProtectEyePlanContact.Presenter {

    @Inject
    public ProtectEyePlanPresenter() {
    }

    @Override
    public void loadPlanList(String familyId) {
        HttpUtil.getObjectList(Api.getPlanScheduleList.mapClear()
                .addMap("familyId", familyId)
                .addBody(), PlanScheduleInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    List<PlanScheduleInfo> planScheduleInfos = new ArrayList<>();
                    planScheduleInfos.addAll((Collection<? extends PlanScheduleInfo>) obj != null ? (Collection<? extends PlanScheduleInfo>) obj : null);
                    mView.setPlanList(planScheduleInfos);
                }
            }
        });
    }

    @Override
    public void changePlanScheduleStatus(String planScheduleId) {
        HttpUtil.getObject(Api.changePlanScheduleStatus.mapClear()
                .addMap("planScheduleId", planScheduleId)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){

                }
            }
        });
    }
}
