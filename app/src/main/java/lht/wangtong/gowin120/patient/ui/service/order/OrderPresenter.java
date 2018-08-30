package lht.wangtong.gowin120.patient.ui.service.order;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.apppay.alipay.AlipayInfo;
import lht.wangtong.gowin120.patient.apppay.weixinpay.WeiXinPayInfo;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.PayTypeInfo;
import lht.wangtong.gowin120.patient.bean.ServiceRecordInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class OrderPresenter extends BasePresenter<OrderContract.View> implements OrderContract.Presenter {

    @Inject
    public OrderPresenter() {
    }

    @Override
    public void initData(String serviceRecordId) {
        loadService(serviceRecordId);
        loadPayType();
    }

    @Override
    public void loadService(String serviceRecordId) {
        HttpUtil.getObject(Api.viewServiceRecord.mapClear()
                .addMap("serviceRecordId", serviceRecordId)
                .addBody(), ServiceRecordInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setServiceInfo((ServiceRecordInfo) obj);
                }
            }
        });
    }

    @Override
    public void loadPayType() {
        HttpUtil.getObjectList(Api.getAppPaymentType.mapClear()
                .addBody(), PayTypeInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<PayTypeInfo> payTypeInfos = new ArrayList<>();
                    payTypeInfos.addAll(obj != null ? (List<PayTypeInfo>) obj : null);
                    PayTypeInfo balance = new PayTypeInfo();
                    balance.setName("余额支付");
                    balance.setTypeFlag(0);
                    balance.setLogo("余额支付");
                    balance.setCode("SYYE");
                    payTypeInfos.add(balance);
                    mView.setPayType(payTypeInfos);
                }
            }
        });
    }

    @Override
    public void balancePay(String serviceRecordId) {
        HttpUtil.getObject(Api.appToPayment.mapClear()
                .addMap("serviceRecordId", serviceRecordId)
                .addMap("code", "SYYE")
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.paySuccess();
                } else {
                    mView.payFailured();
                }
            }
        });
    }

    @Override
    public void getWeiXinPayParms(String serviceRecordId, String payCode) {
        HttpUtil.getObject(Api.appToPayment.mapClear()
                .addMap("serviceRecordId", serviceRecordId)
                .addMap("code", payCode)
                .addBody(), WeiXinPayInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    WeiXinPayInfo info = (WeiXinPayInfo) obj;
                    mView.weixinPay(info);
                }
            }
        });
    }

    @Override
    public void getAliPayParms(String serviceRecordId, String payCode) {
        HttpUtil.getObject(Api.appToPayment.mapClear()
                .addMap("serviceRecordId", serviceRecordId)
                .addMap("code", payCode)
                .addBody(), AlipayInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    AlipayInfo info = (AlipayInfo) obj;
                    mView.aliPay(info);
                }
            }
        });
    }
}
