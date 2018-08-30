package lht.wangtong.gowin120.patient.ui.mine.address;

import android.content.Context;

import java.util.ArrayList;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.AddressBean;
import lht.wangtong.gowin120.patient.bean.AddressInfo;

/**
 * @author luoyc
 */
public interface AddAddressContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setAreaList(ArrayList<AddressBean> areaList, ArrayList<ArrayList<String>> options2Items, ArrayList<ArrayList<ArrayList<String>>> options3Items);

        Context getThisContext();

        void setAddress(AddressInfo addressInfo);

        void addSuccess();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void parseData();

        boolean checkData(String receName, String mobilePhone, String street, String addrArea);

        void addAddress(String addrId, String receName, String mobilePhone, String street, String addrArea);
    }

}
