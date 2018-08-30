package lht.wangtong.gowin120.patient.ui.consult;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.ConsultEmployeeInfo;
import lht.wangtong.gowin120.patient.bean.IllnessQuestionInfoId;

/**
 * @author luoyc
 */
public interface NewConsultContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setSupposeList(List<IllnessQuestionInfoId> supposeList, int total);

        void setAutoConsult(List<BannerInfo> bannerInfos);

        void skipChatActivity(String consultEmployeeType, ConsultEmployeeInfo employeeInfo);

        void showGuide();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void loadSupposeList();

        void getConsult();

        void getConsultEmployeeInfo(String consultEmployeeType);

        void onRefresh();

        void loadMore();

    }

}
