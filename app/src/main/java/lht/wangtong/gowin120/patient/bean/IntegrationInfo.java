package lht.wangtong.gowin120.patient.bean;

/**
 * 积分列表
 *
 * @author luoyc
 */
public class IntegrationInfo {

    private String memberPointIoId;
    private String bonusUsableNew;
    private String createdDate;
    private long bonus;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMemberPointIoId() {
        return memberPointIoId;
    }

    public void setMemberPointIoId(String memberPointIoId) {
        this.memberPointIoId = memberPointIoId;
    }

    public String getBonusUsableNew() {
        return bonusUsableNew;
    }

    public void setBonusUsableNew(String bonusUsableNew) {
        this.bonusUsableNew = bonusUsableNew;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public long getBonus() {
        return bonus;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
    }
}
