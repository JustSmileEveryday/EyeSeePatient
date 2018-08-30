package lht.wangtong.gowin120.patient.ui.mine.address;

import android.support.annotation.Nullable;

import com.vondear.rxtool.view.RxToast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.AddressInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class MyAddressPresenter extends BasePresenter<MyAddressContact.View> implements MyAddressContact.Presenter {

    @Inject
    public MyAddressPresenter() {
    }

    @Override
    public void getAddressList() {
        HttpUtil.getObjectList(Api.getAppMemberAddrList.mapClear()
                .addMap("type", "0")
                .addBody(), AddressInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<AddressInfo> addressInfos = new ArrayList<>();
                    addressInfos.addAll((Collection<? extends AddressInfo>) obj);
                    mView.setAddressList(addressInfos);
                }
            }
        });
    }

    @Override
    public void deleteAddress(String addrId) {
        HttpUtil.getObject(Api.delAppPersonalAddr.mapClear()
                .addMap("addrId", addrId)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    RxToast.success("删除成功");
                    getAddressList();
                }
            }
        });
    }


}
