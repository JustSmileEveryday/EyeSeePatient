package lht.wangtong.gowin120.patient.ui.service.category;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ServiceDetailInfo;

public interface ServiceDetailContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initBanner();

        void setServiceBanner(List<String> bannerInfos);

        void setServiceDetail(ServiceDetailInfo info);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadServiceDetail(String marketActivityId);

        void saveService(String marketActivityId);

    }

}
