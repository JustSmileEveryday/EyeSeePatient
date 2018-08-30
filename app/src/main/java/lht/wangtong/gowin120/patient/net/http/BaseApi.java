package lht.wangtong.gowin120.patient.net.http;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import lht.wangtong.gowin120.patient.config.Constant;
import lht.wangtong.gowin120.patient.util.LoginUtil;


/**
 *
 * @author luoyc
 * @date 2016/10/12 0012
 */
public class BaseApi {
    public String url;
    public RequestParams params;
    public TreeMap<String, Object> map = new TreeMap<>();
    public List<String> jsonKey = new ArrayList<>();
    public List<String> fileUrls = new ArrayList<>();
    public String fileTitle = "";
    public boolean isNeedNet = true;
    public boolean hasProgressbar = false;
    public int PS;
    public int PT;
    private String accessFlag;
    private boolean isToken;

    public BaseApi(String url) {
        this.url = url;
    }

    public BaseApi(String url, String accessFlag, boolean isToken) {
        this.url = url;
        this.accessFlag = accessFlag;
        this.isToken = isToken;
    }

    public BaseApi mapClear() {
        map.clear();
        fileUrls.clear();
        return this;
    }

    //添加请求常量
    private void addConstant() {
        if (accessFlag != null) {
            params.addBodyParameter("accessFlag", accessFlag);
        }
        params.addBodyParameter("appId", Constant.appId);
        params.addBodyParameter("appSecret", Constant.appSecret);
        params.addBodyParameter("operaSystem", Constant.operaSystem);
        params.addBodyParameter("operaSysType", Constant.operaSysType);
        params.addBodyParameter("dataSource", Constant.dataSource);
        params.addBodyParameter("operationMode", Constant.operationMode);
        params.addBodyParameter("operationCode", Constant.operationCode);
    }

    public BaseApi addPage(int pageSize, int pageNo) {
        if (pageSize != 0) {
            PS = pageSize;
        }
        if (pageNo != 0) {
            PT = pageNo;
        }
        return this;
    }

    public BaseApi addMap(String name, Object value) {
        map.put(name, value);
        return this;
    }

    public BaseApi addBody() {
        params = new RequestParams();
        addConstant();
        if (PS != 0) {
            params.addBodyParameter("pageSize", "" + PS);
        }
        if (PT != 0) {
            params.addBodyParameter("pageNo", "" + PT);
        }

        if (isToken) {
            if (LoginUtil.token == null) {
                LoginUtil.getToken();
            }
            if (LoginUtil.token != null) {
                params.addBodyParameter("accessToken", LoginUtil.token.getAccessToken());
            }
        }
        return this;
    }

    public BaseApi addJsonKey(String... key) {
        for (String str : key) {
            jsonKey.add(str);
        }
        return this;
    }

    public BaseApi setFileUrls(List<String> fileUrls) {
        this.fileUrls.addAll(fileUrls);
        return this;
    }

    public BaseApi setFileUrl(String fileUrl) {
        this.fileUrls.add(fileUrl);
        return this;
    }

    public BaseApi setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
        return this;
    }

    public BaseApi isNeedNet(boolean b) {
        this.isNeedNet = b;
        return this;
    }

    public BaseApi hasProgressbar(boolean b) {
        this.hasProgressbar = b;
        return this;
    }
}
