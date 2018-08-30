package lht.wangtong.gowin120.patient.ui.home.plan;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.PlanScheduleDetailInfo;
import lht.wangtong.gowin120.patient.bean.WeekInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author Administrator
 * @date 2018/3/22
 */

public class AddPlanPresenter extends BasePresenter<AddPlanContact.View> implements AddPlanContact.Presenter {

    @Inject
    public AddPlanPresenter() {
    }

    @Override
    public void initData(String mPlanScheduleId) {
        loadWeekList(mPlanScheduleId);
    }

    @Override
    public void loadWeekList(String mPlanScheduleId) {
        WeekInfo weekInfo = new WeekInfo("星期日", false, "N");
        WeekInfo weekInfo1 = new WeekInfo("星期一", false, "N");
        WeekInfo weekInfo2 = new WeekInfo("星期二", false, "N");
        WeekInfo weekInfo3 = new WeekInfo("星期三", false, "N");
        WeekInfo weekInfo4 = new WeekInfo("星期四", false, "N");
        WeekInfo weekInfo5 = new WeekInfo("星期五", false, "N");
        WeekInfo weekInfo6 = new WeekInfo("星期六", false, "N");
        List<WeekInfo> weekInfos = new ArrayList<>();
        weekInfos.add(weekInfo);
        weekInfos.add(weekInfo1);
        weekInfos.add(weekInfo2);
        weekInfos.add(weekInfo3);
        weekInfos.add(weekInfo4);
        weekInfos.add(weekInfo5);
        weekInfos.add(weekInfo6);
        if (mPlanScheduleId != null) {
            //修改
            loadPlanSchedule(mPlanScheduleId, weekInfos);
        } else {
            //新增
            mView.setWeekList(weekInfos);
        }
    }

    @Override
    public void updatePlanSchedule(String planScheduleId, String familyId, String name, String remindDate, String[] remindValue) {
        if (checkData(name, remindDate, remindValue)) {
            if (planScheduleId == null) {
                planScheduleId = "";
            }
            HttpUtil.getObject(Api.updatePlanSchedule.mapClear()
                    .addMap("familyId", familyId)
                    .addMap("planScheduleId", planScheduleId)
                    .addMap("name", name)
                    .addMap("remindDate", remindDate)
                    .addMap("remindValue", remindValue)
                    .addBody(), String.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    if (b) {
                        ToastUtils.showShort("保存成功");
                        mView.finishActivity();
                    }
                }
            });
        }
    }

    @Override
    public boolean checkData(String name, String remindDate, String[] remindValue) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请填写计划名称");
            return false;
        }
        if (TextUtils.isEmpty(remindDate)) {
            ToastUtils.showShort("请选择计划提醒时间");
            return false;
        }
        boolean isChoose = false;
        for (int i = 0; i < remindValue.length; i++) {
            if (TextUtils.equals(remindValue[i], "Y")) {
                isChoose = true;
            }
        }
        if (!isChoose) {
            return false;
        }
        return true;
    }

    @Override
    public void loadPlanSchedule(String planScheduleId, final List<WeekInfo> infos) {
        HttpUtil.getObject(Api.viewPlanSchedule.mapClear()
                .addMap("planScheduleId", planScheduleId)
                .addBody(), PlanScheduleDetailInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    PlanScheduleDetailInfo detailInfo = (PlanScheduleDetailInfo) obj;
                    for (int i = 0; i < detailInfo.getRemindValue().size(); i++) {
                        if (TextUtils.equals("Y", detailInfo.getRemindValue().get(i))) {
                            infos.get(i).setChoosed(true);
                        }
                    }
                    mView.setPlanInfo(infos,detailInfo);
                }
            }
        });
    }
}
