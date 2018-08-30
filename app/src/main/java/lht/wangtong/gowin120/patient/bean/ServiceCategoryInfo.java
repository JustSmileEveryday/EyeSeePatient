package lht.wangtong.gowin120.patient.bean;

/**
 * 服务列表信息
 * @author luoyc
 * @date 2018/4/3
 */

public class ServiceCategoryInfo {


    /**
     * buyCount : 0
     * originalPrice : 100
     * price : 25
     * remark :
     * marketActivityId : 1081
     * title : 就医陪护
     * logoUrl : jjh.png
     */

    private int buyCount;
    private String originalPrice;
    private String price;
    private String remark;
    private int marketActivityId;
    private String title;
    private String logoUrl;

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getMarketActivityId() {
        return marketActivityId;
    }

    public void setMarketActivityId(int marketActivityId) {
        this.marketActivityId = marketActivityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
