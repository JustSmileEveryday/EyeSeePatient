package lht.wangtong.gowin120.patient.ui.mine.service;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.MyServiceInfo;

public interface MyServiceContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setServices(List<MyServiceInfo> myServiceInfos,int total);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void loadMyServices();

        void onRresh();

        void loadMore();

    }

}
