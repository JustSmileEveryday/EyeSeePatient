package lht.wangtong.gowin120.patient.ui.home.appointment;

import android.app.Activity;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.AgentInfo;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDayInfo;
import lht.wangtong.gowin120.patient.bean.ServiceDetailType;
import lht.wangtong.gowin120.patient.bean.ServiceTimeInfo;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;

/**
 * @author luoyc
 * @date 2018/3/15
 */

public interface AppointmentServiceContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setServiceType(List<CommonCdInfo> serviceTypeInfos);

        void setServiceTypeDetail(List<ServiceDetailType> detailTypeList);

        void initItemClick();

        void setDate(List<ServiceDayInfo> serviceDayInfos);

        void setTime(List<ServiceTimeInfo> serviceTimeInfos);

        void setAgent(List<AgentInfo> agentInfoList);

        void setHomeFamilys(List<HomeFamilyInfo> familyInfos);

        void saveService();

        Activity getThisContext();

        void showDetailDialog(String title, String content);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void loadServiceType();

        void loadServiceTypeDetail(String type);

        void loadDate();

        void loadTime(List<ServiceDayInfo> serviceDayInfos, int position);

        void loadAgent();

        void loadHomeFamilys();

        void saveNetService(String reservationTypeId, String reservationServiceDate, String agentId, String familyId);

        boolean checkNetService(String reservationTypeId, String reservationServiceDate, String agentId);

    }

}
