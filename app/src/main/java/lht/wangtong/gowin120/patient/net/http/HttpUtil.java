package lht.wangtong.gowin120.patient.net.http;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

import lht.wangtong.gowin120.patient.util.LoginUtil;


/**
 * @author luoyc
 * @date 2016/10/11 0011
 */
public class HttpUtil {
    //获取object
    public static <T> T getObject(final BaseApi api, final Class<T> mClass, final objectCallback callback) {
        callback.setBase(api, mClass);
        RequestParams entity = HttpUitls.creatUrlRequest(api);
        entity = addBodys(entity, api);
        x.http().post(entity, callback);
        return null;
    }

    //获取list
    public static <T> List<T> getObjectList(BaseApi api, final Class<T> mClass, final objectListCallback callback) {
        callback.setBase(api, mClass);
        RequestParams entity = HttpUitls.creatUrlRequest(api);
        entity = addBodys(entity, api);
        x.http().post(entity, callback);
        return null;
    }


    //获取list  分页加载
    public static <T> List<T> getObjectListPage(BaseApi api, final Class<T> mClass, final objectListPageCallback callback) {
        callback.setBase(api, mClass, api.PS);
        RequestParams entity = HttpUitls.creatUrlRequest(api);
        entity = addBodys(entity, api);
        x.http().post(entity, callback);
        return null;
    }

    //上传文件
    public static <T> T upLoad(BaseApi api, final Class<T> mClass, final objectCallback callback) {
        callback.setBase(api, mClass);
        RequestParams entity = HttpUitls.creatUrlRequest(api);
        entity = addBodys(entity, api);
        entity.setMultipart(true);
        if (api.fileUrls.size() > 0) {
            for (String url : api.fileUrls) {
                if (!TextUtils.isEmpty(url)) {
                    File file = new File(url);
                    entity.addBodyParameter(api.fileTitle, file);
                }
            }
        }
        x.http().post(entity, callback);
        return null;
    }

    //添加post参数
    private static RequestParams addBodys(RequestParams entity, BaseApi api) {
        String bodys;
        if (api.map.size() == 0) {
            bodys = "{}";
        } else {
            bodys = JSON.toJSON(api.map).toString();
        }
        entity.addBodyParameter("requestStr", bodys);
        return entity;
    }


    //请求返回code处理
    private static boolean handCode(String code, String resultDesc) {
        if (!TextUtils.equals(code, "000")) {
            if (!TextUtils.isEmpty(resultDesc)) {
                ToastUtils.showShort(resultDesc);
            }
        }
        if ("000".equals(code)) {
            return true; //成功
        } else if ("007".equals(code)) {
            LoginUtil.loginRe();
            return false;
        } else if ("106".equals(code)) {
            LoginUtil.loginRe();
            return false;
        } else {
            return false;
        }
    }

    //-------------返回回调
    public abstract static class objectCallback extends HttpCallback {
        @Override
        public void onSuccess(String result) {
//            Log.e("luoyc", "result + " + result);
            JSONObject jsonResult = JSON.parseObject(result);
            String code = jsonResult.getString("resultCode");
            String resultDesc = jsonResult.getString("resultDesc");
            if (!handCode(code, resultDesc)) {
                result(false, null);
            } else {
                int total = 0;
                if (jsonResult.toString().contains("totalRecord")) {
                    total = jsonResult.getInteger("totalRecord");
                }
                for (int i = 0; i < api.jsonKey.size(); i++) {
                    if (i == (api.jsonKey.size() - 1)) {
                        try {
                            Object obj = JSON.parseObject(jsonResult.getString(api.jsonKey.get(i)), mClass);
                            result(true, obj);
                            result(true, obj, total);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        jsonResult = JSON.parseObject(jsonResult.getString(api.jsonKey.get(i)));
                    }
                }
            }

        }
    }

    public abstract static class objectListCallback extends HttpCallback {
        @Override
        public void onSuccess(String result) {
//            Log.e("luoyc", "result + " + result);
            JSONObject jsonResult = JSON.parseObject(result);
            String code = jsonResult.getString("resultCode");
            String resultDesc = jsonResult.getString("resultDesc");
            if (!handCode(code, resultDesc)) {
                result(false, null);
                return;
            }
            for (int i = 0; i < api.jsonKey.size(); i++) {
                if (i == api.jsonKey.size() - 1) {
                    Object obj = JSON.parseArray(jsonResult.getString(api.jsonKey.get(i)), mClass);
                    result(true, obj);
                } else {
                    jsonResult = JSON.parseObject(jsonResult.getString(api.jsonKey.get(i)));
                }
            }
        }
    }

    public abstract static class objectListPageCallback extends HttpCallback {
        @Override
        public void onSuccess(String result) {
//            Log.e("luoyc", "result + " + result);
            JSONObject jsonResult = JSON.parseObject(result);
            String code = jsonResult.getString("resultCode");
            String resultDesc = jsonResult.getString("resultDesc");
            if (!handCode(code, resultDesc)) {
                result(false, null);
                return;
            }
            int total = jsonResult.getInteger("totalRecord");
            for (int i = 0; i < api.jsonKey.size(); i++) {
                if (i == api.jsonKey.size() - 1) {
                    Object obj = JSON.parseArray(jsonResult.getString(api.jsonKey.get(i)), mClass);
                    resultPage(true, obj, total);
                } else {
                    jsonResult = JSON.parseObject(jsonResult.getString(api.jsonKey.get(i)));
                }
            }
        }
    }
}
