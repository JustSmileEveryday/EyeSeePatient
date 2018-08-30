package lht.wangtong.gowin120.patient.ui.mine.address;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.AddressInfo;

/**
 * @author luoyc
 */
public interface MyAddressContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setAddressList(List<AddressInfo> addressInfos);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getAddressList();

        void deleteAddress(String addrId);

    }

}
