package lht.wangtong.gowin120.patient.bean;

/**
 *
 * @author luoyc
 * @date 2017/7/11
 */

public class AccountIoInfo {
    private String memberAccountIoId;
    private float cash;
    private String createdDate;
    private String remark;
    private String cashUsableNew;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCashUsableNew() {
        return cashUsableNew;
    }

    public void setCashUsableNew(String cashUsableNew) {
        this.cashUsableNew = cashUsableNew;
    }
}
