package lht.wangtong.gowin120.patient.bean;

/**
 * 余额info
 * Created by luoyc on 2017/7/10.
 */

public class BalanceInfo {

    /**
     * cashUsableNew : 1
     * memberAccountIoId : 1050
     * cash : -25
     * createdDate : 2017-07-06 16:50
     */

    private String cashUsableNew;
    private String memberAccountIoId;
    private float cash;
    private String createdDate;

    public String getCashUsableNew() {
        return cashUsableNew;
    }

    public void setCashUsableNew(String cashUsableNew) {
        this.cashUsableNew = cashUsableNew;
    }

    public String getMemberAccountIoId() {
        return memberAccountIoId;
    }

    public void setMemberAccountIoId(String memberAccountIoId) {
        this.memberAccountIoId = memberAccountIoId;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
