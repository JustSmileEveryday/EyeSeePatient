package lht.wangtong.gowin120.patient.ui.service.category;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;

/**
 * @author luoyc
 * @date 2018/4/2
 */

public interface ServiceCategoryContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setServiceList(List<ServiceCategoryInfo> serviceList, int total);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData(String serviceType);

        void loadServiceList(String serviceType);

        void onRresh(String serviceType);

        void loadMore(String serviceType);

    }


}
