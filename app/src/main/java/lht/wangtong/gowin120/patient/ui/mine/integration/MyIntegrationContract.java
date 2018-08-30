package lht.wangtong.gowin120.patient.ui.mine.integration;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.IntegrationInfo;

/**
 * @author luoyc
 */
public interface MyIntegrationContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setIntegrationNum(String num);

        void setIntegrationList(List<IntegrationInfo> intergrationList, int total);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void getMemberInfo();

        void getIntegration();

        void onRefresh();

        void onLoadMore();

    }

}
