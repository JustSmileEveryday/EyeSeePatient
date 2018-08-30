package lht.wangtong.gowin120.patient.bean;

/**
 * 护眼计划列表
 * @author Administrator
 * @date 2018/3/21
 */

public class PlanScheduleInfo {

    private String planScheduleId;
    private String name;
    private String status;

    public String getPlanScheduleId() {
        return planScheduleId;
    }

    public void setPlanScheduleId(String planScheduleId) {
        this.planScheduleId = planScheduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
