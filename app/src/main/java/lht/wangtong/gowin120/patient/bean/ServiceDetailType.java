package lht.wangtong.gowin120.patient.bean;

/**
 * @author luoyc
 */
public class ServiceDetailType {

    private String reservationTypeId;
    private String name;
    private String type;
    private String typeName;
    private String status;
    private String introduce;
    private boolean isChoose = false;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getReservationTypeId() {
        return reservationTypeId;
    }

    public void setReservationTypeId(String reservationTypeId) {
        this.reservationTypeId = reservationTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
