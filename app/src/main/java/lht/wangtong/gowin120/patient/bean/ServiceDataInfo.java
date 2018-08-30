package lht.wangtong.gowin120.patient.bean;

/**
 * 预约服务数据
 *
 * @author luoyc
 * @date 2018/3/23
 */

public class ServiceDataInfo {
    private String reservationServiceDate;
    private String agentName;
    private String day;

    public String getReservationServiceDate() {
        return reservationServiceDate;
    }

    public void setReservationServiceDate(String reservationServiceDate) {
        this.reservationServiceDate = reservationServiceDate;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
