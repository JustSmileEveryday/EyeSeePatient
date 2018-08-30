package lht.wangtong.gowin120.patient.apppay.alipay;

/**
 * 支付宝上传所用参数
 * Created by luoyc on 2017/5/4.
 */

public class AlipayInfo {
    /**
     * timestamp : 2017-05-03+11%3A29%3A10
     * sign : DZMgV90jX%2BIrnMZ2rFohJvhXDSDEO%2FOAmrZylKgjKGjWjVm%2BQ%2B8aJuIfid9loBaJKImTZXtGxXzuj%2FgBJbDZVuDBVt6C7DD%2BsJcfRegp%2FMBcFNIaYMvnvt%2B4HJppSYsTXN9Omcxdp3LaYrVyba8wPenUvP%2FxxNo7uXNpOCa4h2Y%3D
     * biz_content : %7B%22total_amount%22%3A%2225.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22FW1704270003%22%2C%22out_trade_no%22%3A%22FW-170427-0003-3753%22%7D
     * sign_type : RSA2
     * notify_url : http%3A%2F%2Fappmi.gowin120.com%2FbackPayment%2Fnotify%2FFW-170427-0003-3753%2Fs2s%2Fresp.do
     * charset : UTF-8
     * method : alipay.trade.app.pay
     * app_id : 2088021263504557
     * version : 1.0
     */

    private String timestamp;
    private String sign;
    private String biz_content;
    private String sign_type;
    private String notify_url;
    private String charset;
    private String method;
    private String app_id;
    private String version;

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

    public String getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(String biz_content) {
        this.biz_content = biz_content;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AlipayInfo{" +
                "timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                ", biz_content='" + biz_content + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", charset='" + charset + '\'' +
                ", method='" + method + '\'' +
                ", app_id='" + app_id + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
