package lht.wangtong.gowin120.patient.bean;

/**
 * 服务详情
 *
 * @author luoyc
 */
public class ServiceDetailInfo {


    /**
     * noticeForUse : 使用须知使用须知使用须知使用须知使用须知使用须知
     * originalPrice : 100
     * price : 25
     * bannerUrl : banner路径，banner路径，banner路径，banner路径
     * remark :
     * marketActivityId : 1081
     * title : 就医陪护
     * content : 1、专车接送服务。rn2、提前挂号，及时就诊。rn3、全程陪同服务。
     */

    private String noticeForUse;
    private String originalPrice;
    private String price;
    private String bannerUrl;
    private String remark;
    private int marketActivityId;
    private String title;
    private String content;
    private String effectiveCount;
    private String serviceAgentName;

    public String getNoticeForUse() {
        return noticeForUse;
    }

    public void setNoticeForUse(String noticeForUse) {
        this.noticeForUse = noticeForUse;
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

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
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

    public String getEffectiveCount() {
        return effectiveCount;
    }

    public void setEffectiveCount(String effectiveCount) {
        this.effectiveCount = effectiveCount;
    }

    public String getServiceAgentName() {
        return serviceAgentName;
    }

    public void setServiceAgentName(String serviceAgentName) {
        this.serviceAgentName = serviceAgentName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ServiceDetailInfo{" +
                "noticeForUse='" + noticeForUse + '\'' +
                ", originalPrice=" + originalPrice +
                ", price=" + price +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", remark='" + remark + '\'' +
                ", marketActivityId=" + marketActivityId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
