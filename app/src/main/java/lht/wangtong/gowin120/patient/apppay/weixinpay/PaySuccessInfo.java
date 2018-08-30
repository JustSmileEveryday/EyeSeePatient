package lht.wangtong.gowin120.patient.apppay.weixinpay;

/**
 * 支付成功
 * Created by luoyc on 2017/5/18.
 */

public class PaySuccessInfo {
    private boolean isSuccess;

    public PaySuccessInfo(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
