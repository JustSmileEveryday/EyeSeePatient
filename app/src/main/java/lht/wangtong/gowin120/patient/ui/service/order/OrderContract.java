package lht.wangtong.gowin120.patient.ui.service.order;

import java.util.List;

import lht.wangtong.gowin120.patient.apppay.alipay.AlipayInfo;
import lht.wangtong.gowin120.patient.apppay.weixinpay.WeiXinPayInfo;
import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.PayTypeInfo;
import lht.wangtong.gowin120.patient.bean.ServiceRecordInfo;

public interface OrderContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initItemClick();

        void setPayType(List<PayTypeInfo> payTypeInfos);

        void setServiceInfo(ServiceRecordInfo serviceInfo);

        void payService();

        void weixinPay(WeiXinPayInfo payInfo);

        void aliPay(AlipayInfo alipayInfo);

        void payFailured();

        void paySuccess();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData(String serviceRecordId);

        void loadService(String serviceRecordId);

        void loadPayType();

        void balancePay(String serviceRecordId);

        void getWeiXinPayParms(String serviceRecordId, String payCode);

        void getAliPayParms(String serviceRecordId, String payCode);

    }

}
