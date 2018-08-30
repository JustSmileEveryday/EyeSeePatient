package lht.wangtong.gowin120.patient.bean;

/**
 * @author luoyc
 */
public class CommentInfo {

    /**
     * picUrl : user_head/0/member_def.png
     * createdDate : 2018-03-09 11:03
     * supportCount : 0
     * isSupport : N
     * interactCommentId : 20000
     * userName : 改男
     * content : test
     */

    private String picUrl;
    private String createdDate;
    private int supportCount;
    private String isSupport;
    private int interactCommentId;
    private String userName;
    private String content;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(int supportCount) {
        this.supportCount = supportCount;
    }

    public String getIsSupport() {
        return isSupport;
    }

    public void setIsSupport(String isSupport) {
        this.isSupport = isSupport;
    }

    public int getInteractCommentId() {
        return interactCommentId;
    }

    public void setInteractCommentId(int interactCommentId) {
        this.interactCommentId = interactCommentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
