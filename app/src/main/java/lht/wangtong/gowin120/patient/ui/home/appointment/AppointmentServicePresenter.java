package lht.wangtong.gowin120.patient.ui.home.appointment;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.AgentInfo;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDayInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDetailType;
import lht.wangtong.gowin120.patient.bean.ServiceTimeInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.CommonInfoUtils;

/**
 * @author luoyc
 * @date 2018/3/15
 */

public class AppointmentServicePresenter extends BasePresenter<AppointmentServiceContact.View> implements AppointmentServiceContact.Presenter {


    @Inject
    public AppointmentServicePresenter() {
    }

    @Override
    public void initData() {
        loadServiceType();
        loadDate();
        loadAgent();
        loadHomeFamilys();
    }

    @Override
    public void loadServiceType() {
        List<CommonCdInfo> serviceTypeInfos = new ArrayList<>();
        serviceTypeInfos.addAll(CommonInfoUtils.getCommonCdInfo("ChargeServiceType"));
        mView.setServiceType(serviceTypeInfos);
    }


    @Override
    public void loadServiceTypeDetail(String type) {
        HttpUtil.getObjectList(Api.queryReservationTypeList.mapClear()
                .addMap("type", type)
                .addMap("status", "1")
                .addBody(), ServiceDetailType.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<ServiceDetailType> serviceDetailTypes = new ArrayList<>();
                    serviceDetailTypes.addAll((Collection<? extends ServiceDetailType>) obj);
                    mView.setServiceTypeDetail(serviceDetailTypes);
                }
            }
        });
    }

    @Override
    public void loadDate() {
        //时间具体钟点
        List<ServiceTimeInfo> timeInfos = new ArrayList<>();
        //前后日期差值
        int timeSize = 10;
        for (int i = 0; i < timeSize; i++) {
            ServiceTimeInfo timeInfo = new ServiceTimeInfo((9 + i) + ":00", false);
            timeInfos.add(timeInfo);
        }
        List<ServiceDayInfo> dayInfoList = new ArrayList<>();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //当前日期
        Calendar c = Calendar.getInstance();
        //不加入今天
//        String dateInfo = TimeUtils.getNowString(dateFormat);
//        String weekInfo = TimeUtils.getChineseWeek(TimeUtils.getNowMills());
//        String dayInfo = c.get(Calendar.DAY_OF_MONTH) + "";
//        ServiceDayInfo nowDay = new ServiceDayInfo(dayInfo, weekInfo, dateInfo, timeInfos, true);
//        dayInfoList.add(nowDay);
        //前后日期差值
        int daySize = 30;
        for (int i = 1; i < daySize; i++) {
            List<ServiceTimeInfo> timeInfoList = new ArrayList<>();
            for (int j = 0; j < timeSize; j++) {
                ServiceTimeInfo timeInfo = new ServiceTimeInfo((9 + j) + ":00", false);
                timeInfoList.add(timeInfo);
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
            ServiceDayInfo serviceDayInfo = new ServiceDayInfo(c.get(Calendar.DAY_OF_MONTH) + "", TimeUtils.getChineseWeek(c.getTime()), TimeUtils.date2String(c.getTime(), dateFormat), timeInfoList, false);
            if (i == 1) {
                serviceDayInfo.setChoose(true);
            } else {
                serviceDayInfo.setChoose(false);
            }
            dayInfoList.add(serviceDayInfo);
        }
        mView.setDate(dayInfoList);
    }

    @Override
    public void loadTime(List<ServiceDayInfo> serviceDayInfos, int position) {
        mView.setTime(serviceDayInfos.get(position).getServiceTimeInfos());
    }

    @Override
    public void loadAgent() {
        HttpUtil.getObjectList(Api.SearchAgent.mapClear()
                        .addMap("agentName", "")
                        .addMap("isOpenReservationService", "Y")
                        .addBody(), AgentInfo.class, new HttpUtil.objectListCallback() {
                    @Override
                    public void result(boolean b, @Nullable Object obj) {
                        if (b) {
                            List<AgentInfo> agentInfos = new ArrayList<>();
                            agentInfos.addAll((Collection<? extends AgentInfo>) obj != null ? (Collection<? extends AgentInfo>) obj : null);
                            mView.setAgent(agentInfos);
                        }
                    }
                }
        );
    }

    @Override
    public void loadHomeFamilys() {
        HttpUtil.getObjectList(Api.queryFamilyInfo.mapClear()
                .addBody(), HomeFamilyInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<HomeFamilyInfo> familyInfos = new ArrayList<>();
                    familyInfos.addAll((List<HomeFamilyInfo>) obj);
                    mView.setHomeFamilys(familyInfos);
                }
            }
        });
    }

    @Override
    public void saveNetService(String reservationTypeId, String reservationServiceDate, String agentId, String familyId) {
        if (checkNetService(reservationTypeId, reservationServiceDate, agentId)) {
            HttpUtil.getObject(Api.saveReservationService.mapClear()
                    .addMap("reservationTypeId", reservationTypeId)
                    .addMap("reservationServiceDate", reservationServiceDate)
                    .addMap("agentId", agentId)
                    .addMap("familyId", familyId)
                    .addBody(), String.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    if (b) {
                        ToastUtils.showShort("预约成功");
                        mView.getThisContext().finish();
                    }
                }
            });
        }
    }

    @Override
    public boolean checkNetService(String reservationTypeId, String reservationServiceDate, String agentId) {
        if (TextUtils.isEmpty(reservationTypeId)) {
            ToastUtils.showShort("请选择服务类型");
            return false;
        }
        if (TextUtils.isEmpty(reservationServiceDate)) {
            ToastUtils.showShort("请选择预约时间");
            return false;
        }
        if (TextUtils.isEmpty(agentId)) {
            ToastUtils.showShort("请选择预约门店");
            return false;
        }
        return true;
    }
}
