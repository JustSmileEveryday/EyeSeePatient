package lht.wangtong.gowin120.patient.ui.mine.recharge;

import java.util.List;

import lht.wangtong.gowin120.patient.apppay.alipay.AlipayInfo;
import lht.wangtong.gowin120.patient.apppay.weixinpay.WeiXinPayInfo;
import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.PayTypeInfo;
import lht.wangtong.gowin120.patient.bean.RechargeNumInfo;

/**
 * @author luoyc
 */
public interface RechargeContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initAdapter();

        void setRechargeList(List<RechargeNumInfo> rechargeNumInfos);

        void setPayType(List<PayTypeInfo> payTypeInfos);

        void weixinPay(WeiXinPayInfo payInfo);

        void aliPay(AlipayInfo alipayInfo);

        void payFailured();

        void paySuccess();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        void getRechargeList();

        void getPayType();

        void getWeiXinPayParms(String rechargeIntegralId, String payCode);

        void getAliPayParms(String rechargeIntegralId, String payCode);

    }

}
