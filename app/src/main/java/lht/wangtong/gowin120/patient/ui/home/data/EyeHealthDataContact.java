package lht.wangtong.gowin120.patient.ui.home.data;

import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.PointValue;
import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.EyeDataInfo;
import lht.wangtong.gowin120.patient.bean.LatestEyeData;
import lht.wangtong.gowin120.patient.bean.ServiceDataInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDetailType;

/**
 * @author luoyc
 * @date 2018/3/22
 */

public interface EyeHealthDataContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initChart(List<AxisValue> mAxisValues, List<PointValue> mPointValues);

        void setDetectionTab(ArrayList<CustomTabEntity> detectionTab);

        void setEyeTab(ArrayList<CustomTabEntity> eyeTab);

        void setServiceTab(ArrayList<CustomTabEntity> serviceTab, List<ServiceDetailType> serviceDetailTypes);

        void setEyeHealthData(List<EyeDataInfo> dataList);

        void setServiceData(List<ServiceDataInfo> infos, String reservationTypeId);

        void setLatestEyeData(LatestEyeData eyeData);

        void setInitTime(Date date);

        void showTimePicker(int type);

        void setTestDateStart(Date date);

        void setTestDateEnd(Date date);

        void setServiceDateStart(Date date);

        void setServiceDateEnd(Date date);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void loadDetectionTab();

        void loadEyeTab();

        void loadServiceTab();

        void loadEyeHealthData(int dataType, String testDateStart, String testDateEnd,String familyId);

        void loadLatestEyeData(String familyId);

        void loadServiceData(String reservationTypeId, String reservationServiceDateStart, String reservationServiceDateEnd,String familyId);

    }

}
