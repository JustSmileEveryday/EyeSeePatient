package lht.wangtong.gowin120.patient.ui.mine.coupons;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.CouponInfo;

/**
 * 我的优惠券
 *
 * @author luoyc
 */
public interface MyCouponsContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void setIsHaveCoupon(int count);

        void setMyCouponList(CouponInfo couponInfo);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getMyCouponList();

        void getCanGetCouponList();

    }

}
