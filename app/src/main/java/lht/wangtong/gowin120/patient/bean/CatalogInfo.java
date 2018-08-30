package lht.wangtong.gowin120.patient.bean;

/**
 * 健康资讯目录
 * Created by luoyc on 2017/2/24.
 */

public class CatalogInfo {


    /**
     * catalogId : 1741
     * code : BNZ
     * description : 白内障
     * name : 白内障
     */

    private String catalogId;
    private String code;
    private String description;
    private String name;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
