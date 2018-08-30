package lht.wangtong.gowin120.patient.bean;

import java.util.List;

/**
 * @author 小艾课堂
 */
public class CourseInfo {

    /**
     * articleId : 1820
     * bigImage : focusdir/20170222/1487753268525-101334823.jpg
     * catalogName : 近视眼
     * clickCount : 0
     * commentCount : 0
     * commentList : []
     * content :
     * createdDate :
     * isCollect : N
     * isSupport : N
     * keyWord :
     * shareCount : 2
     * smallImage : focusdir/20170222/1487753268560--736896140.jpg
     * summer : 揭秘近视眼手术后遗症
     * supportCount : 0
     * title : 近视眼手术
     */

    private String articleId;
    private String bigImage;
    private String catalogName;
    private String clickCount;
    private String commentCount;
    private String content;
    private String createdDate;
    private String isCollect;
    private String isSupport;
    private String keyWord;
    private String shareCount;
    private String smallImage;
    private String summer;
    private int supportCount;
    private String title;
    private String clickUrl;
    private List<?> commentList;
    private String playTime;
    private boolean isMore = false;
    private boolean isOne = false;

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getIsSupport() {
        return isSupport;
    }

    public void setIsSupport(String isSupport) {
        this.isSupport = isSupport;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getSummer() {
        return summer;
    }

    public void setSummer(String summer) {
        this.summer = summer;
    }

    public int getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(int supportCount) {
        this.supportCount = supportCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<?> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<?> commentList) {
        this.commentList = commentList;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public boolean isOne() {
        return isOne;
    }

    public void setOne(boolean one) {
        isOne = one;
    }
}
