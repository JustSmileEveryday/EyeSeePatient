package lht.wangtong.gowin120.patient.ui.home.data;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.EyeDataInfo;
import lht.wangtong.gowin120.patient.bean.LatestEyeData;
import lht.wangtong.gowin120.patient.bean.ServiceDataInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDetailType;
import lht.wangtong.gowin120.patient.bean.TabEntity;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/22
 */

public class EyeHealthDataPresenter extends BasePresenter<EyeHealthDataContact.View> implements EyeHealthDataContact.Presenter {

    @Inject
    public EyeHealthDataPresenter() {
    }

    @Override
    public void initData() {
        loadDetectionTab();
        loadEyeTab();
        loadServiceTab();
    }

    @Override
    public void loadDetectionTab() {
        String[] mDetections = {"视力测试", "色盲检测", "散光测试", "黄斑测试"};
        ArrayList<CustomTabEntity> mTabs = new ArrayList<>();
        for (int i = 0; i < mDetections.length; i++) {
            mTabs.add(new TabEntity(mDetections[i]));
        }
        mView.setDetectionTab(mTabs);
    }

    @Override
    public void loadEyeTab() {
        String[] mEyes = {"左眼", "右眼"};
        ArrayList<CustomTabEntity> mTabs = new ArrayList<>();
        for (int i = 0; i < mEyes.length; i++) {
            mTabs.add(new TabEntity(mEyes[i]));
        }
        mView.setEyeTab(mTabs);
    }

    @Override
    public void loadServiceTab() {
        HttpUtil.getObjectList(Api.queryReservationTypeList.mapClear()
                .addMap("type", "1")
                .addMap("status", "1")
                .addBody(), ServiceDetailType.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<ServiceDetailType> serviceDetailTypes = new ArrayList<>();
                    serviceDetailTypes.addAll((Collection<? extends ServiceDetailType>) obj);
                    ArrayList<CustomTabEntity> mTabs = new ArrayList<>();
                    for (int i = 0; i < serviceDetailTypes.size(); i++) {
                        mTabs.add(new TabEntity(serviceDetailTypes.get(i).getName()));
                    }
                    mView.setServiceTab(mTabs, serviceDetailTypes);
                }
            }
        });
    }

    @Override
    public void loadEyeHealthData(int dataType, String testDateStart, String testDateEnd, String familyId) {
        HttpUtil.getObjectList(Api.getEyeHealthData.mapClear()
                .addMap("dataType", dataType)
                .addMap("testDateStart", testDateStart)
                .addMap("testDateEnd", testDateEnd)
                .addMap("familyId", familyId)
                .addBody(), EyeDataInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<EyeDataInfo> infos = new ArrayList<>();
                    infos.addAll((Collection<? extends EyeDataInfo>) obj != null ? (Collection<? extends EyeDataInfo>) obj : null);
                    mView.setEyeHealthData(infos);
                }
            }
        });
    }

    @Override
    public void loadLatestEyeData(String familyId) {
        HttpUtil.getObject(Api.getNewestEyeHealthData.mapClear()
                .addMap("familyId", familyId)
                .addBody(), LatestEyeData.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setLatestEyeData((LatestEyeData) obj);
                }
            }
        });
    }

    @Override
    public void loadServiceData(final String reservationTypeId, String reservationServiceDateStart, String reservationServiceDateEnd, String familyId) {
        HttpUtil.getObjectList(Api.getReservationServiceData.mapClear()
                .addMap("reservationTypeId", reservationTypeId)
                .addMap("familyId", familyId)
                .addMap("reservationServiceDateStart", reservationServiceDateStart)
                .addMap("reservationServiceDateEnd", reservationServiceDateEnd)
                .addBody(), ServiceDataInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<ServiceDataInfo> dataInfos = new ArrayList<>();
                    dataInfos.addAll((Collection<? extends ServiceDataInfo>) obj != null ? (Collection<? extends ServiceDataInfo>) obj : null);
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    for (int i = 0; i < dataInfos.size() - 1; i++) {
                        dataInfos.get(i).setDay(TimeUtils.getFitTimeSpan(dataInfos.get(i + 1).getReservationServiceDate(), dataInfos.get(i).getReservationServiceDate(), dateFormat, 1) + "");
                    }
                    mView.setServiceData(dataInfos, reservationTypeId);
                }
            }
        });
    }
}
