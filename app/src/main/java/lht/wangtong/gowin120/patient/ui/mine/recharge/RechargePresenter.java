package lht.wangtong.gowin120.patient.ui.mine.recharge;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.apppay.alipay.AlipayInfo;
import lht.wangtong.gowin120.patient.apppay.weixinpay.WeiXinPayInfo;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.PayTypeInfo;
import lht.wangtong.gowin120.patient.bean.RechargeNumInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class RechargePresenter extends BasePresenter<RechargeContract.View> implements RechargeContract.Presenter {

    @Inject
    public RechargePresenter() {
    }

    @Override
    public void initData() {
        getRechargeList();
        getPayType();
    }

    @Override
    public void getRechargeList() {
        HttpUtil.getObjectList(Api.queryRechargeIntegral.mapClear()
                .addBody(), RechargeNumInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<RechargeNumInfo> rechargeNumInfos = new ArrayList<>();
                    rechargeNumInfos.addAll(obj != null ? (List<RechargeNumInfo>) obj : null);
                    mView.setRechargeList(rechargeNumInfos);
                }
            }
        });
    }

    @Override
    public void getPayType() {
        HttpUtil.getObjectList(Api.getAppPaymentType.mapClear()
                .addBody(), PayTypeInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<PayTypeInfo> payTypeInfos = new ArrayList<>();
                    payTypeInfos.addAll(obj != null ? (List<PayTypeInfo>) obj : null);
                    mView.setPayType(payTypeInfos);
                }
            }
        });
    }

    @Override
    public void getWeiXinPayParms(String rechargeIntegralId, String payCode) {
        HttpUtil.getObject(Api.appToRecharge.mapClear()
                .addMap("rechargeIntegralId", rechargeIntegralId)
                .addMap("code", payCode)
                .addBody(), WeiXinPayInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.weixinPay((WeiXinPayInfo) obj);
                }
            }
        });
    }

    @Override
    public void getAliPayParms(String rechargeIntegralId, String payCode) {
        HttpUtil.getObject(Api.appToRecharge.mapClear()
                .addMap("rechargeIntegralId", rechargeIntegralId)
                .addMap("code", payCode)
                .addBody(), AlipayInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.aliPay((AlipayInfo) obj);
                }
            }
        });
    }
}
