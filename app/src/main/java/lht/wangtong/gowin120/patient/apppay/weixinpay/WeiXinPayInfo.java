package lht.wangtong.gowin120.patient.apppay.weixinpay;

/**
 * 微信支付参数info
 * Created by luoyc on 2017/5/10.
 */

public class WeiXinPayInfo {


    /**
     * timestamp : 1494321685
     * sign : AD967D2D3298923AC8DBEDAB356C4E13
     * partnerid : 1458854302
     * noncestr : g8Yahw1u4Szjlw8c
     * prepayid : wx20170509172116fb9a9b4e990045052060
     * package : Sign=WXPay
     * appid : wx5d7f760ac6136336
     */

    private String timestamp;
    private String sign;
    private String partnerid;
    private String noncestr;
    private String prepayid;
    private String packageValue;
    private String appid;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public String toString() {
        return "WeiXinPayInfo{" +
                "timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", packageValue='" + packageValue + '\'' +
                ", appid='" + appid + '\'' +
                '}';
    }
}
