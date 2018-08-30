package lht.wangtong.gowin120.patient.bean;

/**
 * @author luoyc
 */
public class DoctorInteractComment {


    /**
     * date : 2018-04-24
     * picUrl : user_head/0/member_def.png
     * scores : null
     * memberName : 改男
     * content : test
     */

    private String date;
    private String picUrl;
    private Object scores;
    private String memberName;
    private String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Object getScores() {
        return scores;
    }

    public void setScores(Object scores) {
        this.scores = scores;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
