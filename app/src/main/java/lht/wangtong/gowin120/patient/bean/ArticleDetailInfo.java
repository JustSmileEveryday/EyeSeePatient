package lht.wangtong.gowin120.patient.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * 文章详情类
 *
 * @author luoyc
 * @date 2017/2/25
 */

public class ArticleDetailInfo implements Parcelable {


    /**
     * articleId : 1720
     * bigImage :
     * clickCount : 0
     * commentCount : 0
     * commentList : []
     * content :
     * createdDate : 2015-11-10
     * isCollect : N
     * isSupport : N
     * smallImage :
     * summer : 首届（2015）四川“互联网+健康服务”创新创业大会国卫云医风采依旧
     * supportCount : 0
     * title : wxts1
     */

    private String articleId;
    private String bigImage;
    private int clickCount;
    private int commentCount;
    private String content;
    private String createdDate;
    private String isCollect;
    private String isSupport;
    private String smallImage;
    private String summer;
    private int supportCount;
    private String title;
    private String shareImg;
    private int favorId = 0;
    private String clickUrl;
    private String playTime;

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public int getFavorId() {
        return favorId;
    }

    public void setFavorId(int favorId) {
        this.favorId = favorId;
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

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
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

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.articleId);
        dest.writeString(this.bigImage);
        dest.writeInt(this.clickCount);
        dest.writeInt(this.commentCount);
        dest.writeString(this.content);
        dest.writeString(this.createdDate);
        dest.writeString(this.isCollect);
        dest.writeString(this.isSupport);
        dest.writeString(this.smallImage);
        dest.writeString(this.summer);
        dest.writeInt(this.supportCount);
        dest.writeString(this.title);
        dest.writeString(this.shareImg);
        dest.writeInt(this.favorId);
        dest.writeString(this.clickUrl);
        dest.writeString(this.playTime);
    }

    public ArticleDetailInfo() {
    }

    protected ArticleDetailInfo(Parcel in) {
        this.articleId = in.readString();
        this.bigImage = in.readString();
        this.clickCount = in.readInt();
        this.commentCount = in.readInt();
        this.content = in.readString();
        this.createdDate = in.readString();
        this.isCollect = in.readString();
        this.isSupport = in.readString();
        this.smallImage = in.readString();
        this.summer = in.readString();
        this.supportCount = in.readInt();
        this.title = in.readString();
        this.shareImg = in.readString();
        this.favorId = in.readInt();
        this.clickUrl = in.readString();
        this.playTime = in.readString();
    }

    public static final Creator<ArticleDetailInfo> CREATOR = new Creator<ArticleDetailInfo>() {
        @Override
        public ArticleDetailInfo createFromParcel(Parcel source) {
            return new ArticleDetailInfo(source);
        }

        @Override
        public ArticleDetailInfo[] newArray(int size) {
            return new ArticleDetailInfo[size];
        }
    };
}
