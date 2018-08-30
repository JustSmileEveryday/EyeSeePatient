package lht.wangtong.gowin120.patient.bean;

import java.util.List;

/**
 * 优惠券info
 *
 * @author luoyc
 */
public class CouponInfo {


    /**
     * couponList :
     * [{"canUseDateEnd":"2018-09-08","faceValue":5,"canUseTotal":99,"canUseDateStart":"2018-08-08","couponId":1,"applyTypeText":"适用于全部服务项目"},{"canUseDateEnd":"2018-09-08","faceValue":10,"canUseTotal":199,"canUseDateStart":"2018-08-08","couponId":2,"applyTypeText":"适用于全部服务项目"},{"canUseDateEnd":"2018-09-08","faceValue":10,"canUseTotal":null,"canUseDateStart":"2018-08-08","couponId":3,"applyTypeText":"适用于眼部雾化"}]
     * count : 3
     */

    private int count;
    private List<CouponListBean> couponList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CouponListBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponListBean> couponList) {
        this.couponList = couponList;
    }

    public static class CouponListBean {
        /**
         * canUseDateEnd : 2018-09-08
         * faceValue : 5
         * canUseTotal : 99
         * canUseDateStart : 2018-08-08
         * couponId : 1
         * applyTypeText : 适用于全部服务项目
         */

        private String canUseDateEnd;
        private int faceValue;
        private int canUseTotal;
        private String canUseDateStart;
        private int couponId;
        private String applyTypeText;
        //是否展示领取
        private boolean isShow = false;

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public String getCanUseDateEnd() {
            return canUseDateEnd;
        }

        public void setCanUseDateEnd(String canUseDateEnd) {
            this.canUseDateEnd = canUseDateEnd;
        }

        public int getFaceValue() {
            return faceValue;
        }

        public void setFaceValue(int faceValue) {
            this.faceValue = faceValue;
        }

        public int getCanUseTotal() {
            return canUseTotal;
        }

        public void setCanUseTotal(int canUseTotal) {
            this.canUseTotal = canUseTotal;
        }

        public String getCanUseDateStart() {
            return canUseDateStart;
        }

        public void setCanUseDateStart(String canUseDateStart) {
            this.canUseDateStart = canUseDateStart;
        }

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public String getApplyTypeText() {
            return applyTypeText;
        }

        public void setApplyTypeText(String applyTypeText) {
            this.applyTypeText = applyTypeText;
        }
    }
}
