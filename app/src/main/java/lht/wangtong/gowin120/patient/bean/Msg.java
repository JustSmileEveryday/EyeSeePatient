package lht.wangtong.gowin120.patient.bean;

/**
 * Created by luoyc on 2016/11/1 0001.
 */
public class Msg {
    private String msg;
    private String hosConsultationId;
    private String memberId;
    private String guahaoId;
    private String mobileKey;
    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMobileKey() {
        return mobileKey;
    }

    public void setMobileKey(String mobileKey) {
        this.mobileKey = mobileKey;
    }

    public String getGuahaoId() {
        return guahaoId;
    }

    public void setGuahaoId(String guahaoId) {
        this.guahaoId = guahaoId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHosConsultationId() {
        return hosConsultationId;
    }

    public void setHosConsultationId(String hosConsultationId) {
        this.hosConsultationId = hosConsultationId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
