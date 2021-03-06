package lht.wangtong.gowin120.patient.apppay.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付宝订单信息工具类
 * Created by luoyc on 2017/5/4.
 */

public class OrderInfoUtil {

    /**
     * 构造支付订单参数信息
     *
     * @param map
     * 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, false));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, false));

        return sb.toString();
    }

    /**
     * 构造支付订单参数列表
     * @param info  当前支付信息参数
     * @return
     */
    public static Map<String, String> buildOrderParamMap(AlipayInfo info) {
        Map<String, String> keyValues = new HashMap<String, String>();

        keyValues.put("app_id", info.getApp_id());

        keyValues.put("biz_content", info.getBiz_content());

        keyValues.put("charset", info.getCharset());

        keyValues.put("method", info.getMethod());

        keyValues.put("notify_url", info.getNotify_url());

        keyValues.put("sign_type", info.getSign_type());

        keyValues.put("timestamp", info.getTimestamp());

        keyValues.put("version", info.getVersion());

        return keyValues;
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }
}
