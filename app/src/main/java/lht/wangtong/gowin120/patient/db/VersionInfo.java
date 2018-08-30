package lht.wangtong.gowin120.patient.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by sanmu on 2016/7/28 0028.
 */
@Table(name = "versionInfo")
public class VersionInfo {
    @Column(name = "id", isId = true,autoGen = false)
    private Long id;
    @Column(name = "dataVersion")
    private String dataVersion;
    @Column(name = "appDescribe")
    private String appDescribe;
    @Column(name = "name")
    private String name;
    @Column(name = "loadPath")
    private String loadPath;
    @Column(name = "appType")
    private String appType;
    @Column(name = "versionNumber")
    private String versionNumber;
    @Column(name = "versionCode")
    private String versionCode;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getAppDescribe() {
        return appDescribe;
    }

    public void setAppDescribe(String appDescribe) {
        this.appDescribe = appDescribe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoadPath() {
        return loadPath;
    }

    public void setLoadPath(String loadPath) {
        this.loadPath = loadPath;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
}
