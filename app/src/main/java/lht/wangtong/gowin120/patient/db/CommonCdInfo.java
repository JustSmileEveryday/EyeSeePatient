package lht.wangtong.gowin120.patient.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 *
 * @author luoyc
 * @date 2016/5/26 0026
 * 获取字典编码  info
 */
@Table(name = "commoncd")
public class CommonCdInfo {
    @Column(name = "id", isId = true, autoGen = true)
    private Long id;
    @Column(name = "applicationid")
    private String applicationId;
    @Column(name = "code")
    private String code;
    @Column(name = "nameen")
    private String nameEn;
    @Column(name = "nameja")
    private String nameJA;
    @Column(name = "nameko")
    private String nameKo;
    @Column(name = "namezh")
    private String nameZh;
    @Column(name = "parentcode")
    private String parentCode;
    private boolean isChoosed = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameJA() {
        return nameJA;
    }

    public void setNameJA(String nameJA) {
        this.nameJA = nameJA;
    }

    public String getNameKo() {
        return nameKo;
    }

    public void setNameKo(String nameKo) {
        this.nameKo = nameKo;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    @Override
    public String toString() {
        return "ChooseTextInfo{" +
                "applicationId='" + applicationId + '\'' +
                ", code='" + code + '\'' +
                ", id='" + id + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameJA='" + nameJA + '\'' +
                ", nameKo='" + nameKo + '\'' +
                ", nameZh='" + nameZh + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }
}
