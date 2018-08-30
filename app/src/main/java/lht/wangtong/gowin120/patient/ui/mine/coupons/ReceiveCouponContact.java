package lht.wangtong.gowin120.patient.ui.mine.coupons;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.CouponInfo;

/**
 * 领取优惠券
 *
 * @author luoyc
 */
public interface ReceiveCouponContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setCanGetCoupon(CouponInfo canGetCoupon);

        void removeItem(int position);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getCanGetCouponList();

        void getCoupon(String couponId,int position);

    }

}
