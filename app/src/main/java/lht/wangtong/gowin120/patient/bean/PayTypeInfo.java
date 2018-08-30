package lht.wangtong.gowin120.patient.bean;

/**
 * 支付类型
 * Created by luoyc on 2017/5/2.
 */

public class PayTypeInfo {

    /**
     * logo : null
     * name : 支付宝APP
     * code : ZFBAPP
     */

    private String logo;
    private String name;
    private String code;
    private int typeFlag;
    private boolean isChecked = false;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(int typeFlag) {
        this.typeFlag = typeFlag;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
