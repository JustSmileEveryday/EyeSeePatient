package lht.wangtong.gowin120.patient.bean;

/**
 * 充值金额info
 * Created by luoyc on 2017/7/6.
 */

public class RechargeNumInfo {

    private String rechargeIntegralId;
    private String money;
    private String integral;
    private boolean isChoose = false;

    public String getRechargeIntegralId() {
        return rechargeIntegralId;
    }

    public void setRechargeIntegralId(String rechargeIntegralId) {
        this.rechargeIntegralId = rechargeIntegralId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
