package lht.wangtong.gowin120.patient.bean;

/**
 * @author luoyc
 */
public class MyServiceInfo {

    /**
     * execStatus : 1
     * serviceTitle : 就医陪护
     * serviceRecordId : 1251
     * reservationDate : 2018-04-04 09:00
     * payStatus : 1
     */

    private int execStatus;
    private String serviceTitle;
    private String serviceRecordId;
    private String reservationDate;
    private String payStatus;
    private int useStatus;
    private int residueCount;
    private String createdDate;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(int execStatus) {
        this.execStatus = execStatus;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(String serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public int getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(int useStatus) {
        this.useStatus = useStatus;
    }

    public int getResidueCount() {
        return residueCount;
    }

    public void setResidueCount(int residueCount) {
        this.residueCount = residueCount;
    }
}
