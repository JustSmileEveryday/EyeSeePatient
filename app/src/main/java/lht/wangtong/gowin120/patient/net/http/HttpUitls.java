package lht.wangtong.gowin120.patient.net.http;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.BodyItemWrapper;

import java.util.List;
import java.util.concurrent.Executor;


/**
 *
 * Created by luoyc on 2016/10/12 0012.
 * 这里没有写完，有空了加一个线程池管理所有请求
 */
public class HttpUitls {


    public static RequestParams creatUrlRequest(BaseApi api) {
        RequestParams xParams = null;
        xParams = new RequestParams(api.url);
        xParams.setConnectTimeout(1000 * 10);
        xParams.setCharset(api.params.getCharset());
        xParams.setMultipart(api.params.isMultipart());
        xParams.setAsJsonContent(api.params.isAsJsonContent());
        String cacheDirName = api.params.getCacheDirName();
        if (cacheDirName != null) {
            xParams.setCacheDirName(cacheDirName);
        }
        long cacheSize = api.params.getCacheSize();
        if (cacheSize != 0) {
            xParams.setCacheSize(cacheSize);
        }
        long cacheMaxAge = api.params.getCacheMaxAge();
        if (cacheMaxAge != 0) {
            xParams.setCacheMaxAge(cacheMaxAge);
        }
        Executor executor = api.params.getExecutor();
        if (executor != null) {
            xParams.setExecutor(executor);
        }
        xParams.setConnectTimeout(api.params.getConnectTimeout());
        xParams.setAutoResume(api.params.isAutoResume());
        xParams.setAutoRename(api.params.isAutoRename());
        xParams.setMaxRetryCount(api.params.getMaxRetryCount());
        xParams.setSaveFilePath(api.params.getSaveFilePath());
        xParams.setCancelFast(api.params.isCancelFast());
        xParams.setLoadingUpdateMaxTimeSpan(api.params.getLoadingUpdateMaxTimeSpan());
        List<RequestParams.Header> headers = api.params.getHeaders();
        for (RequestParams.Header header : headers) {
            xParams.addHeader(header.key, (String) header.value);
        }
        List<KeyValue> queryStringParams = api.params.getQueryStringParams();
        for (KeyValue keyValue : queryStringParams) {
            xParams.addQueryStringParameter(keyValue.key, (String) keyValue.value);
        }
        List<KeyValue> bodyParams = api.params.getBodyParams();
        for (KeyValue keyValue : bodyParams) {
            xParams.addBodyParameter(keyValue.key, (String) keyValue.value);
        }
        List<KeyValue> fileParams = api.params.getFileParams();
        for (KeyValue keyValue : fileParams) {
            Object value = keyValue.value;
            String key = keyValue.key;
            if (value instanceof BodyItemWrapper) {
                BodyItemWrapper bodyItemWrapper = ((BodyItemWrapper) value);
                xParams.addBodyParameter(key, bodyItemWrapper.getValue(), bodyItemWrapper.getContentType(), bodyItemWrapper.getFileName());
            } else {
                xParams.addBodyParameter(key, value, null);
            }
        }
        return xParams;
    }


}
