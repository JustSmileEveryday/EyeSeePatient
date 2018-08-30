package lht.wangtong.gowin120.patient.bean;

/**
 * 最新眼健康数据
 *
 * @author luoyc
 * @date 2018/3/23
 */

public class LatestEyeData {


    /**
     * yysl : 3.4
     * zysl : 3.3
     * sfsg : null
     * sfhb : null
     * smgl : 60%
     * testDate : 2018-03-13 18:02
     */

    private String yysl;
    private String zysl;
    private String sfsg;
    private String sfhb;
    private String smgl;
    private String testDate;

    public String getYysl() {
        return yysl;
    }

    public void setYysl(String yysl) {
        this.yysl = yysl;
    }

    public String getZysl() {
        return zysl;
    }

    public void setZysl(String zysl) {
        this.zysl = zysl;
    }

    public String getSfsg() {
        return sfsg;
    }

    public void setSfsg(String sfsg) {
        this.sfsg = sfsg;
    }

    public String getSfhb() {
        return sfhb;
    }

    public void setSfhb(String sfhb) {
        this.sfhb = sfhb;
    }

    public String getSmgl() {
        return smgl;
    }

    public void setSmgl(String smgl) {
        this.smgl = smgl;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }
}
