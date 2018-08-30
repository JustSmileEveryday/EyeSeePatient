package lht.wangtong.gowin120.patient.bean;

/**
 * 首页banner
 * Created by  luoyc on 2018/3/5.
 */

public class BannerInfo {
    private String title;
    private String imgUrl;
    private int dispOrder;
    private int prodType;
    private String prodId;
    private int articleType;

    public int getProdType() {
        return prodType;
    }

    public void setProdType(int prodType) {
        this.prodType = prodType;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public int getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(int dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
