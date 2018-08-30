package lht.wangtong.gowin120.patient.bean;

/**
 * 返回参数体
 * Created by luoyc on 2018/1/19.
 */

public class DataResponse<T> {
    private int resultCode;
    private String appId;
    private String accessFlag;
    private String resultDesc;
    private T responseStr;

    public int getErrorCode() {
        return resultCode;
    }

    public void setErrorCode(int errorCode) {
        this.resultCode = errorCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(String accessFlag) {
        this.accessFlag = accessFlag;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public T getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(T responseStr) {
        this.responseStr = responseStr;
    }
}
