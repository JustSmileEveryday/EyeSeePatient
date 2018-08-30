package lht.wangtong.gowin120.patient.bean;

/**
 *
 * @author luoyc
 * @date 2018/3/14
 */

public class AppointmentInfo {


    /**
     * reservationServiceDate : 2018-03-15 14:00
     * serviceTypeName : 睑板腺按摩
     * reservationServiceId : 1
     * status : 1
     */

    private String reservationServiceDate;
    private String serviceTypeName;
    private String reservationServiceId;
    private int status;

    public String getReservationServiceDate() {
        return reservationServiceDate;
    }

    public void setReservationServiceDate(String reservationServiceDate) {
        this.reservationServiceDate = reservationServiceDate;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getReservationServiceId() {
        return reservationServiceId;
    }

    public void setReservationServiceId(String reservationServiceId) {
        this.reservationServiceId = reservationServiceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
