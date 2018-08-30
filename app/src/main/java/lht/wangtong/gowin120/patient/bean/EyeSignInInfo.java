package lht.wangtong.gowin120.patient.bean;

/**
 * 眼保健操打卡列表
 * @author luoyc
 * @date 2018/3/21
 */

public class EyeSignInInfo {
    private String date;
    private String amTime;
    private String pmTime;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmTime() {
        return amTime;
    }

    public void setAmTime(String amTime) {
        this.amTime = amTime;
    }

    public String getPmTime() {
        return pmTime;
    }

    public void setPmTime(String pmTime) {
        this.pmTime = pmTime;
    }
}
