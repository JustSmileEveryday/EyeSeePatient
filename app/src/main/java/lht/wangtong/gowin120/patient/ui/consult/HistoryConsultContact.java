package lht.wangtong.gowin120.patient.ui.consult;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ConsultInfo;

/**
 * @author luoyc
 */
public interface HistoryConsultContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setConsultList(List<ConsultInfo> consultInfos);

        void fuseDatas();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getMemberConsultList();

        void onRefresh();

    }

}
