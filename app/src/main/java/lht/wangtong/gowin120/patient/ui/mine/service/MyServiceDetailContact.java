package lht.wangtong.gowin120.patient.ui.mine.service;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.ServiceRecordInfo;

/**
 * @author luoyc
 */
public interface MyServiceDetailContact extends BaseContract {

    interface View extends BaseContract.BaseView{

        void setServiceDetail(ServiceRecordInfo detail);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void loadService(String serviceRecordId);

    }

}
