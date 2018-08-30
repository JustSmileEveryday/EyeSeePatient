package lht.wangtong.gowin120.patient.ui.home.dynamic;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.MessageInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;

/**
 * @author luoyc
 * @date 2018/3/27
 */

public interface MyDynamicContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setMessage(List<MessageInfo> messageInfos, int total);

        void skipReportDetail(ReportInfo reportInfo);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void loadMessage();

        void loadMore();

        void onRefresh();

        void loadReportDetail(String radiographScreenId);

    }
}
